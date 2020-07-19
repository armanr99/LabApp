package main.java.models.LabApp;

import main.java.models.Experiment.ExperimentInfo;
import main.java.models.General.Address;
import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;
import main.java.models.User.Patient;

import java.io.InvalidObjectException;

public class ObjectsInitializer {
    public void initialize() throws InvalidObjectException {
        addPatients();
        addLabs();
        addExperimentInfos();
    }

    private void addPatients() throws InvalidObjectException {
        Address patientAddress = new Address("Tehran", "Somewhere", "Somewhere in Tehran");
        Patient patient = new Patient(0, "Arman Rostami", "arman.rostami.999@gmail.com", "123",
                "123", patientAddress);
        Storage.getInstance().getPatientRepository().insert(patient);
    }

    private void addLabs() throws InvalidObjectException {
        Address labAddress = new Address("Tehran", "Somewhere", "Somewhere in Tehran");

        Lab lab1 = new Lab(0, "Test Lab 1", labAddress);
        Storage.getInstance().getLabRepository().insert(lab1);

        Lab lab2 = new Lab(1, "Test Lab 1", labAddress);
        Storage.getInstance().getLabRepository().insert(lab2);
    }

    private void addExperimentInfos() throws InvalidObjectException {
        ExperimentInfo experimentInfo1 = new ExperimentInfo("Experiment Info 1", "Experiment Info Description 1",
                10000);
        Storage.getInstance().getExperimentInfoRepository().insert(experimentInfo1);

        ExperimentInfo experimentInfo2 = new ExperimentInfo("Experiment Info 2", "Experiment Info Description 2",
                15000);
        Storage.getInstance().getExperimentInfoRepository().insert(experimentInfo2);

        ExperimentInfo experimentInfo3 = new ExperimentInfo("Experiment Info 3", "Experiment Info Description 3",
                20000);
        Storage.getInstance().getExperimentInfoRepository().insert(experimentInfo3);
    }
}
