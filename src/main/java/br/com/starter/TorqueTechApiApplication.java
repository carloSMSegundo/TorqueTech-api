package br.com.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TorqueTechApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorqueTechApiApplication.class, args);
	}
}
