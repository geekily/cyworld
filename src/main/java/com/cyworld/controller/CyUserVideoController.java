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

import com.cyworld.dao.CyIntroDAO;
import com.cyworld.dao.CyMemberDAO;
import com.cyworld.dao.CyUserDAO;
import com.cyworld.dao.CyVideoBoardDAO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.CyVideoDTO;
import com.cyworld.dto.CyVideoMenuDTO;
import com.cyworld.dto.CyVideoRepleDTO;
import com.cyworld.util.FileManager_video;
import com.cyworld.util.MyUtil_pic;

@Controller
public class CyUserVideoController {
	
	@Autowired
	CyVideoBoardDAO dao;
	
	@Autowired
	CyIntroDAO cyIntroDAO;
	
	@Autowired
	CyMemberDAO memberDAO;
	
	@Autowired
	CyUserDAO cyUserDAO;

	@Autowired
	FileManager_video fileManager;

	@Autowired
	MyUtil_pic myUtil;

	@Autowired
	CyUserDTO cyUserDTO;
	
	
	@RequestMapping(value = "/cy/user_video.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView m3(HttpServletRequest request, String userId, 
			String folderName, String mode, String menu) throws Exception{
		
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		
		CyMemberDTO cyMemberDTO=memberDAO.checkMember(userId, cyUserDTO.getUserId());
		
		int privacy=2;

		if(cyMemberDTO!=null) {
			privacy=1;
		}
		
		List<CyVideoMenuDTO> list = dao.getUserMenu(userId, privacy);
		int totalFolderData=dao.getTotalFolderData(userId);
		
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
		mav.addObject("menu", menu);
		mav.addObject("folderName", currentFolder);
		mav.addObject("encodedFolderName", URLEncoder.encode(URLDecoder.decode(currentFolder, "UTF-8"), "UTF-8"));
		mav.addObject("totalFolderData", totalFolderData);
		request.setAttribute("mode",basicMode);
		request.setAttribute("userDTO", cyUserDTO);
		mav.setViewName("userpage/user_video");

		return mav;
	}

	@RequestMapping(value = "/cy/user/video/my_r_video_page.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String r_video_page(String folderName, String userId) throws Exception{

		return "redirect:/cy/user/video/list.action?folderName=" + URLEncoder.encode(folderName, "UTF-8") + "&userId="+userId;
	}

	@RequestMapping(value = "/cy/user/video/list.action", method = { RequestMethod.GET, RequestMethod.POST })
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

		List<CyVideoDTO> list = dao.getList(userId, folderName, start, end);

		Iterator<CyVideoDTO> it = list.iterator();
		int listNum = 0, num = 0;
		while (it.hasNext()) {
			CyVideoDTO temp = it.next();
			listNum = totalData - (start + num - 1);
			temp.setListNum(listNum);
			if (temp.getContent() != null) {
				temp.setContent(temp.getContent().replaceAll("\r\n", "<br/>"));
			}
			temp.setFolderName(URLEncoder.encode(temp.getFolderName(), "UTF-8"));
			num++;
		}

		int totalFolderData=dao.getTotalFolderData(userId);
		
		String urlList = cp + "/cy/user/video/list.action?folderName=" + URLEncoder.encode(folderName, "UTF-8") + "&userId="+userId;
		
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
		mav.setViewName("userpage/user_r_video_page");

