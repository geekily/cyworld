package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cyworld.dto.CyBoardDTO;
import com.cyworld.dto.CyBoardMenuDTO;


@Component
public class CyBoardDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public List<CyBoardMenuDTO>	getBoardMenuList(String userId, int type){
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("type", type);
		List<CyBoardMenuDTO> list = sessionTemplate.selectList("com.cyworld.board.getFolderMenuList",hMap);
		return list;
	}

	public int getCount(String userId, String folderName) {
		Map<String,String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		int temp = sessionTemplate.selectOne("com.cyworld.board.getCount",hMap);
		return temp;
	}
	
	public List<CyBoardDTO> getBoardList(String userId, String folderName, int start, int end) {
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		hMap.put("start", start);
		hMap.put("end", end);
		return sessionTemplate.selectList("com.cyworld.board.getBoardList",hMap);
	}

	public int getNum() {
		return sessionTemplate.selectOne("com.cyworld.board.getNum");
	}

	public void insertData(CyBoardDTO dto) {
		sessionTemplate.insert("com.cyworld.board.insertData",dto);
		
	}

	public void insertFolder(String userId,String folderName, int type) {
		int num = sessionTemplate.selectOne("com.cyworld.board.getFolderNum");
		num++;
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("num",num);
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		hMap.put("type", type);
		sessionTemplate.insert("com.cyworld.board.insertFolder", hMap);		
	}

	public void updateFolder(int num, String newFolderName, int type) {
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("num",num);
		hMap.put("newFolderName", newFolderName);
		hMap.put("type", type);
		sessionTemplate.update("com.cyworld.board.updateFolder",hMap);
	}

	public void updateBoardFolder(String userId, String oldFolderName, String newFolderName) {
		Map<String,String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("oldFolderName", oldFolderName);
		hMap.put("newFolderName", newFolderName);
		sessionTemplate.update("com.cyworld.board.updateBoardFolder",hMap);		
	}

	public void deleteFolder(int num) {
		sessionTemplate.delete("com.cyworld.board.deleteFolder",num);		
	}

	public void deleteBoardFolder(String userId, String folderName) {
		Map<String,String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);
		sessionTemplate.delete("com.cyworld.board.deleteBoardFolder",hMap);
	}

	public List<Integer> getBoardNumList(String userId, String folderName) {
		Map<String,String> hMap = new HashMap<String, String>();
		hMap.put("userId", userId);
		hMap.put("folderName", folderName);		
		return sessionTemplate.selectList("com.cyworld.board.getBoardNumList", hMap);
	}

	public void deleteBoardReple(int guestNum) {
		sessionTemplate.delete("com.cyworld.board.deleteBoardReple",guestNum);
		
	}

	public CyBoardDTO getData(String num) {		
		return sessionTemplate.selectOne("com.cyworld.board.getData",Integer.parseInt(num));
	}

	public void moveFile(String num, String newFolderName) {
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("num",Integer.parseInt(num));
		hMap.put("newFolderName", newFolderName);
		sessionTemplate.update("com.cyworld.board.moveFile",hMap);
	}

	public void deleteBoard(int num) {
		//게시글삭제
		sessionTemplate.delete("com.cyworld.board.deleteBoard",num);
		
		//뎃글삭제
		sessionTemplate.delete("com.cyworld.board.deleteBoardReple",num);
	}

	public void hitCountUp(int num) {
		sessionTemplate.update("com.cyworld.board.hitCountUp",num);
		
	}

	public String getNowDayCount(String userId, String nowDay, int type) {
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("userId",userId);
		hMap.put("nowDay", nowDay);
		hMap.put("type", type);
		int temp = sessionTemplate.selectOne("com.cyworld.board.getNowCount",hMap);
		return Integer.toString(temp);
	}

	public String getTotalDayCount(String userId, int type) {
		Map<String,Object> hMap = new HashMap<String, Object>();
		hMap.put("userId",userId);
		hMap.put("type", type);
		int temp = sessionTemplate.selectOne("com.cyworld.board.getTotalCount",hMap);
		return Integer.toString(temp);
	}

	public void deleteAllBoard(int num) {
		sessionTemplate.delete("com.cyworld.board.deleteAllBoard",num);		
	}

	public List<Integer> selectListNum(int num) {
		return sessionTemplate.selectList("com.cyworld.board.selectNumList",num);
	}

	public void deleteAllReple(int num) {
		sessionTemplate.delete("com.cyworld.board.deleteAllReple",num);		
	}

	public int getOrigiNalNum(int num) {
		return sessionTemplate.selectOne("com.cyworld.board.getOriNum",num);
	}

}
