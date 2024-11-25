//package com.video.server.infra.security;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class WebSecurityConfiguration {
//	
//	
//	@Bean
//	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//		return http
//                .csrf(csrf -> csrf.disable())
//                .authorizeExchange(exchanges -> {
//                	exchanges.anyExchange().authenticated();
//                })
//                .oauth2ResourceServer(configurer -> configurer
//                        .jwt(withDefaults())
//                )
//                .build();
//	}
//}

