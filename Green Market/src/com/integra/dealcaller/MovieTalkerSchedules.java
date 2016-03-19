package com.integra.dealcaller;

import java.util.ArrayList;

public class MovieTalkerSchedules {

	private String title, thumbnailUrl;
	private int year;
	private String date;
	private String genre;

	public MovieTalkerSchedules() {
	}

	public MovieTalkerSchedules(String name, String thumbnailUrl, int year, String date,
			String genre) {
		super();
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.date = date;
		this.genre = genre;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	

}
