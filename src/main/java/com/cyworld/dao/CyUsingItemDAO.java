package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyUsingItemDTO;
import com.cyworld.dto.CyUsingSongsDTO;


@Component
public class CyUsingItemDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}	
	
	public void insertBG(CyUsingItemDTO dto) {
		sessionTemplate.insert("com.cyworld.using.insertBG",dto);
	}
	
	public void insertMinimi(CyUsingItemDTO dto) {
		sessionTemplate.insert("com.cyworld.using.insertMinimi",dto);
	}

	public void insertStoryRoom(CyUsingItemDTO dto) {
		sessionTemplate.insert("com.cyworld.using.insertStoryRoom",dto);	
	}	
	public String useBackGround(String userId) {
		return sessionTemplate.selectOne("com.cyworld.using.getBackGround",userId);
	}

	public CyUsingItemDTO useMinimi(String userId) {
		return sessionTemplate.selectOne("com.cyworld.using.getMinimi",userId);
	}

	public String useStoryRoom(String userId) {
		return sessionTemplate.selectOne("com.cyworld.using.getStoryRoom",userId);
	}
	
	public List<CyUsingSongsDTO> getUsingSongs(String userId){		
		return sessionTemplate.selectList("com.cyworld.using.getUsingSongs",userId);
	}

	public void updateBackGround(String userId, String backGround) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("backGround",backGround);
		sessionTemplate.update("com.cyworld.using.updateBackGround",hMap);
	}

	public void updateStoryRoom(String userId, String storyRoom) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("storyRoom",storyRoom);
		sessionTemplate.update("com.cyworld.using.updateStoryRoom",hMap);		
	}

	public void updateMinimi(String userId, String minimi, String x, String y) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("minimi",minimi);
		hMap.put("imgX", x);
		hMap.put("imgY", y);
		sessionTemplate.update("com.cyworld.using.updateMinimi",hMap);		
	}

	public void insertUsingSongs(CyUsingSongsDTO dto) {
		sessionTemplate.insert("com.cyworld.using.insertUsingSongs", dto);
	}
		
	public void deleteUsingSongs(String userId) {
		sessionTemplate.delete("com.cyworld.using.deleteUsingSongs", userId);
	}

	public int getMaxSongOrder(String userId) {
        return sessionTemplate.selectOne("com.cyworld.using.getMaxSongOrder", userId);
    }
	
	//01-11 예리 추가 (font 관련)
	public void insertUsingFont(CyUsingItemDTO dto) {
		sessionTemplate.insert("com.cyworld.using.insertUsingFont",dto);
	}
	
	public String getUsingFont(String userId) {
		return sessionTemplate.selectOne("com.cyworld.using.getUsingFont",userId);
	}
	
	public String getUsingFontCss(String userId) {
		return sessionTemplate.selectOne("com.cyworld.using.getUsingFontCss",userId);
	}
	
	public void updateUsingFont(String saveFileName, String originalFileName, String userId) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("saveFileName", saveFileName);
		hMap.put("originalFileName", originalFileName);
		hMap.put("userId", userId);
		sessionTemplate.update("com.cyworld.using.updateUsingFont",hMap);		
	}
	
}
