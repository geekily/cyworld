package com.cyworld.dto;

public class CyitemshopDTO {
	
	int Num;
	String ItemName;
	String Type;
	String SaveFileName;
	String OriginalFileName;
	int itemPrice;
	
	public int getNum() {
		return Num;
	}
	public void setNum(int num) {
		Num = num;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getSaveFileName() {
		return SaveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		SaveFileName = saveFileName;
	}
	public String getOriginalFileName() {
		return OriginalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		OriginalFileName = originalFileName;
	}
	public int getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}	

}
