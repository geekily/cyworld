package com.cyworld.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.tei.ImportTEI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyworld.dao.CyitemshopDAO;
import com.cyworld.dto.CyCartDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyMyItemDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.CyitemshopDTO;
import com.cyworld.util.MyUtil_shop;


@Controller("cyitemshopController")
public class CyitemshopController {

	@Autowired
	@Qualifier("cyitemshopDAO")
	CyitemshopDAO cyitemshopDAO;

	@Autowired
	CyitemshopDTO cyitemshopDTO;

	@Autowired
	MyUtil_shop myUtil;

	@RequestMapping(value="/cy/shop/index.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String indexPage(HttpServletRequest request) {

		List<CyitemshopDTO> lists = cyitemshopDAO.getListsRandom();
		request.setAttribute("lists", lists);

		return "shop/index";
	}

	@RequestMapping(value="/cy/shop/product/listdetail.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String listdetail(HttpServletRequest request) throws Exception {

		String cp = request.getContextPath();
		String pageNum = request.getParameter("pageNum");

		int num = Integer.parseInt(request.getParameter("num"));
		
		// 2021-01-18 ��������
		if(num >= 103) {
			List<CyitemshopDTO> lists = new ArrayList<CyitemshopDTO>();
			CyitemshopDTO dto = new CyitemshopDTO();
			
			dto.setItemName("��ī��");
			dto.setItemPrice(50000);
			dto.setNum(num);
			dto.setOriginalFileName("/admin/bacchus.png");
			dto.setSaveFileName("/admin/bacchus.png");
			dto.setType("�ֳ����帵ũ");
			
			lists.add(dto);
			
			request.setAttribute("lists", lists);
			request.setAttribute("dto", dto);
			return "shop/product/listdetail";
		}
		
		cyitemshopDTO = cyitemshopDAO.getReadData(num);

		String searchKey = "itemname";
		String searchValue = request.getParameter("searchValue");

		if(searchValue != null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}

		cyitemshopDTO = cyitemshopDAO.getReadData(num);
		
		// 2021-01-18 ��������
		if(cyitemshopDTO == null){
			return "redirect:/cy/shop/product/list.action";
		}

		String param = "pageNum=" + pageNum;

		if(searchKey != null){
			param += "&searchKey=" + searchKey;
			// param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}

	  List<CyitemshopDTO> lists = cyitemshopDAO.getListsRandom();
	    request.setAttribute("lists", lists);
		

		request.setAttribute("dto", cyitemshopDTO);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("params", param);


		return "shop/product/listdetail";
	}



	@RequestMapping(value="/cy/shop/product/list.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request, String type) throws Exception {

		List<CyitemshopDTO> lists = null;		

		// ����¡ ó��
		String cp = request.getContextPath();
		String pageNum = request.getParameter("pageNum");

		int currentPage = 1;

		if(pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}

		String searchKey = "itemname";
		String searchValue = request.getParameter("searchValue");		

		if(searchValue == null) {
			searchValue = "";
		} else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}

		int dataCount = cyitemshopDAO.getDataCount(searchKey, searchValue);


		int numPerPage = 1;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);

		if(currentPage > totalPage) {
			currentPage = totalPage;
		}

		int start = (currentPage-1) * numPerPage+1;
		int end = currentPage * numPerPage;

		// ������
		if(type == null || type.equals("")) {
			lists = cyitemshopDAO.getListsRandom();
		} else {
			lists = cyitemshopDAO.getListsRandom(type);
		}

		String param = "";

		if(!searchValue.equals("")){
			param = "searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}


		String listUrl = cp + "/list.action";

		if(!param.equals("")){
			listUrl = listUrl + "?" + param;
		}


		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);		
		String articleUrl =	cp + "/list.action?pageNum=" + currentPage;

		if(!param.equals("")) {
			articleUrl = articleUrl + "&" + param;
		}

		request.setAttribute("pageIndexList",pageIndexList);
		request.setAttribute("dataCount",dataCount);
		request.setAttribute("articleUrl",articleUrl);
		request.setAttribute("lists", lists);

