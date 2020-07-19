package main.java.models.General;

public class Address {
    private String city;
    private String district;
    private String fullAddress;

    public Address(String city, String district, String fullAddress) {
        this.city = city;
        this.district = district;
        this.fullAddress = fullAddress;
    }

    public String getFullAddress() {
        return fullAddress;
    }
}
