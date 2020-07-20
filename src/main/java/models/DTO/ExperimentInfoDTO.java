package main.java.models.DTO;

import main.java.models.Experiment.ExperimentInfo;

public class ExperimentInfoDTO {
    private int id;
    private String name;

    public ExperimentInfoDTO(ExperimentInfo experimentInfo) {
        this.id = experimentInfo.getId();
        this.name = experimentInfo.getName();
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
