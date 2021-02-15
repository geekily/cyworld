package com.cyworld.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyworld.dao.CyMemberDAO;
import com.cyworld.dao.CyMyItemDAO;
import com.cyworld.dao.CySettingDAO;
import com.cyworld.dao.CyUserDAO;
import com.cyworld.dao.CyUsingItemDAO;
import com.cyworld.dao.CyitemshopDAO;
import com.cyworld.dto.CyMemberDTO;
import com.cyworld.dto.CyMenuDTO;
import com.cyworld.dto.CyMyItemDTO;
import com.cyworld.dto.CyUserDTO;
import com.cyworld.dto.CyUsingItemDTO;
import com.cyworld.dto.CyUsingSongsDTO;
import com.cyworld.dto.CyitemshopDTO;

@Controller
public class SettingController {

	@Autowired
	CyUserDAO cyUserDAO;

	@Autowired
	CyMemberDAO cyMemberDAO;

	@Autowired
	CyitemshopDAO cyItemShopDAO;

	@Autowired
	CySettingDAO cySettingDAO;

	@Autowired
	CyMyItemDAO cyMyItemDAO;

	@Autowired
	CyUserDTO cyUserDTO;

	@Autowired
	CyUsingSongsDTO cyUsingSongsDTO;

	@Autowired
	CyUsingItemDAO cyUsingItemDAO;

	@RequestMapping(value = "/cy/my_setting.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String settingMainPage(HttpServletRequest request, String menu, String flag) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		CyMenuDTO cyMenuDTO=cySettingDAO.getMenuAvailable(cyUserDTO.getUserId());
		menu="menu7";
		String currentFlag="1";
		if(flag!=null) {
			currentFlag=flag;
		}

		request.setAttribute("cyMenuDTO", cyMenuDTO);
		request.setAttribute("menu", menu);
		request.setAttribute("currentFlag", currentFlag);

		return "mypage/my_setting";
	}

	@RequestMapping(value = "/cy/setting/menuList.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String settingMenu(HttpServletRequest request) throws Exception{

		return "mypage/my_setting_menu";
	}

	@RequestMapping(value = "/cy/setting/rightMenu.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String rightMenu(HttpServletRequest request, String menu) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		CyMenuDTO cyMenuDTO=cySettingDAO.getMenuAvailable(cyUserDTO.getUserId());

		request.setAttribute("cyMenuDTO", cyMenuDTO);
		request.setAttribute("menu", menu);

		return "mypage/my_right_menu";
	}

	@RequestMapping(value = "/cy/setting/menuAvailable.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String settingMenuAvailable(HttpServletRequest request) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		CyMenuDTO cyMenuDTO=cySettingDAO.getMenuAvailable(cyUserDTO.getUserId());

		request.setAttribute("cyMenuDTO", cyMenuDTO);

		return "mypage/my_setting_r_menu_available";
	}

	@RequestMapping(value = "/cy/setting/menuAvailable_ok.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String settingMenuAvailable_ok(HttpServletRequest request, CyMenuDTO cyMenuDTO) throws Exception{
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		cySettingDAO.updateMenuAvailable(cyUserDTO.getUserId(), cyMenuDTO.getMenu3(), cyMenuDTO.getMenu4(), 
				cyMenuDTO.getMenu5(), cyMenuDTO.getMenu6(), cyMenuDTO.getMenuBackgroundColor(), cyMenuDTO.getMenuFontColor(),
				cyMenuDTO.getMenuBorderColor());

		return settingMenuAvailable(request);
	}
	@RequestMapping(value = "/cy/setting/userRightMenu.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String userRightMenu(HttpServletRequest request, String menu, String userId) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		CyMenuDTO cyMenuDTO=cySettingDAO.getMenuAvailable(userId);

		request.setAttribute("cyMenuDTO", cyMenuDTO);
		request.setAttribute("userId", userId);		
		request.setAttribute("menu", menu);

		return "userpage/user_right_menu";
	}


