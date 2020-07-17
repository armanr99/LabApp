package main.java.Models.User;

import main.java.Models.General.Address;

public class Patient extends User {
    private String ssn;
    private Address address;

    public Patient(int id, String name, String email, String password, String ssn, Address address) {
        super(id, name, email, password);
        this.ssn = ssn;
        this.address = address;
    }
}
