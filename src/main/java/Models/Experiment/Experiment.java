package main.java.Models.Experiment;

import main.java.Models.Lab.Lab;
import main.java.Models.User.Sampler;

import java.util.Date;
import java.util.List;

public class Experiment {
    private String id;
    private Lab lab;
    private Sampler sampler;
    private Date date;
    private List<ExperimentInfo> experimentInfos;

    public Experiment(String id) {
        this.id = id;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public void setSampler(Sampler sampler) {
        this.sampler = sampler;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentInfos = experimentInfos;
    }
}
