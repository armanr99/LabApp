package main.java.models.Experiment;

import main.java.models.General.Entity;

import java.util.Date;
import java.util.List;

public abstract class ExperimentRecord extends Entity {
    protected Date date;
    protected List<ExperimentInfo> experimentInfos;

    public ExperimentRecord(int id) {
        super(id);
    }

    public ExperimentRecord(int id, Date date, List<ExperimentInfo> experimentInfos) {
        super(id);
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
