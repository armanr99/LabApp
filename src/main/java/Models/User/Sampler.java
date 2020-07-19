package main.java.Models.User;

import main.java.Models.Experiment.PatientExperimentRecord;
import main.java.Models.Experiment.SamplerExperimentRecord;

import java.util.ArrayList;
import java.util.List;

public class Sampler extends User {
    private List<SamplerExperimentRecord> samplerExperimentRecords;

    public Sampler(int id, String name, String email, String password) {
        super(id, name, email, password);
        samplerExperimentRecords = new ArrayList<>();
    }

    public void addExperimentRecord(Patient patient, PatientExperimentRecord patientExperimentRecord) {
        SamplerExperimentRecord samplerExperimentRecord = new SamplerExperimentRecord(patient, patientExperimentRecord);
        samplerExperimentRecords.add(samplerExperimentRecord);
    }
}
