<%@ page  contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
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
			<c:if test="${dto.replierId==sessionScope.session.userId }">
			<font>
				${dto.replierName }
			</font>
			</c:if>
			<c:if test="${dto.replierId!=sessionScope.session.userId }">
			<font  style="font-size:8pt;color: blue;">
				<a onclick="window.open('user_main.action?userId=${dto.replierId}','${dto.replierId}','width=1090,height=600,location=no,status=no,scrollbars=no');" target="_blank">
					${dto.replierName }</a>
			</font>
			</c:if>
		</td>
		<td width="3">
			:
		</td>
		<td align="left">
			<font>
				${dto.content }&nbsp;
			</font>
			<font style="color: red;font-weight: bold;font-size: 9pt;">
				<a onclick="javascript:deleteOneRepleData('${dto.userId }',${dto.num },${dto.videoNum },${index });">
					×
				</a>
			</font>
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