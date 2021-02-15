package com.cyworld.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cyworld.dao.CyIntroDAO;
import com.cyworld.dao.CyMemberDAO;
import com.cyworld.dao.CyUserDAO;
import com.cyworld.dao.Cyworld_pic_DAO;
import com.cyworld.dto.CyIntroDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.Cyworld_pic_DTO;
import com.cyworld.dto.Cyworld_pic_menu_DTO;
import com.cyworld.dto.Cyworld_pic_reple_DTO;
import com.cyworld.util.FileManager_pic;
import com.cyworld.util.MyUtil_pic;

@Controller
public class Cyworld_pic_Controller {

	@Autowired
	Cyworld_pic_DAO dao;

	@Autowired
	CyIntroDAO cyIntroDAO;

	@Autowired
	CyMemberDAO cyMemberDAO;

	@Autowired
	CyUserDAO cyUserDAO;

	@Autowired
	FileManager_pic fileManager;

	@Autowired
	MyUtil_pic myUtil;

	@Autowired
	CyUserDTO cyUserDTO;
	
	@RequestMapping(value = "/cy/my_picture.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView m3(HttpServletRequest request, String folderName, String mode, String menu) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		session.setMaxInactiveInterval(60*30);//--------------------------------------------------------------------------------------------

		List<Cyworld_pic_menu_DTO> list = dao.getMenu(cyUserDTO.getUserId());
		int totalFolderData=dao.getTotalFolderData(cyUserDTO.getUserId());

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
		mav.addObject("folderName", currentFolder);
		mav.addObject("encodedFolderName", URLEncoder.encode(URLDecoder.decode(currentFolder, "UTF-8"), "UTF-8"));
		mav.addObject("totalFolderData", totalFolderData);
		mav.addObject("menu", menu);
		request.setAttribute("mode",basicMode);
		mav.setViewName("mypage/my_picture");

		//--------------------------------------------------일촌
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		CyIntroDTO cyIntroDTO = cyIntroDAO.getData(userId);
		List<CyMemberDTO> list2 = cyMemberDAO.getMemberList(userId);
		if(list.size()==0) {
			mav.addObject("length", 0);
		}else {
			String[][] userList = getMemberUserId(list2,userId);
			mav.addObject("userId", userList[0]);
			mav.addObject("userName", userList[1]);
			mav.addObject("userValue", userList[2]);
			mav.addObject("length", userList[0].length);
		}

		return mav;
	}

	public String[][] getMemberUserId(List<CyMemberDTO> list, String userId) {
		String[][] userList = new String[3][list.size()];
		Iterator<CyMemberDTO> lit = list.iterator();
		CyMemberDTO vo = null;
		int i = 0;
		while(lit.hasNext()) {
			vo = lit.next();
			if(vo.getUserId1().equals(userId)) { //내가 user1에 있으면.
				userList[0][i] = vo.getUserId2(); //아이디저장
				userList[1][i] = cyUserDAO.getName(userList[0][i]);
				userList[2][i] = vo.getUserValue2();
			}else {
				userList[0][i] = vo.getUserId1(); //아이디저장
				userList[1][i] = cyUserDAO.getName(userList[0][i]);
				userList[2][i] = vo.getUserValue1();
			}
			i++;
		}		
		return userList;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/cy/picture/my_write_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView upload_ok(HttpServletRequest req, MultipartHttpServletRequest request, Cyworld_pic_DTO dto) throws Exception {

		HttpSession session = req.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		MultipartFile mFile = request.getFile("upload");
		InputStream is = mFile.getInputStream();
		String path = "C:\\cyworld\\" + cyUserDTO.getUserId() + "\\picture\\" + URLEncoder.encode(dto.getFolderName(), "UTF-8");

		File file = new File(path);
		if (!file.exists())
			file.mkdirs();

		dto.setNum(dao.getMaxNum(cyUserDTO.getUserId()) + 1);
		dto.setUserId(cyUserDTO.getUserId());
		dto.setUserName(cyUserDTO.getUserName());
		if (dto.getContent() == null) {
			dto.setContent("");
		}
		dto.setSaveFileName(fileManager.doFileUpload(is, mFile.getOriginalFilename(), path));
		dto.setOriginalFileName(mFile.getOriginalFilename());
		dto.setOwner(cyUserDTO.getUserId());
		dto.setOriginalPicNum(dto.getNum());

		dao.insertData(dto);

		ModelAndView mav = new ModelAndView();
		mav.addObject("folderName", dto.getFolderName());
		mav.setViewName("mypage/my_r_pic_upload_ok");

		return mav;
	}


	@RequestMapping(value = "/cy/picture/my_r_pic_page.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String r_pic_page(String folderName) throws Exception{

		return "redirect:/cy/picture/list.action?folderName=" + URLEncoder.encode(folderName, "UTF-8");
	}

	@RequestMapping(value = "/cy/picture/list.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request, String folderName) throws Exception{

		String cp = request.getContextPath();

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		String pageNum = request.getParameter("pageNum");

		int currentPage = 1;

		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}

		int numPerPage = 3;
		int totalData = dao.getTotalData(cyUserDTO.getUserId(), folderName);
		int totalPage = myUtil.getPageCount(numPerPage, totalData);

		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		List<Cyworld_pic_DTO> list = dao.getList(cyUserDTO.getUserId(), folderName, start, end);

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

		//------------------------------
		int totalFolderData=dao.getTotalFolderData(cyUserDTO.getUserId());
		//------------------------------

		String urlList = cp + "/cy/picture/list.action?folderName=" + URLEncoder.encode(folderName, "UTF-8");

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		mav.addObject("folderName", URLDecoder.decode(folderName, "UTF-8"));
		mav.addObject("totalData", totalData);
		mav.addObject("totalFolderData", totalFolderData);
		if (totalData != 0) {
			mav.addObject("pathUserId", list.get(0).getUserId());
		}
		mav.setViewName("mypage/my_r_pic_page");

		return mav;
	}

