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
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">

</script>
 <body background="${resoucePath }/img/admin/pic/present_list.jpg">
	<form action="" name="myForm" method="post" style="position: absolute; top:50px; left: 15px; font-size: 8pt;">
		<c:if test="${lists ne null }">
			<ul style="width: 282px; height:300px; ">				
				<c:forEach var="dto" items="${lists }">
					<li style="vertical-align: top; width: 200px;">
					<b>[${dto.type }]</b> ${dto.itemName }을(를) <b>${dto.buyer }</b>님께서 보내셧습니다.
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${lists eq null }">
			<center>선물함이 비어있습니다.</center>
		</c:if>
	</form>
 </body>
</html>