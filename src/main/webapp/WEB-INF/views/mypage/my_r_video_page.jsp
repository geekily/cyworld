<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String pathUserId=(String)request.getAttribute("pathUserId");
%>
<%
%>
<!DOCTYPE html>
<html>
<head><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
a:link {text-decoration: none; color: black;}
a:visited {text-decoration: none; color: black;}
a:active {text-decoration: none; color: grey;}
a:hover {text-decoration: underline; color: red;}
 	
body{
	scrollbar-face-color: #FFFFFF;
	 scrollbar-highlight-color: #DBDBDB;
	 scrollbar-3dlight-color: #FFFFFF;
	 scrollbar-shadow-color: #9C92FF;
	 scrollbar-darkshadow-color: #FFFFFF;
	 scrollbar-track-color: #FFFFFF;
	 scrollbar-arrow-color: #9C92FF
}

/* .video{
	width: 100%; 
    height: 768px; 
    overflow: hidden; 
    display: flex; 
    justify-content: center; 
    align-items: center;
  	}
  
video[poster]{ 
    height:100%;
    width:100%;
    } */

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">

	function moving(folderNameToMove,userId,num){
	
		location.replace('moving_ok.action?userId='+userId+'&num='+num+'&folderName='+folderNameToMove);
	}
	

	//댓글 아작스------------------------------------------------------------------------------------------------------------------------------------

  $(document).ready(function(){
		 $("#sendButton0").click(function(){
			 var params = "replierId=" + $("#replierId0").val() + "&content=" + $("#content0").val() + "&userId=" + $("#userId0").val()
			 + "&videoNum=" + $("#videoNum0").val()+ "&replierName=" + $("#replierName0").val() + "&index=" + $("#index0").val();
			 
			 $.ajax({
				 type:"POST",
				 url:"<%=cp%>/cy/video/insertReple.action",
				 data:params,
				 success:function(args){
					 	$("#listData0").html(args);
					 	$("#content0").val("");
					 	$("#content0").focus();
				 },
				 beforeSend:showRequest(0),
				 error:function(e){
					 alert(e.responseText);
				 }			 
			 });		 		 
		 });
		 
		 $("#sendButton1").click(function(){
			 var params = "replierId=" + $("#replierId1").val() + "&content=" + $("#content1").val() + "&userId=" + $("#userId1").val()
			 + "&videoNum=" + $("#videoNum1").val()+ "&replierName=" + $("#replierName1").val() + "&index=" + $("#index1").val();
			 
			 $.ajax({
				 type:"POST",
				 url:"<%=cp%>/cy/video/insertReple.action",
				 data:params,
				 success:function(args){
					 	$("#listData1").html(args);
					 	$("#content1").val("");
					 	$("#content1").focus();
				 },
				 beforeSend:showRequest(1),
				 error:function(e){
					 alert(e.responseText);

				 }			 
			 });		 		 
		 });
		 
		 $("#sendButton2").click(function(){
			 var params = "replierId=" + $("#replierId2").val() + "&content=" + $("#content2").val() + "&userId=" + $("#userId2").val()
			 + "&videoNum=" + $("#videoNum2").val()+ "&replierName=" + $("#replierName2").val() + "&index=" + $("#index2").val();
			 
			 $.ajax({
				 type:"POST",
				 url:"<%=cp%>/cy/video/insertReple.action",
				 data:params,
				 success:function(args){
					 	$("#listData2").html(args);
					 	$("#content2").val("");
					 	$("#content2").focus();
				 },
				 beforeSend:showRequest(2),
				 error:function(e){
					 alert(e.responseText);
				 }			 
			 });		 		 
		 });
  });

  function showRequest(index){
	
	  var content = $.trim($("#content"+index).val());
  
	  if(!content){
		  swal("댓글을 입력하세요.", "", "<%=cp %>/resources/images/cy_logo.png");
	  	  $("#content"+index).focus();
	  	  return true;
	  }
	  
	  if(content.length>200){
		  swal("내용은 200자까지 입력할 수 있습니다.", "", "<%=cp %>/resources/images/cy_logo.png");
		  $("#content"+index).focus();
		  return true;
	  }
	  
	  return false;
  }

  function listPage(page,value1,value2,value3){
	  
	  var url = "<%=cp%>/cy/video/listReple.action";
	  
	  
		  $.post(url,{pageNum:page,userId:value1,videoNum:value2,index:value3},function(args){
			  	$("#listData"+value3).html(args); 
		  });
		  
			  $("#listData"+value3).show();
			  
		var btn1 = document.getElementById("btnReple1"+value3);
		var btn2 = document.getElementById("btnReple2"+value3);
				
		btn1.style.display="none";
		btn2.style.display="block";
	  
  }
  
	function closeReple(index) {
		
		$("#listData"+index).hide();
		
		var btn1 = document.getElementById("btnReple1"+index);
		var btn2 = document.getElementById("btnReple2"+index);

		btn2.style.display="none";
		btn1.style.display="block";
	}
  
	function deleteOneRepleData(value1,value2,value3,value4){

		var url = "<%=cp%>/cy/video/deleteOneRepleData.action";
		
		$.post(url,{userId:value1,num:value2,videoNum:value3,index:value4},function(args){
			$("#listData"+value4).html(args);
		});
	}
	 
	function gfg_Run() { 
		document.addEventListener("contextmenu",
			function(e) { 
				if (e.target.nodeName === "video") { 
					e.preventDefault(); 
				} 
			}, false); 
     } 
	 
	window.onload=function(){	

		document.addEventListener("contextmenu",
			function(e) { 
				if (e.target.nodeName === "video") { 
					e.preventDefault(); 
				} 
			}, false); 
		 
		 for(var i=0;i<${list.size()};i++){
			 $('#video'+i).attr('draggable', false);	 
		 }
	 }
