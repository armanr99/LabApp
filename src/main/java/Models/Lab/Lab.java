package main.java.Models.Lab;

import main.java.Models.DTOs.ExperimentInfoDTO;
import main.java.Models.Experiment.ExperimentInfo;
import main.java.Models.General.Address;
import main.java.Models.User.Sampler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Lab {
    private String name;
    private Address address;
    private List<Sampler> samplers;
    private List<ExperimentInfo> experimentInfos;

    public Lab(String name, Address address) {
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

    public boolean hasSupport(List<ExperimentInfoDTO> experimentInfoDTOs) {
        for (ExperimentInfoDTO experimentInfoDTO : experimentInfoDTOs) {
            if (!hasSupport(experimentInfoDTO))
                return false;
        }
        return true;
    }

    private boolean hasSupport(ExperimentInfoDTO experimentInfoDTO) {
        for (ExperimentInfo experimentInfo : experimentInfos) {
            if (experimentInfo.getName().equals(experimentInfoDTO.getName())) {
                return true;
            }
        }
        return false;
    }

    public List<Date> getTimes(List<ExperimentInfoDTO> experimentInfoDTOs) {
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
}
