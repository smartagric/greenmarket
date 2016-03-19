package com.integra.dealcaller;

public class FriendsItem {
	private int id;
	private String  mobile,username, email,school, work, residence, country, timestamp;

	public FriendsItem() {
	}

	public FriendsItem(int id, String mobile,String username,String email,String school,String work,
			String residence,String country,String timestamp) {
		
		super();
		this.id = id;
		this.username = username;
		this.mobile = mobile;
		this.email = email;
		this.school = school;
		this.work = work;
		this.residence = residence;
		this.country = country;
		this.timestamp = timestamp;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	

	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSchool() {
		return school;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}

	

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTimeStamp() {
		return timestamp;
	}

	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
