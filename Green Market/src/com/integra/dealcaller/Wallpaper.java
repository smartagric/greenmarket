package com.integra.dealcaller;

import java.io.Serializable;

public class Wallpaper implements Serializable {
	private static final long serialVersionUID = 1L;
	private String url, photoJson;
	

	public Wallpaper() {
	}

	public Wallpaper(String photoJson, String url) {		
		this.photoJson = photoJson;
		this.url = url;
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhotoJson() {
		return photoJson;
	}

	public void setPhotoJson(String photoJson) {
		this.photoJson = photoJson;
	}

	
}
