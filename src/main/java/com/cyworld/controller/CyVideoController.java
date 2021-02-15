package com.cyworld.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.cyworld.dao.CyVideoBoardDAO;
import com.cyworld.dto.CyIntroDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.CyVideoDTO;
import com.cyworld.dto.CyVideoMenuDTO;
import com.cyworld.dto.CyVideoRepleDTO;
import com.cyworld.util.FileManager_video;
import com.cyworld.util.MyUtil_pic;

@Controller
public class CyVideoController {
	
	@Autowired
	CyVideoBoardDAO dao;
	
	@Autowired
	CyIntroDAO cyIntroDAO;
	
	@Autowired
	CyMemberDAO cyMemberDAO;
	
	@Autowired
	CyUserDAO cyUserDAO;

	@Autowired
	FileManager_video fileManager;

	@Autowired
	MyUtil_pic myUtil;

	@Autowired
	CyUserDTO cyUserDTO;
	
	@RequestMapping(value = "/cy/my_video.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView m3(HttpServletRequest request, String folderName, String mode, String menu) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		session.setMaxInactiveInterval(60*30);//--------------------------------------------------------------------------------------------
		menu="menu5";
		List<CyVideoMenuDTO> list = dao.getMenu(cyUserDTO.getUserId());
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
		mav.setViewName("mypage/my_video");
		
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
	@RequestMapping(value = "/cy/video/my_write_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView upload_ok(HttpServletRequest req, MultipartHttpServletRequest request, CyVideoDTO dto) throws Exception {

		HttpSession session = req.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		MultipartFile mFile = request.getFile("upload");
		InputStream is = mFile.getInputStream();
		String path = "C:\\cyworld\\" + cyUserDTO.getUserId() + "\\video\\" + URLEncoder.encode(dto.getFolderName(), "UTF-8");

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
		dto.setOriginalVideoNum(dto.getNum());

		dao.insertData(dto);

		ModelAndView mav = new ModelAndView();
		mav.addObject("folderName", dto.getFolderName());
		mav.setViewName("mypage/my_r_video_upload_ok");

		return mav;
	}


	@RequestMapping(value = "/cy/video/my_r_video_page.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String r_video_page(String folderName) throws Exception{

		return "redirect:/cy/video/list.action?folderName=" + URLEncoder.encode(folderName, "UTF-8");
	}

	@RequestMapping(value = "/cy/video/list.action", method = { RequestMethod.GET, RequestMethod.POST })
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

		List<CyVideoDTO> list = dao.getList(cyUserDTO.getUserId(), folderName, start, end);

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

		//------------------------------
		int totalFolderData=dao.getTotalFolderData(cyUserDTO.getUserId());
		//------------------------------

		String urlList = cp + "/cy/video/list.action?folderName=" + URLEncoder.encode(folderName, "UTF-8");

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		mav.addObject("folderName", URLDecoder.decode(folderName, "UTF-8"));
		mav.addObject("totalData", totalData);
		mav.addObject("totalFolderData", totalFolderData);
		if (totalData != 0) {
			mav.addObject("pathUserId", list.get(0).getUserId());
		}
		mav.setViewName("mypage/my_r_video_page");

		return mav;
	}

