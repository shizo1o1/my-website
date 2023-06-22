package com.example.mywebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyWebSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWebSiteApplication.class, args);
	}
}
