package main.java.exceptions;

public class LabNotFound extends Exception {
    private String labName;

    public LabNotFound(String labName) {
        this.labName = labName;
    }

    public String toString() {
        return String.format("Lab with name %s was not found", labName);
    }
}
