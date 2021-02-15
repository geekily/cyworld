<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
%>
<head>
<link rel="stylesheet" href="${resourcePath }/img${fontCss}"/><link rel="stylesheet" href="${resourcePath }/img${fontCss}"/>
<script type="text/javascript" src="/cyworld/resources/js/ajaxUtil2.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<style type="text/css">

input.upload_text {/*읽기전용 인풋텍스트*/
	float:left;
	width:230px;/* 버튼 포함 전체 가로 길이*/
	height:19px;
	line-height:19px;
	padding:0 3px;
	border:1px solid #bbb;
}

div.upload-btn_wrap input.input_file {/*파일찾기 폼 투명하게*/
	position:absolute;
	top:0;
	right:0;
	cursor:pointer;
	opacity:0;
	filter:alpha(opacity=0);
	-ms-filter:"alpha(opacity=0)";
	-moz-opacity:0;
}
div.upload-btn_wrap {/*버튼테두리 감싼 div*/
	overflow:hidden;
	position:relative;
	float:left;
	width:70px;/*width, height 값은 button(찾아보기)값과 같아야함 */
	height:21px;
	padding-left:3px;
}
div.upload-btn_wrap button {/*버튼 div*/
	width:70px;
	height:21px;
	font-weight:bold;
	background:#333;
	border:1px solid #333;
	color:#fff;
}
</style>

<script type="text/javascript">

$(function(){
	$('.upload_text').val('.jpg , .png , .bmp 파일만 업로드 할 수 있습니다.');
	$('.input_file').change(function(){
		var i = $(this).val();
		$('.upload_text').val(i);
	});
});

function send(){
	var str = document.myForm.fileUpload.value;
	if(str==""){
		swal("파일을 선택해주세요.", "");
		return;
	}
	document.myForm.submit();
}

</script>

</head>
<form action="my_pic_upload_ok.action" method="post" name="myForm" enctype="multipart/form-data"> 
<!--input box-->
<div align="center">
<img alt="" src="<%=cp %>/resources/images/cy_logo.png" style="width: 30%;"><br/>
</div>
<input type="text" class="upload_text" readonly="readonly">
<!--button-->
	<div class="upload-btn_wrap">
	  <button type="button" title="파일찾기">
	   <span style="font-size: 8pt;">파일찾기</span>  
	  </button>
	  <input type="file" name ="fileUpload" class="input_file" title="파일찾기">
	  <button type="button" title="업로드"><span style="font-size: 8pt;">업로드</span></button>
	</div>
	<div class="upload-btn_wrap">
	  <button type="button" title="업로드" onclick="send();"><span style="font-size: 8pt;">업로드</span></button>
	</div>  
</form>

