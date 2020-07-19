package main.java.models.User;

import main.java.exceptions.*;
import main.java.models.Experiment.PatientExperimentRecord;
import main.java.models.Experiment.ExperimentInfo;
import main.java.models.General.Address;
import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient extends User {
    private String ssn;
    private Address address;
    private List<PatientExperimentRecord> patientExperimentRecords;
    private PatientExperimentRecord currentPatientExperimentRecord;

    public Patient(int id, String name, String email, String password, String ssn, Address address) {
        super(id, name, email, password);
        this.ssn = ssn;
        this.address = address;
        this.patientExperimentRecords = new ArrayList<>();
    }

    public void createNewExperimentRecord() throws InvalidObjectException {
        currentPatientExperimentRecord = new PatientExperimentRecord();
        Storage.getInstance().getPatientExperimentRecordRepository().insert(currentPatientExperimentRecord);
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        currentPatientExperimentRecord.setExperimentInfos(experimentInfos);
    }

    private void checkExperimentInstantiated() throws CurrentExperimentNotInstantiated {
        if (currentPatientExperimentRecord == null)
            throw new CurrentExperimentNotInstantiated();
    }

    public void setExperimentLab(Lab lab) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        currentPatientExperimentRecord.setLab(lab);
    }

    public void setExperimentTime(Date experimentTime) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        currentPatientExperimentRecord.setTime(experimentTime);
    }

    public void setExperimentInsuranceNumber(int insuranceNumber) throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        currentPatientExperimentRecord.setInsuranceNumber(insuranceNumber);
    }

    public double getExperimentTotalPrice() throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        return currentPatientExperimentRecord.getTotalPrice();
    }

    public void payTotalPrice(String bankSessionId) throws CurrentExperimentNotInstantiated, UnsuccessfulPayment {
        checkExperimentInstantiated();
        currentPatientExperimentRecord.payTotalPrice(bankSessionId);
    }

    public void finalizeCurrentExperiment() throws CurrentExperimentNotInstantiated, SamplerNotAvailable,
            SamplerNotAssigned, NoLabAssigned, InvalidObjectException {
        checkExperimentInstantiated();
        currentPatientExperimentRecord.assignSampler();
        currentPatientExperimentRecord.informSampler(this);
        currentPatientExperimentRecord.informLab(this);
        archiveCurrentExperiment();
    }

    private void archiveCurrentExperiment() {
        patientExperimentRecords.add(currentPatientExperimentRecord);
        currentPatientExperimentRecord = null;
    }

    public List<ExperimentInfo> getExperimentInfos() throws CurrentExperimentNotInstantiated {
        checkExperimentInstantiated();
        return currentPatientExperimentRecord.getExperimentInfos();
    }

    public List<Date> getExperimentTimes() throws CurrentExperimentNotInstantiated, NoLabAssigned {
        checkExperimentInstantiated();
        return currentPatientExperimentRecord.getExperimentTimes();
    }
}
