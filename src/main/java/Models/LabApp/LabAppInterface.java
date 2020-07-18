package main.java.Models.LabApp;

import main.java.Exceptions.*;
import main.java.Models.DTOs.ExperimentInfoDTO;
import main.java.Models.DTOs.LabDTO;

import java.util.Date;
import java.util.List;

public interface LabAppInterface {
    void loginPatient(int patientId, String password) throws PatientNotFound;
    List<ExperimentInfoDTO> getExperimentInfos();
    void setExperiments(List<ExperimentInfoDTO> experimentInfoDTOs) throws PatientNotLogin, ExperimentInfoNotFound, CurrentExperimentNotInstantiated;
    List<LabDTO> getLabsForExperiments(List<ExperimentInfoDTO> experimentInfoDTOs);
    List<Date> getTimesForExperiments(LabDTO labDTO, List<ExperimentInfoDTO> experimentInfoDTOs) throws LabNotFound;
}
