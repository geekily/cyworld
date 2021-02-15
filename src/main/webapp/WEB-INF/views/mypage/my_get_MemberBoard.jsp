<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
%>
<ul>
	<c:if test="${length ne 0 }">
	<c:forEach var="i" begin="0" end="${length-1 }" step="1">
		<li>${content[i] } (${memberValue[i] } <a href="" onclick="moveToUser('${memberId[i]}');">
		<font color="#0f3073">${memberName[i] }</font></a>)
		<img alt="no found" src="<%=cp%>/resources/images/folder_deleted.png" height="13px" width="13px"
		onclick="deleteComment('${userId }');">	
	</c:forEach>
	</c:if>
</ul>