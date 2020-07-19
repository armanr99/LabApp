package main.java.models.API;

public class InsuranceAPI {
    private static InsuranceAPI instance;

    public static InsuranceAPI getInstance() {
        if (instance == null) {
            instance = new InsuranceAPI();
        }
        return instance;
    }

    public boolean isValidInsuranceNumber(int insuranceNumber) {
        return (Math.random() < 0.5);
    }

    public double getInsurancePercentage(int insuranceNumber) {
        return Math.random();
    }
}