</script>
</head>
<body>
<form name="poto">
 	<c:if test="${totalFolderData==0 }">
 	<table border="0" width="420" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td align="right">
				<br/>
			</td>
		</tr>
	</table>

	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0">
		<tr height="5px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>

	<!-- 비디오 ----------------------------------------------------------------------------------------------- -->
	
	
	<table border="0" width="420" height="300" cellpadding="1" cellspacing="1" align="center">
		<tr valign="middle">
			<td align="center">
				<font  style="font-size:10pt;font-weight: bold;">
					앨범을 생성한 후 선택해주세요.
				</font>
			</td>
		</tr>
	</table>
 	</c:if>

	<c:if test="${totalFolderData!=0 }">
	<table border="0" width="420" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td align="right">
				<input type="button" value="비디오 올리기" style="height: 25px; width: 82px; font-size: 8pt;" onclick="javascript:location.href='write.action?folderName=${folderName }';"/>				
			</td>
		</tr>
	</table>

	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0">
		<tr height="5px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>

	<!-- 비디오 ----------------------------------------------------------------------------------------------- -->
	
	<c:if test="${totalData==0 }">
	<table border="0" width="420" height="300" cellpadding="1" cellspacing="1" align="center">
		<tr valign="middle">
			<td align="center">
				<font  style="font-size:10pt;font-weight: bold;">
					등록된 비디오가 없습니다.
				</font>
			</td>
		</tr>
	</table>
	</c:if>
	
	<c:forEach var="dto" items="${list }" varStatus="status">
	<c:set var="index" value="${status.index }"/>
	
	<table border="0" bgcolor="#EBEBEB" width="420" cellpadding="1" cellspacing="1" align="center">
		<tr>
			<td>
				<a name="n5"></a>
				<font  style="font-size:9pt;padding-left: 3px;"><b>${dto.listNum }. ${dto.subject }</b></font>
			</td>
		</tr>
	</table>
	<table border="0" bgcolor="#F6F6F6" align="center" width="420" cellpadding="1" cellspacing="1">
		<tr>
			<td width="100">  
				<font  style="font-size:9pt;padding-left: 3px;">${dto.userName }</font>
			</td>
			<td align="right">  
				<font  style="font-size:9pt;padding-right: 3px;">스크랩 : ${dto.scrap }</font>
			</td>
		</tr>
	</table>&nbsp;
	
	<table border="0" align="center" width="420" cellpadding="1" cellspacing="1" style="table-layout: fixed;word-break:break-all;">
		<tr>
			<td align="center">  
				<!-- 비디오 바꾸기 -->
					<video src="/img/<%=pathUserId %>/video/${folderName }/${dto.saveFileName }" width="400" controls>
					</video>
				<!-- --------- -->
									
			</td>
		</tr>
		<tr>
			<td style="padding-left: 8px;padding-right: 4px;">  
				<font>
					${dto.content }
				</font>
			</td>
		</tr>
	</table><br/>
	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0">
		<tr height="2">
			<td align="right" colspan="3" bgcolor="#EBEBEB"> 
			</td>
		</tr>
		<tr bgcolor="#F6F6F6" height="30">
			<td width="5"></td>
			<td width="50">
				<input type="button" id="btnReple1${index }" value="댓글 보기" style="height: 20px; width: 60px; font-size: 8pt;" onclick="javascript:listPage(1,'${dto.userId}',${dto.num },${index })"/>
				<input type="button" id="btnReple2${index }" value="댓글 닫기" style="display: none;height: 20px; width: 60px; font-size: 8pt;" onclick="javascript:closeReple(${index })"/>
			</td>
			<td align="right" style="padding-right: 3px;">
				<font>
					<a onclick="javascript:location.href='update.action?userId=${dto.userId}&num=${dto.num }';">수정</a> | 
					<a onclick="window.open('moving.action?userId=${dto.userId}&num=${dto.num }&folderName=${dto.folderName }','window_moving','width=300,height=300,left=300,top=300,location=no,status=no,scrollbars=no');" target="_blank">이동</a> | 
					<a onclick="javascript:location.href='delete.action?userId=${dto.userId}&num=${dto.num }&saveFileName=${dto.saveFileName }&folderName=${dto.folderName }';">삭제</a>
				</font>
			</td>
		</tr>
	</table>
				
	<!-- 댓글-------------------------------------------------------------------------------------------------------  -->

	<span id="listData${index }"></span>
				
	<table border="0" width="420" bgcolor="#EBEBEB" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" style="padding-left: 6px;padding-top: 4px;padding-bottom: 4px;"> 
				<font>
					댓글
				</font>
				<input type="hidden" id="userId${index }" value="${dto.userId }"/>
				<input type="hidden" id="videoNum${index }" value="${dto.num }"/>
				<input type="hidden" id="replierId${index }" value="${sessionScope.session.userId }"/>
				<input type="hidden" id="replierName${index }" value="${sessionScope.session.userName }"/>
				<input type="hidden" id="index${index }" value="${index }"/>
				
				<input type="text" id="content${index }" maxlength="200" style="width: 76%;"/>
				<input type="button" id="sendButton${index }" style="height: 22px; width: 50px; font-size: 8pt;" value="확인"/>
			</td>
		</tr>
	</table><br/>
	</c:forEach>
	
	<table border="0" width="420" cellpadding="1" cellspacing="1" align="center">
		<tr>
			<td align="center">
				<a name="n5"></a>
				<font><b>${pageIndexList }</b></font>
			</td>
		</tr>
	</table>
	</c:if>

	<table border="0" width="420" align="center" cellpadding="0" cellspacing="0">
		<tr height="5px"></tr><tr height="2"><td align="right" colspan="3" bgcolor="#EBEBEB"></td></tr><tr height="10px"></tr>
	</table>

	<br/><br/><br/>
</form>
</body>
</html>