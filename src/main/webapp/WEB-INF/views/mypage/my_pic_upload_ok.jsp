<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<%
	
%>
<html>
<script type="text/javascript">
if(${result}==0){
	alert("잘못된 확장자 입니다.");
	window.opener.location.reload();
	window.close();
}else if(${result}==2){
	window.close();
}
else{
window.opener.location.reload();
window.close();
}
</script>
</html>
하이하이
