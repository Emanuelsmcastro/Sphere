package com.video.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.server.services.v1.interfaces.LoopVideoService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/video/v1")
public class LoopVideoController {

    @Autowired
    LoopVideoService loopVideoService;
    
    @GetMapping("/test")
    public String test() {
    	return "ok";
    }

    @GetMapping("/{fileName}")
    public Mono<ResponseEntity<byte[]>> getVideo(@PathVariable String fileName, @RequestHeader(value = HttpHeaders.RANGE, required = false) String rangeHeader) {
        return Mono.fromSupplier(() -> loopVideoService.getVideoByFileName(fileName, rangeHeader));
    }
}
