package main.java.Controllers.RequestExperiment;

import main.java.Exceptions.PatientNotFound;

public interface RequestExperimentControllerInterface {
    void loginPatient(int patientId, String password) throws PatientNotFound;
}
