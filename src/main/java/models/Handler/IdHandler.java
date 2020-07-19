package main.java.models.Handler;

public class IdHandler {
    private int currentExperimentId;

    public IdHandler() {
        currentExperimentId = 0;
    }

    public int getNextExperimentId() {
        return currentExperimentId++;
    }
}
