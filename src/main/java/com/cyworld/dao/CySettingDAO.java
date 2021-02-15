package com.cyworld.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.cyworld.dto.CyMenuDTO;

public class CySettingDAO {
	
	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public CyMenuDTO getMenuAvailable(String userId) {
		return sessionTemplate.selectOne("setting.getMenuAvailable", userId);
	}
	
	public void updateMenuAvailable(String userId, String menu3, String menu4, String menu5, String menu6,
			String menuBackgroundColor, String menuFontColor, String menuBorderColor) {
		
		Map<String, Object> hMap=new HashMap<String, Object>();

		hMap.put("userId", userId);
		hMap.put("menu3", menu3);
		hMap.put("menu4", menu4);
		hMap.put("menu5", menu5);
		hMap.put("menu6", menu6);
		hMap.put("menuBackgroundColor", menuBackgroundColor);
		hMap.put("menuFontColor", menuFontColor);
		hMap.put("menuBorderColor", menuBorderColor);
		
		sessionTemplate.insert("setting.updateMenuAvailable", hMap);
	}

}
