package main.java.Models.LabApp;

import main.java.Exceptions.PatientNotFound;
import main.java.Models.DTOs.ExperimentInfoDTO;
import main.java.Models.DTOs.LabDTO;

import java.util.List;

public interface LabAppInterface {
    void loginPatient(int patientId, String password) throws PatientNotFound;
    List<ExperimentInfoDTO> getExperimentInfos();
    List<LabDTO> getLabsForExperiments(List<ExperimentInfoDTO> experimentInfoDTOs);
}
