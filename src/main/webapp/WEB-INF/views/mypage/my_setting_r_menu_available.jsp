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
<script src="https://www.lgkids.co.kr/es_all/plugins/jscolor-2.0.5/jscolor.js"></script>
<script type="text/javascript">

	$(function(){
		
		var menu3=document.getElementById("menu3-${cyMenuDTO.menu3}");
		menu3.checked=true;
		
		var menu4=document.getElementById("menu4-${cyMenuDTO.menu4}");
		menu4.checked=true;
		
		var menu5=document.getElementById("menu5-${cyMenuDTO.menu5}");
		menu5.checked=true;
		
		var menu6=document.getElementById("menu6-${cyMenuDTO.menu6}");
		menu6.checked=true;
		
		$('.menu').css('background-color', '#'+$("#menuBackground").val());
		$('.menu').css('color', '#'+$("#menuFontColor").val());
		$('.menu').css('border-color', '#'+$("#menuBorder").val());
	});
	
	$(document).ready(function(){
		 $("#send").click(function(){
			var params = "menu3="+document.myForm.menu3.value+"&menu4="+document.myForm.menu4.value+
				"&menu5="+document.myForm.menu5.value+"&menu6="+document.myForm.menu6.value+
				"&menuBackgroundColor="+$("#menuBackground").val()+"&menuFontColor="+$("#menuFontColor").val()+
				"&menuBorderColor="+$("#menuBorder").val();
			
			$.ajax({
				type:"POST",
				url:"<%=cp%>/cy/setting/menuAvailable_ok.action",
				data:params,
				success:function(args){
					params="menu=menu7";
					$.ajax({
						type:"POST",
						url:"<%=cp%>/cy/setting/rightMenu.action",
						data:params,
						success:function(args){
							$("#rightMenu").html(args);
						},
						error:function(e){
							alert(e.responseText);
						}			 
					 });
				},
				error:function(e){
					alert(e.responseText);
				}			 
			 });		 		 
		 });
		 $("#menuBackground").change(function(){
			 $('.menu').css('background-color', '#'+$("#menuBackground").val());
		 });
		 $("#menuFontColor").change(function(){
			 $('.menu').css('color', '#'+$("#menuFontColor").val());
		 });
		 $("#menuBorder").change(function(){
			 $('.menu').css('border-color', '#'+$("#menuBorder").val());
		 });
		 $("#basicMenuColor").click(function(){
			 document.getElementById('menuBackground').value="238db3";
			 document.getElementById('menuBackground').style.backgroundColor="#238DB3";
			 document.getElementById('menuBackground').style.color="#FFFFFF"
				 $('.menu').css('background-color', '#238db3');
				 
			 document.getElementById('menuFontColor').value="000000"
			 document.getElementById('menuFontColor').style.backgroundColor="#000000";
			 document.getElementById('menuFontColor').style.color="#FFFFFF"
				 $('.menu').css('color', '#000000');
				 
			 document.getElementById('menuBorder').value="000000"
			 document.getElementById('menuBorder').style.backgroundColor="#000000";
			 document.getElementById('menuBorder').style.color="#FFFFFF"
				 $('.menu').css('border-color', '#000000');
		 });
	});
	
	function bb(){
		
		document.getElementById('menuBackground').style.backgroundColor="black";
	}
	
	
</script>
</head>
<body>
<form name="myForm">
	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0" valine="top">
		<tr height="5px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>
	
	<div align="left" style="width: 420px; height:210px; padding-top: 6px;font: 굴림;font-size: 10pt;border: 0px solid black;background-color: #F6F6F6;">
		<div style="border: 0px solid black;width: 200px;height:150px;float: left;margin-top: 8px;">
			<div style="float: left;border: 0px solid black;padding-top: 5px;">
				<div align="center" class="menu">사진첩</div>
			</div>
			<div style="border: 0px solid black;height: 28px;padding-top: 10px;padding-left: 70px;">
				<input type="radio" name="menu3" id="menu3-0" value="0"> 공개 <input type="radio" name="menu3" id="menu3-1" value="1"> 비공개
			</div>
			<div style="float: left;border: 0px solid black;padding-top: 8px;">
				<div align="center" class="menu">게시판</div>
			</div>
			<div style="border: 0px solid black;height: 28px;padding-top: 13px;padding-left: 70px;">
				<input type="radio" name="menu4" id="menu4-0" value="0"> 공개 <input type="radio" name="menu4" id="menu4-1" value="1"> 비공개
			</div>
			<div style="float: left;border: 0px solid black;padding-top: 8px;">
				<div align="center" class="menu">비디오</div>
			</div>
			<div style="border: 0px solid black;height: 28px;padding-top: 13px;padding-left: 70px;">
				<input type="radio" name="menu5" id="menu5-0" value="0"> 공개 <input type="radio" name="menu5" id="menu5-1" value="1"> 비공개
			</div>
			<div style="float: left;border: 0px solid black;padding-top: 8px;">
				<div align="center" class="menu">방명록</div>
			</div>
			<div style="border: 0px solid black;height: 28px;padding-top: 13px;padding-left: 70px;">
				<input type="radio" name="menu6" id="menu6-0" value="0"> 공개 <input type="radio" name="menu6" id="menu6-1" value="1"> 비공개
			</div>
		</div>
		<div align="center" style="border: 0px solid black;height: 165px;padding-top: 5px;">
			<div align="left" style="padding-left: 228px;">
				바탕 색상
			</div>
			<div style="padding-top: 5px;padding-bottom: 10px;padding-right: 42px; border: 0px solid black;">
				<input class="form-control jscolor" id="menuBackground" value="${cyMenuDTO.menuBackgroundColor }" placeholder="바탕 색상" 
					style="width: 30%;">
			</div>
			<div align="left" style="padding-left: 228px;">
				글씨 색상
			</div>
			<div style="padding-top: 5px;padding-bottom: 10px;padding-right: 42px; border: 0px solid black;">
				<input class="form-control jscolor" id="menuFontColor" value="${cyMenuDTO.menuFontColor }" placeholder="글씨 색상"
					style="width: 30%;">
			</div>
			<div align="left" style="padding-left: 228px;">
				테두리 색상
			</div>
			<div style="padding-top: 5px;padding-bottom: 10px;padding-right: 42px; border: 0px solid black;">
				<input class="form-control jscolor" id="menuBorder" value="${cyMenuDTO.menuBorderColor }" placeholder="테두리 색상"
					style="width: 30%;">
			</div>
		</div>
		<div align="right" style="border: 0px solid black;height: 28px;padding-top: 5px;padding-left: 75px;padding-right: 27px;">
			<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
				<span id="basicMenuColor" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
					style="padding-right: 61px;">
						■ 기본 색상
				</span>
			</font>
			<input type="button" id="send" value="저장">
		</div>
	</div>

	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0">
		<tr height="5px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>
	
	<div style="height: 200px;"></div>
	
</form>
</body>
</html>