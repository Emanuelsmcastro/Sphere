package com.media.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.media.server.entities.LoopVideo;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableFeignClients
public class VideoServerApplication implements CommandLineRunner{
	

	public static void main(String[] args) {
		SpringApplication.run(VideoServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(LoopVideo.Builder.of().build().getCreatedAt());
	}

}
