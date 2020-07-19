package main.java.Controllers.RequestExperiment;

import main.java.Exceptions.*;
import main.java.Models.DTOs.ExperimentInfoDTO;
import main.java.Models.DTOs.LabDTO;

import java.util.Date;
import java.util.List;

public interface RequestExperimentControllerInterface {
    void loginPatient(int patientId, String password) throws PatientNotFound;

    List<ExperimentInfoDTO> getExperimentInfos();

    List<LabDTO> getLabsForExperiments(List<ExperimentInfoDTO> experimentInfoDTOs);

    List<Date> getTimesForExperiments(LabDTO labDTO, List<ExperimentInfoDTO> experimentInfoDTOs) throws LabNotFound;

    void setExperiments(List<ExperimentInfoDTO> experimentInfoDTOs) throws ExperimentInfoNotFound, PatientNotLogin, CurrentExperimentNotInstantiated;

    void setLab(LabDTO labDTO) throws PatientNotLogin, LabNotFound, CurrentExperimentNotInstantiated;

    void setTime(Date experimentTime) throws PatientNotLogin, CurrentExperimentNotInstantiated;

    void setInsurance(int insuranceNumber) throws PatientNotLogin, InvalidInsuranceNumber, CurrentExperimentNotInstantiated;

    double getTotalPrice() throws PatientNotLogin, CurrentExperimentNotInstantiated;

    void payTotalPrice(String bankSessionId) throws PatientNotLogin, CurrentExperimentNotInstantiated, UnsuccessfulPayment, SamplerNotAvailable;
}
