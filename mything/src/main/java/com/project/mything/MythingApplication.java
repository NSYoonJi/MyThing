package com.project.mything;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MythingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MythingApplication.class, args);
	}

}
