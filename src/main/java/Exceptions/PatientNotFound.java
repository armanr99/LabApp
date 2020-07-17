package main.java.Exceptions;

public class PatientNotFound extends Exception {
    private int patientId;

    public PatientNotFound(int patientId) {
        this.patientId = patientId;
    }

    public String toString() {
        return String.format("Patient with id %d and given password was not found", patientId);
    }
}
