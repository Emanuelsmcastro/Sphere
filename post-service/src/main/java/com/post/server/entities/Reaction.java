package com.post.server.entities;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.post.server.entities.enums.ReactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reaction_post_tb")
public class Reaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private ReactionType reactionType;

	private UUID userUUID;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@CreationTimestamp
	private Timestamp createdAt;

	public Reaction() {
	}

	public Reaction(ReactionType reactionType, UUID userUUID, Post post) {
		this.reactionType = reactionType;
		this.userUUID = userUUID;
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public ReactionType getReactionType() {
		return reactionType;
	}

	public UUID getUserUUID() {
		return userUUID;
	}

	public Post getPost() {
		return post;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "Reaction [id=" + id + ", reactionType=" + reactionType + ", userUUID=" + userUUID + ", createdAt="
				+ createdAt + "]";
	}

	public static class Build {

		private ReactionType reactionType = ReactionType.LIKE;

		private UUID userUUID;

		private Post post;

		private Build() {

		}

		public static Build of() {
			return new Build();
		}

		public Build setReactionType(ReactionType reactionType) {
			this.reactionType = reactionType;
			return this;
		}

		public Build setUserUUID(UUID userUUID) {
			this.userUUID = userUUID;
			return this;
		}

		public Build setPost(Post post) {
			this.post = post;
			return this;
		}

		public Reaction build() {
			return new Reaction(reactionType, userUUID, post);
		}
	}

}
