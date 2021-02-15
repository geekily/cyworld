package com.cyworld.controller;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyworld.dao.CyBoardDAO;
import com.cyworld.dao.CyGuestDAO;
import com.cyworld.dao.CyGuestRepleDAO;
import com.cyworld.dao.CyIntroDAO;
import com.cyworld.dao.CyMemberBoardDAO;
import com.cyworld.dao.CyMemberDAO;
import com.cyworld.dao.CyMemberListDAO;
import com.cyworld.dao.CyNewBoardDAO;
import com.cyworld.dao.CySettingDAO;
import com.cyworld.dao.CyUserDAO;
import com.cyworld.dao.CyUsingItemDAO;
import com.cyworld.dao.CyVideoBoardDAO;
import com.cyworld.dao.Cyworld_pic_DAO;
import com.cyworld.dto.CyGuestDTO;
import com.cyworld.dto.CyGuestRepleDTO;
import com.cyworld.dto.CyIntroDTO;
import com.cyworld.dto.CyMemberBoardDTO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyMemberListDTO;
import com.cyworld.dto.CyMenuDTO;
import com.cyworld.dto.CyNewBoardDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.CyUsingItemDTO;
import com.cyworld.dto.CyUsingSongsDTO;
import com.cyworld.util.MyUtil_pic;

@Controller("userPageController")
public class UserPageController {
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
	@Qualifier("cySettingDAO")
	CySettingDAO cySettingDAO;
	
	@Autowired
	@Qualifier("cyMemberListDAO")
	CyMemberListDAO cyMemberListDAO;	
	
	@Autowired
	@Qualifier("cyNewBoardDAO")
	CyNewBoardDAO cyNewBoardDAO;
	
	@Autowired
	@Qualifier("cyVideoBoardDAO")
	CyVideoBoardDAO cyVideoBoardDAO;
	
	
	@Autowired
	CyBoardDAO cyBoardDAO;
	
	@Autowired
	Cyworld_pic_DAO cyPictureDAO;
	
	@Autowired
	MyUtil_pic myUtil;

	
	@Autowired
	CyIntroDTO cyIntroDTO;
	
	@Autowired
	CyUserDTO cyUserDTO;
	
	@Autowired
	CyMemberDTO cyMemberDTO;
	
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
	CyMenuDTO cyMenuDTO;
	
	@Autowired
	CyNewBoardDTO cyNewBoardDTO;
	
	@Autowired
	CyUsingSongsDTO cyUsingSongsDTO;
	
	//유저 메인화면
	@RequestMapping(value="/cy/user_main.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String mainPage(HttpServletRequest request) {		
		String userId = request.getParameter("userId");
		request.setAttribute("userId", userId);
		cyUserDTO = cyUserDAO.getUserData(userId);
		request.setAttribute("userName", cyUserDTO.getUserName());
		Calendar cal = Calendar.getInstance();
		String userDate = cyUserDTO.getCheckDay();
		String[] checkDay = userDate.split("-");
		int[] calDay = new int[checkDay.length];
		calDay[0] = cal.get(Calendar.YEAR);
		calDay[1] = cal.get(Calendar.MONTH)+1;
		calDay[2] = cal.get(Calendar.DATE);
		for(int i = 0 ; i < calDay.length; i++) {
			if(calDay[i] > Integer.parseInt(checkDay[i]))
				cyUserDAO.hitInit(userId);
		}
		cyUserDAO.hitCount(userId);
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		if(userId.equals(cyUserDTO.getUserId())) {
			return "redirect:/cy/my_main.action";
		}
		List<CyUsingSongsDTO> songList=cyUsingItemDAO.getUsingSongs(userId);
        request.setAttribute("songList", songList);
		String backGround = cyUsingItemDAO.useBackGround(userId);
		request.setAttribute("imageFilePath", backGround);
		
		//1-12 예리 fontCss 씌우기 추가함!
		String userFontCss = cyUsingItemDAO.getUsingFontCss(userId);
		session.setAttribute("userFontCss", userFontCss);
		
		return "userpage/user_main";
	}
    
