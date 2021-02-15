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
<html>
<link rel="stylesheet" href="${resourcePath }/img${userFontCss}"/>
<script type="text/javascript" src="/cyworld/resources/js/ajaxUtil2.js"></script>
<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
var str;

$(document).ready(function(){
    initSet();
});

function initSet(){
	var params = "userId=${userId }";
	sendRequest("user_get_Title.action",params,displayTitle,"GET");
}

function displayTitle(){
	if(httpRequest.readyState==4){
		if(httpRequest.status==200){
			str = httpRequest.responseText;
			var listData = document.getElementById("inText");
			listData.innerHTML = str;				
		}
	}
}

</script>
<body style="overflow:hidden;">
<div id="titleName" style="padding-top:12px;">
	<span style="font-weight:bold; font-size: 15px; color: #4B9687;"
		id="inText" onclick="change();"></span>
		</div>
		<div id="titleName_alter">
	<input type="text" id="title_Txt" value=""/>
		<img src="${resourcePath }/img/admin/pic/editOkBtn.jpg" onclick="change_ok();"/>
		</div>
</body>
</html>
