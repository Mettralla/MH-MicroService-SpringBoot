package com.mindhub.userMicroservice.events;

public class RegisteredUser {
    private String username;
    private String email;


    public RegisteredUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public RegisteredUser() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
