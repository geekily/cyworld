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
<script type="text/javascript">

	window.onload = function() { 
		document.addEventListener('contextmenu',  
                event => event.preventDefault()); 
		setTimeout(function() {
			location.replace('<%=cp%>/cy/video/list.action?folderName='+encodeURIComponent("${folderName}")); 
		},2000)
	};

</script>
</head>
<body>
<table border="0" align="center" height="300">
	<tr>
		<td align="center" valign="bottom">
			<img alt="no found" src="<%=cp %>/resources/images/cy_logo.png" width="160" height="150">
		</td>	
	</tr>
	<tr>
		<td align="center" height="30">
			<font  style="font-size:10pt;font-weight: bold;">
				비디오 업로드를 완료 했습니다.
			</font>
		</td>	
	</tr>
	<tr>
		<td align="center" height="30">
			<font  style="font-size:10pt;font-weight: bold;">
				앨범으로 돌아갑니다.
			</font>
		</td>	
	</tr>

</table>

</body>
</html>