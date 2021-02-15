package com.cyworld.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

@Controller("myPageController")
public class MyPageController {
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
	@Qualifier("cySettingDAO")
	CySettingDAO cySettingDAO;
	
	@Autowired
	@Qualifier("cyNewBoardDAO")
	CyNewBoardDAO cyNewBoardDAO;
	
	@Autowired
	@Qualifier("cyVideoBoardDAO")
	CyVideoBoardDAO cyVideoBoardDAO;
	
	@Autowired
	MyUtil_pic myUtil;

	@Autowired
	Cyworld_pic_DAO cyPictureDAO;
	
	@Autowired
	CyBoardDAO cyBoardDAO;
	
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
	
	//나의 메인화면
	@RequestMapping(value="/cy/my_main.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String mainPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		String backGround = cyUsingItemDAO.useBackGround(userId);
		Calendar cal = Calendar.getInstance();
		String userDate = vo.getCheckDay();
		String[] checkDay = userDate.split("-");
		int[] calDay = new int[checkDay.length];
		calDay[0] = cal.get(Calendar.YEAR);
		calDay[1] = cal.get(Calendar.MONTH)+1;
		calDay[2] = cal.get(Calendar.DATE);
		for(int i = 0 ; i < calDay.length; i++) {
			if(calDay[i] > Integer.parseInt(checkDay[i]))
				cyUserDAO.hitInit(userId);
		}
		List<CyUsingSongsDTO> songList=cyUsingItemDAO.getUsingSongs(vo.getUserId());
		request.setAttribute("songList", songList);
		request.setAttribute("imageFilePath", backGround);
		
		//1-12 예리 fontCss 씌우기(위치 옮겼어 여기로!)
		String fontCss = cyUsingItemDAO.getUsingFontCss(vo.getUserId());
		session.setAttribute("fontCss", fontCss);
		
		return "mypage/my_main";
	}
	
	//나의 홈메뉴
	@RequestMapping(value="/cy/my_home.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String m1(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		cyIntroDTO = cyIntroDAO.getData(userId);
		request.setAttribute("dto", cyIntroDTO);
		request.setAttribute("menu", "menu1");
		
		return "mypage/my_home";
	}
	
