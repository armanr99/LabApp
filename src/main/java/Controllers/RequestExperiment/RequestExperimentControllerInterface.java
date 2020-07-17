package main.java.Controllers.RequestExperiment;

import main.java.Exceptions.PatientNotFound;
import main.java.Models.Experiment.ExperimentInfo;

import java.util.List;

public interface RequestExperimentControllerInterface {
    void loginPatient(int patientId, String password) throws PatientNotFound;
    List<ExperimentInfo> getExperimentInfos();
}
