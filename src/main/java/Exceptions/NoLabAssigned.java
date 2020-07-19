package main.java.Exceptions;

public class NoLabAssigned extends Exception {
    public String toString() {
        return "No labs was assigned for current experiment";
    }
}
