package com.chat.server.infra.entities;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtAuthentication implements Authentication {
	private static final long serialVersionUID = -7529969484139406412L;
	private final Jwt jwt;
    private boolean authenticated = true;

    public JwtAuthentication(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; 
    }

    @Override
    public Object getCredentials() {
        return jwt.getTokenValue();
    }

    @Override
    public Jwt getDetails() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return jwt.getSubject();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return jwt.getSubject();
    }
}
