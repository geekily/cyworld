package com.cyworld.dto;

public class CyUsingItemDTO {
	private String userId, saveFileName, originalFileName, imgX, imgY;
	private int type;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImgX() {
		return imgX;
	}
	public void setImgX(String imgX) {
		this.imgX = imgX;
	}
	public String getImgY() {
		return imgY;
	}
	public void setImgY(String imgY) {
		this.imgY = imgY;
	}
}
