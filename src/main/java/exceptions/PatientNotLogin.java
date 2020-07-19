package main.java.exceptions;

public class PatientNotLogin extends Exception {
    public String toString() {
        return "No patient is currently logged in";
    }
}
