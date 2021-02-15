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
<head><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function memberSend_ok(){
	document.myForm.action="my_member_update_ok.action";
	document.myForm.submit();
}
</script>
 <body background="${resoucePath }/img/admin/pic/member_call.jpg">
	<form action="" name="myForm" method="post">
		<span style="position: absolute; top:35px; left:80px;"><font color="Blue">${userName }</font></span>
		<img alt="상대방의 미니미" src="${resoucePath }/img/${minimiPath}" style="position: absolute; top:65px; left:30px;" width="98px" height="98">
		<span style="position: absolute; top:104px; left:138px; font-size:8pt;" ><font color="Blue">${userName }</font>님께 일촌명을 변경합니다..</span>
		<span style="position: absolute; top:163px; left:30px; font-size:8pt;" ><font color="Blue">${userName }</font>님을 <font color="Blue">${myName }</font>님의 <input type="text" style="width:50px; height:10px;" name="userValue1" value="${myValue }"/>으로</span>
		<span style="position: absolute; top:185px; left:30px; font-size:8pt;" ><font color="Blue">${myName }</font>님을 <font color="Blue">${userName }</font>님의 <input type="text" style="width:50px; height:10px;" name="userValue2" value="${userValue }"/>으로</span>
		<span style="position: absolute; top:255px; left:30px; font-size:8pt;" >
		<textarea rows="3" cols="32" style="width: 240px; height: 40px;" name="userMessege"></textarea></span>
		<span style="position: absolute; top:329px; left:95px; font-size:8pt;" >
		<img alt="상대방의 미니미" src="${resoucePath }/img/admin/pic/sendBtn.jpg" onclick="memberSend_ok();"/>
		</span>
		<span style="position: absolute; top:330px; left:158px; font-size:8pt;" >
		<img alt="상대방의 미니미" src="${resoucePath }/img/admin/pic/cancelBtn.jpg" onclick="window.close();"/>
		<input type="hidden" name="userId1" value="${session.getUserId() }">
		<input type="hidden" name="userName1" value="${myName }">
		<input type="hidden" name="userId2" value="${userId }">
		<input type="hidden" name="userName2" value="${userName }">
		</span>
	</form>
 </body>
</html>