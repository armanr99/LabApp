package main.java.Models.Experiment;

import main.java.Models.Lab.Lab;
import main.java.Models.User.Patient;

public class SamplerExperimentRecord extends ExperimentRecord {
    private Patient patient;
    private Lab lab;

    public SamplerExperimentRecord(Patient patient, UserExperimentRecord userExperimentRecord) {
        super(userExperimentRecord.id, userExperimentRecord.date, userExperimentRecord.experimentInfos);
        this.lab = userExperimentRecord.getLab();
        this.patient = patient;
    }
}
