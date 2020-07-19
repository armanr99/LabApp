package main.java.models.User;

import main.java.models.General.Entity;

public class User extends Entity {
    private String name;
    private String email;
    private String password;

    public User(int id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean hasPassword(String password) {
        return (this.password.equals(password));
    }
}
