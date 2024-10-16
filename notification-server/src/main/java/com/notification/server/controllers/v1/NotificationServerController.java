package com.notification.server.controllers.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification/v1")
public class NotificationServerController {
	
	@GetMapping
	public String test() {
		return "Notification Test";
	}
}
