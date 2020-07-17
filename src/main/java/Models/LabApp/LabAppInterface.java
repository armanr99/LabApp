package main.java.Models.LabApp;

import main.java.Exceptions.PatientNotFound;
import main.java.Models.Experiment.ExperimentInfo;

import java.util.List;

public interface LabAppInterface {
    void loginPatient(int patientId, String password) throws PatientNotFound;
    List<ExperimentInfo> getExperimentInfos();
}
