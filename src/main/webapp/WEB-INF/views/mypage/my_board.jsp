<%@page import="java.net.URLEncoder"%>
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
<link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
<meta charset="UTF-8">
<title>Insert title here</title>
 
<style type="text/css">
body
{scrollbar-face-color: #FFFFFF;
 scrollbar-highlight-color: #DBDBDB;
 scrollbar-3dlight-color: #FFFFFF;
 scrollbar-shadow-color: #9C92FF;
 scrollbar-darkshadow-color: #FFFFFF;
 scrollbar-track-color: #FFFFFF;
 scrollbar-arrow-color: #9C92FF}

ul{
   list-style:none;
   padding-left:0px;
   }

 a:link { color: #1294AB; text-decoration: none;}
 a:visited { color: black; text-decoration: none;}
 a:hover { color: RED; text-decoration: none;}

button{
  background-image: url(${pageContext.request.contextPath}/resources/images/guest.png);
  background-position: top left;
  background-repeat: no-repeat;
  background-color: #FFFFFF;
  border: 0px;
  width: 60px;
  height: 50px;
  margin: 7px 0 5px 7px;
  padding: 3px 0 6px 0;
  cursor: hand;
  cursor: pointer;
  color: #2C2C2C;
  font-size: 11px;
}


.modal-container{
	position: absolute;
	left:0; right: 0; top: 0; bottom: 0;
	display : flex;
	justify-content: center;
	align-items: center;
}
.modal{
	width:300px;
	background: #fff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0,0,0,0.8);
}

.modal h3{
	padding-left: 10px;
}


</style>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/cyworld/resources/js/ajaxUtil2.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    initSet();
});

function initSet(){
	if('${type}' != 0){
		alert('${type}');
	}
	sendRequest("<%=cp %>/cy/board/getMenu.action",null,displayInfo,"GET");
}

function displayInfo(){
	if(httpRequest.readyState==4){
		if(httpRequest.status==200){
			var str = httpRequest.responseText;
			var str1 = str.split("//")[0];
			var str2 = str.split("//")[1];
			if(str2 != "0"){
				alert("폴더 이름이 중복 되었습니다.");
			}
			var menuData = document.getElementById("menuData");
			menuData.innerHTML = str1;
		}
	}
	document.getElementById("addFolder").style.display="none";
}

function sendFolder(folderName){
	document.main.action="my_board.action?folderName="+folderName;
	document.main.submit();
}

function cancelFolder(){
	document.getElementById("addFolder").style.display="none"; 
}


function editFolder(num,folderName,type){
	$('.modal-container').fadeIn();
	document.getElementById('num').value = num;
	document.getElementById('oldFolderName').value = folderName;
	$('.oriFolder').text("기존이름 : "+folderName);
	if(type==2){
		$('#all').prop('checked', true);
	}else if(type==1){
		$('#member').prop('checked', true);
	}else{
		$('#secret').prop('checked', true);
	}
}



