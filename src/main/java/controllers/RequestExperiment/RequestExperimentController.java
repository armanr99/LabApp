package main.java.controllers.RequestExperiment;

import main.java.exceptions.*;
import main.java.models.DTO.ExperimentInfoDTO;
import main.java.models.DTO.ExperimentInfoDTOMapper;
import main.java.models.DTO.LabDTO;
import main.java.models.DTO.LabDTOMapper;
import main.java.models.Experiment.ExperimentInfo;
import main.java.models.Lab.Lab;
import main.java.models.LabApp.LabApp;

import java.io.InvalidObjectException;
import java.util.Date;
import java.util.List;

public class RequestExperimentController implements RequestExperimentControllerInterface {
    private static RequestExperimentController instance;
    private LabApp labApp;
    private LabDTOMapper labDTOMapper;
    private ExperimentInfoDTOMapper experimentInfoDTOMapper;

    public static RequestExperimentController getInstance() {
        if (instance == null) {
            instance = new RequestExperimentController();
        }
        return instance;
    }

    private RequestExperimentController() {
        labApp = LabApp.getInstance();
        labDTOMapper = new LabDTOMapper();
        experimentInfoDTOMapper = new ExperimentInfoDTOMapper();
    }

    public void loginPatient(int patientId, String password) throws PatientNotFound {
        labApp.loginPatient(patientId, password);
    }

    public List<ExperimentInfoDTO> getExperimentInfos() throws PatientNotLogin {
        List<ExperimentInfo> experimentInfos = labApp.getExperimentInfos();
        return experimentInfoDTOMapper.getExperimentInfoDTOs(experimentInfos);
    }

    public void setExperimentInfos(List<ExperimentInfoDTO> experimentInfoDTOs) throws PatientNotLogin,
            CurrentExperimentNotInstantiated, InvalidObjectException {
        List<ExperimentInfo> experimentInfos = experimentInfoDTOMapper.getExperimentInfos(experimentInfoDTOs);
        labApp.setExperimentInfos(experimentInfos);
    }

    public List<LabDTO> getExperimentLabs() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        List<Lab> labs = labApp.getExperimentLabs();
        return labDTOMapper.getLabDTOs(labs);
    }

    public void setExperimentLab(LabDTO labDTO) throws PatientNotLogin, LabNotFound, CurrentExperimentNotInstantiated {
        Lab lab = labDTOMapper.getLab(labDTO);
        labApp.setExperimentLab(lab);
    }

    public List<Date> getExperimentTimes() throws NoLabAssigned, PatientNotLogin, CurrentExperimentNotInstantiated {
        return labApp.getExperimentTimes();
    }

    public void setExperimentTime(Date experimentTime) throws PatientNotLogin, CurrentExperimentNotInstantiated {
        labApp.setExperimentTime(experimentTime);
    }

    public void setExperimentInsurance(int insuranceNumber) throws PatientNotLogin, InvalidInsuranceNumber,
            CurrentExperimentNotInstantiated {
        labApp.setExperimentInsurance(insuranceNumber);
    }

    public double getExperimentTotalPrice() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        return labApp.getExperimentTotalPrice();
    }

    public void payExperimentTotalPrice(String bandSessionId) throws PatientNotLogin, CurrentExperimentNotInstantiated,
            UnsuccessfulPayment, SamplerNotAvailable, SamplerNotAssigned, NoLabAssigned, InvalidObjectException {
        labApp.payExperimentTotalPrice(bandSessionId);
    }

    public void logoutPatient() {
        labApp.logoutPatient();
    }
}
