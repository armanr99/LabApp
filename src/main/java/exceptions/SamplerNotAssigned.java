package main.java.exceptions;

public class SamplerNotAssigned extends Exception {
    public String toString() {
        return "No sampler was assigned for experiment";
    }
}
