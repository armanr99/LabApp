package main.java.models.User;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean hasInfo(int id, String password) {
        return (this.id == id && this.password.equals(password));
    }
}
