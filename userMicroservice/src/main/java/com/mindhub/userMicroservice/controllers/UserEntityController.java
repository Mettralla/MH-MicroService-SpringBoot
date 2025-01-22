package com.mindhub.userMicroservice.controllers;

import com.mindhub.userMicroservice.dtos.NewUserEntity;
import com.mindhub.userMicroservice.dtos.UserEntityDTO;
import com.mindhub.userMicroservice.exceptions.EmailAlreadyExistsException;
import com.mindhub.userMicroservice.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    // ====================== USERS =========================

    @GetMapping()
    public ResponseEntity<Object> getAllUserEntities() {
        try {
            List<UserEntityDTO> userList = userEntityService.getAllUserEntities();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public  ResponseEntity<Object> createNewUserEntity(@Valid @RequestBody NewUserEntity newUserData) {
        try {
            UserEntityDTO createdAdmin = userEntityService.createUserEntity(newUserData);
            return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ====================== ROLES =========================

    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return userEntityService.getAllRoles();
    }
}
