package com.media.server.services.v1.impl;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.media.server.dtos.v1.utils.OutputMediaDestinationDTO;
import com.media.server.infra.exceptions.MediaException;
import com.media.server.services.v1.interfaces.ProfileImageService;

import reactor.core.publisher.Mono;

@Service
public class ProfileImageServiceImpl implements ProfileImageService{

	@Autowired
	ResourceLoader resourceLoader;
	
	@Value("${file.storage.images}")
	String imagesLocation;
	
	@Value("${spring.application.host}")
	String host;
	
	// @formatter:off
	@Override
	public Mono<ResponseEntity<String>> uploadFile(Mono<FilePart> filePartMono, UUID profileUUID, String profileName) {
		return filePartMono.flatMap(filePart -> {
			OutputMediaDestinationDTO fileDestinationDTO = createFileDestination(imagesLocation);
			
			String hashedName = sha256Hash(filePart.filename());
	        String extension = extractFileExtension(filePart.filename());
	        
	        Path fileOutput = fileDestinationDTO.destinationDir().resolve(hashedName + extension);
			
	        return Mono.using(
	        		() -> AsynchronousFileChannel.open(fileOutput, StandardOpenOption.CREATE, StandardOpenOption.WRITE),
	        		channel -> DataBufferUtils.write(filePart.content(), channel)
	        				.then(Mono.just(ResponseEntity.status(HttpStatus.CREATED.value()).body("Image saved."))),
	        		channel -> {
	        			try {
	        				channel.close();
	        			} catch(IOException e) {
	        				logger.info("Error closing file channel: " + e.getMessage());
	        			}
	        		}
    		).onErrorResume(e -> Mono.error(new MediaException("Error to save image.", HttpStatus.INTERNAL_SERVER_ERROR)));    		
		});
	}
	// @formatter:on
	
}
