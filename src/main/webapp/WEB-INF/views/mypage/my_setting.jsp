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
<head><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
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
</style>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript">

	$(function(){
		var url = "<%=cp%>/cy/setting/menuList.action";
			$.post(url,{},function(args){
				$("#menuData").html(args); 
			});  
			
		url = "<%=cp%>/cy/setting/rightMenu.action";
			$.post(url,{menu:"${menu}"},function(args){
				$("#rightMenu").html(args); 
			});  
			
			if("${currentFlag}"=="2"){
				url="<%=cp%>/cy/setting/menuAvailable.action";
				$.post(url,{},function(args){
					$("#browser").html(args); 
				});
			}else {
				url="<%=cp%>/cy/setting/setBasicInformation_pw.action";
				$.post(url,{},function(args){
					$("#browser").html(args); 
				});
			}
	});


</script>
</head>
 <body topmargin="0" leftmargin="0">
 <form action="main">
	<table border="0" align="left" valign="top" width="850" height="550"  cellspacing="0" cellpadding="0" style="table-layout: fixed;">
		<tr>
			<td>
				<table border="0" align="left" width="800" height="510">
					<tr>
						<td colspan="2" align="center">
							<br>
							<font ><span style="font-size:8pt;">today <font color="red">${session.getToDay() }</font> | total ${session.getTotalDay() }</span></font>
						</td>
						<td  height="40">
								<iframe frameborder="0" width="470" height="40" src="my_top_title.action"></iframe> 
						</td>
						<td></td>
					</tr>
					<tr>
						<td width="10"></td>
						<td width="178" height="450" align="center" valign="top" background="${pageContext.request.contextPath}/resources/images/bg_left_rect.jpg"
						style="padding-top: 10px;">
						<!-- Setting menu beginning -->
							<span id="menuData"></span>
						<!-- Setting menu ending -->								
						</td>
						<td width="480" height="450" align="center" background="${pageContext.request.contextPath}/resources/images/bg_center_rect.jpg">
						<!-- Setting menu beginning -->
							<span id="browser"></span>
						
						<!-- Setting menu ending -->							
						</td>
						<td valign="top" style="padding-top: 20px;">
						<!-- Setting menu beginning -->
							<span id="rightMenu"></span>
						<!-- Setting menu ending -->	
						</td>
					</tr>
					<tr>
						<td ></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
 </form>
 </body>



</html>