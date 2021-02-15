package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyMyItemDTO;
import com.cyworld.dto.CyPresentDTO;
import com.cyworld.dto.CyUserDTO;


@Component
public class CyUserDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public void insertData(CyUserDTO dto) {
		sessionTemplate.insert("com.cyworld.user.insertData",dto);
	}
	
	public CyUserDTO getData(String userId, String userPw) {
		CyUserDTO dto = null;		
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("userPw", userPw);		
		dto = sessionTemplate.selectOne("com.cyworld.user.getData",hMap);
		return dto;
	}
	
	public List<String> getUserList(String userId){
		List<String> list = null;
		list = sessionTemplate.selectList("com.cyworld.user.getUserList",userId);
		return list;
	}

	public String getName(String userId) {
		return sessionTemplate.selectOne("com.cyworld.user.getName",userId);
	}

	public CyUserDTO getUserData(String userId) {
		return sessionTemplate.selectOne("com.cyworld.user.getUserData",userId);
	}
	
	//예리가 추가함 1-4 (회원 도토리 조회)
	public int getDotory(String userId) {
		return sessionTemplate.selectOne("com.cyworld.user.getDotory", userId);
	}
	
	//예리가 추가함 1-4 (회원 아이디 중복 조회)
	public int idCheck(String userId) {
		return sessionTemplate.selectOne("com.cyworld.user.idCheck", userId);
	}
	
	//예리가 추가함 1-5 (today 값 출력)
	
	public int getToday(String userId) {
		return sessionTemplate.selectOne("com.cyworld.user.getToday", userId);
	}

	public void hitCount(String userId) {
		sessionTemplate.update("com.cyworld.user.hitCount",userId);		
	}
	
	public void hitInit(String userId) {
		sessionTemplate.update("com.cyworld.user.hitInit",userId);
	}
	
	//예리 추가 1-6(menu dummy값 넘김)
	public void insertMenu(String userId) {
		sessionTemplate.insert("com.cyworld.user.insertMenu", userId);
	}
	
	// 선물함
		// 01-08 현종 추가
		public int getPresentList(String userId) {
			return sessionTemplate.selectOne("com.cyworld.user.presentList", userId);
		}

		public List<CyPresentDTO> getPreList(String userId) { 
			return sessionTemplate.selectList("com.cyworld.user.getPreList", userId);
		}
		
		// 선물함 아이템 찾기
		public List<CyMyItemDTO> getMyItem(String userId, int[] num) {
			
			String sql = "";
			
			for(int i=0; i<num.length; i++) {
				sql += "cymyitem.num = " + num[i];
				if(i != num.length-1) {
					sql += " or ";
				}
			}
			
			
			Map<String, String> hMap = new HashMap<String, String>();
			hMap.put("userId", userId);
			hMap.put("sql", sql);		
			
			List<CyMyItemDTO> lists = sessionTemplate.selectList("com.cyworld.user.getMyItem", hMap);
			return lists;	
		}
		
		// 선물함 리스트 update
		public void updatePresentList(String userId, int num) {
			
			CyPresentDTO dto = new CyPresentDTO();
			dto.setPresentNum(num);
			dto.setUserId(userId);
			
			sessionTemplate.update("com.cyworld.user.updatePresentList", dto);
		}
		
		// 선물함 삭제
		public void deletePresentList(String userId, int[] num) {
			
			String sql = "";
			
			for(int i=0; i<num.length; i++) {
				sql += "presentNum = " + num[i];
				if(i != num.length-1) {
					sql += " or ";
				}
			}
			
			Map<String, String> hMap = new HashMap<String, String>();
			hMap.put("userId", userId);
			hMap.put("sql", sql);		
			
			sessionTemplate.delete("com.cyworld.user.deletePresentList", hMap);
			
		}
		
		//01-13 임의빈 추가
		public void updatePw(String userId, String userPw) {

			Map<String, String> hMap = new HashMap<String, String>();
			hMap.put("userId", userId);
			hMap.put("userPw", userPw);	

			sessionTemplate.update("com.cyworld.user.updatePw", hMap);
		}

		public int getCheck(String userId, String userPw) {
			Map<String, String> hMap = new HashMap<String, String>();
			hMap.put("userId", userId);
			hMap.put("userPw", userPw);	
			return sessionTemplate.selectOne("com.cyworld.user.getCheck", hMap);
		}
	
}