	//유저 홈메뉴
	@RequestMapping(value="/cy/user_home.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String m1(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		cyUserDTO = cyUserDAO.getUserData(userId);
		request.setAttribute("userDTO", cyUserDTO);
		request.setAttribute("userId", userId);
		cyIntroDTO = cyIntroDAO.getData(userId);
		request.setAttribute("dto", cyIntroDTO);
		request.setAttribute("menu", "menu1");
		return "userpage/user_home";
	}	
	
	//유저 일촌평 내용 가져옴
	@RequestMapping(value="/cy/user_get_MemberBoard.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String user_get_MemberBoard(HttpServletRequest request) {
		String userId = request.getParameter("userId"); 
		List<CyMemberBoardDTO> list = cyMemberBoardDAO.getMemberBoard(userId);
		if(list.size() == 0 ) {
			request.setAttribute("length", 0);
		}else {
			String[][] boardList = boardListSetting(list);
			request.setAttribute("memberId", boardList[0]);
			request.setAttribute("memberName", boardList[1]);
			request.setAttribute("memberValue", boardList[2]);
			request.setAttribute("content", boardList[3]);
			request.setAttribute("length", boardList[0].length);
			request.setAttribute("userId", userId);
		}
		return "userpage/user_get_MemberBoard";
	}
	
	//유저 일촌평 내용 가져오기 보조
	public String[][] boardListSetting(List<CyMemberBoardDTO> list){
		String[][] boardList = new String[4][list.size()];  //memberid, membervalue, content, memberName정리해야함
		Iterator<CyMemberBoardDTO> lit = list.iterator();
		int i = 0;
		while(lit.hasNext()) {
			CyMemberBoardDTO vo = lit.next();
			boardList[0][i] = vo.getMemberId();
			boardList[1][i] = vo.getMemberName();
			boardList[2][i] = vo.getMemberValue();
			boardList[3][i] = vo.getContent();
			i++;
		}
		return boardList;
	}
	
	//일촌평 등록
	@RequestMapping(value="/cy/user_get_MemberBoard_send.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String user_get_MemberBoard_send(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String memberId = cyUserDTO.getUserId();
		cyMemberDTO = cyMemberDAO.checkMember(userId, memberId);
		if(cyMemberDTO.getUserId1().equals(userId)) {
			cyMemberBoardDTO.setMemberValue(cyMemberDTO.getUserValue1());
		}else {
			cyMemberBoardDTO.setMemberValue(cyMemberDTO.getUserValue2());
		}
		String content = request.getParameter("content");
		cyMemberBoardDTO.setUserId(userId);
		cyMemberBoardDTO.setMemberId(memberId);
		cyMemberBoardDTO.setMemberName(cyUserDTO.getUserName());
		cyMemberBoardDTO.setContent(content);
		cyMemberBoardDAO.insertData(cyMemberBoardDTO); //데이터 등록
		
		List<CyMemberBoardDTO> list = cyMemberBoardDAO.getMemberBoard(userId);
		if(list.size() == 0 ) {
			request.setAttribute("length", 0);
		}else {
			String[][] boardList = boardListSetting(list);
			request.setAttribute("memberId", boardList[0]);
			request.setAttribute("memberName", boardList[1]);
			request.setAttribute("memberValue", boardList[2]);
			request.setAttribute("content", boardList[3]);
			request.setAttribute("length", boardList[0].length);
			request.setAttribute("userId", userId);
		}
		return "userpage/user_get_MemberBoard";
	}
	
