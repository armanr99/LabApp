package main.java.views.CommandLineHandler;

import main.java.controllers.RequestExperiment.RequestExperimentController;
import main.java.exceptions.*;
import main.java.models.DTO.ExperimentInfoDTO;
import main.java.models.DTO.LabDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CommandLineHandler {
    private Scanner scanner;
    private RequestExperimentController requestExperimentController;

    public CommandLineHandler() {
        scanner = new Scanner(System.in);
        requestExperimentController = RequestExperimentController.getInstance();
    }

    public void start() {
        System.out.println("Welcome to LabApp!");

        while (true) {
            try {
                System.out.print("Please enter your id: ");
                String patientIdStr = scanner.nextLine();
                validateIntegerStr(patientIdStr);
                int patientId = Integer.parseInt(patientIdStr);

                handleLoginPatient(patientId);
                handleExperimentRequest();
            } catch (Exception exception) {
                System.out.println("Error: " + exception.toString());
            }
        }
    }

    private void handleExperimentRequest() throws NoExperiments, NoLabs, LabNotFound, ExperimentInfoNotFound,
            PatientNotLogin, CurrentExperimentNotInstantiated, SamplerNotAvailable, SamplerNotAssigned, NoLabAssigned {
        List<ExperimentInfoDTO> selectedExperiments = getSelectedExperimentInfos();
        requestExperimentController.setExperiments(selectedExperiments);

        LabDTO selectedLab = getSelectedLab(selectedExperiments);
        requestExperimentController.setLab(selectedLab);

        Date selectedTime = getExperimentTime(selectedLab, selectedExperiments);
        requestExperimentController.setTime(selectedTime);

        handleInsurance();

        handlePay();
    }

    private void handleLoginPatient(int patientId) throws PatientNotFound {
        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        requestExperimentController.loginPatient(patientId, password);
    }

    private List<ExperimentInfoDTO> getSelectedExperimentInfos() throws NoExperiments {
        List<ExperimentInfoDTO> experimentInfos = requestExperimentController.getExperimentInfos();
        if (experimentInfos.size() == 0) {
            throw new NoExperiments();
        }
        printExperimentInfos(experimentInfos);

        return getSelectedExperimentInfosInput(experimentInfos);
    }

    private void printExperimentInfos(List<ExperimentInfoDTO> experimentInfos) {
        for (int i = 0; i < experimentInfos.size(); i++) {
            String lineResult = String.format("%d: %s", i, experimentInfos.get(i).getName());
            System.out.println(lineResult);
        }
    }

    private List<ExperimentInfoDTO> getSelectedExperimentInfosInput(List<ExperimentInfoDTO> experimentInfos) {
        System.out.print("Please choose experiments number separated by space: ");

        String selectedExperimentsStr = scanner.nextLine();
        String[] splitExperimentsStr = selectedExperimentsStr.split("\\s+");
        List<ExperimentInfoDTO> selectedExperimentInfos = new ArrayList<>();

        try {
            for (String splitExperimentStr : splitExperimentsStr) {
                validateIntegerStr(splitExperimentStr);
                int experimentIndex = Integer.parseInt(splitExperimentStr);
                selectedExperimentInfos.add(experimentInfos.get(experimentIndex));
            }
        } catch (WrongIntegerFormat exception) {
            System.out.println(exception.toString());
            return getSelectedExperimentInfosInput(experimentInfos);
        }

        return selectedExperimentInfos;
    }

    private LabDTO getSelectedLab(List<ExperimentInfoDTO> experimentInfoDTOs) throws NoLabs {
        List<LabDTO> experimentsLabDTOs = requestExperimentController.getLabsForExperiments(experimentInfoDTOs);
        if (experimentsLabDTOs.size() == 0) {
            throw new NoLabs();
        }
        printLabInfos(experimentsLabDTOs);

        return getSelectedLabInput(experimentsLabDTOs);
    }

    private void printLabInfos(List<LabDTO> labDTOs) {
        for (int i = 0; i < labDTOs.size(); i++) {
            LabDTO labDTO = labDTOs.get(i);
            String lineResult = String.format("%d: Name: %s, Address: %s", i, labDTO.getName(),
                    labDTO.getFullAddress());
            System.out.println(lineResult);
        }
    }

    private LabDTO getSelectedLabInput(List<LabDTO> labDTOs) {
        System.out.print("Please select lab number: ");
        LabDTO labDTO;

        try {
            labDTO = (LabDTO) getListIndexInput(labDTOs);
        } catch (WrongIndex | WrongIntegerFormat exception) {
            System.out.println(exception.toString());
            return getSelectedLabInput(labDTOs);
        }

        return labDTO;
    }

    private void validateIndex(int objectIndex, List<?> objects) throws WrongIndex {
        if (objectIndex < 0 || objectIndex >= objects.size())
            throw new WrongIndex();
    }

    private void validateIntegerStr(String integerStr) throws WrongIntegerFormat {
        try {
            Integer.parseInt(integerStr);
        } catch (NumberFormatException exception) {
            throw new WrongIntegerFormat();
        }
    }

    private Date getExperimentTime(LabDTO labDTO, List<ExperimentInfoDTO> experimentInfoDTOS) throws LabNotFound {
        List<Date> experimentTimes = requestExperimentController.getTimesForExperiments(labDTO, experimentInfoDTOS);
        printExperimentTimes(experimentTimes);

        return getSelectedTimeInput(experimentTimes);
    }

    private void printExperimentTimes(List<Date> experimentTimes) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < experimentTimes.size(); i++) {
            String lineResult = String.format("%d: %s", i, dateFormatter.format(experimentTimes.get(i)));
            System.out.println(lineResult);
        }
    }

    public Date getSelectedTimeInput(List<Date> experimentTimes) {
        System.out.print("Please select time number: ");
        Date selectedDate;

        try {
            selectedDate = (Date) getListIndexInput(experimentTimes);
        } catch (WrongIntegerFormat | WrongIndex exception) {
            System.out.println(exception.toString());
            return getSelectedTimeInput(experimentTimes);
        }

        return selectedDate;
    }

    public Object getListIndexInput(List<?> indexObjects) throws WrongIndex, WrongIntegerFormat {
        int indexInteger = getIntegerInput();
        validateIndex(indexInteger, indexObjects);
        return indexObjects.get(indexInteger);
    }

    public int getIntegerInput() throws WrongIntegerFormat {
        String inputStr = scanner.nextLine();
        validateIntegerStr(inputStr);
        return Integer.parseInt(inputStr);
    }

    private void handleInsurance() throws PatientNotLogin, CurrentExperimentNotInstantiated {
        System.out.print("Do you want to use insurance? y/n: ");
        String answerInput = scanner.nextLine();

        try {
            validateIntegerStr(answerInput);
        } catch (WrongIntegerFormat exception) {
            System.out.println(exception.toString());
            handleInsurance();
            return;
        }

        if (answerInput.equals("y")) {
            try {
                handleInsuranceCode();
            } catch (InvalidInsuranceNumber exception) {
                System.out.println(exception.toString());
                handleInsurance();
            }
        }
    }

    private void handleInsuranceCode() throws PatientNotLogin, InvalidInsuranceNumber,
            CurrentExperimentNotInstantiated {
        System.out.println("Please enter your insurance code: ");
        int insuranceNumber;

        try {
            insuranceNumber = getIntegerInput();
        } catch (WrongIntegerFormat exception) {
            System.out.println(exception.toString());
            handleInsuranceCode();
            return;
        }

        requestExperimentController.setInsurance(insuranceNumber);
    }

    private void handlePay() throws PatientNotLogin, CurrentExperimentNotInstantiated, SamplerNotAvailable,
            SamplerNotAssigned, NoLabAssigned {
        double totalPrice = requestExperimentController.getTotalPrice();
        System.out.println(String.format("Your total price is: %f", totalPrice));

        handlePayInput();
    }

    private void handlePayInput() throws PatientNotLogin, CurrentExperimentNotInstantiated, SamplerNotAvailable,
            SamplerNotAssigned, NoLabAssigned {
        System.out.print("Please enter your bank user session id: ");
        String bandSessionId = scanner.nextLine();

        try {
            requestExperimentController.payTotalPrice(bandSessionId);
        } catch (UnsuccessfulPayment exception) {
            System.out.println(exception.toString());
            handlePay();
            return;
        }

        System.out.println("Your payment was successful");
    }
}