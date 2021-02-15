package com.cyworld.dto;

public class CyUsingSongsDTO {
	
	private String userId;
	private int songOrder;
	private String saveFileName;
	private String originalFileName;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getSongOrder() {
		return songOrder;
	}
	public void setSongOrder(int songOrder) {
		this.songOrder = songOrder;
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
}
