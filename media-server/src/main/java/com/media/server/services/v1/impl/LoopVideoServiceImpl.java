package com.media.server.services.v1.impl;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.media.server.clients.OauthServerClient;
import com.media.server.dtos.v1.loop.ResponseLoopVideo;
import com.media.server.dtos.v1.profile.ResponseProfileDTO;
import com.media.server.dtos.v1.utils.OutputMediaDestinationDTO;
import com.media.server.entities.LoopVideo;
import com.media.server.infra.exceptions.VideoException;
import com.media.server.mapper.v1.interfaces.LoopVideoMapper;
import com.media.server.repositories.LoopVideoRepository;
import com.media.server.services.v1.interfaces.LoopVideoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class LoopVideoServiceImpl implements LoopVideoService {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	LoopVideoMapper loopVideoMapper;

	@Autowired
	LoopVideoRepository loopVideoRep;

	@Autowired
	OauthServerClient oauthServerClient;
	
	@Value("${file.storage.videos}")
	String videosLocation;
	
	@Value("${spring.application.host}")
	String host;

	@Override
	public Mono<ResponseEntity<byte[]>> getVideoByFileName(UUID uuid, String fileName) {
        Path filePath = Path.of(videosLocation, uuid.toString(), fileName);
        
        return Mono.fromCallable(() -> Files.readAllBytes(filePath))
                .flatMap(fileData -> {
                    return Mono.just(ResponseEntity.ok()
                                    .header(HttpHeaders.CONTENT_TYPE, getContentType(fileName))
                                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileData.length))
                                    .body(fileData));
                })
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(("Error on load video: " + e.getMessage()).getBytes()));
                });
	}

	// @formatter:off
	@Override
	public Mono<ResponseEntity<String>> uploadFile(Mono<FilePart> filePartMono, UUID profileUUID, String profileName) {
	    return filePartMono.flatMap(filePart -> {
	        OutputMediaDestinationDTO fileDestinationDTO = createFileDestination(videosLocation);
	        UUID uuid = fileDestinationDTO.uuid();
	        Path destinationDir = fileDestinationDTO.destinationDir();

	        String hashedName = sha256Hash(filePart.filename());
	        String extension = extractFileExtension(filePart.filename());
	        Path fileOutput = destinationDir.resolve(hashedName + extension);

	        return Mono.using(
	                () -> AsynchronousFileChannel.open(fileOutput, StandardOpenOption.CREATE, StandardOpenOption.WRITE),
	                channel -> DataBufferUtils.write(filePart.content(), channel)
	                        .then(processVideo(fileOutput, destinationDir, hashedName))
	                        .then(saveLoopVideo(
	                                LoopVideo.Builder.of()
	                                        .setCreatorName(profileName)
	                                        .setCreatorUUID(profileUUID)
	                                        .setVideoURL("http://" + host + ":" + "8765/video/v1/search/" + uuid.toString() + "/" + hashedName + ".m3u8")
	                                        .setUUID(uuid)
	                                        .build()))
	                        .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("File Saved")))
	                        .doOnTerminate(() -> removeFile(fileOutput).subscribe()),
	                channel -> {
	                    try {
	                        channel.close();
	                    } catch (IOException ex) {
	                        logger.error("Error closing file channel", ex);
	                    }
	                }
	        ).onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error: " + e.getMessage())));
	    });
	}
	// @formatter:on
	
	// @formatter:off
	@Override
	public Mono<Page<ResponseLoopVideo>> getFriendLoopVideos(UUID profileUUID, Pageable pageable, String token, String cookie) {
	    return Mono.fromCallable(() -> oauthServerClient.getAllFriends(token, cookie).getBody())
	            .subscribeOn(Schedulers.boundedElastic())
	            .flatMap(friends -> {
	                List<UUID> friendList = friends.stream()
	                        .map(ResponseProfileDTO::uuid)
	                        .collect(Collectors.toList());
	                return loopVideoRep.findByCreatorUUIDIn(friendList, pageable)
	                        .map(loopVideoMapper::toDTO)
	                        .collectList()
	                        .zipWith(loopVideoRep.countByCreatorUUIDIn(friendList))
	                        .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
	            });
	}
	// @formatter:on
	
	@Override
	public Flux<ResponseLoopVideo> getAllLoopVideosByCreatorUUID(UUID creatorUUID) {
		return loopVideoRep.findAllByCreatorUUID(creatorUUID).map(loopVideoMapper::toDTO);
	}

	private Mono<Void> processVideo(Path fileOutput, Path destinationDir, String hashedName) {
		return Mono.defer(() -> {
			String inputFilePath = fileOutput.toString();
			String outputFilePath = destinationDir.resolve(hashedName + ".m3u8").toString();

			ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", inputFilePath, "-codec", "copy",
					"-hls_time", "10", "-hls_list_size", "0", "-f", "hls", outputFilePath);

			processBuilder.inheritIO();

			try {
				Process process = processBuilder.start();
				return Mono.fromFuture(() -> CompletableFuture.supplyAsync(() -> {
					try {
						int exitCode = process.waitFor();
						if (exitCode != 0) {
							throw new VideoException("Processing video error.", HttpStatus.INTERNAL_SERVER_ERROR);
						}
						return null;
					} catch (InterruptedException e) {
						throw new VideoException("Processing video error: " + e.getMessage(),
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}));
			} catch (IOException e) {
				return Mono
						.error(new VideoException("Error starting video process.", HttpStatus.INTERNAL_SERVER_ERROR));
			}
		});
	}

	private Mono<LoopVideo> saveLoopVideo(LoopVideo video) {
		return loopVideoRep.save(video);
	}
	
	private String getContentType(String fileName) {
	    if (fileName.endsWith(".m3u8")) {
	        return "application/vnd.apple.mpegurl";
	    } else if (fileName.endsWith(".ts")) {
	        return "video/MP2T";
	    }
	    return "application/octet-stream";
	}


}
