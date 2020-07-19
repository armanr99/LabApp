package main.java.models.LabApp;

import main.java.models.General.Address;
import main.java.models.Storage.Storage;
import main.java.models.User.Patient;

import java.io.InvalidObjectException;

public class ObjectsInitializer {
    public void initialize() throws InvalidObjectException {
        addPatients();
    }

    private void addPatients() throws InvalidObjectException {
        Address patientAddress = new Address("Tehran", "Somewhere", "Somewhere in Tehran");
        Patient patient = new Patient(0, "Arman Rostami", "arman.rostami.999@gmail.com", "123",
                "123", patientAddress);
        Storage.getInstance().getPatientRepository().insert(patient);
    }
}
