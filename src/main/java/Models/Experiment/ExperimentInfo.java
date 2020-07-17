package main.java.Models.Experiment;

public class ExperimentInfo {
    private String name;
    private String description;

    public ExperimentInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
