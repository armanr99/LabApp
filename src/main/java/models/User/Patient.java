package main.java.models.User;

import main.java.models.Experiment.PatientExperimentRecord;
import main.java.models.General.Address;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private String ssn;
    private Address address;
    private List<PatientExperimentRecord> patientExperimentRecords;

    public Patient(int id, String name, String email, String password, String ssn, Address address) {
        super(id, name, email, password);
        this.ssn = ssn;
        this.address = address;
        this.patientExperimentRecords = new ArrayList<>();
    }

    public void addExperimentRecord(PatientExperimentRecord patientExperimentRecord) {
        patientExperimentRecords.add(patientExperimentRecord);
    }
}
