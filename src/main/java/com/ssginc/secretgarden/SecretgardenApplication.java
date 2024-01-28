package com.ssginc.secretgarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecretgardenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretgardenApplication.class, args);
	}

}
