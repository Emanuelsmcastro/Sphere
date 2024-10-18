package com.oauth.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oauth.server.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	@Query("SELECT p FROM Profile p WHERE CONCAT(p.firstName, ' ', p.lastName) LIKE %:fullName%")
	List<Profile> findByFullNameContaining(@Param("fullName") String name);

	@Query("SELECT p FROM Profile p WHERE CONCAT(p.firstName, ' ', p.lastName) LIKE %:fullName%")
	Page<Profile> findByFullNameContaining(@Param("fullName") String name, Pageable pageable);
}