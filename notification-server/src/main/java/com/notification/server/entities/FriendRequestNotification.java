package com.notification.server.entities;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.notification.server.entities.enums.FriendRequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "friend_rquest_notification_tb", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "sender", "receiver" }) })
public class FriendRequestNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private UUID uuid;

	private UUID sender;

	private String senderName;

	private UUID receiver;

	private boolean isVisualized;

	private FriendRequestStatus status;

	@CreationTimestamp
	private Timestamp createdAt;

	public FriendRequestNotification() {

	}

	public FriendRequestNotification(Long id, UUID uuid, UUID sender, String senderName, UUID receiver,
			boolean isVisualized, FriendRequestStatus status) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.sender = sender;
		this.senderName = senderName;
		this.receiver = receiver;
		this.isVisualized = isVisualized;
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

	public boolean isVisualized() {
		return isVisualized;
	}

	public void setVisualized(boolean isVisualized) {
		this.isVisualized = isVisualized;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
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

		private String senderName;

		private UUID receiver;

		private boolean isVisualized = false;

		private FriendRequestStatus status = FriendRequestStatus.PENDING;

		Builder() {
		}

		Builder(Long id, UUID sender, UUID receiver) {
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

		public Builder setSenderName(String name) {
			this.senderName = name;
			return this;
		}

		public Builder setReceiver(UUID receiver) {
			this.receiver = receiver;
			return this;
		}

		public FriendRequestNotification build() {
			return new FriendRequestNotification(id, uuid, sender, senderName, receiver, isVisualized, status);
		}
	}

}
