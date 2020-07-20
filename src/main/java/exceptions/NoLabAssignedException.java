package main.java.exceptions;

public class NoLabAssignedException extends Exception {
    public String toString() {
        return "No labs was assigned for current experiment";
    }
}
