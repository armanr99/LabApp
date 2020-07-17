package main.java.Models.Lab;

import main.java.Models.Experiment.ExperimentInfo;
import main.java.Models.General.Address;
import main.java.Models.User.Sampler;

import java.util.List;

public class Lab {
    private String name;
    private Address address;
    private List<Sampler> samplers;
    private List<ExperimentInfo> experimentInfos;

    public Lab(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public void setSamplers(List<Sampler> samplers) {
        this.samplers = samplers;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentInfos = experimentInfos;
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return experimentInfos;
    }
}
