package com.media.server.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profile_image_db")
public class ProfileImage {

	@Id
	private Long id;

	private UUID uuid;

	private String filename;

	private UUID profileUUID;

	private String profileImageURL;

	private boolean isMainProfileImage;

	private Timestamp createdAt = Timestamp.from(Instant.now());

	public ProfileImage() {
	}

	public ProfileImage(UUID uuid, String filename, UUID profileUUID, String profileImageURL,
			boolean isMainProfileImage) {
		this.uuid = uuid;
		this.filename = filename;
		this.profileUUID = profileUUID;
		this.profileImageURL = profileImageURL;
		this.isMainProfileImage = isMainProfileImage;
	}

	public Long getId() {
		return id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getFilename() {
		return filename;
	}

	public UUID getProfileName() {
		return profileUUID;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public boolean isMainProfileImage() {
		return isMainProfileImage;
	}

	public void setMainProfileImage(boolean isMainProfileImage) {
		this.isMainProfileImage = isMainProfileImage;
	}

	public static class Builder {
		private UUID uuid;
		
		private String filename;

		private UUID profileUUID;

		private String profileImageURL;

		private boolean isMainProfileImage;

		public Builder() {

		}

		public static Builder of() {
			return new Builder();
		}

		public Builder setUUID(UUID uuid) {
			this.uuid = uuid;
			return this;
		}
		
		public Builder setfilename(String filename) {
			this.filename = filename;
			return this;
		}

		public Builder setProfileUUID(UUID profileUUID) {
			this.profileUUID = profileUUID;
			return this;
		}

		public Builder setProfileImageURL(String profileImageURL) {
			this.profileImageURL = profileImageURL;
			return this;
		}

		public Builder setIsMainProfileImage(boolean isMainProfileImage) {
			this.isMainProfileImage = isMainProfileImage;
			return this;
		}

		public ProfileImage build() {
			UUID uuid = this.uuid != null ? this.uuid : UUID.randomUUID();
			return new ProfileImage(uuid, filename, profileUUID, profileImageURL, isMainProfileImage);
		}
	}
}
