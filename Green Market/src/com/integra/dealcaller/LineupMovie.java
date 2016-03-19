package com.integra.dealcaller;

import java.util.ArrayList;

public class LineupMovie {

	private String title, thumbnailUrl,ticker,day, color, sme_id,location;
	private int year;
	private double rating;
	private String gcm_regid;

	public LineupMovie() {
	}

	public LineupMovie(String name, String thumbnailUrl,String color,String sme_id,String location,String ticker,String day, int year, double rating,
			String gcm_regid) {
		super();
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.color = color;
		this.sme_id = sme_id;
		this.location = location;
		this.rating = rating;
		this.gcm_regid = gcm_regid;
		this.ticker = ticker;
		this.day = day;
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
	
	public String getTicker() {
		return ticker;
	}
	
	public void setSME_id(String sme_id) {
		this.sme_id = sme_id;
	}
	
	public String getSME_id() {
		return sme_id;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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
