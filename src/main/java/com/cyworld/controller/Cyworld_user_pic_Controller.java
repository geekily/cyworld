package com.cyworld.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyworld.dao.CyMemberDAO;
import com.cyworld.dao.CyMemberListDAO;
import com.cyworld.dao.CyUserDAO;
import com.cyworld.dao.Cyworld_pic_DAO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyMemberListDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.Cyworld_pic_DTO;
import com.cyworld.dto.Cyworld_pic_menu_DTO;
import com.cyworld.dto.Cyworld_pic_reple_DTO;
import com.cyworld.util.FileManager_pic;
import com.cyworld.util.MyUtil_pic;

@Controller
public class Cyworld_user_pic_Controller {

	@Autowired
	Cyworld_pic_DAO dao;

	@Autowired
	CyMemberDAO memberDAO;

	@Autowired
	FileManager_pic fileManager;

	@Autowired
	MyUtil_pic myUtil;

	@Autowired
	CyUserDTO cyUserDTO;

	@Autowired
	CyUserDAO cyUserDAO;


	@RequestMapping(value = "/cy/user_picture.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView m3(HttpServletRequest request, String userId, String folderName, String mode, String menu) throws Exception{
		
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		//일촌인지 아닌지 확인--------------------------------------------------------------------
		CyMemberDTO cyMemberDTO=memberDAO.checkMember(userId, cyUserDTO.getUserId());

		int privacy=2;

		if(cyMemberDTO!=null) {
			privacy=1;
		}

		//-----------------------------------------------------------------------------------------
		List<Cyworld_pic_menu_DTO> list = dao.getUserMenu(userId, privacy);
		int totalFolderData=dao.getTotalFolderData(userId);
		//01-08 예리 추가
		cyUserDTO = cyUserDAO.getUserData(userId);
		request.setAttribute("userDTO", cyUserDTO);
		String currentFolder = "기본 폴더";
		String basicMode="normalMode";

		if (list.size() != 0) {
			currentFolder = list.get(0).getFolderName();
		}

		if (folderName != null) {
			currentFolder = folderName;
		}

		if (mode != null) {
			basicMode = mode;
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("userId", userId);
		mav.addObject("menu",menu);
		mav.addObject("folderName", currentFolder);
		mav.addObject("encodedFolderName", URLEncoder.encode(URLDecoder.decode(currentFolder, "UTF-8"), "UTF-8"));
		mav.addObject("totalFolderData", totalFolderData);
		request.setAttribute("mode",basicMode);
		mav.setViewName("userpage/user_picture");

		return mav;
	}

	@RequestMapping(value = "/cy/user/picture/my_r_pic_page.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String r_pic_page(String folderName, String userId) throws Exception{

		return "redirect:/cy/user/picture/list.action?folderName=" + URLEncoder.encode(folderName, "UTF-8") + "&userId="+userId;
	}

	@RequestMapping(value = "/cy/user/picture/list.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request, String folderName, String userId) throws Exception{

		String cp = request.getContextPath();

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		String pageNum = request.getParameter("pageNum");

		int currentPage = 1;

		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}

		int numPerPage = 3;
		int totalData = dao.getTotalData(userId, folderName);
		int totalPage = myUtil.getPageCount(numPerPage, totalData);

		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		List<Cyworld_pic_DTO> list = dao.getList(userId, folderName, start, end);

		Iterator<Cyworld_pic_DTO> it = list.iterator();
		int listNum = 0, num = 0;
		while (it.hasNext()) {
			Cyworld_pic_DTO temp = it.next();
			listNum = totalData - (start + num - 1);
			temp.setListNum(listNum);
			if (temp.getContent() != null) {
				temp.setContent(temp.getContent().replaceAll("\r\n", "<br/>"));
			}
			temp.setFolderName(URLEncoder.encode(temp.getFolderName(), "UTF-8"));
			num++;
		}

		int totalFolderData=dao.getTotalFolderData(userId);

		String urlList = cp + "/cy/user/picture/list.action?folderName=" + URLEncoder.encode(folderName, "UTF-8") + "&userId="+userId;

		CyMemberDTO cyMemberDTO=memberDAO.checkMember(cyUserDTO.getUserId(), userId);


		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		mav.addObject("folderName", URLDecoder.decode(folderName, "UTF-8"));
		mav.addObject("totalData", totalData);
		mav.addObject("totalFolderData", totalFolderData);
		if (totalData != 0) {
			mav.addObject("pathUserId", list.get(0).getUserId());
		}
		mav.addObject("cyMemberDTO", cyMemberDTO);
		mav.setViewName("userpage/user_r_pic_page");

		return mav;
	}


	@RequestMapping(value = "/cy/user/picture/menuList.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String menuList(HttpServletRequest request, String userId, String folderName, String mode) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		//일촌인지 아닌지 확인--------------------------------------------------------------------
		CyMemberDTO cyMemberDTO=memberDAO.checkMember(userId, cyUserDTO.getUserId());

		int privacy=2;

		if(cyMemberDTO!=null) {
			privacy=1;
		}

		//-----------------------------------------------------------------------------------------

		List<Cyworld_pic_menu_DTO> list = dao.getUserMenu(userId, privacy);
		int totalFolderData=dao.getTotalFolderData(userId);

		String currentFolder = "기본 폴더";
		String basicMode="normalMode";

		if (list.size() != 0) {
			currentFolder = list.get(0).getFolderName();
		}


		if (folderName != null) {
			currentFolder = folderName;
		}

		if (mode != null) {
			basicMode = mode;
		}


		request.setAttribute("userId", userId);
		request.setAttribute("list", list);
		request.setAttribute("folderName", currentFolder);
		request.setAttribute("mode",basicMode);
		request.setAttribute("totalFolderData", totalFolderData);

		return "userpage/user_pic_menu";
	}