		return mav;
	}


	@RequestMapping(value = "/cy/user/video/menuList.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String menuList(HttpServletRequest request, String userId, String folderName, String mode) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		
		CyMemberDTO cyMemberDTO=memberDAO.checkMember(userId, cyUserDTO.getUserId());

		int privacy=2;

		if(cyMemberDTO!=null) {
			privacy=1;
		}

		List<CyVideoMenuDTO> list = dao.getUserMenu(userId, privacy);
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

		return "userpage/user_video_menu";
	}


	@RequestMapping(value = "/cy/user/video/insertReple.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String insertReple(HttpServletRequest request, String userId, String replierId, String replierName,
			String content, int videoNum, int index, String userName) throws Exception {
		
		HttpSession session = request.getSession();
		CyUserDTO cyUserDTO = (CyUserDTO) session.getAttribute("session");

		CyVideoRepleDTO dto = new CyVideoRepleDTO();
		dto.setNum(dao.getMaxRepleNum(userId) + 1);
		dto.setVideoNum(videoNum);
		dto.setUserId(userId);
		dto.setUserName(userName);
		dto.setReplierId(replierId);      //DTO
		dto.setReplierName(replierName);
		dto.setGroupNum(0);
		dto.setOrderNum(dao.getMaxRepleOrderNum(userId, videoNum) + 1);
		dto.setContent(content);

		dao.insertRepleData(dto);

		return listReple(request, userId, videoNum, index);
	}

	@RequestMapping(value = "/cy/user/video/listReple.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String listReple(HttpServletRequest request, String userId ,int videoNum, int index) throws Exception {

		String cp = request.getContextPath();

		String pageNum = request.getParameter("pageNum");
		int currentPage = 1;

		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}

		int numPerPage = 10;
		int totalData = dao.getTotalRepleData(userId, videoNum);
		int totalPage = myUtil.getPageCount(numPerPage, totalData);

		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		List<CyVideoRepleDTO> list = dao.getRepleList(userId, videoNum, start, end);

		Iterator<CyVideoRepleDTO> it = list.iterator();
		while (it.hasNext()) {
			CyVideoRepleDTO temp = it.next();
			if (temp.getContent() != null) {
				temp.setContent(temp.getContent().replaceAll("\r\n", "<br/>"));
			}
		}

		request.setAttribute("list", list);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, userId, videoNum, index));
		request.setAttribute("totalData", totalData);
		request.setAttribute("index", index);
		return "userpage/user_r_video_reple";
	}

	@RequestMapping(value = "/cy/user/video/deleteOneRepleData.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteOneRepleData(HttpServletRequest request, String userId, int num, int videoNum, int index) throws Exception {

		dao.deleteOneRepleData(userId, num);

		return listReple(request, userId, videoNum, index);
	}

	@RequestMapping(value = "/cy/user/video/scrapVideo.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView scrapVideo(HttpServletRequest request, String owner, int originalVideoNum, int index) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		List<CyVideoMenuDTO> list = dao.getMenu(cyUserDTO.getUserId());

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("userId", cyUserDTO.getUserId());
		mav.addObject("owner", owner);		
		mav.addObject("originalVideoNum", originalVideoNum);
		mav.addObject("index", index);

		mav.setViewName("userpage/user_r_video_scrap");

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/cy/user/video/scrapVideo_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public void scrapVideo_ok(HttpServletRequest request, String owner, int originalVideoNum, String folderNameForScrap) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		
		CyVideoDTO ownerDTO=dao.getOneData(owner, originalVideoNum);
		CyVideoDTO scraperDTO=new CyVideoDTO();

		scraperDTO.setNum(dao.getMaxNum(cyUserDTO.getUserId()) + 1);
		scraperDTO.setSubject("[스크랩] "+ownerDTO.getSubject());
		scraperDTO.setUserId(cyUserDTO.getUserId());
		scraperDTO.setFolderName(folderNameForScrap);
		scraperDTO.setUserName(cyUserDTO.getUserName());
		scraperDTO.setContent(ownerDTO.getContent());

		//스크랩한 비디오명 재설정
		String fileExt = ownerDTO.getOriginalFileName().substring(ownerDTO.getOriginalFileName().lastIndexOf("."));
		String newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		newFileName += System.nanoTime();//10의 -9승
		newFileName += fileExt;
		
		scraperDTO.setSaveFileName(newFileName);
		scraperDTO.setOriginalFileName(ownerDTO.getOriginalFileName());
		scraperDTO.setOwner(owner);
		scraperDTO.setOriginalVideoNum(originalVideoNum);

		dao.insertData(scraperDTO);
		
		//스크랩한 비디오 사용자 폴더로 복사-----------------------------------------------------------------------------------------
		
			File file1=new File("C:\\cyworld\\"+owner+"\\video\\"+ownerDTO.getFolderName()+"\\"+ownerDTO.getSaveFileName());
			FileInputStream fis=new FileInputStream(file1);

			File file2=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+folderNameForScrap+"\\"+newFileName);
			FileOutputStream fos=new FileOutputStream(file2);

			int data;
			while((data=fis.read())!=-1) {
				fos.write(data);
				fos.flush();
			}

			fis.close();
			fos.close();
			
		//스크랩된 비디오 스크랩 횟수 증가-----------------------------------------------------------------------------------
			dao.updateScrap(owner, originalVideoNum);
	}
	
	@RequestMapping(value = "/cy/user/video/user_main.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String aac(String userId) throws Exception {
		
		return "redirect:/cy/user_main.action?userId="+userId;
	}
	
	@RequestMapping(value = "/cy/video/user_main.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String aa(String userId) throws Exception {
		
		return "redirect:/cy/user_main.action?userId="+userId;
	}


}
