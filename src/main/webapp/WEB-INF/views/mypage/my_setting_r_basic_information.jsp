<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String pathUserId=(String)request.getAttribute("pathUserId");
%>
<%
%>
<!DOCTYPE html>
<html>
<head><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
a:link {text-decoration: none; color: black;}
a:visited {text-decoration: none; color: black;}
a:active {text-decoration: none; color: grey;}
a:hover {text-decoration: underline; color: red;}
 	
body{
	scrollbar-face-color: #FFFFFF;
	 scrollbar-highlight-color: #DBDBDB;
	 scrollbar-3dlight-color: #FFFFFF;
	 scrollbar-shadow-color: #9C92FF;
	 scrollbar-darkshadow-color: #FFFFFF;
	 scrollbar-track-color: #FFFFFF;
	 scrollbar-arrow-color: #9C92FF
}

.menu{
	border: 1px solid;
	border-left: 0px;
	width: 60px;
	height: 22px;
	padding-top: 6px;
	border-top-right-radius: 6px;
	border-bottom-right-radius: 6px;
	font-size: 9pt;
	user-select:none;
}
input::placeholder {
  font-style: italic;
  font-size: 8pt;
}
</style>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">

function changePwd(){
	
	
	if($("#userPw").val()==""){
		 swal("수정할 비밀번호를 입력하세요.", "", "<%=cp %>/resources/images/cy_logo.png");
		return
	}
	
	var url="<%=cp%>/cy/setting/setBasicInformation_changePw_ok.action";
	 
	$.post(url,{userPw:$("#userPw").val()},function(args){
		$("#userPw").val("");
		 swal("비밀번호를 수정했습니다.", "", "<%=cp %>/resources/images/cy_logo.png");
	});
}
	
	
</script>
</head>
<body>
<form name="myForm">
	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0" valine="top">
		<tr height="5px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>
	
	<div align="left" style="width: 420px; height:210px; padding-top: 6px;font: 굴림;font-size: 10pt;border: 0px solid black;background-color: #F6F6F6;">
		<div style="border: 0px solid black;width: 200px;height:150px;float: left;margin-top: 8px;margin-left: 3px;">
			<div style="float: left;border: 0px solid black;padding-top: 5px;font-size: 10pt;">
				아이디
			</div>
			<div style="border: 0px solid black;height: 28px;padding-top: 2px;padding-left: 70px;">
				<input type="text" value="${cyUserDTO.userId }" disabled="disabled"> 
			</div>
			<div style="float: left;border: 0px solid black;padding-top: 8px;font-size: 10pt;">
				패스워드
			</div>
			<div style="border: 0px solid black;height: 28px;padding-top: 2px;padding-left: 70px;">
				<input type="password" id="userPw" value=""> 
			</div>
			<div style="float: left;border: 0px solid black;padding-top: 8px;font-size: 10pt;">
				이름
			</div>
			<div style="border: 0px solid black;height: 28px;padding-top: 2px;padding-left: 70px;">
				<input type="text" value="${cyUserDTO.userName }" disabled="disabled"> 
			</div>
			<div style="float: left;border: 0px solid black;padding-top: 8px;font-size: 10pt;">
				생년월일
			</div>
			<div style="border: 0px solid black;height: 28px;padding-top: 2px;padding-left: 70px;">
				<input type="text" value="${cyUserDTO.userBirth }" disabled="disabled"> 
			</div>
			<div align="center" style="border: 0px solid black;height: 28px;padding-top: 2px;width: 430px;margin-top: 30px;">
				<input type="button" id="changePw" value="수정" onclick="changePwd()">&nbsp;<input type="reset" value="초기화">
			</div>
		</div>
	</div>

	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0">
		<tr height="5px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>
	
	<div style="height: 200px;"></div>
	
</form>
</body>
</html>