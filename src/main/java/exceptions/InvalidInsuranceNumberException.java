package main.java.exceptions;

public class InvalidInsuranceNumberException extends Exception {
    public String toString() {
        return "Entered insurance number is invalid";
    }
}
