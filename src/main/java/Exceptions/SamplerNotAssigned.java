package main.java.Exceptions;

public class SamplerNotAssigned extends Exception {
    public String toString() {
        return "No sampler was assigned for experiment";
    }
}