	@RequestMapping(value = "/cy/picture/update.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView upload_ok(Cyworld_pic_DTO dto, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		dto = dao.getOneData(cyUserDTO.getUserId(), dto.getNum());

		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.setViewName("mypage/my_r_pic_update");

		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/cy/picture/update_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView update_ok(MultipartHttpServletRequest request, Cyworld_pic_DTO dto,String folderName) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		if (request.getFile("upload").getOriginalFilename().equals("")) {
			dao.updateWithoutPic(dto);

			ModelAndView mav = new ModelAndView();
			mav.addObject("folderName", dto.getFolderName());
			mav.setViewName("redirect:/cy/picture/list.action");

			return mav;
		}

		MultipartFile mFile = request.getFile("upload");
		InputStream is = mFile.getInputStream();
		String path = "C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+URLEncoder.encode(folderName, "UTF-8");

		fileManager.doFileDelete(dto.getSaveFileName(), path);
		dto.setSaveFileName(fileManager.doFileUpload(is, mFile.getOriginalFilename(), path));
		dto.setOriginalFileName(mFile.getOriginalFilename());

		dao.updateWithPic(dto);

		ModelAndView mav = new ModelAndView();
		mav.addObject("folderName", dto.getFolderName());
		mav.setViewName("redirect:/cy/picture/list.action");

		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/cy/picture/delete.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView delete_ok(Cyworld_pic_DTO dto, HttpServletRequest request, String folderName) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId=cyUserDTO.getUserId();

		Cyworld_pic_DTO fulldto=dao.getOneData(userId, dto.getNum());

		if(!fulldto.getOwner().equals(userId)) {
			dao.deleteOneData(dto);
			dao.deleteRepleData(userId, dto.getNum());
			String path = "C:\\cyworld\\"+userId+"\\picture\\"+folderName;
			fileManager.doFileDelete(dto.getSaveFileName(), path);
		}else {
			String path = null;
			List<Cyworld_pic_DTO> list=dao.selectOneDataWithAllScrap(userId, dto.getNum());
			Iterator<Cyworld_pic_DTO> it=list.iterator();
			while(it.hasNext()) {
				Cyworld_pic_DTO temp=it.next();
				path = "C:"+File.separator+"cyworld"+File.separator+temp.getUserId()+File.separator+"picture"
						+File.separator+temp.getFolderName();
				fileManager.doFileDelete(temp.getSaveFileName(), path);
				dao.deleteRepleData(temp.getUserId(), temp.getNum());
			}

			dao.deleteOneDataWithAllScrap(userId, dto.getNum());
			dao.deleteRepleData(userId, dto.getNum());

		}


		ModelAndView mav = new ModelAndView();
		mav.addObject("folderName", dto.getFolderName());
		mav.setViewName("redirect:/cy/picture/list.action");

		return mav;
	}

	@RequestMapping(value = "/cy/picture/menuList.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String menuList(HttpServletRequest request, String folderName, String mode) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String contextPath=request.getContextPath();
		
		List<Cyworld_pic_menu_DTO> list = dao.getMenu(cyUserDTO.getUserId());
		int totalFolderData=dao.getTotalFolderData(cyUserDTO.getUserId());

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

		request.setAttribute("contextPath", contextPath);
		request.setAttribute("list", list);
		request.setAttribute("folderName", currentFolder);
		request.setAttribute("mode",basicMode);
		request.setAttribute("totalFolderData", totalFolderData);

		return "mypage/my_pic_menu";
	}

