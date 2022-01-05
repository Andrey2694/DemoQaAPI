package models;

public class UserData {
    private final String userName;
    private final String password;

    public UserData(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}