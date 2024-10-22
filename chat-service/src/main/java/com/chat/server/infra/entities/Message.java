package com.chat.server.infra.entities;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "message_tb")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private UUID uuid = UUID.randomUUID();

	private UUID senderUUID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id")
	private Chat chat;

	@CreationTimestamp
	private Timestamp createdAt;

	private String message;

	public Message() {
	}

	public Message(UUID senderUUID, String message) {
		this.senderUUID = senderUUID;
		this.message = message;
	}

	public Message(Long id, UUID senderUUID, Chat chat, String message) {
		this.id = id;
		this.senderUUID = senderUUID;
		this.chat = chat;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public UUID getSenderUUID() {
		return senderUUID;
	}

	public Chat getChat() {
		return chat;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", uuid=" + uuid + ", senderUUID=" + senderUUID + ", createdAt=" + createdAt
				+ ", message=" + message + "]";
	}

	public static class Builder {
		private Long id;

		private UUID senderUUID;

		private Chat chat;

		private String message;
		
		public Builder(UUID senderUUID, String message) {
			this.senderUUID = senderUUID;
			this.message = message;
		}
		
		public static Builder of(UUID senderUUID, String message) {
			return new Builder(senderUUID, message);
		}
		
		public Builder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder setChat(Chat chat) {
			this.chat = chat;
			return this;
		}
		
		public Message build() {
			return new Message(id, senderUUID, chat, message);
		}

	}
}
