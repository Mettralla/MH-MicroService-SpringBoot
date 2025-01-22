package com.mindhub.userMicroservice.services.impl;

import com.mindhub.userMicroservice.dtos.NewUserEntity;
import com.mindhub.userMicroservice.dtos.UserEntityDTO;
import com.mindhub.userMicroservice.exceptions.EmailAlreadyExistsException;
import com.mindhub.userMicroservice.models.RolType;
import com.mindhub.userMicroservice.models.UserEntity;
import com.mindhub.userMicroservice.repositories.UserEntityRepository;
import com.mindhub.userMicroservice.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public List<UserEntityDTO> getAllUserEntities() {
        return userEntityRepository.findAll()
                                   .stream()
                                   .map( user -> new UserEntityDTO(user) )
                                   .toList();
    }

    @Override
    public List<String> getAllRoles() {
        return Arrays.stream(RolType.values())
                     .map(Enum::name)
                     .collect(Collectors.toList());
    }

    @Override
    public UserEntityDTO createUserEntity(NewUserEntity newUserEntity) throws EmailAlreadyExistsException {
        if (userEntityRepository.existsByEmail(newUserEntity.email())) {
            throw new EmailAlreadyExistsException("Email already exists: " + newUserEntity.email());
        }

        UserEntity userEntity = new UserEntity(
                newUserEntity.username(),
                newUserEntity.password(),
                newUserEntity.email()
        );

        UserEntity createdUserEntity = userEntityRepository.save(userEntity);
        return new UserEntityDTO(createdUserEntity);
    }
}
