package main.java.models.Experiment;

import main.java.models.User.Patient;
import main.java.models.User.Sampler;

public class LabExperimentRecord extends ExperimentRecord {
    private Patient patient;
    private Sampler sampler;

    public LabExperimentRecord(Patient patient, PatientExperimentRecord patientExperimentRecord) {
        super(patientExperimentRecord.getId(), patientExperimentRecord.date, patientExperimentRecord.experimentInfos);
        this.patient = patient;
        this.sampler = patientExperimentRecord.getSampler();
    }
}
