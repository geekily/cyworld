package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyIntroDTO;
import com.cyworld.dto.CyMemberBoardDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyUserDTO;


@Component
public class CyMemberBoardDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}


	public List<CyMemberBoardDTO> getMemberBoard(String userId) {
		List<CyMemberBoardDTO> list = sessionTemplate.selectList("com.cyworld.memberBoard.getMemberBoard",userId);		
		return list;
	}


	public void insertData(CyMemberBoardDTO cyMemberBoardDTO) {
		//우선 데이터가 있는지 확인.
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", cyMemberBoardDTO.getUserId());
		hMap.put("memberId", cyMemberBoardDTO.getMemberId());
		CyMemberBoardDTO vo = sessionTemplate.selectOne("com.cyworld.memberBoard.getMemberCheck",hMap);
		if(vo==null) {
			sessionTemplate.insert("com.cyworld.memberBoard.insertData",cyMemberBoardDTO);
		}else {
			sessionTemplate.delete("com.cyworld.memberBoard.deleteData",hMap);
			sessionTemplate.insert("com.cyworld.memberBoard.insertData",cyMemberBoardDTO);
		}
	}


	public void deleteData(String userId, String memberId) {		
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("memberId", memberId);
		sessionTemplate.delete("com.cyworld.memberBoard.deleteData",hMap);		
	}
}
