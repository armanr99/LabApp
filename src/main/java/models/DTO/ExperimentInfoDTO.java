package main.java.models.DTO;

import main.java.models.Experiment.ExperimentInfo;

public class ExperimentInfoDTO {
    private String name;

    public ExperimentInfoDTO(ExperimentInfo experimentInfo) {
        this.name = experimentInfo.getName();
    }

    public String getName() {
        return name;
    }
}
