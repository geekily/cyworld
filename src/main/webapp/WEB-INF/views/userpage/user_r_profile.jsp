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
<script type="text/javascript" src="/cyworld/resources/js/ajaxUtil2.js"></script>
<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
var str_i="";
$(document).ready(function(){
	var params = "userId=${userId}"
	sendRequest("user_get_profile.action",params,displayInfo,"GET");
});

function displayInfo(){
	if(httpRequest.readyState==4){
		if(httpRequest.status==200){
			str_i = httpRequest.responseText;
			var appendContent = document.getElementById("appendContent");
			appendContent.innerHTML = str_i;	
		}
	}
}

</script>
 </head>

 <body>
  
	<table border="0" width="420" cellpadding="0" cellspacing="0">
		<tr>
			<td>&nbsp;&nbsp;<font><b>프로필</b></font></td>
		</tr>
		<tr>
			<td align="center">  
				<img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt="">
				<font>
					<span id="appendContent"></span>
				</font>
				<img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt="">
			</td>
		</tr>

	</table>
 </body>
</html>