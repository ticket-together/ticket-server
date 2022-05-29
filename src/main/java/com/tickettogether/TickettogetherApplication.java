package com.tickettogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication //(scanBasePackages = {"com.tickettogether.domain.culture.repository"})
public class TickettogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickettogetherApplication.class, args);
	}

}
