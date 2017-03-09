package com.risezhang.api.demo;

import com.risezhang.api.demo.controller.BookController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Application {

	private static Logger logger = LoggerFactory.getLogger(BookController.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication();
		app.run(Application.class, args);
		logger.info("application started!");
	}
}
