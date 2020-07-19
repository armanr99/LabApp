package main.java.controllers.RequestExperiment;

import main.java.exceptions.*;
import main.java.models.DTO.ExperimentInfoDTO;
import main.java.models.DTO.ExperimentInfoDTOMapper;
import main.java.models.DTO.LabDTO;
import main.java.models.DTO.LabDTOMapper;
import main.java.models.Experiment.ExperimentInfo;
import main.java.models.Lab.Lab;
import main.java.models.LabApp.LabApp;

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

    public List<ExperimentInfoDTO> getExperimentInfos() {
        List<ExperimentInfo> experimentInfos = labApp.getExperimentInfos();
        return experimentInfoDTOMapper.getExperimentInfoDTOs(experimentInfos);
    }

    public void setExperiments(List<ExperimentInfoDTO> experimentInfoDTOs) throws PatientNotLogin,
            CurrentExperimentNotInstantiated {
        List<ExperimentInfo> experimentInfos = experimentInfoDTOMapper.getExperimentInfos(experimentInfoDTOs);
        labApp.setExperiments(experimentInfos);
    }

    public List<LabDTO> getLabsForExperiments(List<ExperimentInfoDTO> experimentInfoDTOs) {
        List<ExperimentInfo> experimentInfos = experimentInfoDTOMapper.getExperimentInfos(experimentInfoDTOs);
        List<Lab> labs = labApp.getLabsForExperiments(experimentInfos);
        return labDTOMapper.getLabDTOs(labs);
    }

    public List<Date> getTimesForExperiments(LabDTO labDTO, List<ExperimentInfoDTO> experimentInfoDTOs) throws LabNotFound {
        Lab lab = labDTOMapper.getLab(labDTO);
        List<ExperimentInfo> experimentInfos = experimentInfoDTOMapper.getExperimentInfos(experimentInfoDTOs);
        return labApp.getTimesForExperiments(lab, experimentInfos);
    }

    public void setLab(LabDTO labDTO) throws PatientNotLogin, LabNotFound, CurrentExperimentNotInstantiated {
        Lab lab = labDTOMapper.getLab(labDTO);
        labApp.setLab(lab);
    }

    public void setTime(Date experimentTime) throws PatientNotLogin, CurrentExperimentNotInstantiated {
        labApp.setTime(experimentTime);
    }

    public void setInsurance(int insuranceNumber) throws PatientNotLogin, InvalidInsuranceNumber,
            CurrentExperimentNotInstantiated {
        labApp.setInsurance(insuranceNumber);
    }

    public double getTotalPrice() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        return labApp.getTotalPrice();
    }

    public void payTotalPrice(String bandSessionId) throws PatientNotLogin, CurrentExperimentNotInstantiated,
            UnsuccessfulPayment, SamplerNotAvailable, SamplerNotAssigned, NoLabAssigned {
        labApp.payTotalPrice(bandSessionId);
    }
}
