package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.cyworld.dto.CyVideoDTO;
import com.cyworld.dto.CyVideoMenuDTO;
import com.cyworld.dto.CyVideoRepleDTO;

public class CyVideoBoardDAO {
	
	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public int getMaxNum(String userId) {
	
		return sessionTemplate.selectOne("videoBoard.getMaxNum", userId);
	}
	
	public void insertData(CyVideoDTO dto) {
		
		sessionTemplate.insert("videoBoard.insertData", dto);
	}
	
	public List<CyVideoDTO> getList(String userId, String folderName, int start, int end){

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		hMap.put("start", start);
		hMap.put("end", end);

		return sessionTemplate.selectList("videoBoard.getAllData", hMap);
	}
	
	public int getTotalData(String userId, String folderName) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		
		
		return sessionTemplate.selectOne("videoBoard.getTotalData", hMap);
	}
	
	public CyVideoDTO getOneData(String userId, int num) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("num", num);
		
		return sessionTemplate.selectOne("videoBoard.getOneData", hMap);
	}
	
	public void updateWithoutVideo(CyVideoDTO dto) {
		
		sessionTemplate.update("videoBoard.updateWithoutVideo", dto);
	}
	
	public void updateWithVideo(CyVideoDTO dto) {
		
		sessionTemplate.update("videoBoard.updateWithVideo", dto);
	}
	
	public void deleteOneData(CyVideoDTO dto) {
		
		sessionTemplate.delete("videoBoard.deleteOneData", dto);
	}
	
	public List<CyVideoMenuDTO> getMenu(String userId) {
		
		return sessionTemplate.selectList("videoBoard.getMenu", userId);
	}
	
	public int getMaxFolderOrder(String userId) {
		
		return sessionTemplate.selectOne("videoBoard.getMaxFolderOrder", userId);
	}
	
	public void insertFolderData(CyVideoMenuDTO dto) {
		
		sessionTemplate.insert("videoBoard.insertFolderData", dto);
	}
	
	public int getTotalFolderData(String userId) {
		
		return sessionTemplate.selectOne("videoBoard.getTotalFolderData", userId);
	}
	
	public void updateFolderName(String userId, String editedFolderName, 
			String originalFolderNameToBeEdited, int editedPrivacy) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("editedFolderName", editedFolderName);
		hMap.put("originalFolderNameToBeEdited", originalFolderNameToBeEdited);
		hMap.put("editedPrivacy", editedPrivacy);
		
		sessionTemplate.update("videoBoard.updateFolderNameInTableCyVideo", hMap);
		sessionTemplate.update("videoBoard.updateFolderNameInTableCyVideoBoard", hMap);
	}
	
	public void deleteEverythingOfOneFolder(String userId, String folderName) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		
		List<CyVideoDTO> list=sessionTemplate.selectList("videoBoard.getRealAllData", hMap);
		
		for(int i=0;i<list.size();i++) {
			deleteRepleData(userId, list.get(i).getNum());
		}
		
		sessionTemplate.delete("videoBoard.deleteAllVideosOfOneFolder", hMap);
		
		sessionTemplate.delete("videoBoard.deleteOneFolder", hMap);
	}
	
	public void updateFolderMoving(String userId, String folderName, int num, int maxNum) {
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		hMap.put("num", num);
		hMap.put("maxNum", maxNum);
	
		sessionTemplate.update("videoBoard.updateFolderMoving", hMap);
	}
	
	public void updateVideoNumByFolderMoving(String userId, int num, int maxNum) {
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("num", num);
		hMap.put("maxNum", maxNum);
	
		sessionTemplate.update("videoBoard.updateVideoNumByFolderMoving", hMap);
	}
	
	public int getMaxRepleNum(String userId) {
		
		return sessionTemplate.selectOne("videoBoard.getMaxRepleNum", userId);
	}
	
	public int getMaxRepleOrderNum(String userId, int videoNum) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("videoNum", videoNum);
		
		return sessionTemplate.selectOne("videoBoard.getMaxRepleOrderNum", hMap);
	}
	
	public int getTotalRepleData(String userId, int videoNum) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("videoNum", videoNum);
		
		return sessionTemplate.selectOne("videoBoard.getTotalRepleData", hMap);
	}
	
	public void insertRepleData(CyVideoRepleDTO dto) {
		
		sessionTemplate.insert("videoBoard.insertRepleData", dto);
	}
	
	public List<CyVideoRepleDTO> getRepleList(String userId, int videoNum, int start, int end){
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("videoNum", videoNum);
		hMap.put("start", start);
		hMap.put("end", end);
		
		return sessionTemplate.selectList("videoBoard.getRepleList", hMap);
	}
	
	public void deleteRepleData(String userId, int videoNum) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("videoNum", videoNum);
		
		sessionTemplate.delete("videoBoard.deleteRepleData", hMap);
	}
	
	public void deleteOneRepleData(String userId, int num) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("num", num);
		
		sessionTemplate.delete("videoBoard.deleteOneRepleData", hMap);
	}
	
	public void deleteOneDataWithAllScrap(String owner, int originalVideoNum) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("owner", owner);
		hMap.put("originalVideoNum", originalVideoNum);
		
		sessionTemplate.delete("videoBoard.deleteOneDataWithAllScrap", hMap);
	}
	
	public List<CyVideoDTO> selectOneDataWithAllScrap(String owner, int originalVideoNum){
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("owner", owner);
		hMap.put("originalVideoNum", originalVideoNum);
		
		return sessionTemplate.selectList("videoBoard.selectOneDataWithAllScrap", hMap);
	}
	
	public void updateScrap(String userId, int num) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("num", num);
		
		sessionTemplate.update("videoBoard.updateScrap", hMap);
	}

	public List<CyVideoMenuDTO> getUserMenu(String userId,int privacy) {

		Map<String, Object> hMap=new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("privacy", privacy);

		return sessionTemplate.selectList("videoBoard.getUserMenu", hMap);
	}

	public  String getNowDayCount(String userId, String nowDay, int type) {
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("userId",userId);
		hMap.put("nowDay", nowDay);
		hMap.put("type", type);
		int temp = sessionTemplate.selectOne("videoBoard.getNowDayCount",hMap);
		return Integer.toString(temp);
	}

	public  String getTotalDayCount(String userId , int type) {
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("userId",userId);
		hMap.put("type", type);
		int temp = sessionTemplate.selectOne("videoBoard.getTotalDayCount",hMap);
		return Integer.toString(temp);
	}
	
	
	
}
