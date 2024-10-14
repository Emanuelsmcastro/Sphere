package com.oauth.server.services.v1.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oauth.server.dtos.v1.user.RequestUserRegister;
import com.oauth.server.entities.User;
import com.oauth.server.repositories.ProfileRepository;
import com.oauth.server.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService, com.oauth.server.services.v1.interfaces.UserService {

	@Autowired
	private UserRepository userRep;

	@Autowired
	private ProfileRepository profileRep;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRep.findByUsername(username);
		return user.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found.", username)));
	}

	@Override
	public void register(RequestUserRegister dto) {
		User user = User.Builder.of(dto.username()).setPassword(passwordEncoder.encode(dto.password())).build();
		profileRep.save(user.getProfile());
		userRep.save(user);
	}

}