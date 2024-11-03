package com.video.server.services.v1.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.video.server.infra.exceptions.VideoException;
import com.video.server.services.v1.interfaces.LoopVideoService;

@Service
public class LoopVideoServiceImpl implements LoopVideoService{
	
	@Autowired
    ResourceLoader resourceLoader;
    

	@Override
	public ResponseEntity<byte[]> getVideoByFileName(String fileName, String rangeHeader) {
		Resource videoResource = getVideoResource(fileName);
		try {
            InputStream inputStream = videoResource.getInputStream();
            long fileSize = videoResource.contentLength();

            byte[] data = new byte[(int) fileSize];
            inputStream.read(data, 0, data.length);
            
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "video/MP2T")
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                    .body(data);

        } catch (IOException e) {
            throw new VideoException("Video cannot be loaded.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	private Resource getVideoResource(String fileName) {
		Resource videoResource = resourceLoader.getResource(LoopVideoService.VIDEO_PATH + fileName);
		if(!videoResource.exists()) throw new VideoException("Video " + fileName + " no found.", HttpStatus.NOT_FOUND);
		return videoResource;
	}

}
