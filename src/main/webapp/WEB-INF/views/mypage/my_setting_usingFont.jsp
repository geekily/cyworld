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
<head><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
a:link {text-decoration: none; color: black;}
a:visited {text-decoration: none; color: black;}
a:active {text-decoration: none; color: grey;}
a:hover {text-decoration: underline; color: red;}
 	
 input[type="radio"] {
  margin-top: -1px;
  vertical-align: middle;
}
 	
body{
	scrollbar-face-color: #FFFFFF;
	 scrollbar-highlight-color: #DBDBDB;
	 scrollbar-3dlight-color: #FFFFFF;
	 scrollbar-shadow-color: #9C92FF;
	 scrollbar-darkshadow-color: #FFFFFF;
	 scrollbar-track-color: #FFFFFF;
	 scrollbar-arrow-color: #9C92FF
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#sendBtn").click(function(){
		var font = document.myForm.font.value;
		params="font="+font;
		
		$.ajax({
			 type:"POST",
			 url:"<%=cp%>/cy/setting/changeUsingFont_ok.action",
			 data:params,
			 success:function(args){
				 window.parent.location.href = "<%=cp%>/cy/my_main.action";
			 },
			 error:function(e){
				 window.parent.location.href = "<%=cp%>/cy/my_main.action";
			 }			 
		 });
	 });
	});
function changeFont(src){
	var src = document.myForm.font.value;
	src = src.split("+")[1];
	src = "${resourcePath}/img"+src;
	$("#changeImg").attr("src", src);
}
</script>
</head>
<body>
<form name="myForm">
	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0">
		<tr height="2px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>	
	<div style="width: 420px; height:370px;font-size: 10pt;border: 0px solid black;">	
	<table width="420" height="100" align="center" cellpadding="10" style="text-align:center;">
		<tr>
			<td>
				<b>현재 사용 중.</b>
			</td>
			<td>
				<b>바꿀 예정.</b>
			</td>
		</tr>
		<tr>
			<td>
				<img src="${resourcePath }/img${useFont}" border="0" alt="${resourcePath }/img${useFont}" id="fontImg">
			</td>
			<td>
				<img src="${resourcePath }/img${useFont}" border="0" alt="${resourcePath }/img${useFont}" id="changeImg">
			</td>
		</tr>
	</table>
	<div style="overflow-y:scroll; width:440px;  height:175px; padding:0px">
	<table style="width: 420px; overflow-x: scroll; height:150px; text-align:center;">
		<b>사용하실 글꼴을 선택해 주세요.</b>
		<tr>								
			<td>
			<div style="padding:0px;">			
				<c:if test="${basicFont eq useFont }">
					<input type="radio" name="font" value="${basicFontCss }+${basicFontImage }" onclick="changeFont('${basicFontImage }');" checked="checked"><br/>
			        <img alt="${resourcePath }/img${basicFontCss } " src="${resourcePath }/img${basicFontImage }" width="120px" >
			       	<br/>기본 글꼴<br/>
				</c:if>		
			    <c:if test="${basicFont ne useFont }">
			        <input type="radio" name="font" value="${basicFontCss }+${basicFontImage }" onclick="changeFont('${basicFontImage }');"><br/>
			        <img alt="${resourcePath }/img${basicFontCss } " src="${resourcePath }/img${basicFontImage }" width="120px" >
			       	<br/>기본 글꼴<br/>
			    </c:if>
			  </div>
			</td>	
			<c:if test="${fontListSize ne 0 }">
			   <%int i = 0; %>
				<c:forEach var="i" begin="0" end="${fontListSize-1 }" step="1">
					<%if(i%3==0&&i==2){%>
					 <tr>
					<%} %>					
					<td>	
						<c:if test="${fontList.get(i).getSaveFileName() eq useFont }">
							<input type="radio" name="font" value="${fontList.get(i).getOriginalFileName() }+${fontList.get(i).getSaveFileName()}" onclick="changeFont('${fontList.get(i).getSaveFileName()}');" checked="checked"><br/>
							<img src="${resourcePath }/img${fontList.get(i).getSaveFileName() }" 
							alt="${resourcePath }/img${fontList.get(i).getOriginalFileName() }" width="120px" >
							<br/>${fontNameList.get(i) }<br/>
						</c:if>
						<c:if test="${fontList.get(i).getSaveFileName() ne useFont }">
							<input type="radio" name="font" value="${fontList.get(i).getOriginalFileName() }+${fontList.get(i).getSaveFileName()}" onclick="changeFont('${fontList.get(i).getSaveFileName()}');"><br/>
							<img src="${resourcePath }/img${fontList.get(i).getSaveFileName() }" 
							alt="${resourcePath }/img${fontList.get(i).getOriginalFileName() }" width="120px" >
							<br/>${fontNameList.get(i) }<br/>
						</c:if>	
					</td>
					<%i++; %>
					<%if(i==2||i%3==2){%>
					 </tr>					 
					<%} %>		
				</c:forEach>				
			</c:if>
		</table>
	</div>
	<input type="button" id="sendBtn" value ="저장" onclick="changeUsingFont_ok()"/>
	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0">
		<tr height="5px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>
</form>
</body>
</html>