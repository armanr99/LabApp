package main.java.exceptions;

public class SamplerNotAssignedException extends Exception {
    public String toString() {
        return "No sampler was assigned for experiment";
    }
}
