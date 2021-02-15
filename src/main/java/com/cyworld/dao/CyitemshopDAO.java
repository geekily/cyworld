package com.cyworld.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.cyworld.dto.CyCartDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyMyItemDTO;
import com.cyworld.dto.CyPresentDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.CyitemshopDTO;

public class CyitemshopDAO {
	
	private static SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	
	public int getMaxNum() {
		
		int maxNum = 0;
		maxNum = sessionTemplate.selectOne("com.cyworld.itemshop.maxNum");
		return maxNum;
	}
	
	
	// ��ü ������ ��������
	public List<CyitemshopDTO> getList(int start, int end) {
		// , String searchKey, String searchValue
		HashMap<String, Object> params = new HashMap<String, Object>();
		/*
		 * params.put("searchKey", searchKey); params.put("searchValue", searchValue);
		 */
		params.put("start", start);
		params.put("end", end);
		
		List<CyitemshopDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getLists", params);

		return lists;
	}
	
	
	// Ư�� ������ ��������
	public List<CyitemshopDTO> getSearchData(String ItemName, String Type) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("itemName", ItemName);
		params.put("type", Type);
		List<CyitemshopDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getSearchData", params);	
		return lists;
		
	}
	
	//���� ������ ����ϱ�(��ü ī�װ�)
	public List<CyitemshopDTO> getListsRandom() {
		List<CyitemshopDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getListsRandoms");
		return lists;
	
	}
	
	//���� ������ ����ϱ�(��ü ī�װ�) - type�� ��±���
	public List<CyitemshopDTO> getListsRandom(String type) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		
		List<CyitemshopDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getListsRandom", params);
		
		return lists;
	}

	
	public CyitemshopDTO getReadData(int num) {
		CyitemshopDTO dto = sessionTemplate.selectOne("com.cyworld.itemshop.getReadData", num);
		return dto;
	}
	
	
	public int getDataCount(String searchKey, String searchValue) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		List<CyitemshopDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getLists", params);
		
		int result = sessionTemplate.selectOne("com.cyworld.itemshop.getDataCount", params);
		
		return result;
	}
	
	// ȸ���˻�(Ajax)
	public List<CyUserDTO> getSearch(String userKeyword) {
		
		List<CyUserDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getSearch", userKeyword);	
		return lists;
		
	}
	
	// 2021-01-04 �߰�
	
	// ģ���˻�
	public List<CyMemberDTO> getFriend(String friendId) {
			
		List<CyMemberDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getFriend", friendId);	
		return lists;
		
	}
	
	
	// īƮ ����Ʈ
	public List<CyCartDTO> getCart(String userId){
		
		List<CyCartDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getCart", userId);
		return lists;
	}
	
	
	// ��ٱ��� �߰�
	public void insertCart(CyCartDTO dto){
		sessionTemplate.insert("com.cyworld.itemshop.insertCart", dto);
	}
	
	// ��ٱ��� �ߺ��˻�
	public boolean cartCheck(String userId, String itemName) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("itemName", itemName);
		
		List<CyCartDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.cartCheck", params);
		
		if(lists.size() != 0) {
			return false;
		}
		
		return true;
	}
	
	// ��ٱ��� ����
	public void deleteCart(CyCartDTO dto){
		sessionTemplate.delete("com.cyworld.itemshop.deleteCart", dto);
	}
	
	
	// ���õ� ��ٱ��� + ���� ���丮 ��������
	public List<CyCartDTO> selectCart(String userId, int[] num){
		
		String sql = "";
		
		for(int i=0; i<num.length; i++) {
			sql += "CYCART.num=" + num[i];
			
			// ���� or �߰�
			if(i != num.length-1) {
				sql += " or ";
			}
		}
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("sql", sql);
		
		
		List<CyCartDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.selectCart", params);
		
		return lists;
	}
	
	// ���Ը��
	public List<CyCartDTO> buyCart(String userId, int[] num){
		
		String sql = "";
		
		for(int i=0; i<num.length; i++) {
			sql += "CYCART.num=" + num[i];
			
			// ���� or �߰�
			if(i != num.length-1) {
				sql += " or ";
			}
		}
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("sql", sql);
		
		
		List<CyCartDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.buyCart", params);
		
		return lists;
	}
	
	// ������ ��ٱ��� ����
	public void buyDeleteCart(String userId, int[] num){
		
		String sql = "";
		
		for(int i=0; i<num.length; i++) {
			sql += "CYCART.num=" + num[i];
			
			// ���� or �߰�
			if(i != num.length-1) {
				sql += " or ";
			}
		}
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("sql", sql);
		
		sessionTemplate.delete("com.cyworld.itemshop.buyDeleteCart", params);
	}
	
	// ���� �� ���丮 ����
	public void updateDotory(String userId, int price){
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("price", price);
		
		sessionTemplate.update("com.cyworld.itemshop.updateDotory", params);
		
	}
	
	// ���� ������ ���� �߰� 
	public void buyMyList(String userId, int[] num, String[] type,
			String[] saveFileName, String[] originalFileName){
		
		CyMyItemDTO dto = new CyMyItemDTO();
		
		dto.setUserId(userId);
		dto.setBuyer(userId);
		
		for(int i=0; i<num.length; i++) {
			
			dto.setNum(num[i]);
			dto.setType(type[i]);
			dto.setSaveFileName(saveFileName[i]);
			dto.setOriginalFileName(originalFileName[i]);
			
			sessionTemplate.insert("com.cyworld.itemshop.buyMyList", dto);
			
		}
	}
	
	
	// �����ϱ� ���� �߰�
	public void buyfriendList(String userId, String friendName, int[] num, String[] type,
			String[] saveFileName, String[] originalFileName){
		
		CyMyItemDTO dto = new CyMyItemDTO();
		
		dto.setUserId(friendName);
		dto.setBuyer(userId);
		
		for(int i=0; i<num.length; i++) {
			
			dto.setNum(num[i]);
			dto.setType(type[i]);
			dto.setSaveFileName(saveFileName[i]);
			dto.setOriginalFileName(originalFileName[i]);
			
			CyitemshopDAO.presentPlus(friendName);
			CyitemshopDAO.present(friendName, num[i]);
			
			sessionTemplate.insert("com.cyworld.itemshop.buyfriendList", dto);
			
		}
	}
	
	
	// (����) �ߺ�üũ
	public List<CyMyItemDTO> checkMyItem(CyMyItemDTO dto){

		List<CyMyItemDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.checkMyItem", dto);		
		return lists;
	}
	
	// �ߺ�üũ�� �ɸ��� ����
	public void checkDeleteCart(String userId, String sql){
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("sql", sql);
		
		sessionTemplate.delete("com.cyworld.itemshop.checkDeleteCart", params);
	}
	
	
	// �����ϱ� + ���� ���丮 ��������
	public List<CyCartDTO> buyItem(String userId, int num){
			
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("num", num);
		
		List<CyCartDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.buyItem", params);
			
			return lists;
	}
	
	// ���� üũ
	public List<CyitemshopDTO> buyTypeCheck(int num){
		
		List<CyitemshopDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.buyTypeCheck", num);
			
		return lists;
	}
	
	// 01-08 ���� �߰�
	// ������ ī��Ʈ ����
	public static void presentPlus(String friendId){
		sessionTemplate.update("com.cyworld.itemshop.presentPlus", friendId);	
	}
	
	// ������ ������ insert
	public static void present(String userId, int num) {
		
		CyPresentDTO dto = new CyPresentDTO();
		
		dto.setUserId(userId);
		dto.setPresentNum(num);
		
		sessionTemplate.insert("com.cyworld.itemshop.present", dto);
	}
	
	public CyitemshopDTO getDataByItemName(String itemName){
		return sessionTemplate.selectOne("com.cyworld.itemshop.getDataByItemName", itemName);
	}
	
	// 2021-01-11 �����߰�
	// (Ajax) ������ ã��
	public List<CyitemshopDTO> getSearchItem(String itemName){
		
		List<CyitemshopDTO> lists = sessionTemplate.selectList("com.cyworld.itemshop.getSearchItem", itemName);
			
		return lists;
	}
	
	// (Ajax) ������ ��ȣ ã��
	public int getItemNum(String itemName) {
		int num = 0;
		num = sessionTemplate.selectOne("com.cyworld.itemshop.getSearchNum", itemName);
		return num;
	}
	
}
