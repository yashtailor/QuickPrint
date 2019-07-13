package com.android.quickjob;

public class UserDatabase {
    private String email,password;

    public UserDatabase(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUserEmail() {
        return email;
    }

    public String getUserPassword() {
        return password;
    }
}
