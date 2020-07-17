package main.java.Exceptions;

public class NoLabs extends Exception {
    public String toString() {
        return "No labs was found for selected experiments";
    }
}
