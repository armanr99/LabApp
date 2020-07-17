package main.java.Controllers.RequestExperiment;

import main.java.Exceptions.PatientNotFound;
import main.java.Models.Experiment.ExperimentInfo;
import main.java.Models.LabApp.LabApp;

import java.util.List;

public class RequestExperimentController implements RequestExperimentControllerInterface {
    private static RequestExperimentController instance;
    private LabApp labApp;

    public static RequestExperimentController getInstance() {
        if(instance == null) {
            instance = new RequestExperimentController();
        }
        return instance;
    }

    private RequestExperimentController() {
        labApp = LabApp.getInstance();
    }

    public void loginPatient(int patientId, String password) throws PatientNotFound {
        labApp.loginPatient(patientId, password);
    }

    public List<ExperimentInfo> getExperimentInfos()
    {
        return labApp.getExperimentInfos();
    }
}
