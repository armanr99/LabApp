package main.java.models.Experiment;

import main.java.exceptions.NoLabAssignedException;
import main.java.models.Lab.Lab;
import main.java.models.User.Patient;

public class SamplerExperimentRecord extends ExperimentRecord {
    private Patient patient;
    private Lab lab;
    private int patientExperimentRecordId;

    public SamplerExperimentRecord(Patient patient, PatientExperimentRecord patientExperimentRecord) throws NoLabAssignedException {
        super(patientExperimentRecord.getExperimentRecordInfo());
        this.lab = patientExperimentRecord.getLab();
        this.patient = patient;
        this.patientExperimentRecordId = patientExperimentRecord.getId();
    }
}
