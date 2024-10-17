package com.oauth.server.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.server.dtos.v1.user.ResponseProfileDTO;
import com.oauth.server.services.v1.interfaces.ProfileService;

@RestController
@RequestMapping("/oauth/v1/private")
public class PrivateOauthController {

	@Autowired
	ProfileService profileService;

	@GetMapping
	public String test() {
		return "ok";
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<ResponseProfileDTO>> seatchUsers(@PathVariable String name) {
		return ResponseEntity.status(HttpStatus.OK).body(profileService.getProfiles(name));
	}

}
