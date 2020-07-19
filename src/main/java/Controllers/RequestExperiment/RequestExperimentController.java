package main.java.Controllers.RequestExperiment;

import main.java.Exceptions.*;
import main.java.Models.DTOs.ExperimentInfoDTO;
import main.java.Models.DTOs.LabDTO;
import main.java.Models.LabApp.LabApp;

import java.util.Date;
import java.util.List;

public class RequestExperimentController implements RequestExperimentControllerInterface {
    private static RequestExperimentController instance;
    private LabApp labApp;

    public static RequestExperimentController getInstance() {
        if (instance == null) {
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

    public List<ExperimentInfoDTO> getExperimentInfos() {
        return labApp.getExperimentInfos();
    }

    public void setExperiments(List<ExperimentInfoDTO> experimentInfoDTOs) throws ExperimentInfoNotFound, PatientNotLogin, CurrentExperimentNotInstantiated {
        labApp.setExperiments(experimentInfoDTOs);
    }

    public List<LabDTO> getLabsForExperiments(List<ExperimentInfoDTO> experimentInfoDTOs) {
        return labApp.getLabsForExperiments(experimentInfoDTOs);
    }

    public List<Date> getTimesForExperiments(LabDTO labDTO, List<ExperimentInfoDTO> experimentInfoDTOs) throws LabNotFound {
        return labApp.getTimesForExperiments(labDTO, experimentInfoDTOs);
    }

    public void setLab(LabDTO labDTO) throws PatientNotLogin, LabNotFound, CurrentExperimentNotInstantiated {
        labApp.setLab(labDTO);
    }

    public void setTime(Date experimentTime) throws PatientNotLogin, CurrentExperimentNotInstantiated {
        labApp.setTime(experimentTime);
    }

    public void setInsurance(int insuranceNumber) throws PatientNotLogin, InvalidInsuranceNumber, CurrentExperimentNotInstantiated {
        labApp.setInsurance(insuranceNumber);
    }

    public double getTotalPrice() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        return labApp.getTotalPrice();
    }

    public void payTotalPrice(String bandSessionId) throws PatientNotLogin, CurrentExperimentNotInstantiated, UnsuccessfulPayment {
        labApp.payTotalPrice(bandSessionId);
    }
}
