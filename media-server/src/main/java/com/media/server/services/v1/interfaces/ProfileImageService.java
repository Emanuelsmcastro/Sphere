package com.media.server.services.v1.interfaces;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;

public interface ProfileImageService extends FileService{

	Mono<ResponseEntity<String>> uploadFile(Mono<FilePart> filePartMono, UUID profileUUID, String profileName) ;

	Mono<ResponseEntity<Resource>>  getProfileImage(UUID uuid);
}