	@RequestMapping(value = "/cy/user/picture/insertReple.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String insertReple(HttpServletRequest request, String userId, String replierId, String replierName,
			String content, int picNum, int index, String userName) throws Exception {


		HttpSession session = request.getSession();
		CyUserDTO cyUserDTO = (CyUserDTO) session.getAttribute("session");

		Cyworld_pic_reple_DTO dto = new Cyworld_pic_reple_DTO();
		dto.setNum(dao.getMaxRepleNum(userId) + 1);
		dto.setPicNum(picNum);
		dto.setUserId(userId);
		dto.setUserName(userName);
		dto.setReplierId(replierId);      //DTO
		dto.setReplierName(replierName);
		dto.setGroupNum(0);
		dto.setOrderNum(dao.getMaxRepleOrderNum(userId, picNum) + 1);
		dto.setContent(content);

		dao.insertRepleData(dto);

		return listReple(request, userId, picNum, index);
	}

	@RequestMapping(value = "/cy/user/picture/listReple.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String listReple(HttpServletRequest request, String userId ,int picNum, int index) throws Exception {

		String cp = request.getContextPath();

		String pageNum = request.getParameter("pageNum");
		int currentPage = 1;

		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}

		int numPerPage = 10;
		int totalData = dao.getTotalRepleData(userId, picNum);
		int totalPage = myUtil.getPageCount(numPerPage, totalData);

		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		List<Cyworld_pic_reple_DTO> list = dao.getRepleList(userId, picNum, start, end);

		Iterator<Cyworld_pic_reple_DTO> it = list.iterator();
		while (it.hasNext()) {
			Cyworld_pic_reple_DTO temp = it.next();
			if (temp.getContent() != null) {
				temp.setContent(temp.getContent().replaceAll("\r\n", "<br/>"));
			}
		}

		request.setAttribute("list", list);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, userId, picNum, index));
		request.setAttribute("totalData", totalData);
		request.setAttribute("index", index);
		return "userpage/user_r_pic_reple";
	}

	@RequestMapping(value = "/cy/user/picture/deleteOneRepleData.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteOneRepleData(HttpServletRequest request, String userId, int num, int picNum, int index) throws Exception {

		dao.deleteOneRepleData(userId, num);

		return listReple(request, userId, picNum, index);
	}

	@RequestMapping(value = "/cy/user/picture/scrapPic.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView scrapPic(HttpServletRequest request, String owner, int originalPicNum, int index) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		List<Cyworld_pic_menu_DTO> list = dao.getMenu(cyUserDTO.getUserId());

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("userId", cyUserDTO.getUserId());
		mav.addObject("owner", owner);		
		mav.addObject("originalPicNum", originalPicNum);
		mav.addObject("index", index);

		mav.setViewName("userpage/user_r_pic_scrap");

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/cy/user/picture/scrapPic_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public void scrapPic_ok(HttpServletRequest request, String owner, int originalPicNum, String folderNameForScrap) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		Cyworld_pic_DTO ownerDTO=dao.getOneData(owner, originalPicNum);
		Cyworld_pic_DTO scraperDTO=new Cyworld_pic_DTO();

		scraperDTO.setNum(dao.getMaxNum(cyUserDTO.getUserId()) + 1);
		scraperDTO.setSubject("[스크랩] "+ownerDTO.getSubject());
		scraperDTO.setUserId(cyUserDTO.getUserId());
		scraperDTO.setFolderName(folderNameForScrap);
		scraperDTO.setUserName(cyUserDTO.getUserName());
		scraperDTO.setContent(ownerDTO.getContent());

		//스크랩한 사진명 재설정
		String fileExt = ownerDTO.getOriginalFileName().substring(ownerDTO.getOriginalFileName().lastIndexOf("."));
		String newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		newFileName += System.nanoTime();//10의 -9승
		newFileName += fileExt;

		scraperDTO.setSaveFileName(newFileName);
		scraperDTO.setOriginalFileName(ownerDTO.getOriginalFileName());
		scraperDTO.setOwner(owner);
		scraperDTO.setOriginalPicNum(originalPicNum);

		dao.insertData(scraperDTO);

		//스크랩한 사진 사용자 폴더로 복사-----------------------------------------------------------------------------------------

		File file1=new File("C:\\cyworld\\"+owner+"\\picture\\"+ownerDTO.getFolderName()+"\\"+ownerDTO.getSaveFileName());
		FileInputStream fis=new FileInputStream(file1);

		File file2=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+folderNameForScrap+"\\"+newFileName);
		FileOutputStream fos=new FileOutputStream(file2);

		int data;
		while((data=fis.read())!=-1) {
			fos.write(data);
			fos.flush();
		}

		fis.close();
		fos.close();

		//스크랩된 사진 스크랩 횟수 증가-----------------------------------------------------------------------------------
		dao.updateScrap(owner, originalPicNum);
	}

	@RequestMapping(value = "/cy/user/picture/user_main.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String aac(String userId) throws Exception {

		return "redirect:/cy/user_main.action?userId="+userId;
	}

	@RequestMapping(value = "/cy/picture/user_main.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String aa(String userId) throws Exception {

		return "redirect:/cy/user_main.action?userId="+userId;
	}

}
