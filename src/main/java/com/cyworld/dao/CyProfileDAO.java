package com.cyworld.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyProfileDTO;


@Component
public class CyProfileDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	public CyProfileDTO getContent(String userId) {
		return sessionTemplate.selectOne("com.cyworld.profile.getContent", userId);
	}
	
	public void updateContent(CyProfileDTO dto) {
		sessionTemplate.update("com.cyworld.profile.updateContent",dto);
	}
	
	public void insertData(CyProfileDTO dto) {
		sessionTemplate.insert("com.cyworld.profile.insertContent",dto);		
	}
}
