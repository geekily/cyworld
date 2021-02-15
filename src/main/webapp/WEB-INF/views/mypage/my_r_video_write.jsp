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
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script language="javascript">

	function upload_ok() {

	f = document.upload;
	
	str = f.subject.value;
	str = str.trim();
	if(!str){
		swal("제목을 입력하세요.", "", "<%=cp %>/resources/images/cy_logo.png");
		f.subject.focus();
	return;
	}
	f.subject.value = str;

	str = f.upload.value;
	str = str.trim();
	if(!str){
		swal("등록할 비디오를 고르세요.", "", "<%=cp %>/resources/images/cy_logo.png");
		f.upload.focus();
	return;
	}

	f.action="<%=cp%>/cy/video/my_write_ok.action";
	f.submit();
}
	
	
	function extCheck(obj){
		
		var fileLastIndexOfDot=obj.value.lastIndexOf('.');
		var fileExt=obj.value.substring(fileLastIndexOfDot+1,obj.length);
		fileExt=fileExt.toLowerCase();
		
		var allowedExt=new Array();
		allowedExt=['mp4','avi','mkv','flv','wmv', 'mov'];
		
		if(allowedExt.indexOf(fileExt)==-1){
			swal("", "동영상 파일만 선택할 수 있습니다.", "<%=cp %>/resources/images/cy_logo.png");
			
			var file = document.getElementById("uploadId");
			file.value="";
		}
	}

</script>


 <body>

 <form action="" name="upload" method="post" enctype="multipart/form-data">


	<table align="center" border="0" bgcolor="#EBEBEB" width="420" cellpadding="1" cellspacing="1">
		<tr bgcolor="#FFFFFF" height="30px">
			<td colspan="2" align="center" bgcolor="#EBEBEB">
				<font><b>비디오 업로드</b></font>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF" align="center" height="30px">
			<td width="60" bgcolor="#F6F6F6"><font>제목</font></td>
			<td width="360" align="center">
				<input type="text" name="subject" style="width: 96%"/>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF" style="border-bottom: none;">
			<td colspan="2" style="border-bottom: none;">
				<font  style="font-size:9pt;padding-left: 6px;">비디오 설명</font><br/>
				<span style="padding-left: 5px;padding-right: 5px;">
					<textarea name="content" rows="18" cols="55" style="resize: none; width:96%; height: 80%; margin-top: 5px;"></textarea>
				</span>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF" height="35px">
			<td align="center" bgcolor="#F6F6F6"><font>파일</font></td>
			<td style="padding-left: 5px;">
				<input type="file" id="uploadId" name="upload" accept="video/*" size="35" onchange="extCheck(this)"
				style="font-size: 9pt;"/>
			</td>
		</tr>		
	</table>
	<table align="center" border="0" cellpadding="0" cellspacing="0" width="420">
		<tr height="40px">
			<td align="right">  
				<input type="hidden" name="folderName" value="${folderName }"/>
				<input type="button" name="files_save" value="비디오 올리기" onclick="upload_ok()" 
				style="height: 25px; width: 80px; font-size: 8pt;"/>
				<input type="button" value="취소" onclick="javascript:location.href='list.action?folderName=${folderName}';"
				style="height: 25px; width: 75px; font-size: 8pt;"/>
			</td>
		</tr>
	</table>

</form>

 </body>
</html>