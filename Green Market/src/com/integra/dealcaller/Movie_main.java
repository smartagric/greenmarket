package com.integra.dealcaller;

public class Movie_main {
	private int id;
	private String name, status,profilePic, timeStamp , holder_id,cover_photo,photo1,photo2,photo3,photo4,photo5,verification_status,nesting_institution;

	public Movie_main() {
	}

	public Movie_main(int id, String name, String image, String status,String holder_id,String cover_photo,String photo1,String photo2,String photo3,String photo4,String photo5,
			String profilePic, String timeStamp, String verification_status, String nesting_institution ) {
		super();
		this.id = id;
		this.name = name;

		this.status = status;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.holder_id=holder_id;
		this.cover_photo=cover_photo;
		this.photo1=photo1;
		this.photo2=photo2;
		this.photo3=photo3;
		this.photo4=photo4;
		this.photo5=photo5;
		this.verification_status=verification_status;
		this.nesting_institution=nesting_institution;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getHolder_ID() {
		return holder_id;
	}

	public void setHolder_ID(String holder_id) {
		this.holder_id = holder_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCoverPhoto() {
		return cover_photo;
	}

	public void setCoverPhoto(String cover_photo) {
		this.cover_photo = cover_photo;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	
	public String getPhoto3() {
		return photo3;
	}

	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}
	
	public String getPhoto4() {
		return photo4;
	}

	public void setPhoto4(String photo4) {
		this.photo4 = photo4;
	}
	
	
	public String getPhoto5() {
		return photo5;
	}

	public void setPhoto5(String photo5) {
		this.photo5 = photo5;
	}
	
	
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getVerificationStatus() {
		return verification_status;
	}

	public void setVerificationStatus(String verification_status) {
		this.verification_status= verification_status;
	}
	
	public String getNestingInstitution() {
		return nesting_institution;
	}

	public void setNestingInstitution(String nesting_institution) {
		this.nesting_institution= nesting_institution;
	}
	
}
