package com.example.mywebsite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyWebSiteApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyWebSiteApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MyWebSiteApplication.class, args);
		LOGGER.info("Приложение успешно запущено. Подключено к базе данных.");
	}
}