	@RequestMapping(value = "/cy/video/update.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView upload_ok(CyVideoDTO dto, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		dto = dao.getOneData(cyUserDTO.getUserId(), dto.getNum());

		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.setViewName("mypage/my_r_video_update");

		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/cy/video/update_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView update_ok(MultipartHttpServletRequest request, CyVideoDTO dto,String folderName) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		if (request.getFile("upload").getOriginalFilename().equals("")) {
			dao.updateWithoutVideo(dto);

			ModelAndView mav = new ModelAndView();
			mav.addObject("folderName", dto.getFolderName());
			mav.setViewName("redirect:/cy/video/list.action");

			return mav;
		}

		MultipartFile mFile = request.getFile("upload");
		InputStream is = mFile.getInputStream();
		String path = "C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+URLEncoder.encode(folderName, "UTF-8");
		
		fileManager.doFileDelete(dto.getSaveFileName(), path);
		dto.setSaveFileName(fileManager.doFileUpload(is, mFile.getOriginalFilename(), path));
		dto.setOriginalFileName(mFile.getOriginalFilename());

		dao.updateWithVideo(dto);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("folderName", dto.getFolderName());
		mav.setViewName("redirect:/cy/video/list.action");

		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/cy/video/delete.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView delete_ok(CyVideoDTO dto, HttpServletRequest request, String folderName) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		
		CyVideoDTO fulldto=dao.getOneData(cyUserDTO.getUserId(), dto.getNum());

		if(!fulldto.getOwner().equals(cyUserDTO.getUserId())) {
			dao.deleteOneData(dto);
			dao.deleteRepleData(cyUserDTO.getUserId(), dto.getNum());
			String path = "C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+folderName;
			fileManager.doFileDelete(dto.getSaveFileName(), path);
		}else {
			String path = null;
			List<CyVideoDTO> list=dao.selectOneDataWithAllScrap(cyUserDTO.getUserId(), dto.getNum());
			Iterator<CyVideoDTO> it=list.iterator();
			while(it.hasNext()) {
				CyVideoDTO temp=it.next();
				path = "C:\\cyworld\\"+temp.getUserId()+"\\video\\"+temp.getFolderName();
				fileManager.doFileDelete(temp.getSaveFileName(), path);
				dao.deleteRepleData(temp.getUserId(), temp.getNum());
			}
			
			
			dao.deleteOneDataWithAllScrap(cyUserDTO.getUserId(), dto.getNum());
			dao.deleteRepleData(cyUserDTO.getUserId(), dto.getNum());
			
		}


		ModelAndView mav = new ModelAndView();
		mav.addObject("folderName", dto.getFolderName());
		mav.setViewName("redirect:/cy/video/list.action");

		return mav;
	}

	@RequestMapping(value = "/cy/video/menuList.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String menuList(HttpServletRequest request, String folderName, String mode) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		List<CyVideoMenuDTO> list = dao.getMenu(cyUserDTO.getUserId());
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

		request.setAttribute("list", list);
		request.setAttribute("folderName", currentFolder);
		request.setAttribute("mode",basicMode);
		request.setAttribute("totalFolderData", totalFolderData);

		return "mypage/my_video_menu";
	}

	@RequestMapping(value = "/cy/video/createMenu_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String createMenu_ok(CyVideoMenuDTO dto, HttpServletRequest request, String currentFolderName, HttpServletResponse response,
				int privacy) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");


		List<CyVideoMenuDTO> list=dao.getMenu(cyUserDTO.getUserId());
		Iterator<CyVideoMenuDTO> it=list.iterator();
		while(it.hasNext()) {
			CyVideoMenuDTO temp=it.next();
			if(temp.getFolderName().equals(dto.getFolderName())) {

				response.setContentType("text/html; charset=UTF-8");

				PrintWriter out = response.getWriter();
				out.println("<script src=\"https://unpkg.com/sweetalert/dist/sweetalert.min.js\"></script>");
				out.println("<script>swal(\"폴더의 이름은 중복될 수 없습니다.\", \"\", \""+request.getContextPath()+"/resources/images/cy_logo.png\");</script>");
				out.flush();

				return menuList(request, currentFolderName, "normalMode");
			}
		}


		String path="C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+dto.getFolderName();
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

	@RequestMapping(value = "/cy/video/editFolderName.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String editFolderName(HttpServletRequest request, String editedFolderName, String whetherEditedFolderNameIsCurrentFolder,
			String currentFolderName, String originalFolderNameToBeEdited, int editedPrivacy, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		if(!editedFolderName.equals(originalFolderNameToBeEdited)) {
			
			List<CyVideoMenuDTO> list=dao.getMenu(cyUserDTO.getUserId());
			Iterator<CyVideoMenuDTO> it=list.iterator();
			while(it.hasNext()) {
				CyVideoMenuDTO temp=it.next();
				if(temp.getFolderName().equals(editedFolderName)) {

					response.setContentType("text/html; charset=UTF-8");

					PrintWriter out = response.getWriter();
					out.println("<script src=\"https://unpkg.com/sweetalert/dist/sweetalert.min.js\"></script>");
					out.println("<script>swal(\"폴더의 이름은 중복될 수 없습니다.\", \"\", \""+request.getContextPath()+"/resources/images/cy_logo.png\");</script>");
					out.flush();

					return menuList(request, currentFolderName, "normalMode");
				}
			}
			

			File[] files=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+originalFolderNameToBeEdited).listFiles();

			File newFile=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+editedFolderName);
			if(!newFile.exists()) {
				newFile.mkdirs();
			}

			int data;
			
