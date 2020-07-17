package main.java.Models.DTOs;

import main.java.Models.Experiment.ExperimentInfo;

public class ExperimentInfoDTO {
    private String name;

    public ExperimentInfoDTO(ExperimentInfo experimentInfo) {
        this.name = experimentInfo.getName();
    }

    public String getName() {
        return name;
    }
}
