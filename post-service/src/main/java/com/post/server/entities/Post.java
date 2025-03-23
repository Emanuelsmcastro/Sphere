package com.post.server.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_tb")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private UUID uuid = UUID.randomUUID();

	private UUID creator;

	private String profileName;

	@Column(length = 255, nullable = true)
	private String description;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Reaction> reactions = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "meta_inf_id")
	private MetaInf metaInf;

	@CreationTimestamp
	private Timestamp createdAt;

	public Post() {
	}

	public Post(UUID creator, String profileName, String description, MetaInf metaInf) {
		this.creator = creator;
		this.profileName = profileName;
		this.description = description;
		this.metaInf = metaInf;
	}

	public Long getId() {
		return id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public UUID getCreator() {
		return creator;
	}

	public String getDescription() {
		return description;
	}

	public MetaInf getMetaInf() {
		return metaInf;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public String getProfileName() {
		return profileName;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public void addReaction(Reaction reaction) {
		reactions.add(reaction);
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", uuid=" + uuid + ", creator=" + creator + ", profileName=" + profileName
				+ ", description=" + description + ", metaInf=" + metaInf + ", createdAt=" + createdAt + "]";
	}

	public static class Builder {

		private UUID creator;

		private String description;

		private String profileName;

		private MetaInf metaInf = new MetaInf();

		public Builder(UUID creator, String descriotion) {
			this.creator = creator;
			this.description = descriotion;
		}

		public static Builder of(UUID creator, String description) {
			return new Builder(creator, description);
		}

		public Builder setMetaInf(MetaInf metaInf) {
			this.metaInf = metaInf;
			return this;
		}

		public Builder addMetaInfHashtags(Set<String> hashtags) {
			this.metaInf.getHashtags().addAll(hashtags);
			return this;
		}

		public Builder setProfileName(String profileName) {
			this.profileName = profileName;
			return this;
		}

		public Post build() {
			return new Post(creator, profileName, description, metaInf);
		}
	}

}
