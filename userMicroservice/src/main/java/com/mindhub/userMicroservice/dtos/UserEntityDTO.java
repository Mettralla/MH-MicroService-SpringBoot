package com.mindhub.userMicroservice.dtos;

import com.mindhub.userMicroservice.models.RolType;
import com.mindhub.userMicroservice.models.UserEntity;

public class UserEntityDTO {
    private Long id;

    private String username;

    private String email;

    private RolType roles;

    public UserEntityDTO(UserEntity userEntity) {
        id = userEntity.getId();
        username = userEntity.getUsername();
        email = userEntity.getEmail();
        roles = userEntity.getRoles();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public RolType getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserEntityDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
