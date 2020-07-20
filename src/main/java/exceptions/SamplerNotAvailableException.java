package main.java.exceptions;

public class SamplerNotAvailableException extends Exception {
    public String toString() {
        return "No sampler was found for given experiments";
    }
}
