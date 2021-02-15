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

</script>


<body>
  	
<form name="poto">

	<table border="0" width="420" cellpadding="1" cellspacing="1" align="center">
		<tr>
			<td align="right">
				<input type="button" value="사진올리기" onclick="javascript:location.href='write.action'"/>				
			</td>
		</tr>
	</table>


	<!-- 사진 ----------------------------------------------------------------------------------------------- -->
	<center><img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt=""></center>
	<table border="0" bgcolor="#EBEBEB" width="420" cellpadding="1" cellspacing="1" align="center">
		<tr>
			<td>
				<a name="n5"></a>
				<font><b>el 사진번호, 글이름</b></font>
			</td>
		</tr>
	</table>
	<table border="0" align="center" width="420" cellpadding="1" cellspacing="1">
		<tr>
			<td width="100">  
				<font>el 작성자이름</font>
			</td>
			<td align="right">  
				<font>스크랩 : #</font>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">  
				<!-- 사진 바꾸기 -->
				<a href="${pageContext.request.contextPath}/resources/pics/6.jpg" target="_blank"><img src="${pageContext.request.contextPath}/resources/pics/6.jpg" width="400" height="300" border="0" alt=""></a>
				<!-- --------- -->
			</td>
		</tr>
		<tr>
			<td colspan="2">  
				<font>
				<!-- 내용 바꾸기 -->
					el 내용작성
				</font>
				<!----------  -->
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">  
				<!-- 사진첩 글, 글 밑 정보 부분--------------------------------------------------- -->
				<table border="0" width="400" align="center" cellpadding="0" cellspacing="0">					
					<tr>
						<td align="right"> 
							<img src="${pageContext.request.contextPath}/resources/images/bar.jpg" width="420" height="6" border="0" alt=""><br/>
							<font>
								<a onclick="javascript:alert('수정 했습니다.')">수정</a> | 
								<a onclick="javascript:alert('이동 했습니다.')">이동</a> | 
								<a onclick="javascript:alert('삭제 했습니다.')">삭제</a></font>
						</td>
					</tr>
					<tr>
						<td>	
							뎃글작성 스크랩시 자동으로 "퍼가요~♡" 작성되도록하기 
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
	<!-- 사진 끝 ----------------------------------------------------------------------------------------------- -->
	<br/><br/><br/>
</form>


	<br/></br/>

 </body>

</body>
</html>