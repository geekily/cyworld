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
<head><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function memberCheck_ok(){
	document.myForm.action="my_member_check_ok.action?type=1";
	document.myForm.submit();
}
function memberCheck_next(){
	document.myForm.action="my_member_check_ok.action?type=2";
	document.myForm.submit();
}
function memberCheck_no(){
	document.myForm.action="my_member_check_ok.action?type=0";
	document.myForm.submit();
}
</script>
 <body background="${resoucePath }/img/admin/pic/member_check.jpg">
	<form action="" name="myForm" method="post">
		<span style="position: absolute; top:87px; left:85px; font-size: 9pt">
		<a href="" onclick=""><font color="Blue">${dto.getUserName1() }</font></a>
		</span>
		<img alt="미니미" src="${resoucePath }/img/${minimiPath }" style="position: absolute; top:105px; left:30px;" width="98px" height="98">
		<span style="position: absolute; top:134px; left:138px; font-size:9pt;" ><font color="Blue">${dto.getUserName1() }</font>님께서 <font color="Blue">${dto.getUserName2() }</font>님과</span>
		<span style="position: absolute; top:155px; left:138px; font-size:9pt;" ><font color="black"><b>일촌맺기</b></font>를 희망합니다.</span>
		<textarea rows="5" cols="46" style="position: absolute; top:210px; left:36px; width: 290px; height: 70px; font-size:9pt;" readonly="readonly">${dto.getUserMessege() }</textarea>
		<span style="position: absolute; top:310px; left:34px; font-size:9pt;" ><font color="blue">${dto.getUserName1() }</font>(${dto.getUserValue2() }) - <font color="blue">${dto.getUserName2() }</font>(${dto.getUserValue1() })</span>
		<span style="position: absolute; top:420px; left:115px; font-size:8pt;" >
		<img alt="" src="${resoucePath }/img/admin/pic/yesBtn.jpg" onclick="memberCheck_ok();"/>
		</span>
		<span style="position: absolute; top:420px; left:155px; font-size:8pt;" >
		<img alt="" src="${resoucePath }/img/admin/pic/nextBtn.jpg" onclick="memberCheck_next();"/>
		</span>
		<span style="position: absolute; top:420px; left:205px; font-size:8pt;" >
		<img alt="" src="${resoucePath }/img/admin/pic/noBtn.jpg" onclick="memberCheck_no();"/>
		</span>	
		<input type="hidden" name="userId1" value="${dto.getUserId1() }">
		<input type="hidden" name="userValue1" value="${dto.getUserValue1() }">
		<input type="hidden" name="userId2" value="${dto.getUserId2() }">
		<input type="hidden" name="userValue2" value="${dto.getUserValue2() }">
		<input type="hidden" name="typeMember" value="${dto.getType() }"/>
	</form>
 </body>
</html>