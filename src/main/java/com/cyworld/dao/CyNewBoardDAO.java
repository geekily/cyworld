package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyBoardDTO;
import com.cyworld.dto.CyBoardMenuDTO;
import com.cyworld.dto.CyNewBoardDTO;


@Component
public class CyNewBoardDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public List<CyNewBoardDTO> getMyList(String userId, String[] arr2) {
		Map<String, Object>hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("length", arr2.length);
		for(int i = 0 ; i < arr2.length ; i++) {
			hMap.put(Integer.toString(i), arr2[i]);
		}
		return sessionTemplate.selectList("com.cyworld.newBoard.getMyList",hMap);
	}
	
	public List<CyNewBoardDTO> getUserList(String userId, String me, String[] arr2, int type) {
		Map<String, Object>hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("me", me);
		hMap.put("length", arr2.length);
		hMap.put("type", type);
		for(int i = 0 ; i < arr2.length ; i++) {
			hMap.put(Integer.toString(i), arr2[i]);
		}
		return sessionTemplate.selectList("com.cyworld.newBoard.getUserList",hMap);
	}

	public int getMyListCount(String userId) {
		return sessionTemplate.selectOne("com.cyworld.newBoard.getMyListCount",userId);
	}

}