function editFolder_ok(){
	$('.modal-container').hide();
	var num = document.getElementById('num').value;
	var folderName = document.getElementById('oldFolderName').value;
	var newFolderName = document.getElementById('newFolderName').value;
	newFolderName = newFolderName.trim();
	if(newFolderName==""){
		alert("값을 입력 해주세요");
		return;
	}
	var pattern_spc = /[~!@#$%^&*()+|<>?:{}]/; // 특수문자
	if(pattern_spc.test(newFolderName)){
		alert("특정 특수문자는 폴더 이름으로 할 수 없습니다.");
		return;
	}
	var type = document.main.setType.value;
	var params = "num="+num+"&oldFolderName="+folderName+"&newFolderName="+newFolderName+"&type="+type;
	<%-- sendRequest("<%=cp %>/cy/board/getMenu.action",params,displayInfo,"GET"); --%>
	document.main.action = "<%=cp %>/cy/board/updateMenu.action?"+params;
	document.main.submit();
}

function editFolder_no(){
	$('.modal-container').fadeOut(500);
}

function deletedFolder(num, folderName){
	var flag = confirm("삭제하시겠습니까?\n관련된 모든 데이터가 삭제됩니다.");
	if(flag){
		var params = "delete=ok&folderName="+folderName+"&num="+num;
		document.main.action="board/my_boardFolderDelete.action?"+params;
		document.main.submit();
	}else{
		return;
	}
}
function addFolder(){
	document.getElementById("addFolder").style.display="block"; 
}

function addFolder_ok(){
	var folderName = document.getElementById("addFolderName").value;
	folderName = folderName.trim();
	if(folderName==""){
		alert("값을 입력 해주세요");
		return;
	}
	var pattern_spc = /[~!@#$%^&*()+|<>?:{}]/; // 특수문자
	if(pattern_spc.test(folderName)){
		alert("특정 특수문자는 폴더 이름으로 할 수 없습니다.");
		return;
	}
	var setType = document.main.setType.value;
	params = "folderName="+folderName+"&setType="+setType;
	sendRequest("<%=cp %>/cy/board/getMenu.action",params,displayInfo,"GET");	
}


function sendUser(userId){
	window.open("user_main.action?userId="+userId,userId,"width=1090,height=600,location=no,status=no,scrollbars=no");
}


$(function(){
	url = "<%=cp%>/cy/setting/rightMenu.action";
	$.post(url,{menu:"${menu}"},function(args){
		$("#rightMenu").html(args); 
	});  
});

$(document).ready(function(){
	$('.modal-container').hide();
})
</script>

 </head>


<form name="main" method="post" action="">

 <body topmargin="0" leftmargin="0">
	<table border="0" align="left" valign="top" width="850" height="550"  cellspacing="0" cellpadding="0" style="table-layout: fixed;">
		<tr>
			<td>
				<table border="0" align="left" width="800" height="510">
					<tr>
						<td colspan="2" align="center">
							<br>
							<font><span style="font-size:8pt;">today <font color="red">${session.getToDay() }</font> | total ${session.getTotalDay() }</span></font>
						</td>
					<td  height="40">
						<iframe frameborder="0" width="470" height="40" src="my_top_title.action"></iframe> 
						</td>
						<td></td>
					</tr>
					<tr>
						<td width="10"> </td>

						<td width="178" height="450" align="center" background="${pageContext.request.contextPath}/resources/images/bg_left_rect.jpg"
						style="padding-top: 2px;">

							<!-- 메뉴 ------------------------------------------------------------------------------------------------ -->
							
							<div class="boardTitle" align="left">
								Board
								<img src="${resourcePath }/img/admin/pic/bar.jpg"/>
								<span id="menuData" ></span>
							</div>
							
							

						</td>
						<td width="480" height="450" background="${pageContext.request.contextPath}/resources/images/bg_center_rect.jpg">
							<center>
							<!-- 오른쪽 내용 부분 ----------------------------------------------------------- -->
							<iframe frameborder="0" width="470" height="430" id="iframePic" src="<%=cp %>/cy/board/my_r_board_page.action?folderName=${encodedFolderName }"></iframe>
							<!-- ---------------------------------------------------------------------------- -->
							</center>
						</td>
						<!-- 오른쪽 메뉴 부분 ----------------------------------------------------------- -->
						<!-- 오른쪽 메뉴 부분 이걸 for문 돌려서 메뉴 선택한거 다보여주게 하기 ---------------------------------------------- -->
						<td valign="top" style="padding-top: 20px;">
						<!-- Setting menu beginning -->
							<span id="rightMenu"></span>
						<!-- Setting menu ending -->	
						</td>
						<!-- 오른쪽 메뉴 부분 ----------------------------------------------------------- -->
					</tr>

					<tr>
						<td ></td>
					</tr>
				</table>
				<!-- ------------------------ -->
			</td>
		</tr>
	</table>
	<!-- ------------------------ -->

<div class="modal-container">
	<div class="modal">
		<h3>폴더 수정</h3>
		<p id="oriFolder" class="oriFolder"></p>
		신규이름 : <input type="text" name="newFolderName" id="newFolderName" style="width: 150px;">
		<p>공개설정</p>
		<input type="radio" name="setType" id="all" value="2">전체공개<br/>
		<input type="radio" name="setType" id="member" value="1">일촌공개<br/>
		<input type="radio" name="setType" id="secret" value="0">비공개<br/>
		<div align="center"><input type="button" name="sendBtn" id="sendBtn" value="변경하기" onclick="editFolder_ok();"/><input type="button" name="sendBtn" id="sendBtn" value="취소" onclick="editFolder_no();"/></div>
		<input type="hidden" name="num" id="num" value=""/>
		<input type="hidden" name="oldFolderName" id="oldFolderName" value=""/>
	</div>
</div>

 </body>

 </form>

</html>