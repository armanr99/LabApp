package main.java.models.DTO;

import main.java.models.Lab.Lab;

public class LabDTO {
    private int id;
    private String name;
    private String fullAddress;

    public LabDTO(Lab lab) {
        this.id = lab.getId();
        this.name = lab.getName();
        this.fullAddress = lab.getFullAddress();
    }

    public String getName() {
        return name;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public int getId() {
        return id;
    }
}
