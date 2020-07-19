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

    public void checkPatientLogin() throws PatientNotLogin {
        if (currentPatient == null)
            throw new PatientNotLogin();
    }

    public void createNewExperiment() throws PatientNotLogin {
        checkPatientLogin();
        currentPatient.createNewExperiment(getNextExperimentId());
    }

    public List<ExperimentInfo> getExperimentInfos() throws PatientNotLogin {
        checkPatientLogin();
        return storage.getExperimentInfoContainer().getAll();
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) throws PatientNotLogin,
            CurrentExperimentNotInstantiated {
        createNewExperiment();
        currentPatient.setExperimentInfos(experimentInfos);
    }

    public List<Lab> getExperimentLabs() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();

        List<ExperimentInfo> experimentInfos = currentPatient.getExperimentInfos();
        List<Lab> experimentsLabs = new ArrayList<>();

        for (Lab lab : Storage.getInstance().getLabContainer().getAll()) {
            if (lab.hasSupport(experimentInfos))
                experimentsLabs.add(lab);
        }

        return experimentsLabs;
    }

    public void setExperimentLab(Lab lab) throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        currentPatient.setExperimentLab(lab);
    }

    public List<Date> getExperimentTimes() throws PatientNotLogin, CurrentExperimentNotInstantiated, NoLabAssigned {
        checkPatientLogin();
        return currentPatient.getExperimentTimes();
    }

    public void setExperimentTime(Date experimentTime) throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        currentPatient.setExperimentTime(experimentTime);
    }

    public void setExperimentInsurance(int insuranceNumber) throws InvalidInsuranceNumber, PatientNotLogin,
            CurrentExperimentNotInstantiated {
        checkPatientLogin();
        validateInsuranceNumber(insuranceNumber);
        currentPatient.setExperimentInsuranceNumber(insuranceNumber);
    }

    private void validateInsuranceNumber(int insuranceNumber) throws InvalidInsuranceNumber {
        if (!InsuranceAPI.getInstance().isValidInsuranceNumber(insuranceNumber))
            throw new InvalidInsuranceNumber();
    }

    public double getExperimentTotalPrice() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        return currentPatient.getExperimentTotalPrice();
    }

    public void payExperimentTotalPrice(String bankSessionId) throws PatientNotLogin, CurrentExperimentNotInstantiated,
            UnsuccessfulPayment, SamplerNotAvailable, SamplerNotAssigned, NoLabAssigned {
        checkPatientLogin();
        currentPatient.payTotalPrice(bankSessionId);
        currentPatient.finalizeCurrentExperiment();
    }
}
