package main.java.exceptions;

public class NoLabAssigned extends Exception {
    public String toString() {
        return "No labs was assigned for current experiment";
    }
}
