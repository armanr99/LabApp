package main.java.exceptions;

public class NoTime extends Exception {
    public String toString() {
        return "No time is available for selected experiments";
    }
}
