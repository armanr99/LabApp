package main.java.Models.User;

import main.java.Exceptions.CurrentExperimentNotInstantiated;
import main.java.Models.Experiment.Experiment;
import main.java.Models.Experiment.ExperimentInfo;
import main.java.Models.General.Address;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private String ssn;
    private Address address;
    private List<Experiment> experiments;
    private Experiment currentExperiment;

    public Patient(int id, String name, String email, String password, String ssn, Address address) {
        super(id, name, email, password);
        this.ssn = ssn;
        this.address = address;
        this.experiments = new ArrayList<>();
    }

    public void createNewExperiment(int experimentId) {
        this.currentExperiment = new Experiment(experimentId);
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        this.currentExperiment.setExperimentInfos(experimentInfos);
    }

    private void checkExperimentInstantiated() throws CurrentExperimentNotInstantiated {
        if (currentExperiment == null)
            throw new CurrentExperimentNotInstantiated();
    }
}
