package main.java.models.LabApp;

import main.java.exceptions.*;
import main.java.models.API.InsuranceAPI;
import main.java.models.Experiment.ExperimentInfo;
import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;
import main.java.models.User.Patient;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LabApp {
    private static LabApp instance;
    private Patient currentPatient;
    private Storage storage;
    private ObjectsInitializer objectsInitializer;

    public static LabApp getInstance() throws InvalidObjectException {
        if (instance == null) {
            instance = new LabApp();
        }
        return instance;
    }

    private LabApp() throws InvalidObjectException {
        storage = Storage.getInstance();
        objectsInitializer = new ObjectsInitializer();
        objectsInitializer.initialize();
    }

    public void loginPatient(int patientId, String password) throws PatientNotFoundException {
        Patient patient = storage.getPatientRepository().find(patientId);
        if (patient.hasPassword(password)) {
            currentPatient = patient;
        } else {
            throw new PatientNotFoundException(patientId);
        }
    }

    public void checkPatientLogin() throws PatientNotLoginException {
        if (currentPatient == null)
            throw new PatientNotLoginException();
    }

    public void createNewExperiment() throws PatientNotLoginException, InvalidObjectException {
        checkPatientLogin();
        currentPatient.createNewExperimentRecord();
    }

    public List<ExperimentInfo> getExperimentInfos() throws PatientNotLoginException {
        checkPatientLogin();
        return storage.getExperimentInfoRepository().getRecords();
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) throws PatientNotLoginException,
            CurrentExperimentNotInstantiatedException, InvalidObjectException {
        createNewExperiment();
        currentPatient.setExperimentInfos(experimentInfos);
    }

    public List<Lab> getExperimentLabs() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        checkPatientLogin();

        List<ExperimentInfo> experimentInfos = currentPatient.getExperimentInfos();
        List<Lab> experimentsLabs = new ArrayList<>();

        for (Lab lab : storage.getLabRepository().getRecords()) {
            if (lab.hasSupport(experimentInfos)) {
                experimentsLabs.add(lab);
            }
        }

        return experimentsLabs;
    }

    public void setExperimentLab(Lab lab) throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        checkPatientLogin();
        currentPatient.setExperimentLab(lab);
    }

    public List<Date> getExperimentTimes() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException, NoLabAssignedException {
        checkPatientLogin();
        return currentPatient.getExperimentTimes();
    }

    public void setExperimentTime(Date experimentTime) throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        checkPatientLogin();
        currentPatient.setExperimentTime(experimentTime);
    }

    public void setExperimentInsurance(int insuranceNumber) throws InvalidInsuranceNumberException, PatientNotLoginException,
            CurrentExperimentNotInstantiatedException {
        checkPatientLogin();
        validateInsuranceNumber(insuranceNumber);
        currentPatient.setExperimentInsuranceNumber(insuranceNumber);
    }

    private void validateInsuranceNumber(int insuranceNumber) throws InvalidInsuranceNumberException {
        if (!InsuranceAPI.getInstance().isValidInsuranceNumber(insuranceNumber))
            throw new InvalidInsuranceNumberException();
    }

    public double getExperimentTotalPrice() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        checkPatientLogin();
        return currentPatient.getExperimentTotalPrice();
    }

    public void payExperimentTotalPrice(String bankSessionId) throws PatientNotLoginException, CurrentExperimentNotInstantiatedException,
            UnsuccessfulPaymentException, SamplerNotAvailableException, SamplerNotAssignedException, NoLabAssignedException, InvalidObjectException {
        checkPatientLogin();
        currentPatient.payTotalPrice(bankSessionId);
        currentPatient.finalizeCurrentExperiment();
    }

    public void logoutPatient() {
        currentPatient = null;
    }
}
