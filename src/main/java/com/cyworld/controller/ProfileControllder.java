 package com.cyworld.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cyworld.dao.CyBoardDAO;
import com.cyworld.dao.CyBoardRepleDAO;
import com.cyworld.dao.CyGuestDAO;
import com.cyworld.dao.CyGuestRepleDAO;
import com.cyworld.dao.CyIntroDAO;
import com.cyworld.dao.CyMemberBoardDAO;
import com.cyworld.dao.CyMemberDAO;
import com.cyworld.dao.CyMemberListDAO;
import com.cyworld.dao.CyProfileDAO;
import com.cyworld.dao.CyUserDAO;
import com.cyworld.dao.CyUsingItemDAO;
import com.cyworld.dto.CyBoardDTO;
import com.cyworld.dto.CyBoardMenuDTO;
import com.cyworld.dto.CyBoardRepleDTO;
import com.cyworld.dto.CyGuestDTO;
import com.cyworld.dto.CyGuestRepleDTO;
import com.cyworld.dto.CyIntroDTO;
import com.cyworld.dto.CyMemberBoardDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyMemberListDTO;
import com.cyworld.dto.CyProfileDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.CyUsingItemDTO;
import com.cyworld.util.FileManager_pic;
import com.cyworld.util.MyUtil_pic;

@Controller
public class ProfileControllder {
	@Autowired
	@Qualifier("cyIntroDAO")
	CyIntroDAO cyIntroDAO;

	@Autowired
	@Qualifier("cyUserDAO")
	CyUserDAO cyUserDAO;

	@Autowired
	@Qualifier("cyUsingItemDAO")
	CyUsingItemDAO cyUsingItemDAO;

	@Autowired
	@Qualifier("cyMemberDAO")
	CyMemberDAO cyMemberDAO;

	@Autowired
	@Qualifier("cyGuestDAO")
	CyGuestDAO cyGuestDAO;

	@Autowired
	@Qualifier("cyGuestRepleDAO")
	CyGuestRepleDAO cyGuestRepleDAO;

	@Autowired
	@Qualifier("cyMemberBoardDAO")
	CyMemberBoardDAO cyMemberBoardDAO;

	@Autowired
	@Qualifier("cyMemberListDAO")
	CyMemberListDAO cyMemberListDAO;

	@Autowired
	@Qualifier("cyBoardDAO")
	CyBoardDAO cyBoardDAO;
	
	@Autowired
	@Qualifier("cyBoardRepleDAO")
	CyBoardRepleDAO cyBoardRepleDAO;

	@Autowired
	CyProfileDAO cyProfileDAO;
	
	@Autowired
	CyProfileDTO cyProfileDTO;
	
	@Autowired
	MyUtil_pic myUtil;

	@Autowired
	FileManager_pic fileManager;

	@Autowired
	CyIntroDTO cyIntroDTO;

	@Autowired
	CyUserDTO cyUserDTO;

	@Autowired
	CyMemberDTO cyMemberDTO;

	@Autowired
	CyBoardDTO cyBoardDTO;

	@Autowired
	CyBoardMenuDTO cyBoardMenuDTO;

	@Autowired
	CyGuestDTO cyGuestDTO;

	@Autowired
	CyGuestRepleDTO cyGuestRepleDTO;

	@Autowired
	CyMemberBoardDTO cyMemberBoardDTO;

	@Autowired
	CyMemberListDTO cyMemberListDTO;

	@Autowired
	CyUsingItemDTO cyUsingItemDTO;
	
	@Autowired
	CyBoardRepleDTO cyBoardRepleDTO;


	//mypageㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	
	//프로필메뉴
	@RequestMapping(value="/cy/my_profile.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String m2(HttpServletRequest request, String menu) {
		request.setAttribute("menu", menu);
		return "mypage/my_profile";
	}

	//프로필 오른쪽 화면
	@RequestMapping(value="/cy/my_r_profile.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String r_profile(HttpServletRequest request) {
			return "mypage/my_r_profile";
	}
	
	//프로필 내용 Ajax 가져오기 or 수정하기
	@RequestMapping(value="/cy/my_get_profile.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String my_r_profile(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String content = request.getParameter("content");
		if(content==null) {
			cyUserDTO = (CyUserDTO) session.getAttribute("session");
			cyProfileDTO = cyProfileDAO.getContent(cyUserDTO.getUserId());
			request.setAttribute("dto", cyProfileDTO);
			return "mypage/my_get_profile";
		}else {
			cyProfileDTO.setUserId(cyUserDTO.getUserId());
			cyProfileDTO.setContent(content);
			cyProfileDAO.updateContent(cyProfileDTO);
			cyProfileDTO = cyProfileDAO.getContent(cyUserDTO.getUserId());
			request.setAttribute("dto", cyProfileDTO);
			return "mypage/my_get_profile";
		}
	}
	
	
	//userpageㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	
	//유저 프로필
	@RequestMapping(value="/cy/user_profile.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String user_profile(HttpServletRequest request, String menu) {
		String userId = request.getParameter("userId");
		cyUserDTO = cyUserDAO.getUserData(userId);
		request.setAttribute("userDTO", cyUserDTO);
		request.setAttribute("userId", userId);
		request.setAttribute("menu", menu);
		return "userpage/user_profile";
	}

	//프로필 오른쪽 화면
	@RequestMapping(value="/cy/user_r_profile.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String user_r_profile(HttpServletRequest request) {
		request.setAttribute("userId", request.getParameter("userId"));
		return "userpage/user_r_profile";
	}
	
	//프로필 내용 Ajax 가져오기 or 수정하기
	@RequestMapping(value="/cy/user_get_profile.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String user_get_profile(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		cyProfileDTO = cyProfileDAO.getContent(userId);
		request.setAttribute("dto", cyProfileDTO);
		return "userpage/user_get_profile";	
	}
	
}
