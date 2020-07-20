package main.java.exceptions;

public class LabNotFoundException extends Exception {
    private String labName;

    public LabNotFoundException(String labName) {
        this.labName = labName;
    }

    public String toString() {
        return String.format("Lab with name %s was not found", labName);
    }
}
