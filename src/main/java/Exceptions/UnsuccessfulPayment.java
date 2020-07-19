package main.java.Exceptions;

public class UnsuccessfulPayment extends Exception {
    public String toString() {
        return "Payment was unsuccessful";
    }
}
