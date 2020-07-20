package main.java.models.Experiment;

import main.java.models.User.Patient;
import main.java.models.User.Sampler;

public class LabExperimentRecord extends ExperimentRecord {
    private Patient patient;
    private Sampler sampler;
    private int patientExperimentRecordId;

    public LabExperimentRecord(Patient patient, PatientExperimentRecord patientExperimentRecord) {
        super(patientExperimentRecord.getExperimentRecordInfo());
        this.patient = patient;
        this.sampler = patientExperimentRecord.getSampler();
        this.patientExperimentRecordId = patientExperimentRecord.getId();
    }
}
