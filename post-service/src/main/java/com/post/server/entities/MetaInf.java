package com.post.server.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meta_inf_tb")
public class MetaInf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	@CollectionTable(name = "meta_inf_hashtags", joinColumns = @JoinColumn(name = "meta_inf_id"))
	@Column(name = "hashtag_content")
	private Set<String> hashtags = new HashSet<>();

	@OneToOne(mappedBy = "metaInf")
	private Post post;

	public MetaInf() {
	}

	public Long getId() {
		return id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Set<String> getHashtags() {
		return hashtags;
	}

	@Override
	public String toString() {
		return "MetaInf [id=" + id + ", hashtags=" + hashtags + "]";
	}

}
