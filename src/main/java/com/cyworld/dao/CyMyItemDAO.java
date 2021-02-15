package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cyworld.dto.CyMyItemDTO;

public class CyMyItemDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
    //-------------------승현
	public List<CyMyItemDTO> getBackGround(String userId) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("type", "스킨");
		return sessionTemplate.selectList("com.cyworld.myItem.getItemList",hMap);
	}

	public List<CyMyItemDTO> getStoryRoom(String userId) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("type", "스토리룸");
		return sessionTemplate.selectList("com.cyworld.myItem.getItemList",hMap);
	}
	public List<CyMyItemDTO> getMinimiRoom(String userId) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("type", "미니미");
		return sessionTemplate.selectList("com.cyworld.myItem.getItemList",hMap);
	}
	
	public  String getItemName(int num) {
		return sessionTemplate.selectOne("com.cyworld.myItem.getItemName",num);
	}
	
	public List<String> getNotUsingSong(String userId){
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("type", "배경음악");
		return sessionTemplate.selectList("com.cyworld.myItem.getNotUsingSong", hMap);
	}
	
	//-------------------- 1-11 예리
	public List<CyMyItemDTO> getFonts(String userId) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("type", "글꼴");
		return sessionTemplate.selectList("com.cyworld.myItem.getItemList",hMap);
	}
}
