package com.hostfullystay.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application class for the Hostfully application.
 * This class is annotated with {@code @SpringBootApplication}, indicating that it is the main entry point
 * for the Spring Boot application and includes various configuration and component scanning by default.
 * The {@code main} method is responsible for starting the Spring Boot application.
 *
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.boot.SpringApplication
 */
@SpringBootApplication
public class HostfullyApplication {

    /**
     * The main method to start the Hostfully Spring Boot application.
     *
     * @param args Command-line arguments passed to the application.
     *             These arguments can be used for customizing the application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(HostfullyApplication.class, args);
    }

}
