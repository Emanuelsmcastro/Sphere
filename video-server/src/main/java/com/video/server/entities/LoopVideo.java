package com.video.server.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "loop_video_db")
public class LoopVideo {

	@Id
	private Long id;

	private UUID uuid = UUID.randomUUID();

	private UUID creatorUUID;

	private String creatorName;

	private String description;

	private String videoURL;

	private Timestamp createdAt= Timestamp.from(Instant.now());

	public LoopVideo() {
		super();
	}

	public LoopVideo(UUID creatorUUID, String creatorName, String description, String videoURL) {
		super();
		this.creatorUUID = creatorUUID;
		this.creatorName = creatorName;
		this.description = description;
		this.videoURL = videoURL;
	}

	public Long getId() {
		return id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public UUID getCreatorUUID() {
		return creatorUUID;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public String getDescription() {
		return description;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "LoopVideo [id=" + id + ", uuid=" + uuid + ", creatorUUID=" + creatorUUID + ", creatorName="
				+ creatorName + ", description=" + description + ", videoURL=" + videoURL + ", createdAt=" + createdAt
				+ "]";
	}

	public static class Builder {
		private UUID creatorUUID;

		private String creatorName;

		private String description;

		private String videoURL;

		public Builder() {

		}

		public static Builder of() {
			return new Builder();
		}

		public Builder setCreatorUUID(UUID creatorUUID) {
			this.creatorUUID = creatorUUID;
			return this;
		}

		public Builder setCreatorName(String creatorName) {
			this.creatorName = creatorName;
			return this;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setVideoURL(String videoURL) {
			this.videoURL = videoURL;
			return this;
		}

		public LoopVideo build() {
			return new LoopVideo(creatorUUID, creatorName, description, videoURL);
		}
	}
}
