package com.tickettogether;

import com.tickettogether.global.config.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class TickettogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickettogetherApplication.class, args);
	}

}
