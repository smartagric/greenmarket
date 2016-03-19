package com.integra.dealcaller;

import java.util.ArrayList;

public class MovieMyTalkers {

	private String title, thumbnailUrl,color;
	private int year;
	private double rating;
	private String genre;

	public MovieMyTalkers() {
	}

	public MovieMyTalkers(String name, String thumbnailUrl, String color, int year, double rating,
			String genre) {
		super();
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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
