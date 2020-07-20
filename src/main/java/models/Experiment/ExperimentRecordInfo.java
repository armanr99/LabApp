package main.java.models.Experiment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExperimentRecordInfo {
    private Date date;
    private List<ExperimentInfo> experimentInfos;
    ExperimentStatus experimentStatus;

    public ExperimentRecordInfo(ExperimentStatus experimentStatus) {
        this.experimentStatus = experimentStatus;
        this.experimentInfos = new ArrayList<>();
    }

    public void setTime(Date date) {
        this.date = date;
    }

    public void setStatus(ExperimentStatus experimentStatus) {
        this.experimentStatus = experimentStatus;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentInfos = experimentInfos;
    }

    public List<ExperimentInfo> getExperimentInfos() {
        return experimentInfos;
    }
}
