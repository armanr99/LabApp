package main.java.models.Experiment;

import main.java.exceptions.NoLabAssignedException;
import main.java.exceptions.SamplerNotAssignedException;
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

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public void setSampler(Sampler sampler) {
        this.sampler = sampler;
    }

    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentRecordInfo.setExperimentInfos(experimentInfos);
    }

    public void setTime(Date date) {
        experimentRecordInfo.setTime(date);
    }

    public Lab getLab() throws NoLabAssignedException {
        checkLabAssigned();
        return lab;
    }

    protected Sampler getSampler() throws SamplerNotAssignedException {
        checkSamplerAssigned();
        return sampler;
    }

    public int getInsuranceNumber() {
        return insuranceNumber;
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return experimentRecordInfo.getExperimentInfos();
    }

    public double getPureTotalPrice() {
        double totalPrice = 0;

        for (ExperimentInfo experimentInfo : experimentRecordInfo.getExperimentInfos()) {
            totalPrice += experimentInfo.getPrice();
        }
        return totalPrice;
    }

    public boolean hasInsurance() {
        return (insuranceNumber != Integer.MAX_VALUE);
    }

    private void checkLabAssigned() throws NoLabAssignedException {
        if (lab == null) {
            throw new NoLabAssignedException();
        }
    }

    private void checkSamplerAssigned() throws SamplerNotAssignedException {
        if (sampler == null) {
            throw new SamplerNotAssignedException();
        }
    }
}
