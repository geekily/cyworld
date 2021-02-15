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
<c:if test="${menuLength ne 0 }">
	<c:forEach var="i" begin="0" end="${menuList.size()-1 }" step="1">
	<li><font size="2pt">
	<a href="javascript:void(0)" onclick="sendFolder('${menuList.get(i).getFolderName() }','${userId }')">
	${menuList.get(i).getFolderName() }
	</a></li>
	</c:forEach>
</c:if>
</ul>