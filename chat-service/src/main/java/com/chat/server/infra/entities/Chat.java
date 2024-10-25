package com.chat.server.infra.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.chat.server.infra.entities.enums.ChatType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_tb")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private UUID uuid = UUID.randomUUID();
	
	private ChatType chatType = ChatType.PRIVATE_CHAT;
	
	@ElementCollection
    @CollectionTable(name = "chat_participants", joinColumns = @JoinColumn(name = "chat_id"))
    @Column(name = "participant_uuid")
    private Set<UUID> participantsUUID = new HashSet<>();

	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
	private Set<Message> messages = new HashSet<>();

	public Chat() {
	}

	public Chat(ChatType chatType) {
		this.chatType = chatType;
	}

	public Long getId() {
		return id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public ChatType getChatType() {
		return chatType;
	}

	public Set<UUID> getParticipantsUUID() {
		return participantsUUID;
	}

	public Set<Message> getMessages() {
		return messages;
	}
	
	@Override
	public String toString() {
		return "Chat [id=" + id + ", uuid=" + uuid + ", chatType=" + chatType + ", participantsUUID=" + participantsUUID
				+ "]";
	}
	
}
