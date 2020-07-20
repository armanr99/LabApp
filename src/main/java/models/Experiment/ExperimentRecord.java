package main.java.models.Experiment;

import main.java.models.Entity.Entity;

public abstract class ExperimentRecord extends Entity {
    protected ExperimentRecordInfo experimentRecordInfo;

    public ExperimentRecord() {
        experimentRecordInfo = new ExperimentRecordInfo(ExperimentStatus.NOT_TAKEN);
    }

    public ExperimentRecord(ExperimentRecordInfo experimentRecordInfo) {
        this.experimentRecordInfo = experimentRecordInfo;
    }

    public ExperimentRecordInfo getExperimentRecordInfo() {
        return experimentRecordInfo;
    }

    public void setStatus(ExperimentStatus experimentStatus) {
        experimentRecordInfo.setStatus(experimentStatus);
    }
}
