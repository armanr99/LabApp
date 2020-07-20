package main.java.models.Experiment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExperimentRecordInfo {
    private Date date;
    private List<ExperimentInfo> experimentInfos;

    public ExperimentRecordInfo() {
        experimentInfos = new ArrayList<>();
    }

    public ExperimentRecordInfo(Date date, List<ExperimentInfo> experimentInfos) {
        this.date = date;
        this.experimentInfos = experimentInfos;
    }

    public void setTime(Date date) {
        this.date = date;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentInfos = experimentInfos;
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return experimentInfos;
    }
}
