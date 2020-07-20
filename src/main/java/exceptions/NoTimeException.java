package main.java.exceptions;

public class NoTimeException extends Exception {
    public String toString() {
        return "No time is available for selected experiments";
    }
}
