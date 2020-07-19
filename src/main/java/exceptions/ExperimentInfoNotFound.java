package main.java.exceptions;

public class ExperimentInfoNotFound extends Exception {
    public String toString() {
        return "ExperimentInfo associated with given DTO was not found";
    }
}