	@RequestMapping(value = "/cy/picture/createMenu_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String createMenu_ok(Cyworld_pic_menu_DTO dto, HttpServletRequest request, String currentFolderName, HttpServletResponse response,
			int privacy) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");


		List<Cyworld_pic_menu_DTO> list=dao.getMenu(cyUserDTO.getUserId());
		Iterator<Cyworld_pic_menu_DTO> it=list.iterator();
		while(it.hasNext()) {
			Cyworld_pic_menu_DTO temp=it.next();
			if(temp.getFolderName().equals(dto.getFolderName())) {

				response.setContentType("text/html; charset=UTF-8");

				PrintWriter out = response.getWriter();
				out.println("<script src=\"https://unpkg.com/sweetalert/dist/sweetalert.min.js\"></script>");
				out.println("<script>swal(\"사진첩 이름은 중복될 수 없습니다.\", \"\", \""+request.getContextPath()+"/resources/images/cy_logo.png\");</script>");
				out.flush();

				return menuList(request, currentFolderName, "normalMode");
			}
		}


		String path="C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+dto.getFolderName();
		File file = new File(path);

		if(!file.exists()) {
			file.mkdirs();
		}

		dto.setUserId(cyUserDTO.getUserId());
		dto.setGroupName("");
		dto.setGroupOrder(0);
		dto.setFolderOrder(dao.getMaxFolderOrder(cyUserDTO.getUserId()) + 1);
		dto.setPrivacy(privacy);

		dao.insertFolderData(dto);

