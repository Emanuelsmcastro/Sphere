package com.media.server.services.v1.interfaces;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;

import com.media.server.dtos.v1.utils.OutputMediaDestinationDTO;
import com.media.server.infra.exceptions.MediaException;

import reactor.core.publisher.Mono;

public interface FileService {

	Logger logger = LoggerFactory.getLogger(FileService.class);

	default Resource getMediaResource(UUID uuid, String fileName, String resourceLocation,
			ResourceLoader resourceLoader) {
		Resource mediaResource = resourceLoader
				.getResource(String.format("file:" + resourceLocation + "/%s/%s", uuid.toString(), fileName));
		if (!mediaResource.exists())
			throw new MediaException("Video " + fileName + " no found.", HttpStatus.NOT_FOUND);
		return mediaResource;
	}

	default OutputMediaDestinationDTO createFileDestination(String resourceLocation) {
		return createFileDestination(UUID.randomUUID(), resourceLocation);
	}

	default OutputMediaDestinationDTO createFileDestination(UUID uuid, String resourceLocation) {
		Path destinationDir = Paths.get(resourceLocation, uuid.toString());

		try {
			if (!Files.exists(destinationDir))
				Files.createDirectories(destinationDir);
		} catch (IOException e) {
			throw new MediaException("Media Folder cannot be created.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new OutputMediaDestinationDTO(uuid, destinationDir);
	}

	default String extractFileExtension(String filename) {
		String extension = "";

		int doIndex = filename.lastIndexOf(".");
		if (doIndex > 0) {
			extension = filename.substring(doIndex);
		}

		return extension;
	}

	default Mono<Void> removeFile(Path filePath) {
		return Mono.fromFuture(() -> CompletableFuture.runAsync(() -> {
			try {
				Files.deleteIfExists(filePath);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}));
	}

	default String sha256Hash(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			return byteArrayToHexString(hash);
		} catch (NoSuchAlgorithmException ex) {
			throw new MediaException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	default String byteArrayToHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
