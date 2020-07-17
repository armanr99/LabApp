package main.java.Models.DTOs;

import main.java.Models.Lab.Lab;

public class LabDTO {
    private String name;
    private String fullAddress;

    public LabDTO(Lab lab) {
        this.name = lab.getName();
        this.fullAddress = lab.getFullAddress();
    }

    public String getName() {
        return name;
    }

    public String getFullAddress() {
        return fullAddress;
    }
}
