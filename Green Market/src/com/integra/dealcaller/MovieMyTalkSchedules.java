package com.integra.dealcaller;

import java.util.ArrayList;

public class MovieMyTalkSchedules {

	private String title, thumbnailUrl;
	private String status;
	private String date;
	private String talker;

	public MovieMyTalkSchedules() {
	}

	public MovieMyTalkSchedules(String name, String thumbnailUrl, String status, String date,
			String talker) {
		super();
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.status = status;
		this.date = date;
		this.talker = talker;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTalker() {
		return talker;
	}

	public void setTalker(String talker) {
		this.talker = talker;
	}
	
	

}
