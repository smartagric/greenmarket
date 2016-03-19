package com.integra.dealcaller;

import java.util.ArrayList;

public class Movie00 {

	private String title, thumbnailUrl;
	private int year;
	private double rating;
	private String gcm_regid;

	public Movie00() {
	}

	public Movie00(String name, String thumbnailUrl, int year, double rating,
			String gcm_regid) {
		super();
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.rating = rating;
		this.gcm_regid = gcm_regid;
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

	public String getGCMREG() {
		return gcm_regid;
	}

	public void setGCMREG(String gcm_regid) {
		this.gcm_regid = gcm_regid;
	}
	
	

}
