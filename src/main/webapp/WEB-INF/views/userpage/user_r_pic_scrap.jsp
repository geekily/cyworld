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
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<style type="text/css">
body{
	background-color: #E8D9FF;
}

</style>
<script type="text/javascript">

	function scrapPic(folderNameForScrap) {
		
		 var url = "<%=cp%>/cy/user/picture/scrapPic_ok.action";
		 
		  $.post(url,{owner:"${owner}",originalPicNum:"${originalPicNum}",folderNameForScrap:folderNameForScrap},function(){
			  
		  });
		  
		  swal("성공!", "", "<%=cp %>/resources/images/cy_logo.png");
		  opener.repleAfterScrap("${index}");
		  window.close();
	}

	
</script>
</head>
<body>
<div>
	<c:if test="${list.size()==0 }">
	<div style="background-color: #BDBDBD;text-align: center;height: 50px;display: table;width: 100%;">
		<font  style="font-size:10pt;font-weight: bold;text-align: center;vertical-align: middle;display: table-cell;">
		이동할 사진첩이 없습니다.
		</font>
	</div>
	</c:if>

	<c:if test="${list.size()!=0 }">
	<div style="background-color: #BDBDBD;text-align: center;height: 50px;display: table;width: 100%;">
		<font  style="font-size:10pt;font-weight: bold;text-align: center;vertical-align: middle;display: table-cell;">
		이동할 사진첩을 고르세요.
		</font>
	</div>
	<c:forEach var="dto" items="${list }">
		<div style="background-color: #EBEBEB;text-align: left;height: 30px;display: table;width: 100%;">
			<font  style="font-size:10pt;font-weight: bold;vertical-align: middle;display: table-cell;">		
				<a href="" onclick="javascript:scrapPic('${dto.folderName }');" style="text-decoration: none;">${dto.folderName }</a><br/>
			</font>
		</div>
		<div style="background-color: #BDBDBD;text-align: center;height: 1px;display: table;width: 100%;"></div>

	</c:forEach>
	</c:if>
</div>
</body>
</html>