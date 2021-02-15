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
<script type="text/javascript" src="/cyworld/resources/js/ajaxUtil2.js"></script>
<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
var str_i;

$(document).ready(function(){
    initSet();
});

function initSet(){
	var params = "userId=${userId }"
	sendRequest("user_get_Info.action",params,displayInfo,"GET");
}

function displayInfo(){
	if(httpRequest.readyState==4){
		if(httpRequest.status==200){
			str_i = httpRequest.responseText;
			var infoTxt = document.getElementById("infoTxt");
			infoTxt.innerHTML = str_i;	
		}
	}
}


function moveToUser(){
	var userId = document.getElementById("mySelect").value;
	if(userId == "") return;
	document.getElementById("mySelect")[0].selected;
	window.open("user_main.action?userId="+userId,userId,"width=1090,height=600,location=no,status=no,scrollbars=no");
}
</script>
<meta charset="UTF-8">
<title>Insert title here</title>

 <body bgcolor="#FFFFFF">	
	<table border="0" width="130" bgcolor="#FFFFFF">
		<tr >
			<td bgcolor="#FFFFFF" align="center"><font><div id="profile_status"></div></font></td>
		</tr>
		<tr >
			<td>
				<table bgcolor="#FFFFFF" width="130" cellpadding="1" cellspacing="1">
					<tr bgcolor="#FFFFFF">
						<td><img src="${resourcePath }/img/${imageFilePath}" width="128" height="128" border="0" alt="asd"/></td>
					</tr>
				</table>
			</td>

		</tr>
		<tr>
			<td> 
				<font>
					<div id="profile_info"  style="overflow-y:scroll; width:140px;  height:190px; padding:0px"><span id="infoTxt" style="font-size:13px;"></span></div>
				</font>
				<font style="font-size:10pt; position: absolute; top:265pt; left:5pt;" color="#0f3073"><b>${dto.getUserName() }</b> 
				<c:if test="${dto.getUserGender() eq '남자' }">
				<img src="${resourcePath }/img/admin/pic/man.jpg">
				</c:if>
				<c:if test="${dto.getUserGender() eq '여자' }">
				<img src="${resourcePath }/img/admin/pic/girl.jpg">
				</c:if>
				</font>	
				<c:if test="${member eq null }">
				<img src="${resourcePath }/img/admin/pic/member_btn.jpg" style ="position: absolute; top:280pt; left:25pt;" value="일촌맺기" onclick="window.open('my_member_call.action?userId=${userId }','${userId }memberCall','width=312,height=380,location=no,status=no,scrollbars=no');"/>
				</c:if>
				<c:if test="${member ne null }">
				<select id="mySelect" style="background-color: #9cbde7; width:140px; heigt:5px; position: absolute; top:280pt; left:5pt;" onchange="moveToUser();">
					<option value="">★친구일촌 파도타기</option>
					<c:if test="${length != 0 }">
					<c:forEach var="i" begin="0" end="${length-1 }" step="1">
						<option value="${userIdList[i] }">${userNameList[i] } (${userValueList[i] })</option>
					</c:forEach>
					</c:if>
				</select>
				</c:if>
			</td>
		</tr>
	</table>	
	<img alt="" src="${resourcePath }/img/admin/pic/meBtn.jpg" style="position: absolute; top: 400px; left: 40px" onclick="window.open('my_main.action','${session.getUserId() }',
	'width=1090,height=600,location=no,status=no,scrollbars=no');">
 </body>
</html>