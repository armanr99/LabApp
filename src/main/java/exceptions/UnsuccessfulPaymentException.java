package main.java.exceptions;

public class UnsuccessfulPaymentException extends Exception {
    public String toString() {
        return "Payment was unsuccessful";
    }
}
