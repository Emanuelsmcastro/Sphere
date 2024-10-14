package com.oauth.server.entities;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
	private static final long serialVersionUID = -5998840214479871505L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	@OneToOne
	@JoinColumn(name = "profile_id")
	private Profile profile;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public User(Long id, String username, String password, Profile profile) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.profile = profile;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Profile getProfile() {
		return profile;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", profile=" + profile + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return java.util.List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static class Builder {
		private Long id;
		private String username;
		private String password;
		private Profile profile = new Profile();

		public Builder(String username) {
			this.username = username;
		}

		public static Builder of(String username) {
			return new Builder(username);
		}

		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder setProfileId(Long id) {
			this.profile.setId(id);
			return this;
		}

		public Builder setProfile(Profile profile) {
			this.profile = profile;
			return this;
		}

		public User build() {
			User user = new User(id, username, password, profile);
			profile.setUser(user);
			return user;
		}
	}

}