	//유저 일촌평삭제
	@RequestMapping(value="/cy/user_get_MemberBoard_delete.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String user_get_MemberBoard_delete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO) session.getAttribute("session");
		String memberId = vo.getUserId();
		String userId = request.getParameter("userId");
		cyMemberBoardDAO.deleteData(userId,memberId);
		List<CyMemberBoardDTO> list = cyMemberBoardDAO.getMemberBoard(userId);
		if(list.size() == 0 ) {
			request.setAttribute("length", 0);
		}else {
			String[][] boardList = boardListSetting(list);
			request.setAttribute("memberId", boardList[0]);
			request.setAttribute("memberName", boardList[1]);
			request.setAttribute("memberValue", boardList[2]);
			request.setAttribute("content", boardList[3]);
			request.setAttribute("length", boardList[0].length);
			request.setAttribute("userId", userId);
		}
		return "userpage/user_get_MemberBoard";
	}

	
	//유저 방명록
	@RequestMapping(value="/cy/user_guest.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String m4(HttpServletRequest request, String menu) {
		String userId = request.getParameter("userId");
		cyUserDTO = cyUserDAO.getUserData(userId);
		request.setAttribute("userDTO", cyUserDTO);
		request.setAttribute("userId", userId);
		request.setAttribute("menu", menu);
		return "userpage/user_guest";
	}
	
	//유저 음악
	@RequestMapping(value="/cy/user_music.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String music(HttpServletRequest request) {
		String[] musicPlayList = new String[2];
		musicPlayList[0] = "프리스타일-Y.mp3";
		musicPlayList[1] = "MCSniper-BK Love.mp3";
		request.setAttribute("list", musicPlayList);
		return "userpage/user_music";
	}
	
