package main.java.Controllers.RequestExperiment;

public class RequestExperimentController implements RequestExperimentControllerInterface {
    private static RequestExperimentController instance;

    public static RequestExperimentController getInstance() {
        if(instance == null) {
            instance = new RequestExperimentController();
        }
        return instance;
    }
}
