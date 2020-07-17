package main.java.Views.CommandLineHandler;

import main.java.Controllers.RequestExperiment.RequestExperimentController;
import main.java.Exceptions.PatientNotFound;

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
                int patientId = scanner.nextInt();

                handleLoginPatient(patientId);
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
}
