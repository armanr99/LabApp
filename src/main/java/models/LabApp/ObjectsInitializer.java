package main.java.models.LabApp;

import main.java.models.Experiment.ExperimentInfo;
import main.java.models.General.Address;
import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;
import main.java.models.User.Patient;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class ObjectsInitializer {
    private Storage storage;

    public ObjectsInitializer() {
        this.storage = Storage.getInstance();
    }

    public void initialize() throws InvalidObjectException {
        addPatients();
        addLabs();
        addExperimentInfos();
    }

    private void addPatients() throws InvalidObjectException {
        Address patientAddress = new Address("Tehran", "Somewhere", "Somewhere in Tehran");
        Patient patient = new Patient(0, "Arman Rostami", "arman.rostami.999@gmail.com", "123",
                "123", patientAddress);
        storage.getPatientRepository().insert(patient);
    }

    private void addLabs() throws InvalidObjectException {
        Address labAddress = new Address("Tehran", "Somewhere", "Somewhere in Tehran");

        Lab lab1 = new Lab(0, "Test Lab 1", labAddress);
        storage.getLabRepository().insert(lab1);

        Lab lab2 = new Lab(1, "Test Lab 2", labAddress);
        storage.getLabRepository().insert(lab2);
    }

    private void addExperimentInfos() throws InvalidObjectException {
        ExperimentInfo experimentInfo1 = new ExperimentInfo("Experiment Info 1", "Experiment Info Description 1",
                10000);
        storage.getExperimentInfoRepository().insert(experimentInfo1);

        ExperimentInfo experimentInfo2 = new ExperimentInfo("Experiment Info 2", "Experiment Info Description 2",
                15000);
        storage.getExperimentInfoRepository().insert(experimentInfo2);

        ExperimentInfo experimentInfo3 = new ExperimentInfo("Experiment Info 3", "Experiment Info Description 3",
                20000);
        storage.getExperimentInfoRepository().insert(experimentInfo3);

        ArrayList<ExperimentInfo> lab1ExperimentInfos = new ArrayList<>();
        lab1ExperimentInfos.add(experimentInfo1);
        lab1ExperimentInfos.add(experimentInfo2);
        Lab lab1 = storage.getLabRepository().find(0);
        lab1.setExperimentInfos(lab1ExperimentInfos);

        ArrayList<ExperimentInfo> lab2ExperimentInfos = new ArrayList<>();
        lab1ExperimentInfos.add(experimentInfo1);
        Lab lab2 = storage.getLabRepository().find(1);
        lab2.setExperimentInfos(lab2ExperimentInfos);
    }
}
