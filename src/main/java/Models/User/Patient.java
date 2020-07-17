package main.java.Models.User;

public class Patient extends User {
    private String ssn;
    private Address address;

    public Patient(String name, String email, String password, String ssn, Address address) {
        super(name, email, password);
        this.ssn = ssn;
        this.address = address;
    }
}