	@RequestMapping(value = "/cy/setting/changeUsingSong.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String changeUsingSong(HttpServletRequest request) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		List<String> notUsingSongList=cyMyItemDAO.getNotUsingSong(cyUserDTO.getUserId());
		List<CyUsingSongsDTO> usingSongList=cyUsingItemDAO.getUsingSongs(cyUserDTO.getUserId());
		request.setAttribute("notUsingSongList", notUsingSongList);
		request.setAttribute("usingSongList", usingSongList);

		return "mypage/my_setting_usingSong";
	}

	@ResponseBody
	@RequestMapping(value = "/cy/setting/changeUsingSong_ok.action",method = {RequestMethod.GET,RequestMethod.POST})
	public void movingNotUsingIntoUsing(HttpServletRequest request, String usingSongToSend) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String[] array=usingSongToSend.split("!sep!");

		cyUsingItemDAO.deleteUsingSongs(cyUserDTO.getUserId());

		if(!array[0].equals("")) {
			for(int i=0;i<array.length;i++) {
				CyUsingSongsDTO cyUsingSongsDTO=new CyUsingSongsDTO();
				CyitemshopDTO cyitemshopDTO=cyItemShopDAO.getDataByItemName(array[i]);
				cyUsingSongsDTO.setUserId(cyUserDTO.getUserId());
				cyUsingSongsDTO.setSongOrder(cyUsingItemDAO.getMaxSongOrder(cyUserDTO.getUserId())+1);
				cyUsingSongsDTO.setSaveFileName(cyitemshopDTO.getSaveFileName());
				cyUsingSongsDTO.setOriginalFileName(cyitemshopDTO.getOriginalFileName());
				cyUsingItemDAO.insertUsingSongs(cyUsingSongsDTO);
			}
		}
	}

	@RequestMapping(value = "/cy/setting/setBasicInformation_pw.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String setBasicInformation_pw(HttpServletRequest request) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		request.setAttribute("cyUserDTO", cyUserDTO);
		
		return "mypage/my_setting_r_basic_information_pw";
	}
	
	@RequestMapping(value = "/cy/setting/setBasicInformation_pw_ok.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String setBasicInformation_pw_ok(HttpServletRequest request, String userPw) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		if(!cyUserDTO.getUserPw().equals(userPw)) {
			request.setAttribute("cyUserDTO", cyUserDTO);
			request.setAttribute("msg", "비밀번호 일치하지 않습니다.");
			return "mypage/my_setting_r_basic_information_pw";
		}
		
		request.setAttribute("cyUserDTO", cyUserDTO);
		
		return "mypage/my_setting_r_basic_information";
	}
	
	@ResponseBody
	@RequestMapping(value = "/cy/setting/setBasicInformation_changePw_ok.action",method = {RequestMethod.GET,RequestMethod.POST})
	public void setBasicInformation_changePw_ok(HttpServletRequest request, String userPw) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		
		cyUserDTO.setUserPw(userPw);
		cyUserDAO.updatePw(cyUserDTO.getUserId(), userPw);
		
		request.setAttribute("cyUserDTO", cyUserDTO);
	}
	
	
	//----------------------------------------승현
	@RequestMapping(value = "/cy/setting/usingBackGround.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String usingBackGround(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		List<CyMyItemDTO> list = cyMyItemDAO.getBackGround(userId);
		request.setAttribute("list", list);
		request.setAttribute("size", list.size());
		System.out.println("싸이즈 "+list.size());
		String src = "";
		if(cyUserDTO.getUserGender().equals("남자")) {
			src = "/admin/background/basic_man.jpg";
		}else {
			src = "/admin/background/basic_girl.jpg";
		}
		List<String> nameList = setName(list);
		request.setAttribute("basic", src);
		request.setAttribute("nameList", nameList);
		String useBg = cyUsingItemDAO.useBackGround(userId);
		request.setAttribute("useBg", useBg);
		return "mypage/my_setting_r_background";
	}

	private List<String> setName(List<CyMyItemDTO> list){
		List<String> nameList = new ArrayList<String>();
		Iterator lit = list.iterator();
		while(lit.hasNext()) {
			CyMyItemDTO vo = (CyMyItemDTO) lit.next();
			int num = vo.getNum();
			nameList.add(cyMyItemDAO.getItemName(num));
		}
		return nameList;
	}

	@RequestMapping(value = "/cy/setting/saveBackGround.action",method = {RequestMethod.GET,RequestMethod.POST})
	public void saveBackGround(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		String backGround = request.getParameter("backGround");
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		cyUsingItemDAO.updateBackGround(cyUserDTO.getUserId(),backGround);
	}

	@RequestMapping(value = "/cy/setting/usingStoryMinimi.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String usingStoryMinimi(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		List<CyMyItemDTO> storylist = cyMyItemDAO.getStoryRoom(userId);
		List<CyMyItemDTO> minimilist = cyMyItemDAO.getMinimiRoom(userId);
		request.setAttribute("storyList", storylist);
		request.setAttribute("storySize", storylist.size());
		request.setAttribute("minimilist", minimilist);
		request.setAttribute("minimiSize", minimilist.size());		
		String storyBasic = "";
		String minimiBasic = "";
		if(cyUserDTO.getUserGender().equals("남자")) {
			storyBasic = "/admin/room/basic_room.png";
			minimiBasic = "/admin/minimi/basic_man.png";
		}else {
			storyBasic = "/admin/room/basic_room.png";
			minimiBasic = "/admin/minimi/basic_girl.png";
		}
		List<String> storyNameList = setName(storylist);
		List<String> minimiNameList = setName(minimilist);
		request.setAttribute("storyBasic", storyBasic);
		request.setAttribute("minimiBasic", minimiBasic);
		request.setAttribute("storyNameList", storyNameList);
		request.setAttribute("minimiNameList", minimiNameList);
		String useStory = cyUsingItemDAO.useStoryRoom(userId);
		CyUsingItemDTO useMinimi = cyUsingItemDAO.useMinimi(userId);
		request.setAttribute("useStory", useStory);
		request.setAttribute("useMinimi", useMinimi);
		System.out.println(useMinimi.getOriginalFileName()+" 이게 왜 안뜨냐");
		request.setAttribute("useMinimiSrc", useMinimi.getOriginalFileName());
		return "mypage/my_setting_r_storyMinimi";
	}

	@RequestMapping(value = "/cy/setting/saveStoryMinimi.action",method = {RequestMethod.GET,RequestMethod.POST})
	public void saveStoryMinimi(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String storyRoom = request.getParameter("storyRoom");
		String minimi = request.getParameter("minimi");
		String[] xy = request.getParameter("xy").split(",");
		String x = xy[0];
		String y = xy[1];
		cyUsingItemDAO.updateStoryRoom(cyUserDTO.getUserId(),storyRoom);
		cyUsingItemDAO.updateMinimi(cyUserDTO.getUserId(),minimi,x,y);
	}


	//1-11 예리 추가 - 폰트 적용하기
	@RequestMapping(value = "/cy/setting/changeUsingFont.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String changeUsingFont(HttpServletRequest request) throws Exception{

		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();

		List<CyMyItemDTO> fontlist = cyMyItemDAO.getFonts(userId);
		request.setAttribute("fontList", fontlist);
		request.setAttribute("fontListSize", fontlist.size());

		String basicFontImage = "/admin/font/malgungothic.gif";
		String basicFontCss = "/admin/font/malgungothic.css";
		request.setAttribute("basicFontImage", basicFontImage);
		request.setAttribute("basicFontCss", basicFontCss);

		List<String> fontNameList = setName(fontlist);
		request.setAttribute("fontNameList", fontNameList);

		String useFont = cyUsingItemDAO.getUsingFont(userId);
		request.setAttribute("useFont", useFont);

		return "mypage/my_setting_usingFont";
	}

	//1-11 예리 추가 - 폰트 적용 넘기기
	@RequestMapping(value = "/cy/setting/changeUsingFont_ok.action",method = {RequestMethod.GET,RequestMethod.POST})
	public void changeUsingFont_ok(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");

		String font = request.getParameter("font");
		String[] fonts = font.split(" ");

		String originalFileName = fonts[0];
		String saveFileName = fonts[1];
		cyUsingItemDAO.updateUsingFont(saveFileName, originalFileName, cyUserDTO.getUserId());

	}


	@RequestMapping(value = "/cy/setting/my_setting_r_memberList.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String my_setting_r_memberList(HttpServletRequest request){
		return "mypage/my_setting_r_memberList";
	}


	@RequestMapping(value = "/cy/setting/getMemberList.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String getMemberList(HttpServletRequest request){
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String userId = cyUserDTO.getUserId();
		List<CyMemberDTO> list = cyMemberDAO.getMemberList(userId);
		if(list.size()==0) {
			request.setAttribute("size", 0);
		}else {
			request.setAttribute("size", list.size());
			String[] myValue = new String[list.size()];
			String[] memberId = new String[list.size()];
			String[] memberName = new String[list.size()];
			String[] memberValue = new String[list.size()];

			Iterator<CyMemberDTO> lit = list.iterator();
			int i = 0 ;
			while(lit.hasNext()) {
				CyMemberDTO vo = lit.next();
				if(vo.getUserId1().equals(userId)) {
					myValue[i] = vo.getUserValue1();
					memberId[i] = vo.getUserId2();
					memberName[i] = cyUserDAO.getName(vo.getUserId2());
					memberValue[i] = vo.getUserValue2();
				}else {
					myValue[i] = vo.getUserValue2();
					memberId[i] = vo.getUserId1();
					memberName[i] = cyUserDAO.getName(vo.getUserId1());
					memberValue[i] = vo.getUserValue1();
				}
				i++;
			}
			request.setAttribute("myValue", myValue);
			request.setAttribute("memberId", memberId);
			request.setAttribute("memberName", memberName);
			request.setAttribute("memberValue", memberValue);
		}		
		return "mypage/my_get_MemberList";
	}
	@RequestMapping(value = "/cy/setting/deleteMemberList.action",method = {RequestMethod.GET,RequestMethod.POST})
	public String deleteMemberList(HttpServletRequest request){
		HttpSession session = request.getSession();
		cyUserDTO = (CyUserDTO) session.getAttribute("session");
		String myId = cyUserDTO.getUserId();
		String userId = request.getParameter("userId");
		cyMemberDAO.deleteMember(myId,userId);
		List<CyMemberDTO> list = cyMemberDAO.getMemberList(myId);
		if(list.size()==0) {
			request.setAttribute("size", 0);
		}else {
			request.setAttribute("size", list.size());
			String[] myValue = new String[list.size()];
			String[] memberId = new String[list.size()];
			String[] memberName = new String[list.size()];
			String[] memberValue = new String[list.size()];

			Iterator<CyMemberDTO> lit = list.iterator();
			int i = 0 ;
			while(lit.hasNext()) {
				CyMemberDTO vo = lit.next();
				if(vo.getUserId1().equals(myId)) {
					myValue[i] = vo.getUserValue1();
					memberId[i] = vo.getUserId2();
					memberName[i] = cyUserDAO.getName(vo.getUserId2());
					memberValue[i] = vo.getUserValue2();
				}else {
					myValue[i] = vo.getUserValue2();
					memberId[i] = vo.getUserId1();
					memberName[i] = cyUserDAO.getName(vo.getUserId1());
					memberValue[i] = vo.getUserValue1();
				}
				i++;
			}
			request.setAttribute("myValue", myValue);
			request.setAttribute("memberId", memberId);
			request.setAttribute("memberName", memberName);
			request.setAttribute("memberValue", memberValue);
		}		
		return "mypage/my_get_MemberList";
	}
}
