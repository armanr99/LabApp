package main.java.exceptions;

public class PatientNotFoundException extends Exception {
    private int patientId;

    public PatientNotFoundException(int patientId) {
        this.patientId = patientId;
    }

    public String toString() {
        return String.format("Patient with id %d and given password was not found", patientId);
    }
}
