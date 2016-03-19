package com.integra.dealcaller;

import java.util.ArrayList;

public class Movie6 {

	private String title, thumbnailUrl, email;
	private String year;
	private double rating;
	private String genre;

	public Movie6() {
	}

	public Movie6(String name, String thumbnailUrl,String email, String year, double rating,
			String genre) {
		super();
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.rating = rating;
		this.genre = genre;
		this.email=email;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
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
