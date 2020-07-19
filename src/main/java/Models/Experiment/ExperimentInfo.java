package main.java.Models.Experiment;

public class ExperimentInfo {
    private String name;
    private String description;
    private double price;

    public ExperimentInfo(String name, String description, double price) {
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
