package com.integra.dealcaller;

public class UpdateShowcaseItem {
	private int id;
	private String name, status,nesting,url, verify,profilePic,profilePic2,profilePic3,profilePic4,profilePic5, timeStamp;

	public UpdateShowcaseItem() {
	}

	public UpdateShowcaseItem(int id, String name,String status,String nesting,String url,String verify,
			String profilePic,String profilePic2,String profilePic3,String profilePic4,String profilePic5, String timeStamp) {
		
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.verify = verify;
		this.nesting = nesting;
		this.status = status;
		this.profilePic = profilePic;
		this.profilePic2 = profilePic2;
		this.profilePic3 = profilePic3;
		this.profilePic4 = profilePic4;
		this.profilePic5 = profilePic5;
		this.timeStamp = timeStamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	
	
	public String getNesting() {
		return nesting;
	}
	
	public void setNesting(String nesting) {
		this.nesting = nesting;
	}

	public String getVerify() {
		return verify;
	}
	
	public void setVerify(String verify) {
		this.verify = verify;
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
	
	public String getProfilePic2() {
		return profilePic2;
	}

	public void setProfilePic2(String profilePic2) {
		this.profilePic2 = profilePic2;
	}

	public String getProfilePic3() {
		return profilePic3;
	}

	public void setProfilePic3(String profilePic3) {
		this.profilePic3 = profilePic3;
	}
	
	public String getProfilePic4() {
		return profilePic4;
	}

	public void setProfilePic4(String profilePic4) {
		this.profilePic4 = profilePic4;
	}
	
	public String getProfilePic5() {
		return profilePic5;
	}

	public void setProfilePic5(String profilePic5) {
		this.profilePic5 = profilePic5;
	}
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	
	
}
