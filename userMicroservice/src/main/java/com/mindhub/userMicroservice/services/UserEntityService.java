package com.mindhub.userMicroservice.services;

import com.mindhub.userMicroservice.dtos.EditUserEntity;
import com.mindhub.userMicroservice.dtos.NewUserEntity;
import com.mindhub.userMicroservice.dtos.UserEntityDTO;
import com.mindhub.userMicroservice.exceptions.EmailAlreadyExistsException;
import com.mindhub.userMicroservice.exceptions.UserEntityNotFoundException;

import java.util.List;

public interface UserEntityService {

    List<UserEntityDTO> getAllUserEntities();
    List<String> getAllRoles();
    UserEntityDTO createUserEntity(NewUserEntity newUserEntity) throws EmailAlreadyExistsException;
    UserEntityDTO showUserEntity(Long id) throws UserEntityNotFoundException;
    UserEntityDTO updateUserEntity(Long id, EditUserEntity updatedUserData) throws UserEntityNotFoundException;

}
