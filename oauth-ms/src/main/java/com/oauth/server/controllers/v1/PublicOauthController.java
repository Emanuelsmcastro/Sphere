package com.oauth.server.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.server.dtos.v1.user.RequestUserRegister;
import com.oauth.server.services.v1.interfaces.UserService;

@RestController
@RequestMapping("/oauth/v1/public")
public class PublicOauthController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RequestUserRegister dto) {
		userService.register(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/test")
	public ResponseEntity<Void> test() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
