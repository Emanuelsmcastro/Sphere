package com.notification.server.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.server.clients.OauthServerClient;

@RestController
@RequestMapping("/notification/v1")
public class NotificationServerController {
	
	@Autowired
	OauthServerClient oauthServerClient;
	
	@GetMapping
	public String test() {
		System.out.println(oauthServerClient.test());
		return "Notification Test";
	}
}
