package main.java.exceptions;

public class PatientNotLoginException extends Exception {
    public String toString() {
        return "No patient is currently logged in";
    }
}
