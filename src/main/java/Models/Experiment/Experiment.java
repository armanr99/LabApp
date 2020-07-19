package main.java.Models.Experiment;

import main.java.Models.API.InsuranceAPI;
import main.java.Models.Lab.Lab;
import main.java.Models.User.Sampler;

import java.util.Date;
import java.util.List;

public class Experiment {
    private int id;
    private Lab lab;
    private Sampler sampler;
    private Date date;
    private int insuranceNumber;
    private List<ExperimentInfo> experimentInfos;

    public Experiment(int id) {
        this.id = id;
        this.insuranceNumber = Integer.MAX_VALUE;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public void setSampler(Sampler sampler) {
        this.sampler = sampler;
    }

    public void setTime(Date date) {
        this.date = date;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentInfos = experimentInfos;
    }

    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public double getTotalCost() {
        double totalCost = getPureCost();
        totalCost = getCostWithInsurance(totalCost);
        return totalCost;
    }

    private double getPureCost() {
        double totalCost = 0;

        for (ExperimentInfo experimentInfo : experimentInfos) {
            totalCost += experimentInfo.getPrice();
        }
        return totalCost;
    }

    private double getCostWithInsurance(double totalCost) {
        if (hasInsurance()) {
            double insurancePercentage = InsuranceAPI.getInstance().getInsurancePercentage(insuranceNumber);
            totalCost = Double.max(0, totalCost - (totalCost * insurancePercentage));
        }
        return totalCost;
    }

    private boolean hasInsurance() {
        return (insuranceNumber != Integer.MAX_VALUE);
    }
}
