package com.chat.server.infra.websocket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.chat.server.infra.entities.JwtAuthentication;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor{
	
	@Autowired
	JwtDecoder jwtDecoder;

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		Map<String, String> queryParams = extractParams(request.getURI().getQuery());		
        if(queryParams.isEmpty()) return false;
        
        try {
            Jwt jwt = jwtDecoder.decode(queryParams.get("token"));
            attributes.put("jwt", jwt);
            JwtAuthentication authentication = new JwtAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}
	
	private Map<String, String> extractParams(String query){
		if (query == null || query.isEmpty()) {
            return new HashMap<>();
        }

		return Arrays.stream(query.split("&"))
                .map(param -> param.split("="))
                .collect(Collectors.toMap(splitedResult -> splitedResult[0], splitedResult -> splitedResult[1]));
	}

}
