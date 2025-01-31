package com.mindhub.userMicroservice;

import com.mindhub.userMicroservice.models.UserEntity;
import com.mindhub.userMicroservice.repositories.UserEntityRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(UserEntityRepository userEntityRepository) {
		return args -> {
			UserEntity testUser = new UserEntity(
					"daniTejerina",
					passwordEncoder.encode("strongpass"),
					"dani@gmail.com"
			);

			UserEntity testUser2 = new UserEntity(
					"janeSmith",
					passwordEncoder.encode("securepassword"),
					"jane.smith@example.com"
			);

			UserEntity testUser3 = new UserEntity(
					"johnDoe",
					passwordEncoder.encode("securepassword"),
					"johnDoe@example.com"
			);


			userEntityRepository.save(testUser);
			userEntityRepository.save(testUser2);
			userEntityRepository.save(testUser3);
		};
	}

}
