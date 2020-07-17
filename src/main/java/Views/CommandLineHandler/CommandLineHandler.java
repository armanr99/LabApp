package main.java.Views.CommandLineHandler;

import main.java.Controllers.RequestExperiment.RequestExperimentController;
import main.java.Exceptions.NoExperiments;
import main.java.Exceptions.PatientNotFound;
import main.java.Models.DTOs.ExperimentInfoDTO;

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
                int patientId = Integer.parseInt(scanner.next());

                handleLoginPatient(patientId);
                handleExperimentRequest();
            } catch(Exception exception) {
                System.out.println("Error: " + exception.toString());
            }
        }
    }

    private void handleExperimentRequest() throws NoExperiments {
        List<ExperimentInfoDTO> selectedExperiments = getSelectedExperimentInfos();
    }

    private void handleLoginPatient(int patientId) throws PatientNotFound {
        System.out.print("Please enter your password: ");
        String password = scanner.next();

        requestExperimentController.loginPatient(patientId, password);
    }

    private List<ExperimentInfoDTO> getSelectedExperimentInfos() throws NoExperiments {
        System.out.println("Please choose experiments number separated by space:");

        List<ExperimentInfoDTO> experimentInfos = requestExperimentController.getExperimentInfos();
        if(experimentInfos.size() == 0) {
            throw new NoExperiments();
        }

        printExperimentInfos(experimentInfos);

        List<ExperimentInfoDTO> selectedExperimentInfos = getSelectedExperimentInfos(experimentInfos);
        if(selectedExperimentInfos.size() == 0) {
            System.out.println("You didn't select any experiment");
            return getSelectedExperimentInfos();
        } else {
            return selectedExperimentInfos;
        }
    }

    private void printExperimentInfos(List<ExperimentInfoDTO> experimentInfos) {
        for(int i = 0; i < experimentInfos.size(); i++) {
            String lineResult = String.format("%d: %s", i, experimentInfos.get(i).getName());
            System.out.println(lineResult);
        }
    }

    private List<ExperimentInfoDTO> getSelectedExperimentInfos(List<ExperimentInfoDTO> experimentInfos) {
        String selectedExperimentsStr = scanner.nextLine();
        String[] splitExperimentsStr = selectedExperimentsStr.split("\\s+");
        List<ExperimentInfoDTO> selectedExperimentInfos = new ArrayList<>();

        for(String splitExperimentStr : splitExperimentsStr) {
            int experimentIndex = Integer.parseInt(splitExperimentStr);
            selectedExperimentInfos.add(experimentInfos.get(experimentIndex));
        }

        return selectedExperimentInfos;
    }
}
