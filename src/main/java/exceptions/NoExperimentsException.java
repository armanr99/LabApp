package main.java.exceptions;

public class NoExperimentsException extends Exception {
    public String toString() {
        return "No experiments was found!";
    }
}
