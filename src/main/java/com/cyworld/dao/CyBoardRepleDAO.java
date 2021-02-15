package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyBoardDTO;
import com.cyworld.dto.CyBoardMenuDTO;
import com.cyworld.dto.CyBoardRepleDTO;


@Component
public class CyBoardRepleDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public List<CyBoardRepleDTO> getData(int num) {
		List<CyBoardRepleDTO> list = sessionTemplate.selectList("com.cyworld.boardReple.getData",num);
		return list;
	}

	public int getNum() {
		return sessionTemplate.selectOne("com.cyworld.boardReple.getNum");
	}

	public void insertData(CyBoardRepleDTO dto) {
		sessionTemplate.insert("com.cyworld.boardReple.insertData",dto);
	}

	public void deleteData(int num) {
		sessionTemplate.delete("com.cyworld.boardReple.deleteData",num);
	}

}
