package main.java.models.Experiment;

import main.java.models.General.Entity;

public class ExperimentInfo extends Entity {
    private String name;
    private String description;
    private double price;

    public ExperimentInfo(int id, String name, String description, double price) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
