package main.java.models.Experiment;

import main.java.models.Lab.Lab;
import main.java.models.User.Patient;

public class SamplerExperimentRecord extends ExperimentRecord {
    private Patient patient;
    private Lab lab;

    public SamplerExperimentRecord(Patient patient, PatientExperimentRecord patientExperimentRecord) {
        super(patientExperimentRecord.id, patientExperimentRecord.date, patientExperimentRecord.experimentInfos);
        this.lab = patientExperimentRecord.getLab();
        this.patient = patient;
    }
}
