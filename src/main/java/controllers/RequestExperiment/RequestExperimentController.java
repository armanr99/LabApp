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

    public static RequestExperimentController getInstance() throws InvalidObjectException {
        if (instance == null) {
            instance = new RequestExperimentController();
        }
        return instance;
    }

    private RequestExperimentController() throws InvalidObjectException {
        labApp = LabApp.getInstance();
        labDTOMapper = new LabDTOMapper();
        experimentInfoDTOMapper = new ExperimentInfoDTOMapper();
    }

    public void loginPatient(int patientId, String password) throws PatientNotFoundException {
        labApp.loginPatient(patientId, password);
    }

    public List<ExperimentInfoDTO> getExperimentInfos() throws PatientNotLoginException {
        List<ExperimentInfo> experimentInfos = labApp.getExperimentInfos();
        return experimentInfoDTOMapper.getExperimentInfoDTOs(experimentInfos);
    }

    public void setExperimentInfos(List<ExperimentInfoDTO> experimentInfoDTOs) throws PatientNotLoginException,
            InvalidObjectException {
        List<ExperimentInfo> experimentInfos = experimentInfoDTOMapper.getExperimentInfos(experimentInfoDTOs);
        labApp.setExperimentInfos(experimentInfos);
    }

    public List<LabDTO> getExperimentLabs() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        List<Lab> labs = labApp.getExperimentLabs();
        return labDTOMapper.getLabDTOs(labs);
    }

    public void setExperimentLab(LabDTO labDTO) throws PatientNotLoginException, LabNotFoundException,
            CurrentExperimentNotInstantiatedException {
        Lab lab = labDTOMapper.getLab(labDTO);
        labApp.setExperimentLab(lab);
    }

    public List<Date> getExperimentTimes() throws NoLabAssignedException, PatientNotLoginException,
            CurrentExperimentNotInstantiatedException {
        return labApp.getExperimentTimes();
    }

    public void setExperimentTime(Date experimentTime) throws PatientNotLoginException,
            CurrentExperimentNotInstantiatedException {
        labApp.setExperimentTime(experimentTime);
    }

    public void setExperimentInsurance(int insuranceNumber) throws PatientNotLoginException,
            InvalidInsuranceNumberException,
            CurrentExperimentNotInstantiatedException {
        labApp.setExperimentInsurance(insuranceNumber);
    }

    public double getExperimentTotalPrice() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        return labApp.getExperimentTotalPrice();
    }

    public void payExperimentTotalPrice(String bandSessionId) throws PatientNotLoginException,
            CurrentExperimentNotInstantiatedException,
            UnsuccessfulPaymentException, SamplerNotAvailableException, SamplerNotAssignedException,
            NoLabAssignedException, InvalidObjectException {
        labApp.payExperimentTotalPrice(bandSessionId);
    }

    public void logoutPatient() {
        labApp.logoutPatient();
    }
}
