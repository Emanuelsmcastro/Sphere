package com.sphere.properties.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sphere.utils.cors")
public class SphereProperties {


	private String[] allowOrigins = {"http://localhost:3000"};

	public String[] getAllowOrigins() {
		return allowOrigins;
	}

	public void setAllowOrigins(String[] allowOrigins) {
		this.allowOrigins = allowOrigins;
	}

}
