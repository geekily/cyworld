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
<style type="text/css">
body{
	background-color: #FFFFFF;
}

</style>
<script type="text/javascript">

function selectFolder(folderNameToMove) {

	var num="${num}";
	var folderName= "${folderName}";
	var userId="${userId}";
	opener.moving(encodeURIComponent(folderNameToMove),num,folderName,userId);
	
	window.close();
}
	
</script>
</head>
<body>
<form name="board" method="post" action="">
<div>
	<div style="background-color: #FFAAAA;text-align: center;height: 50px;display: table;width: 100%;">
		<font  style="font-size:10pt;font-weight: bold;text-align: center;vertical-align: middle;display: table-cell;">
		이동할 게시판을 고르세요.
		</font>
	</div>

	<c:forEach var="dto" items="${list }">
		<div style="background-color: #FFFFFF;text-align: left;height: 30px;display: table;width: 100%;">
			<font  style="font-size:10pt;font-weight: bold;vertical-align: middle;display: table-cell;">		
				<a href="javascript:void(0)" onclick="selectFolder('${dto.folderName }');" style="text-decoration: none;">${dto.folderName }</a><br/>
			</font>
		</div>
		<div style="background-color: #FFFFFF;text-align: center;height: 1px;display: table;width: 100%;"></div>
	</c:forEach>
</div>
	<input type="hidden" name="num" value="${num }"/>
</form>
</body>
</html>