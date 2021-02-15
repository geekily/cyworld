package com.cyworld.dto;

public class CyVideoMenuDTO {
	
	private String userId;
	private String groupName;
	private int groupOrder;
	private String folderName;
	private int folderOrder;
	private int privacy;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupOrder() {
		return groupOrder;
	}
	public void setGroupOrder(int groupOrder) {
		this.groupOrder = groupOrder;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getFolderOrder() {
		return folderOrder;
	}
	public void setFolderOrder(int folderOrder) {
		this.folderOrder = folderOrder;
	}
	public int getPrivacy() {
		return privacy;
	}
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	
}
