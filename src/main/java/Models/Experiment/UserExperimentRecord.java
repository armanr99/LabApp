package main.java.Models.Experiment;

import main.java.Exceptions.UnsuccessfulPayment;
import main.java.Models.API.BankAPI;
import main.java.Models.API.InsuranceAPI;
import main.java.Models.General.Payment;
import main.java.Models.Lab.Lab;
import main.java.Models.User.Sampler;

public class UserExperimentRecord extends ExperimentRecord {
    private Lab lab;
    private Sampler sampler;
    private int insuranceNumber;
    private Payment payment;

    public UserExperimentRecord(int id) {
        super(id);
        this.insuranceNumber = Integer.MAX_VALUE;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public void setSampler(Sampler sampler) {
        this.sampler = sampler;
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

    protected Lab getLab() {
        return lab;
    }
}
