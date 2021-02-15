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
<head><link rel="stylesheet" href="${resourcePath }/img${userFontCss}"/>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
audio:hover, audio:focus, audio:active
{
-webkit-box-shadow: 15px 15px 20px rgba(0,0, 0, 0.4);
-moz-box-shadow: 15px 15px 20px rgba(0,0, 0, 0.4);
box-shadow: 15px 15px 20px rgba(0,0, 0, 0.4);
-webkit-transform: scale(1.05);
-moz-transform: scale(1.05);
transform: scale(1.05);
}
audio
{
-webkit-transition:all 0.5s linear;
-moz-transition:all 0.5s linear;
-o-transition:all 0.5s linear;
transition:all 0.5s linear;
-moz-box-shadow: 2px 2px 4px 0px #006773;
-webkit-box-shadow:  2px 2px 4px 0px #006773;
box-shadow: 2px 2px 4px 0px #006773;
-moz-border-radius:7px 7px 7px 7px ;
-webkit-border-radius:7px 7px 7px 7px ;
border-radius:7px 7px 7px 7px ;
}
</style>
<script type="text/javascript">

function nextPlay(){
	document.getElementById('audio_player').src = "${resoucePath }/img/admin/music/${list[1]}"; 

	var media = document.getElementById('audio_player');

	media.currentTime = 0;

	media.play();
}
</script>
</head>
<body>
<iframe src="${resoucePath }/img/admin/music/s.mp3" allow="autoplay" id="audio" style="display:none"></iframe>
<audio autoplay controls id="audio_player" onended="nextPlay();"> 
<source src="${resoucePath }/img/admin/music/1${list[0]}" type="audio/mp3">
</audio>
</body>
</html>
