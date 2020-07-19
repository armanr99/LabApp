package main.java.models.Lab;

import main.java.exceptions.SamplerNotAvailable;
import main.java.models.Experiment.ExperimentInfo;
import main.java.models.Experiment.LabExperimentRecord;
import main.java.models.General.Address;
import main.java.models.General.Entity;
import main.java.models.User.Sampler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Lab extends Entity {
    private String name;
    private Address address;
    private List<Sampler> samplers;
    private List<ExperimentInfo> experimentInfos;
    private List<LabExperimentRecord> experimentRecords;

    public Lab(int id, String name, Address address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public void setSamplers(List<Sampler> samplers) {
        this.samplers = samplers;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentInfos = experimentInfos;
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return experimentInfos;
    }

    public String getName() {
        return name;
    }

    public String getFullAddress() {
        return address.getFullAddress();
    }

    public boolean hasSupport(List<ExperimentInfo> inExperimentInfos) {
        for (ExperimentInfo inExperimentInfo : inExperimentInfos) {
            if (!hasSupport(inExperimentInfo))
                return false;
        }
        return true;
    }

    private boolean hasSupport(ExperimentInfo inExperimentInfo) {
        for (ExperimentInfo experimentInfo : experimentInfos) {
            if (experimentInfo.equals(inExperimentInfo)) {
                return true;
            }
        }
        return false;
    }

    public List<Date> getTimes(List<ExperimentInfo> inExperimentInfos) {
        List<Date> dates = new ArrayList<>();
        int numberOfDates = (int) (Math.random() * 10);

        for (int i = 0; i < numberOfDates; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int hourShift = (int) (Math.random() * 1000);
            cal.add(Calendar.HOUR_OF_DAY, hourShift);
            dates.add(cal.getTime());
        }
        return dates;
    }

    public Sampler getSampler(List<ExperimentInfo> experimentInfos) throws SamplerNotAvailable {
        if (samplers.isEmpty()) {
            throw new SamplerNotAvailable();
        } else {
            int samplerIndex = (int) (Math.random() * samplers.size());
            return samplers.get(samplerIndex);
        }
    }

    public void addExperimentRecord(LabExperimentRecord labExperimentRecord) {
        experimentRecords.add(labExperimentRecord);
    }
}
