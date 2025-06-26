package com.kitnet.kitnet;

import com.kitnet.kitnet.config.EnvironmentConfigLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KitnetApplication {

	public static void main(String[] args) {

		EnvironmentConfigLoader.loadEnvironmentVariables();

		SpringApplication.run(KitnetApplication.class, args);
	}

}
