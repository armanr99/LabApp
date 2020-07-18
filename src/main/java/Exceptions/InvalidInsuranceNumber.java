package main.java.Exceptions;

public class InvalidInsuranceNumber extends Exception {
    public String toString() {
        return "Entered insurance number is invalid";
    }
}
