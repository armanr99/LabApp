package main.java.Models.User;

import main.java.Exceptions.CurrentExperimentNotInstantiated;
import main.java.Exceptions.UnsuccessfulPayment;
import main.java.Models.Experiment.UserExperimentRecord;
import main.java.Models.Experiment.ExperimentInfo;
import main.java.Models.General.Address;
import main.java.Models.Lab.Lab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient extends User {
    private String ssn;
    private Address address;
    private List<UserExperimentRecord> userExperimentRecords;
    private UserExperimentRecord currentUserExperimentRecord;

    public Patient(int id, String name, String email, String password, String ssn, Address address) {
        super(id, name, email, password);
        this.ssn = ssn;
        this.address = address;
        this.userExperimentRecords = new ArrayList<>();
    }

    public void createNewExperiment(int experimentId) {
        this.currentUserExperimentRecord = new UserExperimentRecord(experimentId);
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        this.currentUserExperimentRecord.setExperimentInfos(experimentInfos);
    }

    private void checkExperimentInstantiated() throws CurrentExperimentNotInstantiated {
        if (currentUserExperimentRecord == null)
            throw new CurrentExperimentNotInstantiated();
    }

    public void setExperimentLab(Lab lab) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        currentUserExperimentRecord.setLab(lab);
    }

    public void setExperimentTime(Date experimentTime) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        currentUserExperimentRecord.setTime(experimentTime);
    }

    public void setExperimentInsuranceNumber(int insuranceNumber) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        currentUserExperimentRecord.setInsuranceNumber(insuranceNumber);
    }

    public double getExperimentTotalPrice() throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        return currentUserExperimentRecord.getTotalPrice();
    }

    public void payTotalPrice(String bankSessionId) throws CurrentExperimentNotInstantiated, UnsuccessfulPayment {
        checkExperimentInstantiated();
        currentUserExperimentRecord.payTotalPrice(bankSessionId);
        finalizeCurrentExperiment();
    }

    public void finalizeCurrentExperiment() {
        userExperimentRecords.add(currentUserExperimentRecord);
        currentUserExperimentRecord = null;
    }
}
