package com.notification.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.notification.server.entities.FriendRequestNotification;
import com.notification.server.entities.enums.FriendRequestStatus;

public interface FriendRequestRepository extends JpaRepository<FriendRequestNotification, Long>{
	
	Optional<FriendRequestNotification> findByUuid(UUID uuid);
	
	Optional<FriendRequestNotification> findBySenderAndReceiver(UUID sender, UUID receiver);
	
	Page<FriendRequestNotification> findByReceiver(UUID receiver, Pageable pageable);
	
	Page<FriendRequestNotification> findByReceiverAndStatus(UUID receiver, FriendRequestStatus status, Pageable pageable);
	
	boolean existsByUuid(UUID uuid);
}
