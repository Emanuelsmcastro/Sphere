package com.sphere.websockets.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sphere.websockets")
public class WebsocketsProperties {

	private String path;

	private String[] allowOrigins = {"http://localhost:3000"};

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String[] getAllowOrigins() {
		return allowOrigins;
	}

	public void setAllowOrigins(String[] allowOrigins) {
		this.allowOrigins = allowOrigins;
	}

}
