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

	public Message() {
	}

	public Message(UUID senderUUID) {
		this.senderUUID = senderUUID;
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

	@Override
	public String toString() {
		return "Message [id=" + id + ", uuid=" + uuid + ", senderUUID=" + senderUUID + ", createdAt=" + createdAt + "]";
	}

}