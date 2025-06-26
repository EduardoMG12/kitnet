package com.kitnet.kitnet.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import java.io.File;

public class EnvironmentConfigLoader {

    public static void loadEnvironmentVariables() {
        Dotenv dotenv = null;
        String defaultEnvPath = "./.env";
        String dockerEnvPath = "./.env.docker";

        File defaultEnvFile = new File(defaultEnvPath);
        File dockerEnvFile = new File(dockerEnvPath);

        DotenvBuilder builder = Dotenv.configure();

        if (defaultEnvFile.exists() && defaultEnvFile.isFile()) {
            System.out.println("Loading environment variables from: " + defaultEnvPath);
            dotenv = builder.filename(".env").load();
        } else if (dockerEnvFile.exists() && dockerEnvFile.isFile()) {
            System.out.println("Loading environment variables from: " + dockerEnvPath);
            dotenv = builder.filename(".env.docker").load();
        } else {
            System.out.println("No .env or .env.docker file found. Relying on system/Docker Compose injected environment variables.");
        }

        if (dotenv != null) {
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
            System.out.println("Environment variables successfully loaded into System Properties.");
        }
    }
}