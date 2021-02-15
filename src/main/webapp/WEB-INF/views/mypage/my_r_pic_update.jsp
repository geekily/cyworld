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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">

	function update_ok() {

		f = document.update;
	
		str = f.subject.value;
		str = str.trim();
		if(!str){
			swal("제목을 입력하세요.", "", "<%=cp %>/resources/images/cy_logo.png");
			f.subject.focus();
			return;
		}
		f.subject.value = str;

		f.action="<%=cp%>/cy/picture/update_ok.action";
		f.submit();
	}
	
	
	function updatePicture() {
		
		var btn1 = document.getElementById("updateBTN1");
		var btn2 = document.getElementById("updateBTN2");

		btn1.style.display="none";
		btn2.style.display="block";
	}
	
	function extCheck(obj){
		
		var fileLastIndexOfDot=obj.value.lastIndexOf('.');
		var fileExt=obj.value.substring(fileLastIndexOfDot+1,obj.length);
		fileExt=fileExt.toLowerCase();
		
		var allowedExt=new Array();
		allowedExt=['jpg','gif','png','jpeg','bmp'];
		
		if(allowedExt.indexOf(fileExt)==-1){
			swal("", "이미지 파일만 선택할 수 있습니다.", "<%=cp %>/resources/images/cy_logo.png");
			
			var file = document.getElementById("uploadId");
			file.value="";
		}
	}
	
	window.onload=function(){	

		document.addEventListener("contextmenu",
			function(e) { 
				if (e.target.nodeName === "IMG") { 
					e.preventDefault(); 
				} 
			}, false); 
		 
		$('#image').attr('draggable', false);	 	
	 }
</script>


 <body>

 <form action="" name="update" method="post" enctype="multipart/form-data">


	<table align="center" border="0" bgcolor="#EBEBEB" width="420" cellpadding="1" cellspacing="1">
		<tr bgcolor="#FFFFFF" height="30px">
			<td colspan="2" align="center" bgcolor="#EBEBEB">
				<font><b>게시글 수정</b></font>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" bgcolor="#FFFFFF">  
				<img src="/img/${dto.userId }/picture/${dto.folderName }/${dto.saveFileName }" width="400" height="300" border="0" alt="no found" 
				id="image" style="padding-top: 7px;padding-bottom: 5px;">
			</td>
		</tr>
		<tr bgcolor="#FFFFFF" align="center" height="30px">
			<td width="60" bgcolor="#F6F6F6"><font>제목</font></td>
			<td width="360" align="center">
				<input type="text" name="subject" value="${dto.subject }" style="width: 95%;"/>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF" style="border-bottom: none;">
			<td colspan="2" style="border-bottom: none;">
				<font  style="font-size:9pt;padding-left: 6px;">사진설명</font><br/>
				<span style="padding-left: 5px;padding-right: 5px;">
					<textarea name="content" rows="14" cols="55" style="resize: none;width: 96%;">${dto.content }</textarea>
				</span>
			</td>
		</tr>
	</table>
	<table align="center" id="updateBTN1" border="0" width="434" cellpadding="1" cellspacing="1" style="border: none;">
		<tr bgcolor="#FFFFFF" height="35px">
			<td>
				<input type="button" value="이미지 수정" onclick="updatePicture()" style="height: 25px; width: 75px; font-size: 8pt;"/>
			</td>
		</tr>		
	</table>
	
	<table align="center" id="updateBTN2" border="0" width="434" cellpadding="0" cellspacing="1" style="display: none;">
		<tr bgcolor="#FFFFFF" height="35px">
			<td align="center" bgcolor="#F6F6F6" width="50px"><font>파일</font></td>
			<td style="padding-left: 5px;">
				<input type="file" id="uploadId" name="upload" accept="image/*" size="35" onchange="extCheck(this)"
				style="font-size: 9pt;"/>
			</td>
		</tr>	
	</table>
	<br/>
	<table align="center" border="0" cellpadding="0" cellspacing="0" width="420" >
		<tr>
			<td align="right">  
				<input type="hidden" name="num" value="${dto.num }">
				<input type="hidden" name="userId" value="${dto.userId }">
				<input type="hidden" name="saveFileName" value="${dto.saveFileName }">
				<input type="hidden" name="folderName" value="${dto.folderName }">
				
				<input type="button" value="수정하기" onclick="update_ok()"/>
				<input type="button" value="취소" onclick="javascript:location.href='list.action?folderName=${dto.folderName }';"/>
			</td>
		</tr>
	</table>

</form>

 </body>
</html>