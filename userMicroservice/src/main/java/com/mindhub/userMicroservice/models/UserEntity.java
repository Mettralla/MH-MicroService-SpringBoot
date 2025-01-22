package com.mindhub.userMicroservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username can't be blank")
    @NotNull(message = "Username can't be null")
    private String username;

    @NotBlank(message = "Password can't be blank")
    @NotNull(message = "Password can't be null")
    private String password;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email can't be blank")
    private String email;

    @Enumerated(EnumType.STRING)
    private  RolType roles = RolType.USER;

    public UserEntity() {}

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RolType getRoles() {
        return roles;
    }

    public void setRoles(RolType roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
