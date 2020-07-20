package main.java.models.Experiment;

import main.java.exceptions.NoLabAssignedException;
import main.java.exceptions.SamplerNotAssignedException;
import main.java.models.API.InsuranceAPI;
import main.java.models.General.Payment;
import main.java.models.Lab.Lab;
import main.java.models.User.Sampler;

import java.util.Date;
import java.util.List;

public class PatientExperimentRecord extends ExperimentRecord {
    private Lab lab;
    private Sampler sampler;
    private int insuranceNumber;
    private Payment payment;

    public PatientExperimentRecord() {
        super();
        this.insuranceNumber = Integer.MAX_VALUE;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentRecordInfo.setExperimentInfos(experimentInfos);
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public void setTime(Date date) {
        experimentRecordInfo.setTime(date);
    }

    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setSampler(Sampler sampler) {
        this.sampler = sampler;
    }

    public double getTotalPrice() {
        if (payment != null) {
            return payment.getTotalPrice();
        } else {
            return getCalculatedPrice();
        }
    }

    private double getCalculatedPrice() {
        double totalPrice = getPurePrice();
        totalPrice = getPriceWithInsurance(totalPrice);
        return totalPrice;
    }

    private double getPurePrice() {
        double totalPrice = 0;

        for (ExperimentInfo experimentInfo : experimentRecordInfo.getExperimentInfos()) {
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

    public Lab getLab() throws NoLabAssignedException {
        checkLabAssigned();
        return lab;
    }

    private void checkLabAssigned() throws NoLabAssignedException {
        if (lab == null) {
            throw new NoLabAssignedException();
        }
    }

    protected Sampler getSampler() throws SamplerNotAssignedException {
        checkSamplerAssigned();
        return sampler;
    }

    private void checkSamplerAssigned() throws SamplerNotAssignedException {
        if (sampler == null) {
            throw new SamplerNotAssignedException();
        }
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return experimentRecordInfo.getExperimentInfos();
    }
}
