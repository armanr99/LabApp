package main.java.Models.Experiment;

import main.java.Models.User.Patient;
import main.java.Models.User.Sampler;

public class LabExperimentRecord extends ExperimentRecord {
    private Patient patient;
    private Sampler sampler;

    public LabExperimentRecord(Patient patient, PatientExperimentRecord patientExperimentRecord) {
        super(patientExperimentRecord.id, patientExperimentRecord.date, patientExperimentRecord.experimentInfos);
        this.patient = patient;
        this.sampler = patientExperimentRecord.getSampler();
    }
}
