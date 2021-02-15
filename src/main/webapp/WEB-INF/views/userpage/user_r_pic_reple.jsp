<%@ page  contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><link rel="stylesheet" href="${resourcePath }/img${userFontCss}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<table border="0" align="center" width="420" bgcolor="#EBEBEB">
	<c:if test="${totalData==0 }">
	<tr>
		<td align="center" colspan="4">
			<font>
				등록된 댓글이 없습니다.
			</font>
		</td>
	</tr>
	</c:if>
	<c:if test="${totalData!=0 }">
	<c:forEach var="dto" items="${list }">
	<tr>
		<td width="2"></td>
		<td align="left" width="60" valign="top"> 
			<font>
				<a onclick="window.open('user_main.action?userId=${dto.replierId}','${dto.replierId}','width=1090,height=600,location=no,status=no,scrollbars=no');" target="_blank">
					${dto.replierName }</a>
			</font>
		</td>
		<td width="3">
			:
		</td>
		<td align="left">
			<font>
				${dto.content }&nbsp;
			</font>
			<c:if test="${sessionScope.session.userId==dto.replierId }">
			<font style="color: red;font-weight: bold;font-size: 9pt;">
				<a onclick="javascript:deleteOneRepleData('${dto.userId }',${dto.num },${dto.picNum },${index });">
					×
				</a>
			</font>
			</c:if>
		</td>
	</tr>
	</c:forEach>
	</c:if>
	<c:if test="${totalData!=0 }">
	<tr>
		<td align="center" colspan="4">
			<font>
				${pageIndexList }
			</font>
		</td>
	</tr>
	</c:if>
</table>

</body>
</html>