package main.java.models.LabApp;

import main.java.exceptions.*;
import main.java.models.API.InsuranceAPI;
import main.java.models.Experiment.ExperimentInfo;
import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;
import main.java.models.User.Patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LabApp {
    private static LabApp instance;
    private Patient currentPatient;
    private int nextExperimentId;
    private Storage storage;

    public static LabApp getInstance() {
        if (instance == null) {
            instance = new LabApp();
        }
        return instance;
    }

    private LabApp() {
        nextExperimentId = 0;
        storage = Storage.getInstance();
    }

    private int getNextExperimentId() {
        return nextExperimentId++;
    }

    public void loginPatient(int patientId, String password) throws PatientNotFound {
        Patient patient = storage.getPatientContainer().find(patientId);
        if (patient.hasPassword(password)) {
            currentPatient = patient;
        } else {
            throw new PatientNotFound(patientId);
        }
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return storage.getExperimentInfoContainer().getAll();
    }

    public List<Lab> getLabsForExperiments(List<ExperimentInfo> experimentInfos) {
        List<Lab> experimentsLabs = new ArrayList<>();

        for (Lab lab : Storage.getInstance().getLabContainer().getAll()) {
            if (lab.hasSupport(experimentInfos))
                experimentsLabs.add(lab);
        }

        return experimentsLabs;
    }

    public List<Date> getTimesForExperiments(Lab lab, List<ExperimentInfo> experimentInfoDTOs) {
        return lab.getTimes(experimentInfoDTOs);
    }

    public void setExperiments(List<ExperimentInfo> experimentInfos) throws PatientNotLogin,
            CurrentExperimentNotInstantiated {
        createNewExperiment();
        currentPatient.setExperimentInfos(experimentInfos);
    }

    public void createNewExperiment() throws PatientNotLogin {
        checkPatientLogin();
        currentPatient.createNewExperiment(getNextExperimentId());
    }

    public void checkPatientLogin() throws PatientNotLogin {
        if (currentPatient == null)
            throw new PatientNotLogin();
    }

    public void setLab(Lab lab) throws PatientNotLogin, LabNotFound, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        currentPatient.setExperimentLab(lab);
    }

    public void setTime(Date experimentTime) throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        currentPatient.setExperimentTime(experimentTime);
    }

    public void setInsurance(int insuranceNumber) throws InvalidInsuranceNumber, PatientNotLogin,
            CurrentExperimentNotInstantiated {
        checkPatientLogin();
        validateInsuranceNumber(insuranceNumber);
        currentPatient.setExperimentInsuranceNumber(insuranceNumber);
    }

    private void validateInsuranceNumber(int insuranceNumber) throws InvalidInsuranceNumber {
        if (!InsuranceAPI.getInstance().isValidInsuranceNumber(insuranceNumber))
            throw new InvalidInsuranceNumber();
    }

    public double getTotalPrice() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        return currentPatient.getExperimentTotalPrice();
    }

    public void payTotalPrice(String bankSessionId) throws PatientNotLogin, CurrentExperimentNotInstantiated,
            UnsuccessfulPayment, SamplerNotAvailable, SamplerNotAssigned, NoLabAssigned {
        checkPatientLogin();
        currentPatient.payTotalPrice(bankSessionId);
        currentPatient.finalizeCurrentExperiment();
    }
}
