package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyIntroDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyUserDTO;


@Component
public class CyMemberDAO {

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
	
	public void insertData(CyMemberDTO dto) {
		sessionTemplate.insert("com.cyworld.member.insertData", dto);
	}
	
	public List<CyMemberDTO> getMemberList(String userId) {
		List<CyMemberDTO> list = null;
		list = sessionTemplate.selectList("com.cyworld.member.getMemberList", userId);
		return list;
	}


	public void deleteMember(String myId, String userId) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("myId", myId);
		hMap.put("userId", userId);
		sessionTemplate.delete("com.cyworld.member.deleteMember",hMap);		
	}


	public void updateData(CyMemberDTO cyMemberDTO) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("myId", cyMemberDTO.getUserId1());
		hMap.put("userId", cyMemberDTO.getUserId2());
		sessionTemplate.delete("com.cyworld.member.deleteMember",hMap);
		sessionTemplate.insert("com.cyworld.member.insertData", cyMemberDTO);
	}
}
