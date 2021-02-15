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
<style type="text/css">
body{
	background-color: #E8D9FF;
}

</style>
<script type="text/javascript">

	function selectFolder(folderNameToMove) {
		
		var userId="${userId}";
		var num=${num};

		opener.moving(encodeURIComponent(folderNameToMove),userId,num);
		
		window.close();
	}
	
</script>
</head>
<body>
<div>
	<div style="background-color: #BDBDBD;text-align: center;height: 50px;display: table;width: 100%;">
		<font  style="font-size:10pt;font-weight: bold;text-align: center;vertical-align: middle;display: table-cell;">
		이동할 사진첩을 고르세요.
		</font>
	</div>

	<c:forEach var="dto" items="${list }">
	<c:if test="${dto.folderName==folderName }">
		<div style="background-color: #EBEBEB;text-align: left;height: 30px;display: table;width: 100%;">
			<font  style="font-size:10pt;font-weight: bold;vertical-align: middle;display: table-cell;">		
				${dto.folderName } (현재폴더)<br/>
			</font>
		</div>
		<div style="background-color: #BDBDBD;text-align: center;height: 1px;display: table;width: 100%;"></div>
	</c:if>
	
	
	
	<c:if test="${dto.folderName!=folderName }">
		<div style="background-color: #EBEBEB;text-align: left;height: 30px;display: table;width: 100%;">
			<font  style="font-size:10pt;font-weight: bold;vertical-align: middle;display: table-cell;">		
				<a href="" onclick="javascript:selectFolder('${dto.folderName }');" style="text-decoration: none;">${dto.folderName }</a><br/>
			</font>
		</div>
		<div style="background-color: #BDBDBD;text-align: center;height: 1px;display: table;width: 100%;"></div>
	</c:if>
	</c:forEach>
</div>
</body>
</html>