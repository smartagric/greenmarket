package com.integra.dealcaller;

import java.util.ArrayList;

public class MovieMyMessages {

	private String title, thumbnailUrl;
	private int year;
	private double rating;
	private String genre;

	public MovieMyMessages() {
	}

	public MovieMyMessages(String name, String thumbnailUrl, int year, double rating,
			String genre) {
		super();
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.rating = rating;
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	

}
