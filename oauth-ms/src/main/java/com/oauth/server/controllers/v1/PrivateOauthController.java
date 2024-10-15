package com.oauth.server.controllers.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth/v1/private")
public class PrivateOauthController {

	@GetMapping
	public String test() {
		return "ok";
	}
}
