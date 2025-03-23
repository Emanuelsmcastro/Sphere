package com.post.server.entities;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment_tb")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private UUID creatorUUID;

	private String profileName;

	private String content;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@CreationTimestamp
	private Timestamp createdAt;

	public Comment() {
	}

	public Comment(UUID creatorUUID, String profileName, String content, Post post) {
		this.creatorUUID = creatorUUID;
		this.profileName = profileName;
		this.content = content;
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public UUID getCreatorUUID() {
		return creatorUUID;
	}

	public String getProfileName() {
		return profileName;
	}

	public String getContent() {
		return content;
	}

	public Post getPost() {
		return post;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", creatorUUID=" + creatorUUID + ", profileName=" + profileName + ", content="
				+ content + ", createdAt=" + createdAt + "]";
	}

	public static class Build {

		private UUID creatorUUID;
		
		private String profileName;

		private String content;

		private Post post;

		public Build(Post post) {
			this.post = post;
		}

		public static Build of(Post post) {
			return new Build(post);
		}

		public Build setCreatorUUID(UUID creatorUUID) {
			this.creatorUUID = creatorUUID;
			return this;
		}
		
		public Build setProfileName(String profileName) {
			this.profileName = profileName;
			return this;
		}

		public Build setContent(String content) {
			this.content = content;
			return this;
		}

		public Comment build() {
			return new Comment(creatorUUID, profileName, content, post);
		}
	}
}
