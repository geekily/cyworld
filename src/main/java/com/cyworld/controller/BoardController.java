 package com.cyworld.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.CyUsingItemDTO;
import com.cyworld.util.FileManager_pic;
import com.cyworld.util.MyUtil_pic;

@Controller
public class BoardController {
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


	//mypage�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
	
	// �Խ��� ����ȭ��
	@RequestMapping(value = "/cy/my_board.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String my_board(HttpServletRequest request, String menu) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		menu="menu4";
		String type = request.getParameter("type");
		String folderName = request.getParameter("folderName");
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,2);
		if (menuList.size() > 0) {
			if (folderName == null) {
				request.setAttribute("type", 0);
				request.setAttribute("encodedFolderName", menuList.get(0).getFolderName());
			} else {
				if(type != null)
					request.setAttribute("type", "�����̸��� �ߺ��Ǿ����ϴ�.");
				request.setAttribute("encodedFolderName", folderName);
			}
		} else {
			request.setAttribute("type", 0);
			request.setAttribute("encodedFolderName", null);
		}
		request.setAttribute("menu", menu);
		return "mypage/my_board";
	}

	// �Խ��� �޴���������
	@RequestMapping(value = "/cy/board/getMenu.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String getMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		// ���ο� ���� �����
		String folderName = request.getParameter("folderName");
		if (folderName != null) {			
			List<CyBoardMenuDTO> list = cyBoardDAO.getBoardMenuList(userId, 0);
			String newFolderName = request.getParameter("folderName");
			Iterator<CyBoardMenuDTO> lit = list.iterator();
			while(lit.hasNext()) {
				CyBoardMenuDTO vo = lit.next();
				if(vo.getFolderName().equals(newFolderName)) {
					List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,0);
					request.setAttribute("result", "�����̸��� �ߺ��Ǿ����ϴ�.");
					request.setAttribute("menuList", menuList);
					request.setAttribute("menuLength", menuList.size());
					return "mypage/my_board_getMenu";
				}
			}
			String type = request.getParameter("setType");
			cyBoardDAO.insertFolder(userId, folderName, Integer.parseInt(type));
			String path = "C:\\cyworld\\" + userId + "\\board\\" + folderName;
			File file = new File(path);
			file.mkdirs();
		}
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,0);
		request.setAttribute("result", 0);
		request.setAttribute("menuList", menuList);
		request.setAttribute("menuLength", menuList.size());
		return "mypage/my_board_getMenu";
	}
	
	// �Խ��� �޴� ����
	@RequestMapping(value = "/cy/board/updateMenu.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateMenu(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		// ���� �̸� ����
		String str_num = request.getParameter("num");
		String newFolderName="";
		if (str_num != null) {
			String type = request.getParameter("type");
			// DB��λ� ����
			int num = Integer.parseInt(str_num);
			String oldFolderName = request.getParameter("oldFolderName");
			newFolderName = request.getParameter("newFolderName");
			List<CyBoardMenuDTO> list = cyBoardDAO.getBoardMenuList(userId, 0);
			Iterator<CyBoardMenuDTO> lit = list.iterator();
			while(lit.hasNext()) {
				CyBoardMenuDTO vo = lit.next();
				if(vo.getFolderName().equals(newFolderName)) {
					if(!newFolderName.equals(oldFolderName)) {
						List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,0);
						request.setAttribute("menuList", menuList);
						request.setAttribute("menuLength", menuList.size());
						return "redirect:/cy/my_board.action?folderName="+URLEncoder.encode(newFolderName, "UTF-8")+"&type=0";
					}
				}
			}			
			
			// cyBoardMenu
			cyBoardDAO.updateFolder(num, newFolderName, Integer.parseInt(type));
			// cyBoard
			cyBoardDAO.updateBoardFolder(userId, oldFolderName, newFolderName);

			// �������� ������ ����
			String oldPath = "C:\\cyworld\\" + userId + "\\board\\" + oldFolderName;
			String newPath = "C:\\cyworld\\" + userId + "\\board\\" + newFolderName;
			File oldFile = new File(oldPath);
			File newFile = new File(newPath);
			oldFile.renameTo(newFile);
		}
		return "redirect:/cy/my_board.action?folderName="+URLEncoder.encode(newFolderName, "UTF-8");
	}

	// �Խ��� ���� ����
	@RequestMapping(value = "/cy/board/my_boardFolderDelete.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String boardFolderDelete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();

		// ���� ����
		String delete = request.getParameter("delete");
		if (delete != null) {
			String folderName = request.getParameter("folderName");
			int num = Integer.parseInt(request.getParameter("num"));
			// DB����-�޴�
			cyBoardDAO.deleteFolder(num);
			// DB����-����
			// �ش�Ǵ� �Խñ��� ��ȣ �����ͼ� ����
			List<Integer> list = cyBoardDAO.getBoardNumList(userId, folderName);
			for (int i = 0; i < list.size(); i++) {
				cyBoardDAO.deleteBoardReple(list.get(0));
			}
			// DB����-�Խñ�
			cyBoardDAO.deleteBoardFolder(userId, folderName);
			// �������� ���� ����
			String path = "C:\\cyworld\\" + userId + "\\board\\" + folderName;
			File deleteFolder = new File(path);

			if (deleteFolder.exists()) {
				File[] deleteFolderList = deleteFolder.listFiles();
				for (int j = 0; j < deleteFolderList.length; j++) {
					deleteFolderList[j].delete();
				}
				if (deleteFolderList.length == 0 && deleteFolder.isDirectory()) {
					deleteFolder.delete();
				}
			}
			deleteFolder.delete();
			String refresh = "aaa";
			request.setAttribute("refresh", refresh);
		}
		return "redirect:/cy/my_board.action";
	}

	// �Խ��� �޴��� �´� �Խ���
	@RequestMapping(value = "/cy/board/my_r_board_page.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String my_r_board_page(HttpServletRequest request) throws UnsupportedEncodingException {
		String cp = request.getContextPath();
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		String folderName = request.getParameter("folderName");
		if (folderName == null) {
			List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,2);
			request.setAttribute("menuLength", menuList.size());
			return "mypage/my_r_board_page";
		}
		String pageNum = request.getParameter("pageNum");
		int currentPage = 1;

		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}

		int numPerPage = 10;
		int totalData = cyBoardDAO.getCount(userId, folderName);
		int totalPage = myUtil.getPageCount(numPerPage, totalData);
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		List<CyBoardDTO> boardList = cyBoardDAO.getBoardList(userId, folderName, start, end);

		Iterator<CyBoardDTO> it = boardList.iterator();
		int listNum = 0, num = 0;
		while (it.hasNext()) {
			CyBoardDTO temp = it.next();
			listNum = totalData - (start + num - 1);
			temp.setListNum(listNum);
			if (temp.getContent() != null) {
				temp.setContent(temp.getContent().replaceAll("\r\n", "<br/>"));
			}
			num++;
		}

		String listUrl = cp + "/cy/board/my_r_board_page.action?folderName="+folderName;
		if (boardList.size() == 0)
			request.setAttribute("totalFolderData", 0);
		else
			request.setAttribute("totalFolderData", 1);
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,2);
		request.setAttribute("menuLength", menuList.size());
		request.setAttribute("list", boardList);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, listUrl));
		request.setAttribute("totalData", totalData);
		request.setAttribute("length", boardList.size());
		request.setAttribute("pageNum", currentPage);
		request.setAttribute("folderName", folderName);
		return "mypage/my_r_board_page";
	}

	// �Խ��� ���ۼ�
	@RequestMapping(value = "/cy/board/my_board_write.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String my_board_write(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,2);
		request.setAttribute("menuList", menuList);
		request.setAttribute("folderName", request.getParameter("folderName"));
		request.setAttribute("pageNum", request.getParameter("pageNum"));
		return "mypage/my_r_board_write";
	}

	// �Խ��� �۵��
	@RequestMapping(value = "/cy/board/my_board_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String my_board_ok(HttpServletRequest request, MultipartHttpServletRequest req, CyBoardDTO dto)
			throws Exception {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		String folderName = request.getParameter("folderName");
		MultipartFile mFile = req.getFile("upload");
		if (!mFile.getOriginalFilename().equals("")) {
			InputStream is = mFile.getInputStream();
			String path = "C:\\cyworld\\" + userId + "\\board\\" + URLEncoder.encode(folderName, "UTF-8");
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("��ΰ� �����ϴ�.");
				file.mkdirs();
			}
			dto.setSaveFileName(fileManager.doFileUpload(is, mFile.getOriginalFilename(), path));
			dto.setOriginalFileName(mFile.getOriginalFilename());
		} else {
			dto.setSaveFileName("");
			dto.setOriginalFileName("");
		}
		dto.setNum(cyBoardDAO.getNum() + 1);
		dto.setUserId(userId);
		dto.setUserName(cyUserDTO.getUserName());
		dto.setOriginalNum(0);
		dto.setFolderName(folderName);
		dto.setSubject(dto.getSubject().replaceAll("<[^>]*>", " "));
		dto.setContent(dto.getContent().replaceAll("<[^>]*>", " "));
		dto.setContent(dto.getContent().replaceAll("\n", "<br/>"));
		cyBoardDAO.insertData(dto);
		return "redirect:/cy/board/my_r_board_page.action?folderName=" + URLEncoder.encode(folderName, "UTF-8");
	}

	// �Խ��� ��
	@RequestMapping(value = "/cy/board/my_boardArticle.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String my_boardArticle(HttpServletRequest request) {
		String num = request.getParameter("num");
		String pageNum = request.getParameter("pageNum");
		if (num != null) {
			cyBoardDTO = cyBoardDAO.getData(num);
			cyBoardDTO.setContent(cyBoardDTO.getContent().replace("\n", "<br/>"));
			request.setAttribute("dto", cyBoardDTO);
			request.setAttribute("pageNum", pageNum);
		}
		return "mypage/my_r_board_article";
	}

	// �Խ��� �̵�
	@RequestMapping(value = "/cy/board/my_board_move.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String my_board_move(HttpServletRequest request) {
		String num = request.getParameter("num");
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,2);
		request.setAttribute("list", menuList);
		request.setAttribute("num", num);
		request.setAttribute("folderName", request.getParameter("folderName"));
		return "mypage/my_r_board_moving";
	}

	// �̵��ϱ�
	@RequestMapping(value = "/cy/board/my_board_move_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String my_board_move_ok(HttpServletRequest request) throws Exception {
		//DB�̵�
		String num = request.getParameter("num");
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		cyBoardDTO = cyBoardDAO.getData(num);
		String oldFolderName = cyBoardDTO.getFolderName();
		String newFolderName = request.getParameter("newFolderName");
		String saveFileName = cyBoardDTO.getSaveFileName();
		cyBoardDAO.moveFile(num, newFolderName);
		if(saveFileName!=null) {		
			//��������̵�
			String oldPath = "C:\\cyworld\\"+userId+"\\board\\"+oldFolderName+"\\"+saveFileName;
			String newPath = "C:\\cyworld\\"+userId+"\\board\\"+newFolderName+"\\"+saveFileName;
			File oldFile = FileUtils.getFile(oldPath);
			File newFile = FileUtils.getFile(newPath);
			FileUtils.moveFile(oldFile, newFile);
		}
		
		
		return "redirect:/cy/board/my_r_board_page.action?folderName="+ URLEncoder.encode(request.getParameter("folderName"), "UTF-8");
	}

	// �Խ��� ���ϴٿ�ε�
	@RequestMapping(value = "/cy/board/my_download.action")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) {
		String num = request.getParameter("num");
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		String folderName = request.getParameter("folderName");
		String saveDir = "C:\\cyworld\\" + userId + "\\board\\" + folderName;
		String fileName = request.getParameter("saveFileName");
		File file = new File(saveDir + "/" + fileName);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ServletOutputStream sos = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			sos = response.getOutputStream();
			String reFilename = "";
			boolean isMSIE = request.getHeader("user-agent").indexOf("MSIE") != -1
					|| request.getHeader("user-agent").indexOf("Trident") != -1;
			if (isMSIE) {
				reFilename = URLEncoder.encode(fileName, "utf-8");
				reFilename = reFilename.replaceAll("\\+", "%20");
			} else {
				reFilename = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
			}
			response.setContentType("application/octet-stream;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + reFilename + "\"");
			response.setContentLength((int) file.length());
			int read = 0;
			while ((read = bis.read()) != -1) {
				sos.write(read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// �Խñ� ����
	@RequestMapping(value = "/cy/board/my_board_deleted.action")
	public String my_board_deleted(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		String str_num = request.getParameter("num");
		cyBoardDTO = cyBoardDAO.getData(str_num);
		String folderName = cyBoardDTO.getFolderName();
		String saveFileName = cyBoardDTO.getSaveFileName();
		// DB����
		if (str_num != null) {
			cyBoardDAO.deleteBoard(Integer.parseInt(str_num));
			List<Integer> oriNumList = cyBoardDAO.selectListNum(Integer.parseInt(str_num));
			Iterator<Integer> lit = oriNumList.iterator();
			while(lit.hasNext()) {
				int num = lit.next();
				cyBoardDAO.deleteAllReple(num);
			}
			cyBoardDAO.deleteAllBoard(Integer.parseInt(str_num));
		}
		String path = "C:\\cyworld\\"+userId+"\\board\\"+folderName+"\\"+saveFileName;
		// �������ΰ�λ���
		File file = new File(path);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("���ϻ��� ����");
			} else {
				System.out.println("���ϻ��� ����");
			}
		} else {
			System.out.println("������ �������� �ʽ��ϴ�.");
		}
		return "redirect:/cy/board/my_r_board_page.action?folderName=" + URLEncoder.encode(folderName, "UTF-8");
	}
	
	// ���� ��������
	@RequestMapping(value = "/cy/board/my_board_getReple.action")
	public String my_board_getReple(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String num = request.getParameter("num");
		String reple = request.getParameter("reple");
		
		//�����ۼ�
		if(reple != null) {
			HttpSession session = request.getSession();
			cyUserDTO = (CyUserDTO) session.getAttribute("session");
			String userId = cyUserDTO.getUserId();
			cyBoardRepleDTO.setNum(cyBoardRepleDAO.getNum()+1);
			cyBoardRepleDTO.setContent(reple);
			cyBoardRepleDTO.setGuestNum(Integer.parseInt(num));
			cyBoardRepleDTO.setUserId(userId);
			cyBoardRepleDTO.setUserName(cyUserDTO.getUserName());
			cyBoardRepleDAO.insertData(cyBoardRepleDTO);
		}
		List<CyBoardRepleDTO> list = cyBoardRepleDAO.getData(Integer.parseInt(num));
		request.setAttribute("length", list.size());
		request.setAttribute("list", list);
		return "mypage/my_board_getReple";
	}
	
	// ���� �����ϱ�
	@RequestMapping(value = "/cy/board/my_board_deleteReple.action")
	public String my_board_deleteReple(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String num = request.getParameter("num");
		String repleNum = request.getParameter("repleNum");
		cyBoardRepleDAO.deleteData(Integer.parseInt(repleNum));
		List<CyBoardRepleDTO> list = cyBoardRepleDAO.getData(Integer.parseInt(num));
		request.setAttribute("length", list.size());
		request.setAttribute("list", list);
		return "mypage/my_board_getReple";
	}
	
	//�����̸� �ߺ��˻�
	@RequestMapping(value = "/cy/board/my_boardMenu_check.action")
	public String my_boardMenu_check(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		List<CyBoardMenuDTO> list = cyBoardDAO.getBoardMenuList(userId, 0);
		String folderName = request.getParameter("folderName");
		Iterator<CyBoardMenuDTO> lit = list.iterator();
		while(lit.hasNext()) {
			CyBoardMenuDTO vo = lit.next();
			if(vo.getFolderName().equals(folderName)) {
				request.setAttribute("result", 1);
				return "mypage/my_boardMenu_check";
			}
		}		
		request.setAttribute("result", 0);
		return "mypage/my_boardMenu_check";
	}
	//userpage�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
	
	// �Խ��� ����ȭ��
	@RequestMapping(value = "/cy/user_board.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String user_board(HttpServletRequest request,String menu) {
		String userId = request.getParameter("userId");
		String folderName = request.getParameter("folderName");
		//01-08 ���� �߰�
		cyUserDTO = cyUserDAO.getUserData(userId);
		request.setAttribute("userDTO", cyUserDTO);
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO) session.getAttribute("session");
		cyMemberDTO = cyMemberDAO.checkMember(userId, vo.getUserId());
		int type = 1;
		if(cyMemberDTO==null)
			type = 2;
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId, type);
		if (menuList.size() > 0) {
			if (folderName == null) {
				request.setAttribute("encodedFolderName", menuList.get(0).getFolderName());
			} else {
				request.setAttribute("encodedFolderName", folderName);
			}
		} else {
			request.setAttribute("encodedFolderName", null);
		}
		request.setAttribute("userId", userId);
		request.setAttribute("menu", menu);
		return "userpage/user_board";
	}
	
	// �Խ��� �޴���������
	@RequestMapping(value = "/cy/board/user_getMenu.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String user_getMenu(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = request.getParameter("userId");
		cyMemberDTO = cyMemberDAO.checkMember(userId, cyUserDTO.getUserId());
		int type = 1;
		if(cyMemberDTO==null)
			type = 2;
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId, type);
		request.setAttribute("menuList", menuList);
		request.setAttribute("menuLength", menuList.size());
		request.setAttribute("userId", userId);
		return "userpage/user_board_getMenu";
	}
	
	// �Խ��� �޴��� �´� �Խ���
	@RequestMapping(value = "/cy/board/user_r_board_page.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String user_r_board_page(HttpServletRequest request) throws UnsupportedEncodingException {
		String cp = request.getContextPath();
		String userId = request.getParameter("userId");
		String folderName = request.getParameter("folderName");
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO) session.getAttribute("session");
		cyMemberDTO = cyMemberDAO.checkMember(userId, vo.getUserId());
		int type = 1;
		if(cyMemberDTO==null)
			type = 2;
		if (folderName == null) {
			List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,type);
			request.setAttribute("menuLength", menuList.size());
			return "mypage/my_r_board_page";
		}
		String pageNum = request.getParameter("pageNum");
		int currentPage = 1;
	
		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}
	
		int numPerPage = 10;
		int totalData = cyBoardDAO.getCount(userId, folderName);
		int totalPage = myUtil.getPageCount(numPerPage, totalData);
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		List<CyBoardDTO> boardList = cyBoardDAO.getBoardList(userId, folderName, start, end);
	
		Iterator<CyBoardDTO> it = boardList.iterator();
		int listNum = 0, num = 0;
		while (it.hasNext()) {
			CyBoardDTO temp = it.next();
			listNum = totalData - (start + num - 1);
			temp.setListNum(listNum);
			if (temp.getContent() != null) {
				temp.setContent(temp.getContent().replaceAll("\r\n", "<br/>"));
			}
			num++;
		}
	
		String listUrl = cp + "/cy/board/user_r_board_page.action?folderName="+folderName+"&userId="+userId;
		if (boardList.size() == 0)
			request.setAttribute("totalFolderData", 0);
		else
			request.setAttribute("totalFolderData", 1);
		cyMemberDTO = cyMemberDAO.checkMember(userId, vo.getUserId());
		int temp = 1;
		if(cyMemberDTO==null)
			temp = 2;
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,temp);
		request.setAttribute("menuLength", menuList.size());
		request.setAttribute("list", boardList);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, listUrl));
		request.setAttribute("totalData", totalData);
		request.setAttribute("length", boardList.size());
		request.setAttribute("pageNum", currentPage);
		request.setAttribute("folderName", folderName);
		request.setAttribute("userId", userId);
		return "userpage/user_r_board_page";
	}

	// �Խ��� ��
	@RequestMapping(value = "/cy/board/user_boardArticle.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String user_boardArticle(HttpServletRequest request) {
		String num = request.getParameter("num");
		String pageNum = request.getParameter("pageNum");
		if (num != null) {
			cyBoardDTO = cyBoardDAO.getData(num);
			cyBoardDTO.setContent(cyBoardDTO.getContent().replace("\n", "<br/>"));
			cyBoardDAO.hitCountUp(Integer.parseInt(num));
			request.setAttribute("dto", cyBoardDTO);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("userId", request.getParameter("userId"));
		}
		return "userpage/user_r_board_article";
	}
	
	// ���� ��������
	@RequestMapping(value = "/cy/board/user_board_getReple.action")
	public String user_board_getReple(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String num = request.getParameter("num");
		String reple = request.getParameter("reple");
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		//�����ۼ�
		if(reple != null) {
			cyBoardRepleDTO.setNum(cyBoardRepleDAO.getNum()+1);
			cyBoardRepleDTO.setContent(reple);
			cyBoardRepleDTO.setGuestNum(Integer.parseInt(num));
			cyBoardRepleDTO.setUserId(userId);
			cyBoardRepleDTO.setUserName(cyUserDTO.getUserName());
			cyBoardRepleDAO.insertData(cyBoardRepleDTO);
		}
		List<CyBoardRepleDTO> list = cyBoardRepleDAO.getData(Integer.parseInt(num));
		request.setAttribute("length", list.size());
		request.setAttribute("list", list);		
		request.setAttribute("userId",userId);
		return "userpage/user_board_getReple";
	}
	
	// ���� �����ϱ�
	@RequestMapping(value = "/cy/board/user_board_deleteReple.action")
	public String user_board_deleteReple(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		String num = request.getParameter("num");
		String repleNum = request.getParameter("repleNum");
		cyBoardRepleDAO.deleteData(Integer.parseInt(repleNum));
		List<CyBoardRepleDTO> list = cyBoardRepleDAO.getData(Integer.parseInt(num));
		request.setAttribute("length", list.size());
		request.setAttribute("list", list);
		request.setAttribute("userId",userId);
		return "userpage/user_board_getReple";
	}
	
	// �Խ��� ���ϴٿ�ε�
	@RequestMapping(value = "/cy/board/user_download.action")
	public void user_download(HttpServletRequest request, HttpServletResponse response) {
		String num = request.getParameter("num");
		String userId = request.getParameter("userId");
		String folderName = request.getParameter("folderName");
		String saveDir = "C:\\cyworld\\" + userId + "\\board\\" + folderName;
		String fileName = request.getParameter("saveFileName");
		File file = new File(saveDir + "/" + fileName);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ServletOutputStream sos = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			sos = response.getOutputStream();
			String reFilename = "";
			boolean isMSIE = request.getHeader("user-agent").indexOf("MSIE") != -1
					|| request.getHeader("user-agent").indexOf("Trident") != -1;
			if (isMSIE) {
				reFilename = URLEncoder.encode(fileName, "utf-8");
				reFilename = reFilename.replaceAll("\\+", "%20");
			} else {
				reFilename = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
			}
			response.setContentType("application/octet-stream;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + reFilename + "\"");
			response.setContentLength((int) file.length());
			int read = 0;
			while ((read = bis.read()) != -1) {
				sos.write(read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// �Խ��� �̵�
	@RequestMapping(value = "/cy/board/user_board_move.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String user_board_move(HttpServletRequest request) {
		String num = request.getParameter("num");
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		List<CyBoardMenuDTO> menuList = cyBoardDAO.getBoardMenuList(userId,0); //�� �Խ���
		request.setAttribute("list", menuList);
		request.setAttribute("num", num);
		request.setAttribute("folderName", request.getParameter("folderName"));
		request.setAttribute("userId", request.getParameter("userId"));
		return "userpage/user_r_board_moving";
	}

	// �̵��ϱ�
	@RequestMapping(value = "/cy/board/user_board_move_ok.action", method = { RequestMethod.GET, RequestMethod.POST })
	public String user_board_move_ok(HttpServletRequest request) throws Exception {
		//DB�̵�
		String num = request.getParameter("num");
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String me = cyUserDTO.getUserId();                  //���� ������ tmdgusdl911
		String userId = request.getParameter("userId");     //���� ������ tmdgusdl91
		cyBoardDTO = cyBoardDAO.getData(num);
		String oldFolderName = cyBoardDTO.getFolderName();
		String newFolderName = request.getParameter("newFolderName");
		String saveFileName = cyBoardDTO.getSaveFileName();
		if(saveFileName == null) {
			saveFileName="";
		}
		CyBoardDTO newCyBoardDTO = new CyBoardDTO();
		newCyBoardDTO.setNum(cyBoardDAO.getNum()+1);
		newCyBoardDTO.setContent(cyBoardDTO.getContent());
		newCyBoardDTO.setFolderName(newFolderName);
		newCyBoardDTO.setOriginalFileName(saveFileName);
		newCyBoardDTO.setSaveFileName(saveFileName);
		newCyBoardDTO.setUserId(me);
		int origiNalNum = cyBoardDAO.getOrigiNalNum(Integer.parseInt(num));
		if(origiNalNum == 0) {
			newCyBoardDTO.setSubject("[��ũ��] "+cyBoardDTO.getSubject());
			newCyBoardDTO.setOriginalNum(Integer.parseInt(num));
		}else {
			newCyBoardDTO.setSubject(cyBoardDTO.getSubject());
			newCyBoardDTO.setOriginalNum(origiNalNum);
		}
		newCyBoardDTO.setUserName(cyUserDTO.getUserName());
		cyBoardDAO.insertData(newCyBoardDTO);
		
		//�۰��� ���۴ޱ�
		cyBoardRepleDTO.setNum(cyBoardRepleDAO.getNum()+1);
		cyBoardRepleDTO.setContent("�۰���~��");
		cyBoardRepleDTO.setGuestNum(Integer.parseInt(num));
		cyBoardRepleDTO.setUserId(me);
		cyBoardRepleDTO.setUserName(cyUserDTO.getUserName());
		cyBoardRepleDAO.insertData(cyBoardRepleDTO);
		
		if(!saveFileName.equals("")) {
			//��������̵�
			String oldPath = "C:\\cyworld\\"+userId+"\\board\\"+oldFolderName+"\\"+saveFileName;
			String newPath = "C:\\cyworld\\"+me+"\\board\\"+newFolderName+"\\"+saveFileName;
			File oldFile = new File(oldPath);
			File newFile = new File(newPath);
			try {
	            
	            FileInputStream fis = new FileInputStream(oldFile); //��������
	            FileOutputStream fos = new FileOutputStream(newFile); //����������
	            
	            int fileByte = 0; 
	            // fis.read()�� -1 �̸� ������ �� ������
	            while((fileByte = fis.read()) != -1) {
	                fos.write(fileByte);
	            }
	            //�ڿ��������
	            fis.close();
	            fos.close();
	            
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		}
		return "redirect:/cy/board/user_boardArticle.action?num="+num+"&userId="+userId+"&folderName="+ URLEncoder.encode(request.getParameter("folderName"), "UTF-8");
	}

}
