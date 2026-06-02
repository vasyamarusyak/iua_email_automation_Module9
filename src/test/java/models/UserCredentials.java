package models;

import lombok.Getter;

@Getter
public class UserCredentials {
    String username;
    String password;

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
