package main.java.exceptions;

public class InvalidInsuranceNumber extends Exception {
    public String toString() {
        return "Entered insurance number is invalid";
    }
}
