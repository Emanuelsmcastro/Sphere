package com.oauth.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oauth.server.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}