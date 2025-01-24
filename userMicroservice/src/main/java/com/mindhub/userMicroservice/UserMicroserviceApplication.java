package com.mindhub.userMicroservice;

import com.mindhub.userMicroservice.models.UserEntity;
import com.mindhub.userMicroservice.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserEntityRepository userEntityRepository) {
		return args -> {
			UserEntity testUser = new UserEntity(
					"daniTejerina",
					"strongpass",
					"dani@gmail.com"
			);

			UserEntity testUser2 = new UserEntity(
					"janeSmith",
					"securepassword",
					"jane.smith@example.com"
			);

			UserEntity testUser3 = new UserEntity(
					"johnDoe",
					"securepassword",
					"johnDoe@example.com"
			);


			userEntityRepository.save(testUser);
			userEntityRepository.save(testUser2);
			userEntityRepository.save(testUser3);
		};
	}

}
