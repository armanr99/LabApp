package main.java.Exceptions;

public class SamplerNotAvailable extends Exception {
    public String toString() {
        return "No sampler was found for given experiments and lab";
    }
}