	//일촌평삭제
	@RequestMapping(value="/cy/my_get_MemberBoard_delete.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String my_get_MemberBoard_delete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO) session.getAttribute("session");
		String userId = vo.getUserId();
		String memberId = request.getParameter("userId");
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
		}
		return "mypage/my_get_MemberBoard";
	}
	
	//일촌평 내용 가져옴
	@RequestMapping(value="/cy/my_get_MemberBoard.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String my_get_MemberBoard(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId(); 
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
		}
		return "mypage/my_get_MemberBoard";
	}
	
	//일촌평 내용 가져오기 보조
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
		
	//방명록메뉴
	@RequestMapping(value="/cy/my_guest.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String m4(HttpServletRequest request, String menu) {
		request.setAttribute("menu", menu);
		return "mypage/my_guest";
	}	
	
	//방명록삭제
	@RequestMapping(value="/cy/my_guest_delete.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String my_guest_delete(HttpServletRequest request, String num, String currentPage) {
		cyGuestDAO.deleteData(Integer.parseInt(num));
		cyGuestRepleDAO.deleteAllData(Integer.parseInt(num));
		return "redirect:/cy/my_r_guest.action";
	}	
	
	//방명록내용가져오기
	@RequestMapping(value="/cy/my_r_guest.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String r_guest(HttpServletRequest request) {
		String cp = request.getContextPath();
		
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		
		String pageNum = request.getParameter("pageNum");
		int currentPage = 1;
		
		if(pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		int numPerPage = 3;
		int totalData = cyGuestDAO.getMyTotalData(cyUserDTO.getUserId());
		int totalPage = myUtil.getPageCount(numPerPage, totalData);
		if(currentPage > totalPage) {
			currentPage = totalPage;
		}
		int start = (currentPage -1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		List<CyGuestDTO> list = cyGuestDAO.getMyDataList(cyUserDTO.getUserId(),start,end);
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
			CyUsingItemDTO vo = cyUsingItemDAO.useMinimi(temp.getMemberId());
			minimiArray[num] = vo.getOriginalFileName();
			num++;
		}
		String listUrl = cp + "/cy/my_r_guest.action";
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
		return "mypage/my_r_guest";
	}
	
	//일촌신청
	@RequestMapping(value="/cy/my_member_call.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String my_member_call(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userName1 = cyUserDTO.getUserName(); //나의 그것
		String userId = request.getParameter("userId");
		String userName2 = cyUserDAO.getName(userId);
        CyUsingItemDTO usingItem = cyUsingItemDAO.useMinimi(userId);
        String minimiPath = usingItem.getOriginalFileName();
        request.setAttribute("minimiPath", minimiPath);
		request.setAttribute("userName1", userName1);
		request.setAttribute("userId2", userId);
		request.setAttribute("userName2", userName2);
		return "mypage/my_member_call";
	}
	
	//01-13 승현
	//일촌변경
    @RequestMapping(value="/cy/my_member_update.action", method = {RequestMethod.GET,RequestMethod.POST})
    public String my_member_update(HttpServletRequest request) {
        HttpSession session = request.getSession();
        cyUserDTO = (CyUserDTO) session.getAttribute("session");
        String userName1 = cyUserDTO.getUserName(); //나의 그것
        String userId = request.getParameter("userId");
        String userName2 = cyUserDAO.getName(userId);
        request.setAttribute("minimiPath", cyUsingItemDAO.useMinimi(userId));

        cyMemberDTO = cyMemberDAO.checkMember(cyUserDTO.getUserId(), userId);
   
        if(cyMemberDTO.getUserId1().equals(cyUserDTO.getUserId())) {
            request.setAttribute("userValue", cyMemberDTO.getUserValue2());
            request.setAttribute("myValue", cyMemberDTO.getUserValue1());
            
        }else {
            request.setAttribute("userValue", cyMemberDTO.getUserValue1());
            request.setAttribute("myValue", cyMemberDTO.getUserValue2());
        }
        request.setAttribute("myName", userName1);
        request.setAttribute("userId", userId);
        request.setAttribute("userName", userName2);
        CyUsingItemDTO usingItem = cyUsingItemDAO.useMinimi(userId);
        String minimiPath = usingItem.getOriginalFileName();
        request.setAttribute("minimiPath", minimiPath);
        return "mypage/my_member_update";
    }
    
    //01-13 승현
    //일촌명변경신청
    @RequestMapping(value="/cy/my_member_update_ok.action", method= {RequestMethod.GET,RequestMethod.POST})
    public String my_member_update_ok(HttpServletRequest request, CyMemberListDTO cyMemberListDTO) {
    	cyMemberListDTO.setUserValue1(cyMemberListDTO.getUserValue1().replaceAll("<[^>]*>", " "));
		cyMemberListDTO.setUserValue2(cyMemberListDTO.getUserValue2().replaceAll("<[^>]*>", " "));
		cyMemberListDTO.setUserMessege(cyMemberListDTO.getUserMessege().replaceAll("<[^>]*>", " "));
        cyMemberListDAO.updateList(cyMemberListDTO);
        request.setAttribute("result", 2);
        return "mypage/my_pic_upload_ok";
    }
	
	//일촌신청신청
	@RequestMapping(value="/cy/my_member_call_ok.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String my_member_call_ok(HttpServletRequest request, CyMemberListDTO cyMemberListDTO) {
		cyMemberListDTO.setUserValue1(cyMemberListDTO.getUserValue1().replaceAll("<[^>]*>", " "));
		cyMemberListDTO.setUserValue2(cyMemberListDTO.getUserValue2().replaceAll("<[^>]*>", " "));
		cyMemberListDTO.setUserMessege(cyMemberListDTO.getUserMessege().replaceAll("<[^>]*>", " "));
		cyMemberListDAO.insertList(cyMemberListDTO);
		request.setAttribute("result", 2);
		return "mypage/my_pic_upload_ok";
	}
	
	//내 일촌 신청자 확인
	@RequestMapping(value="/cy/my_member_list.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String my_member_list(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		List<CyMemberListDTO> list = cyMemberListDAO.getList(userId);
		if(list.size()==0) list = null;
		request.setAttribute("list", list);
		return "mypage/my_member_list";
	}
	
	//일촌 신청 상세확인
	@RequestMapping(value="/cy/my_member_check.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String my_member_check(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId1 = request.getParameter("userId1"); //신청한사람
		String userId2 = cyUserDTO.getUserId(); //신청받은사람 나	
		cyMemberListDTO = cyMemberListDAO.getData(userId1, userId2);
		CyUsingItemDTO vo = cyUsingItemDAO.useMinimi(userId1);
		String minimiPath = vo.getOriginalFileName();
		request.setAttribute("dto", cyMemberListDTO);	
		request.setAttribute("minimiPath", minimiPath);
		return "mypage/my_member_check";
	}
	
	//일촌신청자 수락여부
	@RequestMapping(value="/cy/my_member_check_ok.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String my_member_check_ok(HttpServletRequest request, CyMemberDTO cyMemberDTO) {
		int type = 0;
		String userId1 = "";
		String userId2 = "";
		if(request.getParameter("type")!=null) {
			type = Integer.parseInt(request.getParameter("type"));
		}
		switch(type) {
		case 0:  //거절했을때
			userId1 = cyMemberDTO.getUserId1();
			userId2 = cyMemberDTO.getUserId2();
			cyMemberListDAO.deleteData(userId1, userId2);  //신청리스트데이터 삭제
			request.setAttribute("result", 1);
			break;
		case 1: //승낙했을때
			String str_type = request.getParameter("typeMember");
			int int_type= 0 ;
			if(str_type !=null) {
				int_type = Integer.parseInt(str_type);
			}
			if(int_type == 0) {
				userId1 = cyMemberDTO.getUserId1();
				userId2 = cyMemberDTO.getUserId2();
				CyMemberDTO vo = cyMemberDAO.checkMember(userId1, userId2);
				if(vo==null)
					cyMemberDAO.insertData(cyMemberDTO);
				else
					cyMemberDAO.updateData(cyMemberDTO);
			}else if(int_type ==1) {
				cyMemberDAO.updateData(cyMemberDTO);
			}
			cyMemberListDAO.deleteData(userId1, userId2);  //신청리스트데이터 삭제
			request.setAttribute("result", 1);  // 새로고침을 위해 1번을줌
			break;
		case 2:
			request.setAttribute("result", 2);
			break;
		}		
		return "mypage/my_pic_upload_ok";
	}	
		
	//내사진, 내 일촌 리스트
	@RequestMapping(value="/cy/my_left_intro.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String left_intro(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		cyIntroDTO = cyIntroDAO.getData(userId);
		List<CyMemberDTO> list = cyMemberDAO.getMemberList(userId);
		if(list.size()==0) {
			request.setAttribute("length", 0);
		}else {
			String[][] userList = getMemberUserId(list,userId);
			request.setAttribute("userId", userList[0]);
			request.setAttribute("userName", userList[1]);
			request.setAttribute("userValue", userList[2]);
			request.setAttribute("length", userList[0].length);
		}
		request.setAttribute("imageFilePath", cyIntroDTO.getOriginalFileName());
		return "mypage/my_left_intro";
	}	

	//내 일촌 리스트 보조
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
	
	//랜덤 파도 타기
	@RequestMapping(value="/cy/random.action", method = {RequestMethod.GET,RequestMethod.POST})
	public String random(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		List<String> userList = cyUserDAO.getUserList(userId);
		Random rand = new Random();
		int index = rand.nextInt(userList.size());
		userId = userList.get(index);
		request.setAttribute("userId",userId);
		return "mypage/my_get_random";
	}
	
	//홈 오른쪽 화면
	@RequestMapping(value="/cy/my_r_home.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String r_home(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		CyUsingItemDTO useMinimi = cyUsingItemDAO.useMinimi(cyUserDTO.getUserId());
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
		String useStoryRoom = cyUsingItemDAO.useStoryRoom(cyUserDTO.getUserId());
		request.setAttribute("useMinimi", useMinimi);
		request.setAttribute("useStoryRoom", useStoryRoom);
		
		cyMenuDTO = cySettingDAO.getMenuAvailable(cyUserDTO.getUserId());	
		
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
				arr[count][1] = "javascript:parent.location='my_picture.action?menu=menu3';";
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DATE);
				String nowDay = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
				arr[count][2] = cyPictureDAO.getNowDayCount(cyUserDTO.getUserId(),nowDay,0);
				arr[count][3] = cyPictureDAO.getTotalDayCount(cyUserDTO.getUserId(),0);
				arr2[count] ="picture";
				count++;
			}
			if(cyMenuDTO.getMenu4().equals("0")) {			
				arr[count][0]="게시판";
				arr[count][1] = "javascript:parent.location='my_board.action?menu=menu4';";
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DATE);
				String nowDay = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
				arr[count][2] = cyBoardDAO.getNowDayCount(cyUserDTO.getUserId(),nowDay,0);
				arr[count][3] = cyBoardDAO.getTotalDayCount(cyUserDTO.getUserId(),0);
				arr2[count] ="board";
				count++;
			}
			if(cyMenuDTO.getMenu5().equals("0")) {			
				arr[count][0]="비디오";
				arr[count][1] = "javascript:parent.location='my_video.action?menu=menu5';";
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DATE);
				String nowDay = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
				arr[count][2] = cyVideoBoardDAO.getNowDayCount(cyUserDTO.getUserId(),nowDay,0);
				arr[count][3] = cyVideoBoardDAO.getTotalDayCount(cyUserDTO.getUserId(),0);
				arr2[count] ="video";
				count++;
			}
			if(cyMenuDTO.getMenu6().equals("0")) {			
				arr[count][0]="방명록";
				arr[count][1] = "javascript:parent.location='my_guest.action?menu=menu6';";
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DATE);
				String nowDay = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
				arr[count][2] = cyGuestDAO.getNowDayCount(cyUserDTO.getUserId(),nowDay);
				arr[count][3] = cyGuestDAO.getTotalDayCount(cyUserDTO.getUserId());
				arr2[count] ="guest";
				count++;
			}
			List<CyNewBoardDTO> list =null;
			if(arr2[0]!=null) {
				list = cyNewBoardDAO.getMyList(cyUserDTO.getUserId(),arr2);
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
			return "mypage/my_r_home";
		}else {
			request.setAttribute("size", 0);
			request.setAttribute("length", count);
			return "mypage/my_r_home";
		}
		
	}
	

	
	//방명록 비밀글로 설정
	@RequestMapping(value="/cy/my_guestBook_secret.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String my_guestBook_secret(HttpServletRequest request, String currentPage) {
		String num = request.getParameter("num");
		cyGuestDAO.setSecret(Integer.parseInt(num));
		return "redirect:/cy/my_r_guest.action?pageNum="+currentPage;
	}
	
	
	//방명록 리플 확인
	@RequestMapping(value="/cy/my_get_guestbook.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String my_get_guestbook(HttpServletRequest request) {
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
		
		return "mypage/my_get_guestbook";
	}

	
	//방명록 리플 달기
	@RequestMapping(value="/cy/my_guestBook_wirte.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String my_guestBook_wirte(HttpServletRequest request, String currentPage) {
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
		return "redirect:/cy/my_r_guest.action?pageNum="+currentPage;
	}
	
	//방명록 리플 삭제
	@RequestMapping(value="/cy/my_get_guestBoard_delete.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String my_get_guestBoard_delete(HttpServletRequest request, String currentPage) {		
		String num = request.getParameter("num");
		cyGuestRepleDAO.deleteData(Integer.parseInt(num));	
		return "redirect:/cy/my_r_guest.action?pageNum="+currentPage;
	}
	

	
	@RequestMapping(value="/cy/my_top_title.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String top_title(HttpServletRequest request) {
		return "mypage/my_top_title";
	}
	
	//타이틀 내용 가져오고  수정하기
	@RequestMapping(value="/cy/my_get_Title.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String get_Title(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		String newTitle = request.getParameter("newTitle");
		if(newTitle==null || newTitle.equals("")) {
			String title = cyIntroDAO.getTitle(userId);
			request.setAttribute("title", title);
		}else {
			cyIntroDAO.updateTitle(userId, newTitle);
			String title = cyIntroDAO.getTitle(userId);
			request.setAttribute("title", title);
		}
		return "mypage/my_get_Title";
	}
	
	//대문사진 업로드창
	@RequestMapping(value="/cy/my_pic_upload.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String pic_upload(HttpServletRequest request) {
		return "mypage/my_pic_upload";
	}
	
	//대문사진 업로드완료
	@RequestMapping(value="/cy/my_pic_upload_ok.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String pic_upload_ok(HttpServletRequest request, MultipartHttpServletRequest reqFile) {
		MultipartFile file = reqFile.getFile("fileUpload");
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		String path = "C:\\cyworld\\"+userId+"\\intro";
		String extens = StringUtils.getFilenameExtension(file.getOriginalFilename());
		if(!extens.equals("jpg")&&!extens.equals("png")&&!extens.equals("bmp")) {
			request.setAttribute("result", 0);
			return "mypage/my_pic_upload_ok";
		}
		File fileTemp = new File(path);
		if(!fileTemp.exists()) {
			fileTemp.mkdirs();
		}
		String fileNameFullPath = "";
		if(file!=null || file.getSize()>0) {
			try {
				fileNameFullPath= userId+"\\intro\\"+file.getOriginalFilename();
				FileOutputStream fos = new FileOutputStream("C:\\cyworld\\"+fileNameFullPath);
				InputStream is = file.getInputStream();
				byte[] buffer = new byte[512];
				while(true) {
					int data = is.read(buffer,0,buffer.length);
					if(data==-1) {
						break;
					}
					fos.write(buffer,0,data);
				}
				is.close();
				fos.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		cyIntroDAO.updatePic(userId, fileNameFullPath);
		request.setAttribute("result", 1);
		return "mypage/my_pic_upload_ok";
	}
	
	//오늘의 투데이 --- 수정해야함
	@RequestMapping(value="/cy/my_get_Today.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String get_Today(HttpServletRequest request) {
		String newToday = request.getParameter("newToday");
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		String today ="";
		if(newToday==null||newToday.equals("")) {
			today = cyIntroDAO.getToday(userId);
			request.setAttribute("today", today);
			return "mypage/my_get_Today";
		}else {
			cyIntroDAO.updateToday(userId, newToday);
			today = cyIntroDAO.getToday(userId);
			request.setAttribute("today", today);
		}		
		return "mypage/my_get_Today";
	}
	
	//인트로 부분 내 소개
	@RequestMapping(value="/cy/my_get_Info.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String get_Info(HttpServletRequest request) {
		String newInfo = request.getParameter("newInfo");
		HttpSession session = request.getSession();
		CyUserDTO vo = (CyUserDTO)session.getAttribute("session");
		String userId = vo.getUserId();
		String info ="";
		if(newInfo==null||newInfo.equals("")) {
			info = cyIntroDAO.getInfo(userId);
			request.setAttribute("info", info);
		}else {
			cyIntroDAO.updateInfo(userId, newInfo);
			info = cyIntroDAO.getInfo(userId);
			request.setAttribute("info", info);
		}		
		return "mypage/my_get_Info";
	}


}
