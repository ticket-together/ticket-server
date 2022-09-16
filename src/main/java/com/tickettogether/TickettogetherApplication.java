package com.tickettogether;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class TickettogetherApplication{
	public static void main(String[] args) {
		SpringApplication.run(TickettogetherApplication.class, args);
	}
}
