package main.java.Views.CommandLineHandler;

import main.java.Controllers.RequestExperiment.RequestExperimentController;

public class CommandLineHandler implements CommandLineHandlerInterface {
    private RequestExperimentController requestExperimentController;

    public CommandLineHandler() {
        requestExperimentController = RequestExperimentController.getInstance();
    }

    public void start() {
    }
}
