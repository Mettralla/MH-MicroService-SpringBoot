package com.mindhub.userMicroservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.userMicroservice.events.UserEntityData;
import com.mindhub.userMicroservice.models.UserEntity;
import com.mindhub.userMicroservice.repositories.UserEntityRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @RabbitListener(queues = "user.request.queue")
    public String getUserData(Long userId) throws JsonProcessingException {
        UserEntity user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserEntityData userEntityData = convertToUserEntityData(user);

        return new ObjectMapper().writeValueAsString(userEntityData);
    }

    private UserEntityData convertToUserEntityData(UserEntity user) {
        String roles = user.getRoles().name();

        return new UserEntityData(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles
        );
    }
}
