package main.java.controllers.RequestExperiment;

import main.java.exceptions.*;
import main.java.models.DTO.ExperimentInfoDTO;
import main.java.models.DTO.LabDTO;

import java.io.InvalidObjectException;
import java.util.Date;
import java.util.List;

public interface RequestExperimentControllerInterface {
    void loginPatient(int patientId, String password) throws PatientNotFoundException;

    List<ExperimentInfoDTO> getExperimentInfos() throws PatientNotLoginException;

    void setExperimentInfos(List<ExperimentInfoDTO> experimentInfoDTOs) throws
            PatientNotLoginException,
            InvalidObjectException;

    List<LabDTO> getExperimentLabs() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException;

    void setExperimentLab(LabDTO labDTO) throws PatientNotLoginException, CurrentExperimentNotInstantiatedException;

    List<Date> getExperimentTimes() throws NoLabAssignedException, PatientNotLoginException,
            CurrentExperimentNotInstantiatedException;

    void setExperimentTime(Date experimentTime) throws PatientNotLoginException,
            CurrentExperimentNotInstantiatedException;

    void setExperimentInsurance(int insuranceNumber) throws PatientNotLoginException, InvalidInsuranceNumberException,
            CurrentExperimentNotInstantiatedException;

    double getExperimentTotalPrice() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException;

    void payExperimentTotalPrice(String bankSessionId) throws PatientNotLoginException,
            CurrentExperimentNotInstantiatedException,
            UnsuccessfulPaymentException, SamplerNotAvailableException, SamplerNotAssignedException,
            NoLabAssignedException, InvalidObjectException;

    void logoutPatient();
}
