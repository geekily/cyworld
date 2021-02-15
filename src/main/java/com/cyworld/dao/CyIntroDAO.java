package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyIntroDTO;
import com.cyworld.dto.CyUsingItemDTO;


@Component
public class CyIntroDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public void insertData(CyIntroDTO dto) {
		sessionTemplate.insert("com.cyworld.intro.insertData",dto);
	}
	
	public String getTitle(String userId) {
		return sessionTemplate.selectOne("com.cyworld.intro.getTitle",userId);
	}
	
	public void updateTitle(String userId, String newTitle) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("newTitle", newTitle);
		sessionTemplate.update("com.cyworld.intro.updateTitle",hMap);
	}
	
	public CyIntroDTO getData(String userId) {
		CyIntroDTO dto = null;
		dto = sessionTemplate.selectOne("com.cyworld.intro.getIntroData",userId);
		return dto;
	}
	
	public void updatePic(String userId, String fileName) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("fileName", fileName);
		sessionTemplate.update("com.cyworld.intro.updatePic",hMap);
	}
	
	public String getToday(String userId) {
		return sessionTemplate.selectOne("com.cyworld.intro.getToday",userId);
	}
	
	public void updateToday(String userId, String newToday) {
		Map<String, String>	hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("newToday", newToday);
		sessionTemplate.update("com.cyworld.intro.updateToday",hMap);
	}
	
	public String getInfo(String userId) {
		return sessionTemplate.selectOne("com.cyworld.intro.getInfo",userId);
	}
	
	public void updateInfo(String userId, String newInfo) {
		Map<String, String>	hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("newInfo", newInfo);
		sessionTemplate.update("com.cyworld.intro.updateInfo",hMap);
	}
	
	//예리가 추가함 12-28
	public List<String> getAllCyuser() {
		List<String> lists = sessionTemplate.selectList("com.cyworld.user.getAllCyuser");
		return lists;
	}
	
	//이 코드는 CyUsingItemDAO에도 있음 지워도 됨
	public CyUsingItemDTO useMinimi(String userId) {
		return sessionTemplate.selectOne("com.cyworld.using.getMinimi",userId);
	}
	
	
	
}
