package main.java.Models.Experiment;

import main.java.Exceptions.SamplerNotAssigned;
import main.java.Exceptions.SamplerNotAvailable;
import main.java.Exceptions.UnsuccessfulPayment;
import main.java.Models.API.BankAPI;
import main.java.Models.API.InsuranceAPI;
import main.java.Models.General.Payment;
import main.java.Models.Lab.Lab;
import main.java.Models.User.Patient;
import main.java.Models.User.Sampler;

public class PatientExperimentRecord extends ExperimentRecord {
    private Lab lab;
    private Sampler sampler;
    private int insuranceNumber;
    private Payment payment;

    public PatientExperimentRecord(int id) {
        super(id);
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

    public void informSampler(Patient patient) throws SamplerNotAssigned {
        checkSamplerAssigned();
        sampler.addExperimentRecord(patient, this);
    }

    private void checkSamplerAssigned() throws SamplerNotAssigned {
        if (sampler == null) {
            throw new SamplerNotAssigned();
        }
    }
}
