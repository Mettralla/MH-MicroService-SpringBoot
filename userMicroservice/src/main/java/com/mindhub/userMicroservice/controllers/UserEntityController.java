package com.mindhub.userMicroservice.controllers;

import com.mindhub.userMicroservice.dtos.EditUserEntity;
import com.mindhub.userMicroservice.dtos.NewUserEntity;
import com.mindhub.userMicroservice.dtos.UserEntityDTO;
import com.mindhub.userMicroservice.exceptions.EmailAlreadyExistsException;
import com.mindhub.userMicroservice.exceptions.UserEntityNotFoundException;
import com.mindhub.userMicroservice.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "User Entity", description = "Operations related to User Entity")
    @Operation(summary = "Get All Users", description = "Retrieve all User entities.")
    public ResponseEntity<Object> getAllUserEntities() {
        try {
            List<UserEntityDTO> userList = userEntityService.getAllUserEntities();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Tag(name = "User Entity", description = "Operations related to User Entity")
    @Operation(summary = "Shows User", description = "Retrieve an User entity.")
    public ResponseEntity<Object> showUserEntity(@PathVariable Long id) {
        try {
            UserEntityDTO userEntityFound = userEntityService.showUserEntity(id);
            return new ResponseEntity<>(userEntityFound, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    @Tag(name = "User Entity", description = "Operations related to User Entity")
    @Operation(summary = "Create New User Entity", description = "Create a New User entity.")
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

    @PutMapping("/{id}")
    @Tag(name = "User Entity", description = "Operations related to User Entity")
    @Operation(summary = "Update User Entity", description = "Update an existing User entity.")
    public ResponseEntity<UserEntityDTO> updateUserEntity(
            @Valid @RequestBody EditUserEntity updatedData,
            @PathVariable Long id) {
        try {
            UserEntityDTO updatedUserEntity = userEntityService.updateUserEntity(id, updatedData);
            return new ResponseEntity<>(updatedUserEntity, HttpStatus.OK);
        } catch (UserEntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // ====================== ROLES =========================

    @Tag(name = "User Entity", description = "Operations related to User Entity")
    @Operation(summary = "Get All User Roles", description = "Retrieve User Entity Roles.")
    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return userEntityService.getAllRoles();
    }
}
