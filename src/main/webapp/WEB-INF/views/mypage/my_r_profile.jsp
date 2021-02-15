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
<script type="text/javascript" src="/cyworld/resources/js/ajaxUtil2.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript">
var str_i="";
$(document).ready(function(){
	document.getElementById("change_ok").style.display="none";	
	sendRequest("my_get_profile.action",null,displayInfo,"GET");
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

function change(){
	document.getElementById("change_ok").style.display="block";
	document.getElementById("change").style.display="none";
	str_i = str_i.replace(/<br\/>/ig, "\n");			
	document.getElementById("textContent").value = str_i;
}

function change_ok(){
	document.getElementById("change").style.display="block";
	document.getElementById("change_ok").style.display="none";
	var str = document.getElementById("textContent").value;
	str = str.replace(/(<([^>]+)>)/gi, "");
	str = str.replace(/\n/gi, "<br/>");
	var url = "my_get_profile.action?content="+str;
	url = encodeURI(url);
	sendRequest(url,null,displayInfo,"GET");
	
	//sendRequest("my_get_profile.action",params,displayInfo,"GET");
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
				<div id="change"><div align="right"><input type="button" value="수정하기" onclick="change();"></div>
				<img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt="">
				<font>
					<span id="appendContent" align="center"></span>
				</font>
				<img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt="">
				</div>
				<div id="change_ok"><div align="right"><input type="button" value="수정완료" onclick="change_ok();"></div>
				<img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt="">
				<font>
					<textarea rows="50" cols="30" style="width: 400px; height: 300px;resize: none;" id="textContent"></textarea>
				</font>
				<img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt="">
				</div>
			</td>
		</tr>

	</table>
 </body>
</html>