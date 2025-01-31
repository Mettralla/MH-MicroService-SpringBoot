package com.mindhub.userMicroservice.controllers;

import com.mindhub.userMicroservice.config.JwtUtils;
import com.mindhub.userMicroservice.dtos.LoginUser;
import com.mindhub.userMicroservice.models.UserEntity;
import com.mindhub.userMicroservice.repositories.UserEntityRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginUser loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity authUser = userEntityRepository.findByEmail(authentication.getName()).orElse(null);
        String jwt = jwtUtil.createToken(authentication.getName(), authUser.getId());
        return ResponseEntity.ok(jwt);
    }
}
