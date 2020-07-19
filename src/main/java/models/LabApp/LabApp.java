package main.java.models.LabApp;

import main.java.exceptions.*;
import main.java.models.API.InsuranceAPI;
import main.java.models.DTO.ExperimentInfoDTO;
import main.java.models.DTO.LabDTO;
import main.java.models.Experiment.ExperimentInfo;
import main.java.models.Handler.IdHandler;
import main.java.models.Lab.Lab;
import main.java.models.User.Patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LabApp {
    private static LabApp instance;
    private Patient currentPatient;
    private List<Lab> labs;
    private List<Patient> patients;
    private List<ExperimentInfo> experimentInfos;
    private IdHandler idHandler;

    public static LabApp getInstance() {
        if (instance == null) {
            instance = new LabApp();
        }
        return instance;
    }

    private LabApp() {
        initializeObjects();
    }

    private void initializeObjects() {
        labs = new ArrayList<>();
        patients = new ArrayList<>();
        experimentInfos = new ArrayList<>();
        idHandler = new IdHandler();
    }

    public void loginPatient(int patientId, String password) throws PatientNotFound {
        currentPatient = getPatient(patientId, password);
    }

    private Patient getPatient(int patientId, String password) throws PatientNotFound {
        for (Patient patient : patients) {
            if (patient.hasInfo(patientId, password))
                return patient;
        }
        throw new PatientNotFound(patientId);
    }

    public List<ExperimentInfoDTO> getExperimentInfos() {
        List<ExperimentInfoDTO> experimentInfoDTOS = new ArrayList<>();

        for (ExperimentInfo experimentInfo : experimentInfos) {
            experimentInfoDTOS.add(new ExperimentInfoDTO(experimentInfo));
        }
        return experimentInfoDTOS;
    }

    public List<LabDTO> getLabsForExperiments(List<ExperimentInfoDTO> experimentInfoDTOs) {
        List<LabDTO> experimentsLabDTOs = new ArrayList<>();

        for (Lab lab : labs) {
            if (lab.hasSupport(experimentInfoDTOs))
                experimentsLabDTOs.add(new LabDTO(lab));
        }

        return experimentsLabDTOs;
    }

    public List<Date> getTimesForExperiments(LabDTO labDTO, List<ExperimentInfoDTO> experimentInfoDTOs) throws LabNotFound {
        Lab lab = getLab(labDTO.getName());
        return lab.getTimes(experimentInfoDTOs);
    }

    private Lab getLab(String labName) throws LabNotFound {
        for (Lab lab : labs) {
            if (lab.getName().equals(labName)) {
                return lab;
            }
        }
        throw new LabNotFound(labName);
    }

    public void setExperiments(List<ExperimentInfoDTO> experimentInfoDTOs) throws PatientNotLogin,
            ExperimentInfoNotFound, CurrentExperimentNotInstantiated {
        createNewExperiment();
        List<ExperimentInfo> patientExperimentInfos = getExperimentInfos(experimentInfoDTOs);
        currentPatient.setExperimentInfos(patientExperimentInfos);
    }

    public void createNewExperiment() throws PatientNotLogin {
        checkPatientLogin();
        currentPatient.createNewExperiment(idHandler.getNextExperimentId());
    }

    public void checkPatientLogin() throws PatientNotLogin {
        if (currentPatient == null)
            throw new PatientNotLogin();
    }

    private List<ExperimentInfo> getExperimentInfos(List<ExperimentInfoDTO> experimentInfoDTOS) throws ExperimentInfoNotFound {
        List<ExperimentInfo> matchedExperimentInfos = new ArrayList<>();

        for (ExperimentInfoDTO experimentInfoDTO : experimentInfoDTOS) {
            matchedExperimentInfos.add(getExperimentInfo(experimentInfoDTO));
        }
        return matchedExperimentInfos;
    }

    private ExperimentInfo getExperimentInfo(ExperimentInfoDTO experimentInfoDTO) throws ExperimentInfoNotFound {
        for (ExperimentInfo experimentInfo : experimentInfos) {
            if (experimentInfo.getName().equals(experimentInfoDTO.getName())) {
                return experimentInfo;
            }
        }
        throw new ExperimentInfoNotFound();
    }

    public void setLab(LabDTO labDTO) throws PatientNotLogin, LabNotFound, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        Lab patientLab = getLab(labDTO);
        currentPatient.setExperimentLab(patientLab);
    }

    public Lab getLab(LabDTO labDTO) throws LabNotFound {
        for (Lab lab : labs) {
            if (lab.getName().equals(labDTO.getName())) {
                return lab;
            }
        }
        throw new LabNotFound(labDTO.getName());
    }

    public void setTime(Date experimentTime) throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        currentPatient.setExperimentTime(experimentTime);
    }

    public void setInsurance(int insuranceNumber) throws InvalidInsuranceNumber, PatientNotLogin,
            CurrentExperimentNotInstantiated {
        checkPatientLogin();
        validateInsuranceNumber(insuranceNumber);
        currentPatient.setExperimentInsuranceNumber(insuranceNumber);
    }

    private void validateInsuranceNumber(int insuranceNumber) throws InvalidInsuranceNumber {
        if (!InsuranceAPI.getInstance().isValidInsuranceNumber(insuranceNumber))
            throw new InvalidInsuranceNumber();
    }

    public double getTotalPrice() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        checkPatientLogin();
        return currentPatient.getExperimentTotalPrice();
    }

    public void payTotalPrice(String bankSessionId) throws PatientNotLogin, CurrentExperimentNotInstantiated,
            UnsuccessfulPayment, SamplerNotAvailable, SamplerNotAssigned, NoLabAssigned {
        checkPatientLogin();
        currentPatient.payTotalPrice(bankSessionId);
        currentPatient.finalizeCurrentExperiment();
    }
}