	@RequestMapping(value="/cy/user_left_intro.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String left_intro(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId"); //접속한  홈피의 주인 아이디
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId2 = cyUserDTO.getUserId();  //로그인한 사람의 아이디
		cyIntroDTO = cyIntroDAO.getData(userId); //홈피주인의 인트로 정보
		cyMemberDTO = cyMemberDAO.checkMember(userId, userId2); //홈피주인과 로그인한 사람이 일촌인지 확인
		CyUserDTO cyUserDTO2 = cyUserDAO.getUserData(userId);  //홈피주인의 DTO
		List<CyMemberDTO> list = cyMemberDAO.getMemberList(userId); //홈피주인의 일촌리스트
		if(list.size()==0) {
			request.setAttribute("length", 0);
		}else {
			String[][] userList = getMemberUserId(list,userId);
			request.setAttribute("userIdList", userList[0]);
			request.setAttribute("userNameList", userList[1]);
			request.setAttribute("userValueList", userList[2]);
			request.setAttribute("length", userList[0].length);
		}
		request.setAttribute("member", cyMemberDTO);
		request.setAttribute("userId", userId);
		request.setAttribute("dto", cyUserDTO2);
		request.setAttribute("imageFilePath", cyIntroDTO.getOriginalFileName());
		return "userpage/user_left_intro";
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
				userList[2][i] = vo.getUserValue1();
			}else {
				userList[0][i] = vo.getUserId1(); //아이디저장
				userList[1][i] = cyUserDAO.getName(userList[0][i]);
				userList[2][i] = vo.getUserValue2();
			}
			i++;
		}		
		return userList;
	}
	@RequestMapping(value="/cy/user_r_home.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String r_home(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		CyUsingItemDTO useMinimi = cyUsingItemDAO.useMinimi(userId);
		String useStoryRoom = cyUsingItemDAO.useStoryRoom(userId);
		String x = useMinimi.getImgX();
		String y = useMinimi.getImgY();
		x = x.replaceAll("px", "");
		y = y.replaceAll("px", "");
		int intx = Integer.parseInt(x);
		int inty = Integer.parseInt(y);
		intx -= 204;
		inty += 70;
		x = Integer.toString(intx)+"px";
		y = Integer.toString(inty)+"px";
		useMinimi.setImgX(x);
		useMinimi.setImgY(y);
		request.setAttribute("userId", userId);
		request.setAttribute("useMinimi", useMinimi);
		request.setAttribute("useStoryRoom", useStoryRoom);
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String me = cyUserDTO.getUserId();
		cyMemberDTO = cyMemberDAO.checkMember(userId, me);
		int type = 1;
		if(cyMemberDTO == null) {
			request.setAttribute("check", 0);
			type = 2;
		}else {
			request.setAttribute("check", 1);
		}
		cyMenuDTO = cySettingDAO.getMenuAvailable(userId);	
		
		int count = 0 ;
		if(cyMenuDTO.getMenu3().equals("0"))
			count++;
		if(cyMenuDTO.getMenu4().equals("0"))
			count++;
		if(cyMenuDTO.getMenu5().equals("0"))
			count++;
		if(cyMenuDTO.getMenu6().equals("0"))
			count++;
		if(count != 0 ) {
			String[][] arr = new String[count][4];
			String[] arr2 = new String[count];
			count = 0;
			if(cyMenuDTO.getMenu3().equals("0")) {
				arr[count][0] = "사진첩";
				arr[count][1] = "javascript:parent.location='user_picture.action?menu=menu3&userId="+userId+"';";
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DATE);
				String nowDay = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
				arr[count][2] = cyPictureDAO.getNowDayCount(userId,nowDay,type);
				arr[count][3] = cyPictureDAO.getTotalDayCount(userId,type);
				arr2[count] ="picture";
				count++;
			}
			if(cyMenuDTO.getMenu4().equals("0")) {			
				arr[count][0]="게시판";
				arr[count][1] = "javascript:parent.location='user_board.action?menu=menu4&userId="+userId+"';";
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DATE);
				String nowDay = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
				arr[count][2] = cyBoardDAO.getNowDayCount(userId,nowDay,type);
				arr[count][3] = cyBoardDAO.getTotalDayCount(userId,type);
				arr2[count] ="board";
				count++;
			}
			if(cyMenuDTO.getMenu5().equals("0")) {
				arr[count][0]="비디오";
				arr[count][1] = "javascript:parent.location='user_video.action?menu=menu5&userId="+userId+"';";
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DATE);
				String nowDay = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
				arr[count][2] = cyVideoBoardDAO.getNowDayCount(userId,nowDay,type);
				arr[count][3] = cyVideoBoardDAO.getTotalDayCount(userId,type);
				arr2[count] ="video";
				count++;
			}
			if(cyMenuDTO.getMenu6().equals("0")) {
				arr[count][0]="방명록";
				arr[count][1] = "javascript:parent.location='user_guest.action?menu=menu6&userId="+userId+"';";
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DATE);
				String nowDay = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
				arr[count][2] = cyGuestDAO.getNowDayCount(userId,nowDay);
				arr[count][3] = cyGuestDAO.getTotalDayCount(userId);
				arr2[count] ="guest";
				count++;
			}
			List<CyNewBoardDTO> list =null;
			if(arr2[0]!=null) {
				list = cyNewBoardDAO.getUserList(userId,me,arr2,type);
				Iterator<CyNewBoardDTO> lit = list.iterator();
				while(lit.hasNext()) {
					CyNewBoardDTO vo = lit.next();
					if(vo.getSubject().length()>=13) {
						vo.setSubject(vo.getSubject().substring(0,13)+"...");
					}
				}
			}
			if(list==null) {
				request.setAttribute("size", 0);
			}else {
				request.setAttribute("size", list.size());
				request.setAttribute("list", list);
			}
			request.setAttribute("length", count);		
			request.setAttribute("array", arr);
			return "userpage/user_r_home";
		}else {
			request.setAttribute("size", 0);
			request.setAttribute("length", count);
			return "userpage/user_r_home";
		}
	}
	
	//유저 방명록 글 삭제
	@RequestMapping(value="/cy/user_guest_delete.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String my_guest_delete(HttpServletRequest request, String num, String currentPage) {
		cyGuestDAO.deleteData(Integer.parseInt(num));
		cyGuestRepleDAO.deleteAllData(Integer.parseInt(num));
		return "redirect:/cy/user_r_guest.action?userId="+request.getParameter("userId");
	}
	
	//유저 방명록 글작성
	@RequestMapping(value="/cy/user_guest_write.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String user_guest_write(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		cyMemberDTO = cyMemberDAO.checkMember(cyUserDTO.getUserId(), request.getParameter("userId"));
		cyGuestDTO.setNum(cyGuestDAO.getMaxNum()+1);
		cyGuestDTO.setUserId(request.getParameter("userId"));
		cyGuestDTO.setMemberId(cyUserDTO.getUserId());
		if(cyMemberDTO.getUserId1().equals(cyUserDTO.getUserId()))
			cyGuestDTO.setMemberValue(cyMemberDTO.getUserValue1());
		else
			cyGuestDTO.setMemberValue(cyMemberDTO.getUserValue2());
		cyGuestDTO.setMemberName(cyUserDTO.getUserName());
		String content = request.getParameter("content");
		content = content.replaceAll("<[^>]*>", " ");
		cyGuestDTO.setContent(content);
		if(request.getParameter("secret")==null)
			cyGuestDTO.setSecret(0);
		else
			cyGuestDTO.setSecret(1);
		cyGuestDAO.insertData(cyGuestDTO);
		return "redirect:/cy/user_r_guest.action?userId="+request.getParameter("userId");
	}
	
	
	//유저 방명록 화면
	@RequestMapping(value="/cy/user_r_guest.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String r_guest(HttpServletRequest request) {
		String cp = request.getContextPath();
		
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = request.getParameter("userId");
		String me = cyUserDTO.getUserId();
		String pageNum = request.getParameter("pageNum");
		cyMemberDTO = cyMemberDAO.checkMember(userId, me);
		if(cyMemberDTO!=null) {
			request.setAttribute("member", 1);
		}else {
			request.setAttribute("member", 0);
		}
		int currentPage = 1;
		
		if(pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		int numPerPage = 3;
		int totalData = cyGuestDAO.getUserTotalData(userId,me);
		int totalPage = myUtil.getPageCount(numPerPage, totalData);
		if(currentPage > totalPage) {
			currentPage = totalPage;
		}
		int start = (currentPage -1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		List<CyGuestDTO> list = cyGuestDAO.getUserDataList(userId,me,start,end);
		Iterator<CyGuestDTO> it = list.iterator();
		int listNum = 0, num = 0;
		String[] minimiArray = new String[list.size()];
		while (it.hasNext()) {
			CyGuestDTO temp = it.next();
			listNum = totalData - (start + num - 1);
			temp.setListNum(listNum);
			if (temp.getContent() != null) {
				temp.setContent(temp.getContent().replaceAll("\r\n", "<br/>"));
			}
			CyUsingItemDTO useMinimi = cyUsingItemDAO.useMinimi(temp.getMemberId());
			minimiArray[num] = useMinimi.getOriginalFileName();
			num++;
		}
		CyUsingItemDTO useMinimi = cyUsingItemDAO.useMinimi(cyUserDTO.getUserId());
		String minimi = useMinimi.getOriginalFileName(); //나의 미니미
		String listUrl = cp + "/cy/user_r_guest.action";
		if(list.size() == 0 ) 
			request.setAttribute("length", 0);
		else
			request.setAttribute("length", 1);
		request.setAttribute("list", list);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, listUrl));
		request.setAttribute("totalData", totalData);
		request.setAttribute("minimiArray", minimiArray);
		request.setAttribute("length", list.size());
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("minimi",minimi);
		request.setAttribute("userId", userId);
		return "userpage/user_r_guest";
	}
	

	//방명록 비밀글로 설정
	@RequestMapping(value="/cy/user_guestBook_secret.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String user_guestBook_secret(HttpServletRequest request, String currentPage) {
		String num = request.getParameter("num");
		cyGuestDAO.setSecret(Integer.parseInt(num));
		return "redirect:/cy/user_r_guest.action?userId="+request.getParameter("userId")+"&pageNum="+currentPage;
	}
	
	
	//방명록 확인
	@RequestMapping(value="/cy/user_get_guestbook.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String user_get_guestbook(HttpServletRequest request) {
		String length = request.getParameter("length");
		
		String num0 = request.getParameter("num0"); //첫번째 글
		List<CyGuestRepleDTO> list0 = cyGuestRepleDAO.getDataList(Integer.parseInt(num0));
		List<CyGuestRepleDTO> list1 = null;
		List<CyGuestRepleDTO> list2 = null;
		if(list0.size()==0) request.setAttribute("length0", 0);
		request.setAttribute("list0", list0);
		
		if(Integer.parseInt(length)>=2) {
			String num1 = request.getParameter("num1"); //두번째 글
			list1 = cyGuestRepleDAO.getDataList(Integer.parseInt(num1));
			if(list1.size()==0) request.setAttribute("length1", 0);
			request.setAttribute("list1", list1);
		}
		
		if(Integer.parseInt(length)>=3) {
			String num2 = request.getParameter("num2"); //세번째 글
			list2 = cyGuestRepleDAO.getDataList(Integer.parseInt(num2));
			if(list2.size()==0) request.setAttribute("length2", 0);
			request.setAttribute("list2", list2);
		}
		
		return "userpage/user_get_guestbook";
	}

	
	//방명록리플달기
	@RequestMapping(value="/cy/user_guestBook_wirte.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String user_guestBook_wirte(HttpServletRequest request, String currentPage) {
		String guestNum = request.getParameter("num");
		String content = request.getParameter("comment");
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO) session.getAttribute("session");
		cyGuestRepleDTO.setNum(cyGuestRepleDAO.getMaxNum()+1);
		cyGuestRepleDTO.setGuestNum(Integer.parseInt(guestNum));
		cyGuestRepleDTO.setUserId(vo.getUserId());
		cyGuestRepleDTO.setUserName(vo.getUserName());
		cyGuestRepleDTO.setContent(content);
		cyGuestRepleDAO.insertData(cyGuestRepleDTO);
		return "redirect:/cy/user_r_guest.action?pageNum="+currentPage+"&userId="+request.getParameter("userId");
	}
	
	//방명록리플삭제
	@RequestMapping(value="/cy/user_get_guestBoard_delete.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String user_get_guestBoard_delete(HttpServletRequest request, String currentPage) {		
		String num = request.getParameter("num");
		cyGuestRepleDAO.deleteData(Integer.parseInt(num));	
		return "redirect:/cy/user_r_guest.action?pageNum="+currentPage;
	}

	
	@RequestMapping(value="/cy/user_top_title.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String top_title(HttpServletRequest request) {
		request.setAttribute("userId", request.getParameter("userId"));
		return "userpage/user_top_title";
	}
	
	@RequestMapping(value="/cy/user_get_Title.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String get_Title(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		request.setAttribute("userId", userId);			
		String title = cyIntroDAO.getTitle(userId);
		request.setAttribute("title", title);
		return "userpage/user_get_Title";
	}
	@RequestMapping(value="/cy/user_get_Today.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String get_Today(HttpServletRequest request) {
		String newToday = request.getParameter("newToday");
		String userId = request.getParameter("userId");
		request.setAttribute("userId", userId);
		String today ="";
		if(newToday==null||newToday.equals("")) {
			today = cyIntroDAO.getToday(userId);
			request.setAttribute("today", today);
			return "userpage/user_get_Today";
		}else {
			cyIntroDAO.updateToday(userId, newToday);
			today = cyIntroDAO.getToday(userId);
			request.setAttribute("today", today);
		}		
		return "userpage/user_get_Today";
	}
	
	@RequestMapping(value="/cy/user_get_Info.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String get_Info(HttpServletRequest request) {
		String newInfo = request.getParameter("newInfo");
		String userId = request.getParameter("userId");
		request.setAttribute("userId", userId);
		String info ="";
		if(newInfo==null||newInfo.equals("")) {
			info = cyIntroDAO.getInfo(userId);
			request.setAttribute("info", info);
		}else {
			cyIntroDAO.updateInfo(userId, newInfo);
			info = cyIntroDAO.getInfo(userId);
			request.setAttribute("info", info);
		}		
		return "userpage/user_get_Info";
	}
}
