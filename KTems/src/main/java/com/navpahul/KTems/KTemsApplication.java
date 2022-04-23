package com.navpahul.KTems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KTemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KTemsApplication.class, args);
	}
}
