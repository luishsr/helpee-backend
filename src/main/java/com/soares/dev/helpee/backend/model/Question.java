package com.soares.dev.helpee.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "userId")
	private String userId;
	
	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private boolean published;
	
	public Question() {

	}

	public Question(String title, String description, boolean published, String userId) {
		this.title = title;
		this.description = description;
		this.published = published;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + ", userId=" + userId +"]";
	}

}
