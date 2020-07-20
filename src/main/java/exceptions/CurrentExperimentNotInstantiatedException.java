package main.java.exceptions;

public class CurrentExperimentNotInstantiatedException extends Exception {
    public String toString() {
        return "No current experiment is instantiated for user";
    }
}
