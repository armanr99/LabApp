package main.java.Controllers.RequestExperiment;

import main.java.Exceptions.PatientNotFound;
import main.java.Models.DTOs.ExperimentInfoDTO;
import main.java.Models.Experiment.ExperimentInfo;
import main.java.Models.LabApp.LabApp;

import java.util.ArrayList;
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

    public List<ExperimentInfoDTO> getExperimentInfos()
    {
        List<ExperimentInfo> experimentInfos = labApp.getExperimentInfos();
        List<ExperimentInfoDTO> experimentInfoDTOS = new ArrayList<>();

        for(ExperimentInfo experimentInfo : experimentInfos) {
            experimentInfoDTOS.add(new ExperimentInfoDTO(experimentInfo));
        }

        return experimentInfoDTOS;
    }
}
