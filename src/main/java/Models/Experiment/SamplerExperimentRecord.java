package main.java.Models.Experiment;

import main.java.Models.Lab.Lab;
import main.java.Models.User.User;

public class SamplerExperimentRecord extends ExperimentRecord {
    private User user;
    private Lab lab;

    public SamplerExperimentRecord(User user, UserExperimentRecord userExperimentRecord) {
        super(userExperimentRecord.id, userExperimentRecord.date, userExperimentRecord.experimentInfos);
        this.lab = userExperimentRecord.getLab();
        this.user = user;
    }
}
