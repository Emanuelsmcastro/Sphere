package com.notification.server.entities;

import java.util.UUID;

import com.notification.server.entities.enums.FriendRequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "friend_rquest_notification_tb", uniqueConstraints = { @UniqueConstraint(columnNames = { "sender", "receiver" }) })
public class FriendRequestNotification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private UUID uuid;
	
	private UUID sender;
	
	private UUID receiver;
	
	private FriendRequestStatus status;
	

	public FriendRequestNotification() {
		
	}

	public FriendRequestNotification(Long id, UUID uuid, UUID sender, UUID receiver, FriendRequestStatus status) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}

	public FriendRequestStatus getStatus() {
		return status;
	}

	public void setStatus(FriendRequestStatus status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public UUID getSender() {
		return sender;
	}

	public UUID getReceiver() {
		return receiver;
	}

	@Override
	public String toString() {
		return "FriendRequestNotification [id=" + id + ", uuid=" + uuid + ", sender=" + sender + ", receiver="
				+ receiver + ", status=" + status + "]";
	}
	
	
	public static class Builder {
		private Long id;
		
		private UUID uuid = UUID.randomUUID();
		
		private UUID sender;
		
		private UUID receiver;
		
		private FriendRequestStatus status = FriendRequestStatus.PENDING;
		
		Builder(){
		}
		
		Builder(Long id, UUID sender, UUID receiver){
			this.id = id;
			this.sender = sender;
			this.receiver = receiver;
		}
		
		public static Builder of() {
			return new Builder();
		}
		
		public Builder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder setSender(UUID sender) {
			this.sender = sender;
			return this;
		}
		
		public Builder setReceiver(UUID receiver) {
			this.receiver = receiver;
			return this;
		}
		
		public FriendRequestNotification build() {
			return new FriendRequestNotification(id, uuid, sender, receiver, status);
		}
	}
	
}