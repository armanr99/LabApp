package main.java.Models.LabApp;

import main.java.Exceptions.PatientNotFound;
import main.java.Models.Experiment.ExperimentInfo;
import main.java.Models.Lab.Lab;
import main.java.Models.User.Patient;

import java.util.ArrayList;
import java.util.List;

public class LabApp implements LabAppInterface {
    private static LabApp instance;
    private Patient currentPatient;
    private List<Lab> labs;
    private List<Patient> patients;
    private List<ExperimentInfo> experimentInfos; //TODO: Use a set and override corresponding methods

    public static LabApp getInstance() {
        if(instance == null) {
            instance = new LabApp();
        }
        return instance;
    }

    private LabApp() {
        initializeObjects();
    }

    private void initializeObjects() {
        labs = new ArrayList<>();
        patients = new ArrayList<>();
        experimentInfos = new ArrayList<>();
    }

    public void loginPatient(int patientId, String password) throws PatientNotFound {
        currentPatient = getPatient(patientId, password);
    }

    private Patient getPatient(int patientId, String password) throws PatientNotFound {
        for(Patient patient : patients) {
            if(patient.hasInfo(patientId, password))
                return patient;
        }
        throw new PatientNotFound(patientId);
    }

    public List<ExperimentInfo> getExperimentInfos()
    {
        return experimentInfos;
    }
}
