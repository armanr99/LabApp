package main.java.exceptions;

public class UnsuccessfulPayment extends Exception {
    public String toString() {
        return "Payment was unsuccessful";
    }
}
