package com.video.server.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/video/v1")
public class LoopVideoController {

    @Autowired
    ResourceLoader resourceLoader;
    
    @GetMapping("/test")
    public String test() {
    	return "ok";
    }

    @GetMapping("/{fileName}")
    public Mono<ResponseEntity<byte[]>> getVideo(@PathVariable String fileName, @RequestHeader(value = HttpHeaders.RANGE, required = false) String rangeHeader) {
        return Mono.fromSupplier(() -> {
            try {
                Resource videoResource = resourceLoader.getResource("classpath:videos/" + fileName);
                InputStream inputStream = videoResource.getInputStream();
                long fileSize = videoResource.contentLength();

                long start = 0;
                long end = fileSize - 1;

                if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                    HttpRange range = HttpRange.parseRanges(rangeHeader).get(0);
                    start = range.getRangeStart(fileSize);
                    end = range.getRangeEnd(fileSize);
                }

                long contentLength = end - start + 1;
                byte[] data = new byte[(int) contentLength];
                inputStream.skip(start);
                inputStream.read(data, 0, data.length);
                System.out.println(String.valueOf(contentLength));
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .header(HttpHeaders.CONTENT_TYPE, "video/MP2T")
                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                        .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                        .body(data);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        });
    }
}
