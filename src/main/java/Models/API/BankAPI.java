package main.java.Models.API;

import main.java.Exceptions.UnsuccessfulPayment;
import main.java.Models.General.Payment;

import java.util.Random;

public class BankAPI {
    private static BankAPI instance;

    public static BankAPI getInstance() {
        if (instance == null) {
            instance = new BankAPI();
        }
        return instance;
    }

    public Payment pay(String sessionId, double totalPrice) throws UnsuccessfulPayment {
        boolean isSuccessfulPay = (Math.random() > 0.1);

        if (isSuccessfulPay) {
            return new Payment(totalPrice, getRandomString());
        } else {
            throw new UnsuccessfulPayment();
        }
    }

    public String getRandomString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 16;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
