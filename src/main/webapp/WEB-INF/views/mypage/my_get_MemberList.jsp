<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
%>
<c:if test="${size ne 0 }">
	<c:forEach var="i" begin="0" end="${size-1 }" step="1">
		<font style="margin-left: 10px;">나(${memberValue[i] }) - <a href="#"><font color="blue">${memberName[i] }</font></a>(${myValue[i] })</font>
		<input type="button" value="일촌명 변경" onclick="updateMember('${memberId[i]}');"/><input type="button" value="일촌끊기" onclick="deleteMember('${memberId[i]}');"/><br/>
	</c:forEach>
</c:if>