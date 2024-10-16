package com.publisher.server;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PublisherServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublisherServerApplication.class, args);
	}

}
