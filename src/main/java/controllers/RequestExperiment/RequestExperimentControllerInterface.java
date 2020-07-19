package main.java.controllers.RequestExperiment;

import main.java.exceptions.*;
import main.java.models.DTO.ExperimentInfoDTO;
import main.java.models.DTO.LabDTO;

import java.io.InvalidObjectException;
import java.util.Date;
import java.util.List;

public interface RequestExperimentControllerInterface {
    void loginPatient(int patientId, String password) throws PatientNotFound;

    List<ExperimentInfoDTO> getExperimentInfos() throws PatientNotLogin;

    void setExperimentInfos(List<ExperimentInfoDTO> experimentInfoDTOs) throws ExperimentInfoNotFound, PatientNotLogin,
            CurrentExperimentNotInstantiated, InvalidObjectException;

    List<LabDTO> getExperimentLabs() throws PatientNotLogin, CurrentExperimentNotInstantiated;

    void setExperimentLab(LabDTO labDTO) throws PatientNotLogin, LabNotFound, CurrentExperimentNotInstantiated;

    List<Date> getExperimentTimes() throws LabNotFound, NoLabAssigned, PatientNotLogin,
            CurrentExperimentNotInstantiated;

    void setExperimentTime(Date experimentTime) throws PatientNotLogin, CurrentExperimentNotInstantiated;

    void setExperimentInsurance(int insuranceNumber) throws PatientNotLogin, InvalidInsuranceNumber,
            CurrentExperimentNotInstantiated;

    double getExperimentTotalPrice() throws PatientNotLogin, CurrentExperimentNotInstantiated;

    void payExperimentTotalPrice(String bankSessionId) throws PatientNotLogin, CurrentExperimentNotInstantiated,
            UnsuccessfulPayment, SamplerNotAvailable, SamplerNotAssigned, NoLabAssigned, InvalidObjectException;

    void logoutPatient();
}
