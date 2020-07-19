package main.java.Models.Experiment;

import main.java.Exceptions.UnsuccessfulPayment;
import main.java.Models.API.BankAPI;
import main.java.Models.API.InsuranceAPI;
import main.java.Models.General.Payment;
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
    private Payment payment;

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

    public double getTotalPrice() {
        double totalPrice = getPurePrice();
        totalPrice = getPriceWithInsurance(totalPrice);
        return totalPrice;
    }

    private double getPurePrice() {
        double totalPrice = 0;

        for (ExperimentInfo experimentInfo : experimentInfos) {
            totalPrice += experimentInfo.getPrice();
        }
        return totalPrice;
    }

    private double getPriceWithInsurance(double totalPrice) {
        if (hasInsurance()) {
            double insurancePercentage = InsuranceAPI.getInstance().getInsurancePercentage(insuranceNumber);
            totalPrice = Double.max(0, totalPrice - (totalPrice * insurancePercentage));
        }
        return totalPrice;
    }

    private boolean hasInsurance() {
        return (insuranceNumber != Integer.MAX_VALUE);
    }

    public void payTotalPrice(String bankSessionId) throws UnsuccessfulPayment {
        double totalPrice = getTotalPrice();
        payment = BankAPI.getInstance().pay(bankSessionId, totalPrice);
    }
}
