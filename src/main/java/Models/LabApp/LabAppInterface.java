package main.java.Models.LabApp;

import main.java.Exceptions.PatientNotFound;

public interface LabAppInterface {
    void loginPatient(int patientId, String password) throws PatientNotFound;
}
