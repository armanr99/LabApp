package main.java.Views.CommandLineHandler;

import main.java.Controllers.RequestExperiment.RequestExperimentController;
import main.java.Exceptions.*;
import main.java.Models.DTOs.ExperimentInfoDTO;
import main.java.Models.DTOs.LabDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineHandler implements CommandLineHandlerInterface {
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

    private void handleExperimentRequest() throws NoExperiments, NoLabs, WrongIndex {
        List<ExperimentInfoDTO> selectedExperiments = getSelectedExperimentInfos();
        LabDTO selectedLab = getSelectedLab(selectedExperiments);
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

    private LabDTO getSelectedLab(List<ExperimentInfoDTO> experimentInfoDTOs) throws NoLabs, WrongIndex {
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
            String lineResult = String.format("%d: Name: %s, Address: %s", i, labDTO.getName(), labDTO.getFullAddress());
            System.out.println(lineResult);
        }
    }

    private LabDTO getSelectedLabInput(List<LabDTO> labDTOs) {
        System.out.print("Please select lab number: ");
        int labIndex;

        try {
            String labIndexStr = scanner.nextLine();
            validateIntegerStr(labIndexStr);
            labIndex = Integer.parseInt(labIndexStr);
            validateIndex(labIndex, labDTOs);
        } catch (WrongIndex | WrongIntegerFormat exception) {
            System.out.println(exception.toString());
            return getSelectedLabInput(labDTOs);
        }

        return labDTOs.get(labIndex);
    }

    private void validateIndex(int objectIndex, List<?> objects) throws WrongIndex {
        if (objectIndex < 0 || objectIndex >= objects.size())
            throw new WrongIndex();
    }

    private void validateIntegerStr(String integerStr) throws WrongIntegerFormat {
        if (!integerStr.matches(("-?\\d+")))
            throw new WrongIntegerFormat();
    }
}
