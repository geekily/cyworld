package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyGuestDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyUserDTO;


@Component
public class CyGuestDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public List<CyGuestDTO> getMyDataList(String userId, int start, int end) {
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("start", start);
		hMap.put("end", end);
		return sessionTemplate.selectList("com.cyworld.guest.getMyDataList",hMap);
	}
	
	public List<CyGuestDTO> getUserDataList(String userId, String memberId, int start, int end) {
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("memberId", memberId);
		hMap.put("start", start);
		hMap.put("end", end);
		return sessionTemplate.selectList("com.cyworld.guest.getUserDataList",hMap);
	}

	public int getMyTotalData(String userId) {
		return sessionTemplate.selectOne("com.cyworld.guest.getMyTotalData",userId);
	}
	
	public int getUserTotalData(String userId, String memberId) {
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("memberId", memberId);
		return sessionTemplate.selectOne("com.cyworld.guest.getUserTotalData",hMap);
	}

	public void deleteData(int num) {
		sessionTemplate.delete("com.cyworld.guest.deleteData",num);
	}

	public int getMaxNum() {		
		return sessionTemplate.selectOne("com.cyworld.guest.getMaxNum");
	}

	public void insertData(CyGuestDTO cyGuestDTO) {
		sessionTemplate.insert("com.cyworld.guest.insertData",cyGuestDTO);
		
	}

	public void setSecret(int num) {
		sessionTemplate.update("com.cyworld.guest.setSecret",num);		
	}

	public String getTotalDayCount(String userId) {
		int temp = sessionTemplate.selectOne("com.cyworld.guest.getTotalCount",userId);
		return Integer.toString(temp);
	}

	public String getNowDayCount(String userId, String nowDay) {
		Map<String,String> hMap = new HashMap<String, String>();
		hMap.put("userId",userId);
		hMap.put("nowDay", nowDay);
		int temp = sessionTemplate.selectOne("com.cyworld.guest.getNowCount",hMap);
		return Integer.toString(temp);
	}

}