		return "shop/product/list";
	}
	
	
	// Ajax (ȸ���˻�)
	@RequestMapping("/cy/doSearch")
	@ResponseBody
	String Search(String userKeyword) throws UnsupportedEncodingException {
		
		List<CyUserDTO> lists = cyitemshopDAO.getSearch(userKeyword);
		// Check
		String data = "";
		String name = "";
		String id = "";
		
		for(int i=0; i<lists.size(); i++) { 
			
			id += lists.get(i).getUserId() + ",";
			
			// �ѱ۱������� ���ڵ�
			name += URLEncoder.encode(lists.get(i).getUserName(), "UTF-8") + ","; 
		}
		
		data = id + "/" + name;
		
		// ajax�� list�� �������迭�� ������ �ȵȴ�....
		return data;
	}
	
	// 2021-01-04 �߰�
	
	// ��ٱ���
	@RequestMapping(value="/cy/shop/cart.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String cart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CyUserDTO cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		
		List<CyCartDTO> lists = cyitemshopDAO.getCart(userId);
		request.setAttribute("lists", lists);

		return "shop/product/cart";
	}
	
	// ��ٱ��� �ߺ��˻� �� �߰� (Ajax)
	@RequestMapping("/cy/shop/product/doInsertCart")
	@ResponseBody
	String Insert(String userId, String itemName, int itemPrice, String type,
			String saveFileName, String originalFileName, int num) throws UnsupportedEncodingException {
		
		// �ߺ��˻�
		boolean result = cyitemshopDAO.cartCheck(userId, itemName);
		
		if(!result) {
			return "false";
		}
		
		// ��ٱ��� DTO
		CyCartDTO dto = new CyCartDTO();
		dto.setUserId(userId);
		dto.setItemName(itemName);
		dto.setItemPrice(itemPrice);
		dto.setType(type);
		dto.setSaveFileName(saveFileName);
		dto.setOriginalFileName(originalFileName);
		dto.setNum(num);
		
		// ��ٱ��� �߰�
		cyitemshopDAO.insertCart(dto);

		return "true";
	}
	
	@RequestMapping(value="/cy/shop/cartDelete.action", method ={RequestMethod.GET,RequestMethod.POST}) 
	public String cartDelete(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		CyUserDTO cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		CyCartDTO dto = new CyCartDTO();
		dto.setUserId(userId);
		dto.setNum(num);
		cyitemshopDAO.deleteCart(dto);
		
		return "redirect:/cy/shop/cart.action?userId=" + userId; 
		}
	
	
	// �ֹ�����Ʈ
	@RequestMapping(value="/cy/shop/order.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String order(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		CyUserDTO cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();

		String cp = request.getContextPath();
		String[] itemNum = request.getParameterValues("num");
		int num[] = new int[itemNum.length];
		int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
		
		String buyType = request.getParameter("buyType");
		
		if(buyType == null || buyType == "") {
			buyType = "cart";
		}
		
		
		if (userId == null || userId.equals("")) {
			return "redirect:/cy/index.action";
		}
		
		if (itemNum.length == 0) {
			return "redirect:/cy/shop/cart.action?userId=" + userId;
		}
		
		// ����ȭ
		for(int i=0; i<num.length; i++) {
			num[i] = Integer.parseInt(itemNum[i]);
		}
		
		if(buyType == "buy" || buyType.equals("buy")) {
			
			List<CyCartDTO> lists = cyitemshopDAO.buyItem(userId, num[0]);
			String userDotory = Integer.toString(lists.get(0).getUserDotory());
			request.setAttribute("lists", lists);
			request.setAttribute("userDotory", userDotory);
			request.setAttribute("buyType", buyType);
			
		} else {
			
			List<CyCartDTO> lists = cyitemshopDAO.selectCart(userId, num);
			String userDotory = Integer.toString(lists.get(0).getUserDotory());
			request.setAttribute("lists", lists);
			request.setAttribute("userDotory", userDotory);
			request.setAttribute("buyType", "cart");
			
		}
		
		return "shop/product/orderMain";
	}
	
	
	// ���ſϷ� ������
	@RequestMapping(value="/cy/shop/order_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String order_ok(HttpServletRequest request) {
		
		CyMyItemDTO dto = new CyMyItemDTO();
		
		int price;
		String[] itemNum = request.getParameterValues("num");
		int num[] = new int[itemNum.length];
		HttpSession session = request.getSession();
		CyUserDTO cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		String friendName = request.getParameter("friendName");
		String buyerType = request.getParameter("type");	
		int userDotory = Integer.parseInt(request.getParameter("userDotory"));
		
		String buyType = request.getParameter("buyType");
		
		String redirectURL = "";
		
		
		String[] type;
		String[] saveFileName;
		String[] originalFileName;
		
		
		boolean checked = false;
		
		// �ߺ� ������ �ޱ����� ����
		String data = "";
		
		for(int i=0; i<num.length; i++) {
			num[i] = Integer.parseInt(itemNum[i]);
		}
		
		
 		if(buyType.equals("buy")){
			
			List<CyitemshopDTO> lists = cyitemshopDAO.buyTypeCheck(num[0]);
			
			// DB�� �Ѿ �迭
			type = new String[lists.size()];
			saveFileName = new String[lists.size()];
			originalFileName = new String[lists.size()];
			
			for(int i=0; i<lists.size(); i++) {
				type[i] = lists.get(i).getType();
				saveFileName[i] = lists.get(i).getSaveFileName();
				originalFileName[i] = lists.get(i).getOriginalFileName();
				
			}
			request.setAttribute("lists", lists);
			redirectURL = "redirect:/cy/shop/product/listdetail.action?num=" + num[0] + "&msg=1";
			
		
		} else {
			
			
			List<CyCartDTO> lists = cyitemshopDAO.buyCart(userId, num);
			
			// DB�� �Ѿ �迭
			type = new String[lists.size()];
			saveFileName = new String[lists.size()];
			originalFileName = new String[lists.size()];
			
			for(int i=0; i<lists.size(); i++) {
				type[i] = lists.get(i).getType();
				saveFileName[i] = lists.get(i).getSaveFileName();
				originalFileName[i] = lists.get(i).getOriginalFileName();
			}
			
			
			request.setAttribute("lists", lists);
			redirectURL = "redirect:/cy/shop/cart.action?userId=" + userId + "&msg=1";
			
			
		}
			
		// ���� ����ϱ����� ����
		if (buyerType == "my" || buyerType.equals("my")) {
			
			// �ߺ� ���Ź���
			for(int i=0; i<num.length; i++) {
				dto.setUserId(userId);
				dto.setNum(num[i]);
				
				List<CyMyItemDTO> itemList = cyitemshopDAO.checkMyItem(dto);
				
				// �ߺ��� ������ �˻�
				if(itemList.size() != 0) {
					data += "num = " + dto.getNum();
					checked = true;					
				}
				
				// �ߺ������� ��ٱ��Ͽ��� ����
				if(itemList.size() != 0) {
					cyitemshopDAO.checkDeleteCart(userId, data);
				}
				
				if(checked == true) {
					return redirectURL;
				}
					
				

			}
			
			cyitemshopDAO.buyMyList(userId, num, type, saveFileName, originalFileName);
				
		} else {
			
			//�����ϱ�
			
			
			// �ߺ� ���Ź���
			for(int i=0; i<num.length; i++) {
				dto.setUserId(friendName);
				dto.setNum(num[i]);
				List<CyMyItemDTO> itemList = cyitemshopDAO.checkMyItem(dto);
				// �ߺ��� ������ �˻�
				
				if(itemList.size() != 0) {
					data += "num = " + dto.getNum();
					checked = true;
					}
				// �ߺ������� ��ٱ��Ͽ��� ����
				if(itemList.size() != 0) {
					cyitemshopDAO.checkDeleteCart(userId, data);
				}
							
				if(checked == true) {
					return redirectURL;
				}

				// http://localhost:8080/cyworld/cy/shop/cart.action?userId=duswhd114

			}

			cyitemshopDAO.buyfriendList(userId,friendName ,num, type, saveFileName, originalFileName);
			
		}
		
		
		// ������ ��ٱ��� , ���丮 ����
		price = userDotory - num.length * 10;
		cyitemshopDAO.buyDeleteCart(userId, num);
		cyitemshopDAO.updateDotory(userId, price);
			
			
		return "shop/product/orderComplete";
	}
		

	
	
	// Ajax ģ�����
	@RequestMapping("/cy/shop/doFriend")
	@ResponseBody
	String Friend(String userId, String friendId) throws UnsupportedEncodingException {
		
		String friendList = "";
		
		List<CyMemberDTO> lists = cyitemshopDAO.getFriend(friendId);
		// Check

		
		for(int i=0; i<lists.size(); i++) {
			
			if(lists.get(i).getUserId1().equals(userId)) {
				friendList += lists.get(i).getUserId2();
			} else {
				friendList += lists.get(i).getUserId1();
			}
			
			if(i != lists.size()-1) {
				friendList += ",";
			}
		}
		
		return friendList;
	}
	
	// 2021-01-11 �����߰�
	
	// Ajax (������ ��ȣ �˻�)
	@ResponseBody
	@RequestMapping(value = {"/cy/shop/product/doGetItemNum", "/cy/shop/doGetItemNum"})
	String itemNum(String userKeyword) throws UnsupportedEncodingException { 
		
		String num = Integer.toString(cyitemshopDAO.getItemNum(userKeyword));
		return num;
	}
	
	
	
	// Ajax (������ �˻�)
	@ResponseBody 
	@RequestMapping(value = {"/cy/shop/product/doSearch", "/cy/shop/doSearch"})
	String itemSearch(String userKeyword) throws UnsupportedEncodingException { 
		
		userKeyword = URLDecoder.decode(userKeyword, "UTF-8");
		List<CyitemshopDTO> lists = cyitemshopDAO.getSearchItem(userKeyword);
		
		String data = "";
		String num = "";
		String itemName = "";
		String type = "";
		
		for(int i=0; i<lists.size(); i++) { 
			
			num += lists.get(i).getNum() + ",";
			itemName += URLEncoder.encode(lists.get(i).getItemName(), "UTF-8").replace("+", "%20") + ","; 
			type += URLEncoder.encode(lists.get(i).getType(), "UTF-8") + ","; 
		}
		
		data = num + "/" + itemName + "/" + type;
		
		return data; 
	}
	
}

