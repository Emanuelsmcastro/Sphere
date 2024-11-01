package com.video.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.video.server.entities.LoopVideo;

@SpringBootApplication
public class VideoServerApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(VideoServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(LoopVideo.Builder.of().build().getCreatedAt());
	}

}
