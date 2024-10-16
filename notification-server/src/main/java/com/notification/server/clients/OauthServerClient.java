package com.notification.server.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(contextId = "OauthServerClient", name = "oauth-ms", path = "oauth/v1/private")
public interface OauthServerClient {

	@GetMapping
	String test();
}
