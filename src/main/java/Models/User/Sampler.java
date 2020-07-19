package main.java.Models.User;

import main.java.Models.Experiment.SamplerExperimentRecord;

import java.util.ArrayList;
import java.util.List;

public class Sampler extends User {
    private List<SamplerExperimentRecord> experimentRecords;

    public Sampler(int id, String name, String email, String password) {
        super(id, name, email, password);
        experimentRecords = new ArrayList<>();
    }

    public void addExperimentRecord(SamplerExperimentRecord samplerExperimentRecord) {
        experimentRecords.add(samplerExperimentRecord);
    }
}
