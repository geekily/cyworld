package com.cyworld.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.cyworld.dto.Cyworld_pic_DTO;
import com.cyworld.dto.Cyworld_pic_menu_DTO;
import com.cyworld.dto.Cyworld_pic_reple_DTO;

public class Cyworld_pic_DAO {

	private SqlSessionTemplate sessionTemplate;

	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public int getMaxNum(String userId) {

		return sessionTemplate.selectOne("pictureBoard.getMaxNum", userId);
	}

	public void insertData(Cyworld_pic_DTO dto) {

		sessionTemplate.insert("pictureBoard.insertData", dto);
	}

	public List<Cyworld_pic_DTO> getList(String userId, String folderName, int start, int end){

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		hMap.put("start", start);
		hMap.put("end", end);

		return sessionTemplate.selectList("pictureBoard.getAllData", hMap);
	}

	public int getTotalData(String userId, String folderName) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);


		return sessionTemplate.selectOne("pictureBoard.getTotalData", hMap);
	}

	public Cyworld_pic_DTO getOneData(String userId, int num) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("num", num);

		return sessionTemplate.selectOne("pictureBoard.getOneData", hMap);
	}

	public void updateWithoutPic(Cyworld_pic_DTO dto) {

		sessionTemplate.update("pictureBoard.updateWithoutPic", dto);
	}

	public void updateWithPic(Cyworld_pic_DTO dto) {

		sessionTemplate.update("pictureBoard.updateWithPic", dto);
	}

	public void deleteOneData(Cyworld_pic_DTO dto) {

		sessionTemplate.delete("pictureBoard.deleteOneData", dto);
	}

	public List<Cyworld_pic_menu_DTO> getMenu(String userId) {

		return sessionTemplate.selectList("pictureBoard.getMenu", userId);
	}

	public int getMaxFolderOrder(String userId) {

		return sessionTemplate.selectOne("pictureBoard.getMaxFolderOrder", userId);
	}

	public void insertFolderData(Cyworld_pic_menu_DTO dto) {

		sessionTemplate.insert("pictureBoard.insertFolderData", dto);
	}

	public int getTotalFolderData(String userId) {

		return sessionTemplate.selectOne("pictureBoard.getTotalFolderData", userId);
	}

	public void updateFolderName(String userId, String editedFolderName, String originalFolderNameToBeEdited, int editedPrivacy) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("editedFolderName", editedFolderName);
		hMap.put("originalFolderNameToBeEdited", originalFolderNameToBeEdited);
		hMap.put("editedPrivacy", editedPrivacy);


		sessionTemplate.update("pictureBoard.updateFolderNameInTableCyPicture", hMap);
		sessionTemplate.update("pictureBoard.updateFolderNameInTableCyPictureBoard", hMap);
	}

	public void deleteEverythingOfOneFolder(String userId, String folderName) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);

		List<Cyworld_pic_DTO> list=sessionTemplate.selectList("pictureBoard.getRealAllData", hMap);

		for(int i=0;i<list.size();i++) {
			deleteRepleData(userId, list.get(i).getNum());
		}

		sessionTemplate.delete("pictureBoard.deleteAllPicsOfOneFolder", hMap);

		sessionTemplate.delete("pictureBoard.deleteOneFolder", hMap);
	}

	public void updateFolderMoving(String userId, String folderName, int num, int maxNum) {
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		hMap.put("num", num);
		hMap.put("maxNum", maxNum);

		sessionTemplate.update("pictureBoard.updateFolderMoving", hMap);
	}

	public void updatePicNumByFolderMoving(String userId, int num, int maxNum) {
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("num", num);
		hMap.put("maxNum", maxNum);

		sessionTemplate.update("pictureBoard.updatePicNumByFolderMoving", hMap);
	}

	public int getMaxRepleNum(String userId) {

		return sessionTemplate.selectOne("pictureBoard.getMaxRepleNum", userId);
	}

	public int getMaxRepleOrderNum(String userId, int picNum) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("picNum", picNum);

		return sessionTemplate.selectOne("pictureBoard.getMaxRepleOrderNum", hMap);
	}

	public int getTotalRepleData(String userId, int picNum) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("picNum", picNum);

		return sessionTemplate.selectOne("pictureBoard.getTotalRepleData", hMap);
	}

	public void insertRepleData(Cyworld_pic_reple_DTO dto) {

		sessionTemplate.insert("pictureBoard.insertRepleData", dto);
	}

	public List<Cyworld_pic_reple_DTO> getRepleList(String userId, int picNum, int start, int end){

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("picNum", picNum);
		hMap.put("start", start);
		hMap.put("end", end);

		return sessionTemplate.selectList("pictureBoard.getRepleList", hMap);
	}

	public void deleteRepleData(String userId, int picNum) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("picNum", picNum);

		sessionTemplate.delete("pictureBoard.deleteRepleData", hMap);
	}

	public void deleteOneRepleData(String userId, int num) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("num", num);

		sessionTemplate.delete("pictureBoard.deleteOneRepleData", hMap);
	}



	public void deleteOneDataWithAllScrap(String owner, int originalPicNum) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("owner", owner);
		hMap.put("originalPicNum", originalPicNum);

		sessionTemplate.delete("pictureBoard.deleteOneDataWithAllScrap", hMap);
	}

	public List<Cyworld_pic_DTO> selectOneDataWithAllScrap(String owner, int originalPicNum){

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("owner", owner);
		hMap.put("originalPicNum", originalPicNum);

		return sessionTemplate.selectList("pictureBoard.selectOneDataWithAllScrap", hMap);
	}

	public void updateScrap(String userId, int num) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("num", num);

		sessionTemplate.update("pictureBoard.updateScrap", hMap);
	}

	public List<Cyworld_pic_menu_DTO> getUserMenu(String userId,int privacy) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("privacy", privacy);

		return sessionTemplate.selectList("pictureBoard.getUserMenu", hMap);
	}

	public String getNowDayCount(String userId,String nowDay, int type) {
		Map<String, Object>hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("nowDay", nowDay);
		hMap.put("type", type);
		int nowCount = sessionTemplate.selectOne("pictureBoard.getNowDayCount",hMap);
		return Integer.toString(nowCount);
	}

	public String getTotalDayCount(String userId, int type) {
		Map<String, Object>hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("type", type);
		int totalCount = sessionTemplate.selectOne("pictureBoard.getTotalDayCount",hMap);
		return Integer.toString(totalCount);
	}





}
