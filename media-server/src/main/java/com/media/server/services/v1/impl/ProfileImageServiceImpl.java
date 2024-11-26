package com.media.server.services.v1.impl;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.media.server.dtos.v1.utils.OutputMediaDestinationDTO;
import com.media.server.entities.ProfileImage;
import com.media.server.infra.exceptions.MediaException;
import com.media.server.repositories.ProfileImageRepository;
import com.media.server.services.v1.interfaces.ProfileImageService;

import reactor.core.publisher.Mono;

@Service
public class ProfileImageServiceImpl implements ProfileImageService {

	@Value("${file.storage.images}")
	String imagesLocation;

	@Value("${spring.application.host}")
	String host;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	ProfileImageRepository profileImageRep;

	// @formatter:off
	@Override
	public Mono<ResponseEntity<String>> uploadFile(Mono<FilePart> filePartMono, UUID profileUUID, String profileName) {
		return filePartMono.flatMap(filePart -> {
			OutputMediaDestinationDTO fileDestinationDTO = createFileDestination(profileUUID, imagesLocation);
			
			String hashedName = sha256Hash(filePart.filename());
	        String extension = extractFileExtension(filePart.filename());
	        
	        String newFilename = hashedName + extension;
	        
	        Path fileOutput = fileDestinationDTO.destinationDir().resolve(newFilename);
			
	        return Mono.using(
	        		() -> AsynchronousFileChannel.open(fileOutput, StandardOpenOption.CREATE, StandardOpenOption.WRITE),
	        		channel -> DataBufferUtils.write(filePart.content(), channel)
	        				.then(save(ProfileImage.Builder.of()
	        												.setUUID(fileDestinationDTO.uuid())
	        												.setfilename(newFilename)
	        												.setProfileUUID(profileUUID)
	        												.setIsMainProfileImage(true)
	        												.build(), profileUUID))
	        				.then(Mono.just(ResponseEntity.status(HttpStatus.CREATED.value()).body("Image saved."))),
	        		channel -> {
	        			try {
	        				channel.close();
	        			} catch(IOException e) {
	        				logger.info("Error closing file channel: " + e.getMessage());
	        			}
	        		}
    		).onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error to save image: " + e.getMessage())));    		
		});
	}
	// @formatter:on

	@Override
	public Mono<ResponseEntity<Resource>> getProfileImage(UUID profileUUID) {
		return profileImageRep.findByIsMainProfileImageAndProfileUUID(true, profileUUID).flatMap(profileImage -> {
			logger.info("Get Profile Image For: " + profileUUID.toString());

			try {
				
				Resource profileImageResource = getMediaResource(profileUUID, profileImage.getFilename(), imagesLocation,
						resourceLoader);
				
				return Mono.just(ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profileImageResource));
			} catch (MediaException e) {
				return getDefaultImage();
			}
		}).switchIfEmpty(getDefaultImage());
	}

	private Mono<ResponseEntity<Resource>> getDefaultImage() {
		logger.info("Return default Image.");
		String filename = "user-profile.png";
		Resource mediaResource = resourceLoader
				.getResource(String.format("file:" + "images/default" + "/%s", filename));
		if (!mediaResource.exists())
			throw new MediaException("Image: " + filename + " no found.", HttpStatus.NOT_FOUND);
		return Mono.just(ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(mediaResource));
	}

	private Mono<ProfileImage> save(ProfileImage entity, UUID profileUUID) {
		return updateMainProfileImage(profileUUID).then(profileImageRep.save(entity)).onErrorResume(e -> {
			logger.error("Failed to save ProfileImage: {}", e.getMessage());
			return Mono.error(new MediaException("Failed to save ProfileImage", HttpStatus.INTERNAL_SERVER_ERROR));
		});
	}

	private Mono<ProfileImage> updateMainProfileImage(UUID profileUUID) {
		return profileImageRep.findByIsMainProfileImageAndProfileUUID(true, profileUUID).flatMap(profileImage -> {
			profileImage.setMainProfileImage(false);
			logger.info("Updating Main Profile Image: {}", profileImage);
			return profileImageRep.save(profileImage);
		}).onErrorResume(e -> {
			logger.error("Failed to save ProfileImage: {}", e.getMessage());
			return Mono.error(e);
		});
	}

}
