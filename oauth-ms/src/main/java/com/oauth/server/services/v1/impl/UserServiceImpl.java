package com.oauth.server.services.v1.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oauth.server.dtos.v1.user.RequestUserRegister;
import com.oauth.server.entities.User;
import com.oauth.server.infra.exceptions.RegistrationException;
import com.oauth.server.repositories.ProfileRepository;
import com.oauth.server.repositories.UserRepository;
import com.oauth.server.services.v1.interfaces.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

	@Transactional
	@Override
	public void register(RequestUserRegister dto) {
		if (this.userExists(dto.username())) {
			throw new RegistrationException("User already exists.");
		}
		User user = User.Builder.of(dto.username()).setPassword(passwordEncoder.encode(dto.password()))
				.setProfileFirstName(dto.profileFirstName()).setProfileLastName(dto.profileLastName()).build();
		profileRep.save(user.getProfile());
		userRep.save(user);
		logger.debug("New User: " + user.getUsername());
	}

	private Boolean userExists(String username) {
		return userRep.existsByUsername(username);
	}

}