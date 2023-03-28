package com.prueba.superheroe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SuperheroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperheroApplication.class, args);
	}

	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy() {
		return flyway -> {
			// Configuración adicional de Flyway
			flyway.clean();
			flyway.migrate();
		};
	}
}
