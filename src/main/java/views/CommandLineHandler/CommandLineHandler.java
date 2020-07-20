package main.java.views.CommandLineHandler;

import main.java.controllers.RequestExperiment.RequestExperimentController;
import main.java.exceptions.*;
import main.java.models.DTO.ExperimentInfoDTO;
import main.java.models.DTO.LabDTO;

import java.io.InvalidObjectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CommandLineHandler {
    private Scanner scanner;
    private RequestExperimentController requestExperimentController;

    public CommandLineHandler() throws InvalidObjectException {
        scanner = new Scanner(System.in);
        requestExperimentController = RequestExperimentController.getInstance();
    }

    public void start() {
        System.out.println("Welcome to LabApp!");

        while (true) {
            try {
                handleLoginPatient();
                handleExperimentRequest();
                requestExperimentController.logoutPatient();
            } catch (Exception exception) {
                System.out.println("Error: " + exception.toString());
                requestExperimentController.logoutPatient();
            }
        }
    }

    private void handleLoginPatient() throws PatientNotFoundException, WrongIntegerFormatException {
        System.out.print("Please enter your id: ");

        String patientIdStr = scanner.nextLine();
        validateIntegerStr(patientIdStr);
        int patientId = Integer.parseInt(patientIdStr);

        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        requestExperimentController.loginPatient(patientId, password);
    }

    private void handleExperimentRequest() throws NoExperimentsException, NoLabsException, LabNotFoundException,
            PatientNotLoginException, CurrentExperimentNotInstantiatedException, SamplerNotAvailableException, SamplerNotAssignedException, NoLabAssignedException
            , InvalidObjectException, NoTimeException {
        handleSelectExperiments();
        handleSelectLab();
        handleSelectTime();
        handleInsurance();
        handlePay();
    }

    private void handleSelectExperiments() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException, NoExperimentsException,
            InvalidObjectException {
        List<ExperimentInfoDTO> selectedExperiments = getSelectedExperimentInfos();
        requestExperimentController.setExperimentInfos(selectedExperiments);
    }

    private List<ExperimentInfoDTO> getSelectedExperimentInfos() throws NoExperimentsException, PatientNotLoginException {
        List<ExperimentInfoDTO> experimentInfos = requestExperimentController.getExperimentInfos();
        if (experimentInfos.isEmpty()) {
            throw new NoExperimentsException();
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
        System.out.print("Please choose experiment numbers separated by space: ");

        String selectedExperimentsStr = scanner.nextLine();
        String[] splitExperimentsStr = selectedExperimentsStr.split("\\s+");
        List<ExperimentInfoDTO> selectedExperimentInfos = new ArrayList<>();

        try {
            for (String splitExperimentStr : splitExperimentsStr) {
                validateIntegerStr(splitExperimentStr);
                int experimentIndex = Integer.parseInt(splitExperimentStr);
                validateIndex(experimentIndex, experimentInfos);
                selectedExperimentInfos.add(experimentInfos.get(experimentIndex));
            }
        } catch (WrongIntegerFormatException | WrongIndexException exception) {
            System.out.println(exception.toString());
            return getSelectedExperimentInfosInput(experimentInfos);
        }

        return selectedExperimentInfos;
    }

    private void handleSelectLab() throws PatientNotLoginException, NoLabsException, CurrentExperimentNotInstantiatedException, LabNotFoundException {
        LabDTO selectedLab = getSelectedLab();
        requestExperimentController.setExperimentLab(selectedLab);
    }

    private LabDTO getSelectedLab() throws NoLabsException, PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        List<LabDTO> experimentsLabDTOs = requestExperimentController.getExperimentLabs();
        if (experimentsLabDTOs.isEmpty()) {
            throw new NoLabsException();
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
        } catch (WrongIndexException | WrongIntegerFormatException exception) {
            System.out.println(exception.toString());
            return getSelectedLabInput(labDTOs);
        }

        return labDTO;
    }

    private void validateIndex(int objectIndex, List<?> objects) throws WrongIndexException {
        if (objectIndex < 0 || objectIndex >= objects.size())
            throw new WrongIndexException();
    }

    private void validateIntegerStr(String integerStr) throws WrongIntegerFormatException {
        try {
            Integer.parseInt(integerStr);
        } catch (NumberFormatException exception) {
            throw new WrongIntegerFormatException();
        }
    }

    private void handleSelectTime() throws NoLabAssignedException, PatientNotLoginException, CurrentExperimentNotInstantiatedException, NoTimeException {
        Date selectedTime = getExperimentTime();
        requestExperimentController.setExperimentTime(selectedTime);
    }

    private Date getExperimentTime() throws NoLabAssignedException,
            PatientNotLoginException, CurrentExperimentNotInstantiatedException, NoTimeException {
        List<Date> experimentTimes = requestExperimentController.getExperimentTimes();
        if (experimentTimes.isEmpty()) {
            throw new NoTimeException();
        }
        printExperimentTimes(experimentTimes);

        return getSelectedTimeInput(experimentTimes);
    }

    private void printExperimentTimes(List<Date> experimentTimes) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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
        } catch (WrongIntegerFormatException | WrongIndexException exception) {
            System.out.println(exception.toString());
            return getSelectedTimeInput(experimentTimes);
        }

        return selectedDate;
    }

    public Object getListIndexInput(List<?> indexObjects) throws WrongIndexException, WrongIntegerFormatException {
        int indexInteger = getIntegerInput();
        validateIndex(indexInteger, indexObjects);
        return indexObjects.get(indexInteger);
    }

    public int getIntegerInput() throws WrongIntegerFormatException {
        String inputStr = scanner.nextLine();
        validateIntegerStr(inputStr);
        return Integer.parseInt(inputStr);
    }

    private void handleInsurance() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException {
        System.out.print("Do you want to use insurance? y/n: ");
        String answerInput = scanner.nextLine();

        try {
            validateQuestionInputStr(answerInput);
        } catch (WrongQuestionInputFormatException exception) {
            System.out.println(exception.toString());
            handleInsurance();
            return;
        }

        if (answerInput.equals("y")) {
            try {
                handleInsuranceCode();
            } catch (InvalidInsuranceNumberException exception) {
                System.out.println(exception.toString());
                handleInsurance();
            }
        }
    }

    private void validateQuestionInputStr(String inputStr) throws WrongQuestionInputFormatException {
        if (!inputStr.equals("y") && !inputStr.equals("n"))
            throw new WrongQuestionInputFormatException();
    }

    private void handleInsuranceCode() throws PatientNotLoginException, InvalidInsuranceNumberException,
            CurrentExperimentNotInstantiatedException {
        System.out.print("Please enter your insurance code: ");
        int insuranceNumber;

        try {
            insuranceNumber = getIntegerInput();
        } catch (WrongIntegerFormatException exception) {
            System.out.println(exception.toString());
            handleInsuranceCode();
            return;
        }

        requestExperimentController.setExperimentInsurance(insuranceNumber);
    }

    private void handlePay() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException, SamplerNotAvailableException,
            SamplerNotAssignedException, NoLabAssignedException, InvalidObjectException {
        double totalPrice = requestExperimentController.getExperimentTotalPrice();
        System.out.println(String.format("Your total price is: %f", totalPrice));

        handlePayInput();
    }

    private void handlePayInput() throws PatientNotLoginException, CurrentExperimentNotInstantiatedException, SamplerNotAvailableException,
            SamplerNotAssignedException, NoLabAssignedException, InvalidObjectException {
        System.out.print("Please enter your bank user session id: ");
        String bandSessionId = scanner.nextLine();

        try {
            requestExperimentController.payExperimentTotalPrice(bandSessionId);
        } catch (UnsuccessfulPaymentException exception) {
            System.out.println(exception.toString());
            handlePay();
            return;
        }

        System.out.println("Your payment was successful");
        System.out.println("Experiment request is successfully submitted");
    }
}
