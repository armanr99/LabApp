package main.java.models.LabApp;

import main.java.exceptions.*;
import main.java.models.API.BankAPI;
import main.java.models.API.InsuranceAPI;
import main.java.models.Experiment.ExperimentInfo;
import main.java.models.Experiment.LabExperimentRecord;
import main.java.models.Experiment.PatientExperimentRecord;
import main.java.models.Experiment.SamplerExperimentRecord;
import main.java.models.General.Payment;
import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;
import main.java.models.User.Patient;
import main.java.models.User.Sampler;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LabApp {
    private static LabApp instance;
    private Patient currentPatient;
    private PatientExperimentRecord currentPatientExperimentRecord;
    private Storage storage;
    private BankAPI bankAPI;
    private InsuranceAPI insuranceAPI;

    public static LabApp getInstance() throws InvalidObjectException {
        if (instance == null) {
            instance = new LabApp();
        }
        return instance;
    }

    private LabApp() throws InvalidObjectException {
        storage = Storage.getInstance();
        bankAPI = BankAPI.getInstance();
        insuranceAPI = InsuranceAPI.getInstance();
        ObjectsInitializer.getInstance().initialize();
    }

    public void loginPatient(int patientId, String password) throws PatientNotFoundException {
        Patient patient = storage.getPatientRepository().find(patientId);
        if (patient.hasPassword(password)) {
            currentPatient = patient;
        } else {
            throw new PatientNotFoundException(patientId);
        }
    }

    private void checkExperimentOperationsPrerequisites() throws PatientNotLoginException,
            CurrentExperimentNotInstantiatedException {
        checkPatientLogin();
        checkExperimentInstantiated();
    }

    private void checkPatientLogin() throws PatientNotLoginException {
        if (currentPatient == null) {
            throw new PatientNotLoginException();
        }
    }

    private void checkExperimentInstantiated() throws CurrentExperimentNotInstantiatedException {
        if (currentPatientExperimentRecord == null) {
            throw new CurrentExperimentNotInstantiatedException();
        }
    }

    public List<ExperimentInfo> getExperimentInfos() throws PatientNotLoginException {
        checkPatientLogin();
        return storage.getExperimentInfoRepository().getRecords();
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) throws PatientNotLoginException,
            InvalidObjectException {
        checkPatientLogin();
        createNewExperiment();
        currentPatientExperimentRecord.setExperimentInfos(experimentInfos);
    }

    private void createNewExperiment() throws PatientNotLoginException, InvalidObjectException {
        currentPatientExperimentRecord = new PatientExperimentRecord();
        storage.getPatientExperimentRecordRepository().insert(currentPatientExperimentRecord);
    }

    public List<Lab> getExperimentLabs() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        checkExperimentOperationsPrerequisites();
        List<ExperimentInfo> experimentInfos = currentPatientExperimentRecord.getExperimentInfos();
        return getLabsWithSupport(experimentInfos);
    }

    private List<Lab> getLabsWithSupport(List<ExperimentInfo> experimentInfos) {
        List<Lab> experimentsLabs = new ArrayList<>();

        for (Lab lab : storage.getLabRepository().getRecords()) {
            if (lab.hasSupport(experimentInfos)) {
                experimentsLabs.add(lab);
            }
        }

        return experimentsLabs;
    }

    public void setExperimentLab(Lab lab) throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        checkExperimentOperationsPrerequisites();
        currentPatientExperimentRecord.setLab(lab);
    }

    public List<Date> getExperimentTimes() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException
            , NoLabAssignedException {
        checkExperimentOperationsPrerequisites();
        Lab lab = currentPatientExperimentRecord.getLab();
        List<ExperimentInfo> experimentInfos = currentPatientExperimentRecord.getExperimentInfos();
        return lab.getTimes(experimentInfos);
    }

    public void setExperimentTime(Date experimentTime) throws PatientNotLoginException,
            CurrentExperimentNotInstantiatedException {
        checkExperimentOperationsPrerequisites();
        currentPatientExperimentRecord.setTime(experimentTime);
    }

    public void setExperimentInsurance(int insuranceNumber) throws InvalidInsuranceNumberException,
            PatientNotLoginException,
            CurrentExperimentNotInstantiatedException {
        checkExperimentOperationsPrerequisites();
        validateInsuranceNumber(insuranceNumber);
        currentPatientExperimentRecord.setInsuranceNumber(insuranceNumber);
    }

    private void validateInsuranceNumber(int insuranceNumber) throws InvalidInsuranceNumberException {
        if (!insuranceAPI.isValidInsuranceNumber(insuranceNumber)) {
            throw new InvalidInsuranceNumberException();
        }
    }

    public double getExperimentTotalPrice() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        checkExperimentOperationsPrerequisites();
        double totalPrice = currentPatientExperimentRecord.getPureTotalPrice();

        if (currentPatientExperimentRecord.hasInsurance()) {
            int insuranceNumber = currentPatientExperimentRecord.getInsuranceNumber();
            totalPrice = getPriceWithInsurance(totalPrice, insuranceNumber);
        }

        return totalPrice;
    }

    public void payExperimentTotalPrice(String bankSessionId) throws PatientNotLoginException,
            CurrentExperimentNotInstantiatedException,
            UnsuccessfulPaymentException, SamplerNotAvailableException, SamplerNotAssignedException,
            NoLabAssignedException, InvalidObjectException {
        checkExperimentOperationsPrerequisites();
        payCurrentExperiment(bankSessionId);
        finalizeCurrentExperiment();
    }

    public void payCurrentExperiment(String bankSessionId) throws UnsuccessfulPaymentException,
            CurrentExperimentNotInstantiatedException, PatientNotLoginException {
        checkExperimentOperationsPrerequisites();
        double totalPrice = getExperimentTotalPrice();
        Payment payment = bankAPI.pay(bankSessionId, totalPrice);
        currentPatientExperimentRecord.setPayment(payment);
    }

    private double getPriceWithInsurance(double price, int insuranceNumber) {
        double insurancePercentage = insuranceAPI.getInsurancePercentage(insuranceNumber);
        return Double.max(0, price - (price * insurancePercentage));
    }

    private void finalizeCurrentExperiment() throws NoLabAssignedException, SamplerNotAvailableException,
            InvalidObjectException, SamplerNotAssignedException {
        Lab lab = currentPatientExperimentRecord.getLab();
        List<ExperimentInfo> experimentInfos = currentPatientExperimentRecord.getExperimentInfos();

        Sampler sampler = lab.getSampler(experimentInfos);
        currentPatientExperimentRecord.setSampler(sampler);

        informSampler(sampler);
        informLab(lab);

        currentPatient.addExperimentRecord(currentPatientExperimentRecord);
        currentPatientExperimentRecord = null;
    }

    private void informSampler(Sampler sampler) throws InvalidObjectException, NoLabAssignedException {
        SamplerExperimentRecord samplerExperimentRecord = new SamplerExperimentRecord(currentPatient,
                currentPatientExperimentRecord);
        storage.getSamplerExperimentRecordRepository().insert(samplerExperimentRecord);
        sampler.addExperimentRecord(samplerExperimentRecord);
    }

    private void informLab(Lab lab) throws InvalidObjectException, SamplerNotAssignedException {
        LabExperimentRecord labExperimentRecord = new LabExperimentRecord(currentPatient,
                currentPatientExperimentRecord);
        storage.getLabExperimentRecordRepository().insert(labExperimentRecord);
        lab.addExperimentRecord(labExperimentRecord);
    }

    public void logoutPatient() {
        currentPatient = null;
        currentPatientExperimentRecord = null;
    }
}
