package main.java.models.Experiment;

import java.util.Date;
import java.util.List;

public abstract class ExperimentRecord {
    protected int id;
    protected Date date;
    protected List<ExperimentInfo> experimentInfos;

    public ExperimentRecord(int id) {
        this.id = id;
    }

    public ExperimentRecord(int id, Date date, List<ExperimentInfo> experimentInfos) {
        this.id = id;
        this.date = date;
        this.experimentInfos = experimentInfos;
    }

    public void setTime(Date date) {
        this.date = date;
    }

    public void setExperimentInfos(List<ExperimentInfo> experimentInfos) {
        this.experimentInfos = experimentInfos;
    }
}
