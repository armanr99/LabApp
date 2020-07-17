package main.java.Views.CommandLineHandler;

import main.java.Controllers.RequestExperiment.RequestExperimentController;
import main.java.Exceptions.NoExperiments;
import main.java.Exceptions.PatientNotFound;
import main.java.Models.Experiment.ExperimentInfo;

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
                handleGetExperimentInfos();
            } catch(Exception exception) {
                System.out.println("Error: " + exception.toString());
            }
        }
    }

    private void handleLoginPatient(int patientId) throws PatientNotFound {
        System.out.print("Please enter your password: ");
        String password = scanner.next();

        requestExperimentController.loginPatient(patientId, password);
    }

    private void handleGetExperimentInfos() throws NoExperiments {
        System.out.println("Please choose experiments number separated by space:");

        List<ExperimentInfo> experimentInfos = requestExperimentController.getExperimentInfos();
        if(experimentInfos.size() == 0) {
            throw new NoExperiments();
        }

        printExperimentInfos(experimentInfos);

        List<ExperimentInfo> selectedExperimentInfos = getSelectedExperimentInfos(experimentInfos);
        if(selectedExperimentInfos.size() == 0) {
            System.out.println("You didn't select any experiment");
            handleGetExperimentInfos();
        }

    }

    private void printExperimentInfos(List<ExperimentInfo> experimentInfos) {
        for(int i = 0; i < experimentInfos.size(); i++) {
            String lineResult = String.format("%d: %s", i, experimentInfos.get(i).getName());
            System.out.println(lineResult);
        }
    }

    private List<ExperimentInfo> getSelectedExperimentInfos(List<ExperimentInfo> experimentInfos) {
        String selectedExperimentsStr = scanner.nextLine();
        String[] splitExperimentsStr = selectedExperimentsStr.split("\\s+");
        List<ExperimentInfo> selectedExperimentInfos = new ArrayList<>();

        for(String splitExperimentStr : splitExperimentsStr) {
            int experimentIndex = Integer.parseInt(splitExperimentStr);
            selectedExperimentInfos.add(experimentInfos.get(experimentIndex));
        }

        return selectedExperimentInfos;
    }
}
