package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyGuestDTO;
import com.cyworld.dto.CyGuestRepleDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyUserDTO;


@Component
public class CyGuestRepleDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public List<CyGuestRepleDTO> getDataList(int num) {
		return sessionTemplate.selectList("com.cyworld.guestReple.getDataList",num);
	}

	public int getMaxNum() {
		String temp = sessionTemplate.selectOne("com.cyworld.guestReple.getMaxNum");
		return Integer.parseInt(temp);
	}
	
	public void insertData(CyGuestRepleDTO dto) {
		sessionTemplate.insert("com.cyworld.guestReple.insertData",dto);
	}

	public void deleteData(int num) {
		sessionTemplate.delete("com.cyworld.guestReple.deleteData",num);
		
	}

	public void deleteAllData(int num) {
		sessionTemplate.delete("com.cyworld.guestReple.deleteAllData",num);
	}
}