			for(int i=0;i<files.length;i++) {
				FileInputStream fis=new FileInputStream("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+originalFolderNameToBeEdited+"\\"+files[i].getName());
				FileOutputStream fos=new FileOutputStream("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+editedFolderName+"\\"+files[i].getName());

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

			File file2=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+originalFolderNameToBeEdited);
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

	@RequestMapping(value = "/cy/video/showDeleteMenuButton.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String showDeleteMenuButton(CyVideoMenuDTO dto, HttpServletRequest request, String currentFolderName) throws Exception {

		return menuList(request, currentFolderName, "deleteMode");
	}

	@RequestMapping(value = "/cy/video/deleteOneFolder.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteOneFolder(HttpServletRequest request, String folderName, String currentFolderName) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		dao.deleteEverythingOfOneFolder(cyUserDTO.getUserId(), folderName);

		String path = "C:\\cyworld\\" + cyUserDTO.getUserId() + "\\video\\" + folderName;


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


	@RequestMapping(value = "/cy/video/moving.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView moving(HttpServletRequest request, String folderName, int num) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		List<CyVideoMenuDTO> list = dao.getMenu(cyUserDTO.getUserId());
		String currentFolder = list.get(0).getFolderName();

		if (folderName != null) {
			currentFolder = folderName;
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("userId", cyUserDTO.getUserId());
		mav.addObject("folderName", currentFolder);
		mav.addObject("num", num);

		mav.setViewName("mypage/my_r_video_moving");

		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/cy/video/moving_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView moving_ok(HttpServletRequest request, String folderName, int num) throws Exception {

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		CyVideoDTO dto=dao.getOneData(cyUserDTO.getUserId(), num);
		int maxNum = dao.getMaxNum(cyUserDTO.getUserId()) + 1;

		dao.updateFolderMoving(cyUserDTO.getUserId(), folderName, num, maxNum);
		dao.updateVideoNumByFolderMoving(cyUserDTO.getUserId(), num, maxNum);

		try {
			File file1=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+dto.getFolderName()+"\\"+dto.getSaveFileName());
			FileInputStream fis=new FileInputStream(file1);

			File file2=new File("C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+folderName+"\\"+dto.getSaveFileName());
			FileOutputStream fos=new FileOutputStream(file2);

			int data;
			while((data=fis.read())!=-1) {
				fos.write(data);
				fos.flush();
			}

			fis.close();
			fos.close();
		} catch (Exception e) {
		}

		fileManager.doFileDelete(dto.getSaveFileName(), "C:\\cyworld\\"+cyUserDTO.getUserId()+"\\video\\"+dto.getFolderName());

		ModelAndView mav = new ModelAndView();
		mav.addObject("folderName", dto.getFolderName());
		mav.setViewName("redirect:/cy/video/list.action");

		return mav;
	}

	@RequestMapping(value = "/cy/video/insertReple.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String insertReple(HttpServletRequest request, String userId, String replierId, String replierName,
			String content, int videoNum, int index) throws Exception {


		HttpSession session = request.getSession();
		CyUserDTO cyUserDTO = (CyUserDTO) session.getAttribute("session");
		
		CyVideoRepleDTO dto = new CyVideoRepleDTO();
		dto.setNum(dao.getMaxRepleNum(userId) + 1);
		dto.setVideoNum(videoNum);
		dto.setUserId(cyUserDTO.getUserId());
		dto.setUserName(cyUserDTO.getUserName());
		dto.setReplierId(replierId);      //DTO
		dto.setReplierName(replierName);
		dto.setGroupNum(0);
		dto.setOrderNum(dao.getMaxRepleOrderNum(userId, videoNum) + 1);
		dto.setContent(content);

		dao.insertRepleData(dto);

		return listReple(request, cyUserDTO.getUserId(), videoNum, index);
	}

	@RequestMapping(value = "/cy/video/listReple.action", method = { RequestMethod.GET, RequestMethod.POST })
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
		return "mypage/my_r_video_reple";
	}

	@RequestMapping(value = "/cy/video/deleteOneRepleData.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteOneRepleData(HttpServletRequest request, String userId, int num, int videoNum, int index) throws Exception {

		dao.deleteOneRepleData(userId, num);

		return listReple(request, userId, videoNum, index);
	}




	@RequestMapping(value="/cy/video/write.action", method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView write(String folderName) {

		ModelAndView mav=new ModelAndView();
		mav.addObject("folderName", folderName);
		mav.setViewName("mypage/my_r_video_write");

		return mav;
	}
}
