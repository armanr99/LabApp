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

    public void loginPatient(int patientId, String password) throws PatientNotFound {
        Patient patient = storage.getPatientRepository().find(patientId);
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

    public void createNewExperiment() throws PatientNotLogin, InvalidObjectException {
        checkPatientLogin();
        currentPatient.createNewExperimentRecord();
    }

    public List<ExperimentInfo> getExperimentInfos() throws PatientNotLogin {
        checkPatientLogin();
        return storage.getExperimentInfoRepository().getAll();
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) throws PatientNotLogin,
            CurrentExperimentNotInstantiated, InvalidObjectException {
        createNewExperiment();
        currentPatient.setExperimentInfos(experimentInfos);
    }

    public List<Lab> getExperimentLabs() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();

        List<ExperimentInfo> experimentInfos = currentPatient.getExperimentInfos();
        List<Lab> experimentsLabs = new ArrayList<>();

        for (Lab lab : Storage.getInstance().getLabRepository().getAll()) {
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
            UnsuccessfulPayment, SamplerNotAvailable, SamplerNotAssigned, NoLabAssigned, InvalidObjectException {
        checkPatientLogin();
        currentPatient.payTotalPrice(bankSessionId);
        currentPatient.finalizeCurrentExperiment();
    }

    public void logoutPatient() {
        currentPatient = null;
    }
}
