package main.java.Models.General;

public class Payment {
    private double totalPrice;
    private String verificationCode;

    public Payment(double totalPrice, String verificationCode) {
        this.totalPrice = totalPrice;
        this.verificationCode = verificationCode;
    }
}
