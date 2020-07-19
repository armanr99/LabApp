package main.java.models.User;

import main.java.models.Storage.ContainerEntity;

public class User extends ContainerEntity {
    private String name;
    private String email;
    private String password;

    public User(int id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean hasInfo(int id, String password) {
        return (this.id == id && this.password.equals(password));
    }
}
