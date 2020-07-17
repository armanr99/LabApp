package main.java.Models.Lab;

import main.java.Models.General.Address;
import main.java.Models.User.Sampler;

import java.util.List;

public class Lab {
    private String name;
    private Address address;
    private List<Sampler> samplers;

    public Lab(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public void setSamplers(List<Sampler> samplers) {
        this.samplers = samplers;
    }
}
