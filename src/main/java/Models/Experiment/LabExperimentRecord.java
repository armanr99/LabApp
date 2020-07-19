package main.java.Models.Experiment;

import main.java.Models.User.Patient;
import main.java.Models.User.Sampler;

public class LabExperimentRecord extends ExperimentRecord {
    private Patient patient;
    private Sampler sampler;

    public LabExperimentRecord(Patient patient, UserExperimentRecord userExperimentRecord) {
        super(userExperimentRecord.id, userExperimentRecord.date, userExperimentRecord.experimentInfos);
        this.patient = patient;
        this.sampler = userExperimentRecord.getSampler();
    }
}
