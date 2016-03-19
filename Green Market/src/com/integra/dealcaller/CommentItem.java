package com.integra.dealcaller;

import java.io.Serializable;

public class CommentItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private int id, expression, comments;
	private String name, status, image, profilePic, timeStamp, url, email;

	public CommentItem() {
	}

	public CommentItem(int id,int expression,int comments, String name, String image, String status,
			String profilePic, String timeStamp, String url, String email) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.status = status;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.url = url;
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
