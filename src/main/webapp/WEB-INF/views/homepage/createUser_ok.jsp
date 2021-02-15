<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<% /* post방식일때 한글깨짐 방지 */
	request.setCharacterEncoding("UTF-8");  
%>

<%@ include file="../shop/header.jsp" %>
<script>
	var timer = setTimeout(function() {
	    window.location='index.action';
	}, 3000);
</script>

<form action="" method="post" name="myForm">

<div id="container">
	
	<img src="<%=cp %>/resources/images/welcome_gunchim.png"
		style="display: block; margin: 0px auto;"
	/>


</div><br/><br/><br/><br/>
						
</form>						
</body>
</html>
<%@ include file="../shop/footer.jsp" %>
