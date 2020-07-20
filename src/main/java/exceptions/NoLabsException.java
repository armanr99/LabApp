package main.java.exceptions;

public class NoLabsException extends Exception {
    public String toString() {
        return "No labs was found for selected experiments";
    }
}
