package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyMemberListDTO;


@Component
public class CyMemberListDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	

	public CyMemberDTO checkMember(String userId, String userId2) {
		CyMemberDTO dto = null;
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("userId2", userId2);
		dto = sessionTemplate.selectOne("com.cyworld.member.getMember",hMap);
		return dto;
	}
	
	public void insertList(CyMemberListDTO dto) {
		sessionTemplate.insert("com.cyworld.memberList.insertList",dto);
	}
	
	//01-13 ½ÂÇö - ÀÏÃÌ º¯°æÇÏ±â
	public void updateList(CyMemberListDTO dto) {
        sessionTemplate.insert("com.cyworld.memberList.updateList",dto);
    }

	public List<CyMemberListDTO> getList(String userId) {
		List<CyMemberListDTO> list = sessionTemplate.selectList("com.cyworld.memberList.getList",userId);
		return list;
	}
	
	public CyMemberListDTO getData(String userId1, String userId2) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId1", userId1);
		hMap.put("userId2", userId2);
		CyMemberListDTO dto = sessionTemplate.selectOne("com.cyworld.memberList.getData",hMap);
		return dto;
	}

	public int getCount(String userId) {
		return sessionTemplate.selectOne("com.cyworld.memberList.getCount",userId);
	}

	public void deleteData(String userId1, String userId2) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId1", userId1);
		hMap.put("userId2", userId2);
		sessionTemplate.delete("com.cyworld.memberList.deleteData",hMap);		
	}
	
}
