package com.notification.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notification.server.entities.FriendRequestNotification;

public interface FriendRequestRepository extends JpaRepository<FriendRequestNotification, Long>{

	Optional<FriendRequestNotification> findBySenderAndReceiver(UUID sender, UUID receiver);
}
