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

 </head>

<script language="javascript">
<!--
	function comment_ok()
	{
		var text = document.poto.commenti.value;
		if (text != "")
		{
			text = text + '\n\n\n' + '댓글이 추가되었습니다. ^_^';
			alert(text);

		}
		else
		{
			alert('댓글을 입력해 주세요 ^^');
		}
	}
//-->
</script>


 <body>
  	
<form name="poto">

	<table border="0" width="420" cellpadding="1" cellspacing="1" align="center">
		<tr>
			<td align="center"> 
				<font>
					펼쳐보기 | 작게보기 | 슬라이드
				</font>
			</td>
			<td align="right">
				<input type="submit" value="사진인화"/>
				<input type="submit" value="사진올리기"/>
			</td>
		</tr>
	</table>
	<center><img src="images/bar.jpg" width="420" height="6" border="0" alt=""></center>
	<table border="0" bgcolor="#EBEBEB" width="420" cellpadding="1" cellspacing="1" align="center">
		<tr>
			<td>
				<font><b>사진첩</b></font>
			</td>
		</tr>
	</table>
	<table border="0" align="center" width="420" cellpadding="1" cellspacing="1">
		<tr>
			<td width="100">  
				<font>장인수</font>
			</td>
			<td align="right">  
				<font>2008.02.25 12:10 스크랩 : 0</font>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">  
				<img src="./pics/kim1.jpg" width="400" height="300" border="0" alt="">
			</td>
		</tr>
		<tr>
			<td colspan="2">  
				<font>
					안녕하세요~~~
				</font>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">  
				<!-- 사진첩 글, 글 밑 정보 부분--------------------------------------------------- -->
				<img src="images/bar.jpg" width="420" height="6" border="0" alt="">
				<table border="0" width="400" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td> 
							<font>
								출처 : <br/>
								작성자 : <br/>
								작성일 : <br/>
								공개설정 : <br/>
							</font>
						</td>
					</tr>
					<tr>
						<td align="right"> 
							<img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt=""><br/>
							<font>수정 | 이동 | 삭제</font>
						</td>
					</tr>
					<tr>
						<td align="center"> 
							<br/>
							<input type="text" name="commenti" size="50"></textarea>
							<input type="button" name="comment_save" value="확인" onclick="comment_ok()">
						</td>
					</tr>
				</table>
				
				<!-- ---------------------------------------------------------------------------- -->
			</td>
		</tr>
	</table>
</form>


	<br/></br/>


 </body>
</html>