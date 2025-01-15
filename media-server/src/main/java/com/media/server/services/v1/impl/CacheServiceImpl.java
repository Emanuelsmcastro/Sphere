package com.media.server.services.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.media.server.services.v1.interfaces.CacheService;

import reactor.core.publisher.Mono;

@Service
public class CacheServiceImpl implements CacheService{
	
	@Autowired
	ReactiveRedisTemplate<String, byte[]> redisTemplate;

	@Override
	public Mono<Void> cacheFile(String key, byte[] data) {
		return redisTemplate.opsForValue()
                .set(key, data)
                .then();
	}

	@Override
	public Mono<byte[]> getFileFromCache(String key) {
		return redisTemplate.opsForValue().get(key);
	}

}
