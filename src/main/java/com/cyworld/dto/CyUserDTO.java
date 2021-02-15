package com.cyworld.dto;

public class CyUserDTO {
	private String userId, userPw, userName, userGender, userBirth, checkDay;
	private int userDotory , toDay, totalDay;

	public String getUserId() {
		return userId;
	}

	public String getCheckDay() {
		return checkDay;
	}

	public void setCheckDay(String checkDay) {
		this.checkDay = checkDay;
	}	

	public int getToDay() {
		return toDay;
	}

	public void setToDay(int toDay) {
		this.toDay = toDay;
	}

	public int getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(int totalDay) {
		this.totalDay = totalDay;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public int getUserDotory() {
		return userDotory;
	}

	public void setUserDotory(int userDototy) {
		this.userDotory = userDototy;
	}

	public String getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}
	
	

}
