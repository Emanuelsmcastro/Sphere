package com.oauth.server.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_profile")
public class Profile implements Serializable {
	private static final long serialVersionUID = 3404984861493278590L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private UUID uuid = UUID.randomUUID();

	private String firstName;

	private String lastName;

	@Column(length = 255)
	private String description;

	@OneToOne(mappedBy = "profile")
	private User user;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_friends", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private Set<Profile> friends = new HashSet<>();

	public Profile() {

	}

	public Profile(Long id, User user) {
		this.id = id;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public Set<Profile> getFriends() {
		return friends;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", uuid=" + uuid + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", description=" + description;
	}

}