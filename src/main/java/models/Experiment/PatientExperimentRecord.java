package main.java.models.Experiment;

import main.java.exceptions.NoLabAssignedException;
import main.java.exceptions.SamplerNotAssignedException;
import main.java.exceptions.SamplerNotAvailableException;
import main.java.exceptions.UnsuccessfulPaymentException;
import main.java.models.API.BankAPI;
import main.java.models.API.InsuranceAPI;
import main.java.models.General.Payment;
import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;
import main.java.models.User.Patient;
import main.java.models.User.Sampler;

import java.io.InvalidObjectException;
import java.util.Date;
import java.util.List;

public class PatientExperimentRecord extends ExperimentRecord {
    private Lab lab;
    private Sampler sampler;
    private int insuranceNumber;
    private Payment payment;

    public PatientExperimentRecord() {
        this.insuranceNumber = Integer.MAX_VALUE;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
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

    public void payTotalPrice(String bankSessionId) throws UnsuccessfulPaymentException {
        double totalPrice = getTotalPrice();
        payment = BankAPI.getInstance().pay(bankSessionId, totalPrice);
    }

    protected Lab getLab() {
        return lab;
    }

    protected Sampler getSampler() {
        return sampler;
    }

    public void assignSampler() throws SamplerNotAvailableException {
        this.sampler = lab.getSampler(experimentInfos);
    }

    public void informSampler(Patient patient) throws SamplerNotAssignedException, NoLabAssignedException, InvalidObjectException {
        checkLabAssigned();
        checkSamplerAssigned();
        SamplerExperimentRecord samplerExperimentRecord = new SamplerExperimentRecord(patient, this);
        Storage.getInstance().getSamplerExperimentRecordRepository().insert(samplerExperimentRecord);
        sampler.addExperimentRecord(samplerExperimentRecord);
    }

    private void checkSamplerAssigned() throws SamplerNotAssignedException {
        if (sampler == null) {
            throw new SamplerNotAssignedException();
        }
    }

    public void informLab(Patient patient) throws NoLabAssignedException, SamplerNotAssignedException, InvalidObjectException {
        checkLabAssigned();
        checkSamplerAssigned();
        LabExperimentRecord labExperimentRecord = new LabExperimentRecord(patient, this);
        Storage.getInstance().getLabExperimentRecordRepository().insert(labExperimentRecord);
        lab.addExperimentRecord(labExperimentRecord);
    }

    private void checkLabAssigned() throws NoLabAssignedException {
        if (lab == null) {
            throw new NoLabAssignedException();
        }
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return experimentInfos;
    }

    public List<Date> getExperimentTimes() throws NoLabAssignedException {
        checkLabAssigned();
        return lab.getTimes(experimentInfos);
    }
}
