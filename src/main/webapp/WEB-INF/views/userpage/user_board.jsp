<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
%>
<!DOCTYPE html>
<html>
<head><link rel="stylesheet" href="${resourcePath }/img${userFontCss}"/>
<meta charset="UTF-8">
<title>Insert title here</title>
 
<style type="text/css">
body
{scrollbar-face-color: #FFFFFF;
 scrollbar-highlight-color: #DBDBDB;
 scrollbar-3dlight-color: #FFFFFF;
 scrollbar-shadow-color: #9C92FF;
 scrollbar-darkshadow-color: #FFFFFF;
 scrollbar-track-color: #FFFFFF;
 scrollbar-arrow-color: #9C92FF}

ul{
   list-style:none;
   padding-left:0px;
   }

 a:link { color: #1294AB; text-decoration: none;}
 a:visited { color: black; text-decoration: none;}
 a:hover { color: RED; text-decoration: none;}

button{
  background-image: url(${pageContext.request.contextPath}/resources/images/guest.png);
  background-position: top left;
  background-repeat: no-repeat;
  background-color: #FFFFFF;
  border: 0px;
  width: 60px;
  height: 50px;
  margin: 7px 0 5px 7px;
  padding: 3px 0 6px 0;
  cursor: hand;
  cursor: pointer;
  color: #2C2C2C;
  font-size: 11px;
}

</style>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/cyworld/resources/js/ajaxUtil2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    initSet();
});

$(function(){
	$("#menuData").show();
	url = "<%=cp%>/cy/setting/userRightMenu.action";
	$.post(url,{menu:"${menu}",userId:"${userId}"},function(args){
		$("#rightMenu").html(args); 
	});  
});

function initSet(){
	var params = "userId=${userId}";
	sendRequest("<%=cp %>/cy/board/user_getMenu.action",params,displayInfo,"GET");
}

function displayInfo(){
	if(httpRequest.readyState==4){
		if(httpRequest.status==200){
			str_i = httpRequest.responseText;
			var menuData = document.getElementById("menuData");
			menuData.innerHTML = str_i;
		}
	}
	document.getElementById("addFolder").style.display="none";
}

function sendFolder(folderName,userId){
	document.main.action="user_board.action?folderName="+folderName+"&userId="+userId;
	document.main.submit();
}
function sendUser(userId){
	alert(userId);
	window.open("user_main.action?userId="+userId,userId,"width=1090,height=600,location=no,status=no,scrollbars=no");
}

</script>

 </head>


<form name="main" method="post" action="">

 <body topmargin="0" leftmargin="0">
	<table border="0" align="left" valign="top" width="850" height="550"  cellspacing="0" cellpadding="0" style="table-layout: fixed;">
		<tr>
			<td>
				<table border="0" align="left" width="800" height="510">
					<tr>
						<td colspan="2" align="center">
							<br>
							<font ><span style="font-size:8pt;">today <font color="red">${userDTO.getToDay() }</font>  total ${userDTO.getTotalDay() }</span></font>
						</td>
					<td  height="40">
						<center>
								<iframe frameborder="0" width="470" height="40" src="user_top_title.action?userId=${userId }"></iframe> 
							</center>
						</td>
						<td></td>
					</tr>
					<tr>
						<td width="10"> </td>

						<td width="178" height="450" align="center" background="${pageContext.request.contextPath}/resources/images/bg_left_rect.jpg"
						style="padding-top: 2px;">

							<!-- 메뉴 ------------------------------------------------------------------------------------------------ -->
							
							<div class="boardTitle" align="left">
								Board
								<img src="${resourcePath }/img/admin/pic/bar.jpg"/>
								<span id="menuData" ></span>
							</div>	

						</td>
						<td width="480" height="450" background="${pageContext.request.contextPath}/resources/images/bg_center_rect.jpg">
							<center>
							<!-- 오른쪽 내용 부분 ----------------------------------------------------------- -->
							<iframe frameborder="0" width="470" height="430" id="iframePic" src="<%=cp %>/cy/board/user_r_board_page.action?folderName=${encodedFolderName }&userId=${userId }"></iframe>
							<!-- ---------------------------------------------------------------------------- -->
							</center>
						</td>
						<!-- 오른쪽 메뉴 부분 ----------------------------------------------------------- -->
							<td valign="top" style="padding-top: 20px;">
								<!-- Setting menu beginning -->
									<span id="rightMenu"></span>
								<!-- Setting menu ending -->	
							</td>
						<!-- 오른쪽 메뉴 부분 ----------------------------------------------------------- -->
					</tr>

					<tr>
						<td ></td>
					</tr>
				</table>
				<!-- ------------------------ -->
			</td>
		</tr>
	</table>
	<!-- ------------------------ -->



 </body>

 </form>

</html>