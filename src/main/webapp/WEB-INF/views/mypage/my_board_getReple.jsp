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
	<c:forEach var="i" begin="0" end="${list.size()-1 }" step="1">
	<li>
	<a href="" onclick="window.open('<%=cp %>/cy/user_main.action?userId=${list.get(i).getUserId() }','${list.get(i).getUserId() }','width=1090,height=600,location=no,status=no,scrollbars=no');"><font color="#243354">${list.get(i).getUserName() }</font></a>
	 : ${list.get(i).getContent() } <font size="1pt">(${list.get(i).getCreated() })</font>
	 <img alt="" src="${pageContext.request.contextPath}/resources/images/deleteBtn2.png" onclick="deleteReple('${list.get(i).getNum()}');"></li>
	</c:forEach>
</c:if>
</ul>