package com.video.server.services.v1.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.video.server.entities.LoopVideo;
import com.video.server.infra.exceptions.VideoException;
import com.video.server.repositories.LoopVideoRepository;
import com.video.server.services.v1.interfaces.LoopVideoService;

import reactor.core.publisher.Mono;

@Service
public class LoopVideoServiceImpl implements LoopVideoService {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	LoopVideoRepository loopVideoRep;

	@Override
	public ResponseEntity<byte[]> getVideoByFileName(String fileName, String rangeHeader) {
		Resource videoResource = getVideoResource(fileName);
		try {
			InputStream inputStream = videoResource.getInputStream();
			long fileSize = videoResource.contentLength();

			byte[] data = new byte[(int) fileSize];
			inputStream.read(data, 0, data.length);

			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).header(HttpHeaders.CONTENT_TYPE, "video/MP2T")
					.header(HttpHeaders.ACCEPT_RANGES, "bytes")
					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize)).body(data);

		} catch (IOException e) {
			throw new VideoException("Video cannot be loaded.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Mono<ResponseEntity<String>> uploadFile(Mono<FilePart> filePartMono, UUID profileUUID, String profileName) {
		return filePartMono.flatMap(filePart -> {
			UUID uuid = UUID.randomUUID();
			Path destinationDir = Paths.get("src/main/resources/videos", uuid.toString());

			try {
				if (!Files.exists(destinationDir))
					Files.createDirectories(destinationDir);
			} catch (IOException e) {
				return Mono
						.error(new VideoException("Video Folder cannot be created.", HttpStatus.INTERNAL_SERVER_ERROR));
			}

			String hashedName = sha256Hash(filePart.filename());
			String extension = "";

			int doIndex = filePart.filename().lastIndexOf(".");
			if (doIndex > 0) {
				extension = filePart.filename().substring(doIndex);
			}

			Path destination = destinationDir.resolve(hashedName + extension);

			return Mono
					.fromCallable(() -> AsynchronousFileChannel.open(destination, StandardOpenOption.CREATE,
							StandardOpenOption.WRITE))
					.flatMap(channel -> DataBufferUtils.write(filePart.content(), channel).doOnComplete(() -> {
						try {
							channel.close();
							String inputFilePath = destination.toString();
							String outputFilePath = destinationDir.resolve(hashedName + ".m3u8").toString();
							ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", inputFilePath, "-codec",
									"copy", "-hls_time", "10", "-hls_list_size", "0", "-f", "hls", outputFilePath);
							processBuilder.inheritIO();
							Process process = processBuilder.start();
							int exitCode = process.waitFor();
							if (exitCode != 0) {
								throw new VideoException("Processing video error.", HttpStatus.INTERNAL_SERVER_ERROR);
							}
						} catch (IOException | InterruptedException ex) {
							ex.printStackTrace();
						}
					}).then(saveLoopVideo(LoopVideo.Builder.of().setCreatorName(profileName).setCreatorUUID(profileUUID)
							.setVideoURL("http://localhost:8765/video/v1/search/" + uuid.toString() + "/" + hashedName
									+ ".m3u8").setUUID(uuid)
							.build())).then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("File Saved")))
							.onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.body("Error: " + e.getMessage()))));
		});
	}

	private Mono<LoopVideo> saveLoopVideo(LoopVideo video) {
		return loopVideoRep.save(video);
	}

	private Resource getVideoResource(String fileName) {
		Resource videoResource = resourceLoader.getResource(LoopVideoService.VIDEO_PATH + fileName);
		if (!videoResource.exists())
			throw new VideoException("Video " + fileName + " no found.", HttpStatus.NOT_FOUND);
		return videoResource;
	}

	private String sha256Hash(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			return byteArrayToHexString(hash);
		} catch (NoSuchAlgorithmException ex) {
			throw new VideoException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String byteArrayToHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
