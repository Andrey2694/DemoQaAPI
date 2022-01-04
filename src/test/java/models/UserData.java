package models;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    private final String userName;
    private final String password;

    public UserData(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Map<String, String> getUserData() {
        Map<String, String> userData = new HashMap<>();
        userData.put("userName", userName);
        userData.put("password", password);
        return userData;
    }
}