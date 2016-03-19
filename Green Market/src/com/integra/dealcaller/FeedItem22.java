package com.integra.dealcaller;

import java.io.Serializable;

public class FeedItem22 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private int id, expression, comments, boo;
	private String name, status, lesson, image, profilePic, timeStamp, url, email;

	public FeedItem22() {
	}

	public FeedItem22(int id,int expression,int comments,int boo, String name, String image, String status, String lesson,
			String profilePic, String timeStamp, String url, String email) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.status = status;
		this.lesson = lesson;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.url = url;
		this.boo = boo;
		this.expression=expression;
		this.comments=comments;
		this.email=email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getExp() {
		return expression;
	}

	public void setExp(int expression) {
		this.expression = expression;
	}

	
	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}
	
	public int getBoos() {
		return boo;
	}

	public void setBoos(int boo) {
		this.boo = boo;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImge() {
		return image;
	}

	public void setImge(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLesson() {
		return lesson;
	}

	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	
	
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