		return menuList(request, currentFolderName, "normalMode");
	}

	@RequestMapping(value = "/cy/picture/editFolderName.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String editFolderName(HttpServletRequest request, String editedFolderName, String whetherEditedFolderNameIsCurrentFolder,
			String currentFolderName, String originalFolderNameToBeEdited, int editedPrivacy, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		if(!editedFolderName.equals(originalFolderNameToBeEdited)) {
			
			List<Cyworld_pic_menu_DTO> list=dao.getMenu(cyUserDTO.getUserId());
			Iterator<Cyworld_pic_menu_DTO> it=list.iterator();
			while(it.hasNext()) {
				Cyworld_pic_menu_DTO temp=it.next();
				if(temp.getFolderName().equals(editedFolderName)) {

					response.setContentType("text/html; charset=UTF-8");

					PrintWriter out = response.getWriter();
					out.println("<script src=\"https://unpkg.com/sweetalert/dist/sweetalert.min.js\"></script>");
					out.println("<script>swal(\"사진첩 이름은 중복될 수 없습니다.\", \"\", \""+request.getContextPath()+"/resources/images/cy_logo.png\");</script>");
					out.flush();
					return menuList(request, currentFolderName, "normalMode");
				}
			}
			


			File[] files=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+originalFolderNameToBeEdited).listFiles();

			File newFile=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+editedFolderName);
			if(!newFile.exists()) {
				newFile.mkdirs();
			}


			int data;
			for(int i=0;i<files.length;i++) {
				FileInputStream fis=new FileInputStream("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+originalFolderNameToBeEdited+"\\"+files[i].getName());
				FileOutputStream fos=new FileOutputStream("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+editedFolderName+"\\"+files[i].getName());

				while((data=fis.read())!=-1) {
					fos.write(data);
					fos.flush();
				}

				fis.close();
				fos.close();
			}

			if(files!=null) {
				for(int i=0;i<files.length;i++) {
					files[i].delete();
				}
			}

			File file2=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+originalFolderNameToBeEdited);
			file2.delete();

			dao.updateFolderName(cyUserDTO.getUserId(), editedFolderName, originalFolderNameToBeEdited, editedPrivacy);

			if(whetherEditedFolderNameIsCurrentFolder.equals("same")) {

				return menuList(request, editedFolderName, "normalMode");

			}else if(whetherEditedFolderNameIsCurrentFolder.equals("different")) {

				return menuList(request, currentFolderName, "normalMode");
			}

		}else {
				
			dao.updateFolderName(cyUserDTO.getUserId(), editedFolderName, originalFolderNameToBeEdited, editedPrivacy);
			
			if(whetherEditedFolderNameIsCurrentFolder.equals("same")) {

				return menuList(request, editedFolderName, "normalMode");

			}else if(whetherEditedFolderNameIsCurrentFolder.equals("different")) {

				return menuList(request, currentFolderName, "normalMode");
			}
			
		}


		return menuList(request, null, "normalMode");

	}

	@RequestMapping(value = "/cy/picture/showDeleteMenuButton.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String showDeleteMenuButton(Cyworld_pic_menu_DTO dto, HttpServletRequest request, String currentFolderName) throws Exception {

		return menuList(request, currentFolderName, "deleteMode");
	}

	@RequestMapping(value = "/cy/picture/deleteOneFolder.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteOneFolder(HttpServletRequest request, String folderName, String currentFolderName) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId=cyUserDTO.getUserId();
		
		dao.deleteEverythingOfOneFolder(userId, folderName);

		String path = "C:"+File.separator+"cyworld"+File.separator
				+userId+File.separator+"picture"+File.separator+folderName;

		File[] file1=new File(path).listFiles();
		if(file1!=null) {
			for(int i=0;i<file1.length;i++) {
				file1[i].delete();
			}
		}

		File file2=new File(path);
		file2.delete();

		return menuList(request, currentFolderName, "deleteMode");
	}


	@RequestMapping(value = "/cy/picture/moving.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView moving(HttpServletRequest request, String folderName, int num) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		List<Cyworld_pic_menu_DTO> list = dao.getMenu(cyUserDTO.getUserId());
		String currentFolder = list.get(0).getFolderName();

		if (folderName != null) {
			currentFolder = folderName;
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("userId", cyUserDTO.getUserId());
		mav.addObject("folderName", currentFolder);
		mav.addObject("num", num);

		mav.setViewName("mypage/my_r_pic_moving");

		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/cy/picture/moving_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView moving_ok(HttpServletRequest request, String folderName, int num) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		Cyworld_pic_DTO dto=dao.getOneData(cyUserDTO.getUserId(), num);
		int maxNum = dao.getMaxNum(cyUserDTO.getUserId()) + 1;

		dao.updateFolderMoving(cyUserDTO.getUserId(), folderName, num, maxNum);
		dao.updatePicNumByFolderMoving(cyUserDTO.getUserId(), num, maxNum);

		try {
			File file1=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+dto.getFolderName()+"\\"+dto.getSaveFileName());
			FileInputStream fis=new FileInputStream(file1);

			File file2=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+folderName+"\\"+dto.getSaveFileName());
			FileOutputStream fos=new FileOutputStream(file2);

			int data;
			while((data=fis.read())!=-1) {
				fos.write(data);
				fos.flush();
			}

			fis.close();
			fos.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		fileManager.doFileDelete(dto.getSaveFileName(), "C:\\cyworld\\"+cyUserDTO.getUserId()+"\\picture\\"+dto.getFolderName());

		ModelAndView mav = new ModelAndView();
		mav.addObject("folderName", dto.getFolderName());
		mav.setViewName("redirect:/cy/picture/list.action");

		return mav;
	}

	@RequestMapping(value = "/cy/picture/insertReple.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String insertReple(HttpServletRequest request, String userId, String replierId, String replierName,
			String content, int picNum, int index) throws Exception {


		HttpSession session = request.getSession();
		CyUserDTO cyUserDTO = (CyUserDTO) session.getAttribute("session");

		Cyworld_pic_reple_DTO dto = new Cyworld_pic_reple_DTO();
		dto.setNum(dao.getMaxRepleNum(userId) + 1);
		dto.setPicNum(picNum);
		dto.setUserId(cyUserDTO.getUserId());
		dto.setUserName(cyUserDTO.getUserName());
		dto.setReplierId(replierId);      //DTO
		dto.setReplierName(replierName);
		dto.setGroupNum(0);
		dto.setOrderNum(dao.getMaxRepleOrderNum(userId, picNum) + 1);
		dto.setContent(content);

		dao.insertRepleData(dto);

		return listReple(request, cyUserDTO.getUserId(), picNum, index);
	}

	@RequestMapping(value = "/cy/picture/listReple.action", method = { RequestMethod.GET, RequestMethod.POST })
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
		return "mypage/my_r_pic_reple";
	}

	@RequestMapping(value = "/cy/picture/deleteOneRepleData.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteOneRepleData(HttpServletRequest request, String userId, int num, int picNum, int index) throws Exception {

		dao.deleteOneRepleData(userId, num);

		return listReple(request, userId, picNum, index);
	}




	@RequestMapping(value="/cy/picture/write.action", method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView write(String folderName, HttpServletRequest request) {

		String contextPath=request.getContextPath();

		ModelAndView mav=new ModelAndView();
		mav.addObject("folderName", folderName);
		mav.addObject("contextPath", contextPath);
		mav.setViewName("mypage/my_r_pic_write");

		return mav;
	}
}
