package com.media.server.services.v1.interfaces;

import reactor.core.publisher.Mono;

public interface CacheService {

	Mono<Void> cacheFile(String key, byte[] data);
	
	Mono<byte[]> getFileFromCache(String key);
}
