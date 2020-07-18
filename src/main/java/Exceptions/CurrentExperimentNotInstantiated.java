package main.java.Exceptions;

public class CurrentExperimentNotInstantiated extends Exception {
    public String toString() {
        return "No current experiment is instantiated for user";
    }
}
