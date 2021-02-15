<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
%>
<c:if test="${length0 ne 0}">
	<ul style="font-size: 8pt;">
	<c:forEach var="i" begin="0" end="${list0.size()-1 }" step="1">
		<li> <a href="" onclick="moveToUser('${list0.get(i).userId}');"><font color="#0f3073">${list0.get(i).userName }</font></a>: 
		${list0.get(i).content }
		<img alt="no found" src="<%=cp%>/resources/images/folder_deleted.png" height="13px" width="13px"
		onclick="deleteComment('${list0.get(i).num }');">	
	</c:forEach>
	</ul>
</c:if>==__ 
<c:if test="${list1 ne null }">
	<c:if test="${length1 ne 0}">
		<ul style="font-size: 8pt;">
		<c:forEach var="i" begin="0" end="${list1.size()-1 }" step="1">
			<li> <a href="" onclick="moveToUser('${list1.get(i).userId}');"><font color="#0f3073">${list1.get(i).userName }</font></a> : 
			${list1.get(i).content }
			<img alt="no found" src="<%=cp%>/resources/images/folder_deleted.png" height="13px" width="13px"
			onclick="deleteComment('${list1.get(i).num }');">	
		</c:forEach>
		</ul>
</c:if>
</c:if>==__ 
<c:if test="${list2 ne null }">
	<c:if test="${length2 ne 0 && list2 ne null}">
		<ul style="font-size: 8pt;">
		<c:forEach var="i" begin="0" end="${list2.size()-1 }" step="1">
			<li> <a href="" onclick="moveToUser('${list2.get(i).userId}');"><font color="#0f3073">${list2.get(i).userName }</font></a>: 
			${list2.get(i).content }
			<img alt="no found" src="<%=cp%>/resources/images/folder_deleted.png" height="13px" width="13px"
			onclick="deleteComment('${list2.get(i).num }');">	
		</c:forEach>
		</ul>
	</c:if>
</c:if>