package main.java.models.Experiment;

import main.java.exceptions.NoLabAssigned;
import main.java.exceptions.SamplerNotAssigned;
import main.java.exceptions.SamplerNotAvailable;
import main.java.exceptions.UnsuccessfulPayment;
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

    public void payTotalPrice(String bankSessionId) throws UnsuccessfulPayment {
        double totalPrice = getTotalPrice();
        payment = BankAPI.getInstance().pay(bankSessionId, totalPrice);
    }

    protected Lab getLab() {
        return lab;
    }

    protected Sampler getSampler() {
        return sampler;
    }

    public void assignSampler() throws SamplerNotAvailable {
        this.sampler = lab.getSampler(experimentInfos);
    }

    public void informSampler(Patient patient) throws SamplerNotAssigned, NoLabAssigned, InvalidObjectException {
        checkLabAssigned();
        checkSamplerAssigned();
        SamplerExperimentRecord samplerExperimentRecord = new SamplerExperimentRecord(patient, this);
        Storage.getInstance().getSamplerExperimentRecordRepository().insert(samplerExperimentRecord);
        sampler.addExperimentRecord(samplerExperimentRecord);
    }

    private void checkSamplerAssigned() throws SamplerNotAssigned {
        if (sampler == null) {
            throw new SamplerNotAssigned();
        }
    }

    public void informLab(Patient patient) throws NoLabAssigned, SamplerNotAssigned, InvalidObjectException {
        checkLabAssigned();
        checkSamplerAssigned();
        LabExperimentRecord labExperimentRecord = new LabExperimentRecord(patient, this);
        Storage.getInstance().getLabExperimentRecordRepository().insert(labExperimentRecord);
        lab.addExperimentRecord(labExperimentRecord);
    }

    private void checkLabAssigned() throws NoLabAssigned {
        if (lab == null) {
            throw new NoLabAssigned();
        }
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return experimentInfos;
    }

    public List<Date> getExperimentTimes() throws NoLabAssigned {
        checkLabAssigned();
        return lab.getTimes(experimentInfos);
    }
}
