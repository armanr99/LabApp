package main.java.exceptions;

public class SamplerNotAvailable extends Exception {
    public String toString() {
        return "No sampler was found for given experiments and lab";
    }
}
