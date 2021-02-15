<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("UTF-8");
%>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
	
<%@ include file="../shop/header.jsp"%>
<style>

.able {
	background:url("<%=cp %>/resources/images/able1.png") no-repeat;
}

.able:hover {
	background:url("<%=cp %>/resources/images/able2.png") no-repeat;
}

</style>

<script type="text/javascript">
	
	function enterkey() {
	
   		if (window.event.keyCode == 13) {

         // 엔터키가 눌렸을 때 실행할 내용
        login();
    }
}

	function login(){
		var str = document.myForm.userId.value;
		if(str == ""){
			alert("아이디 입력!");
			document.myForm.userId.focus();
			return;
		}
		str = document.myForm.userPw.value;
		if(str == ""){
			alert("비밀번호 입력!");
			document.myForm.userPw.focus();
			return;
		}
		document.myForm.action = "login.action";
		document.myForm.submit();
		
	}
	
	$(document).ready(function() {
		if('${alert}' != 0){
			alert("로그인 정보가 맞지 않습니다.");
		}
		$("#logo").attr("src", "<%=cp %>/resources/images/logo.png");
		$("#logo").attr(
		"style", "padding-top:10px; padding-left:45px; width: 254; height:55;"
		);
	});
	
</script>

<div id="container">
	<form action="" method="post" class="form-horizontal" name="myForm">
	<ul class="path">
			<li><a href="<%=cp%>/cy/index.action">
			홈</a></li>
			<li class="current"><a href="<%=cp%>/cy/signin.action">
			로그인</a></li>
	</ul>

	<!-- contents -->
	<div id="contents">

	<div class="titWrap">
		<h2 class="pTit">로그인</h2> 
	</div>

	<div class="loginCont">

	<!-- 회원 로그인 -->
	<div class="flL">
		<div class="titWrap">
			<h3 class="tit">회원 로그인</h3>
		</div>
		
		<div class="inputWrap">
			<input type="text" class="lgn placeholder required" name="userId"
			 style="width:308px" placeholder="아이디를 입력해주세요." title="아이디를 입력해주세요." aria-required="true">
			<input type="password" onkeypress="enterkey();" class="lgn placeholder required" name="userPw" 
			style="width:308px" placeholder="비밀번호를 입력해주세요." title="비밀번호를 입력해주세요." aria-required="true">

			<button type="button" class="btnLgn" onClick="login();">
				로그인</button>
		</div>
		
	<div>
		<span class="chkW mgR20"><input type="checkbox" id="gn_lild" name="gn_lild" value="EHMP" checked=""><label for="gn_lild">자동 로그인</label></span>
		<span class="chkW"><input type="checkbox" id="mrkt_rcv_yn" name="mrkt_rcv_yn" value="Y" checked=""><label for="mrkt_rcv_yn">혜택 알림받기</label></span>
	
		<div class="tooltipWrap">
			<span class="icon tooltipBtn"><span>안내보기</span></span>
			<div class="tooltipLyr" style="left:0;">
			<div class="tooltipTit">혜택 알림받기</div>
			<div class="tooltipCont">마케팅 수신동의하시면 이벤트 특가정보 및 공지사항 등의 알림을 받을 수 있습니다. 
			알림해지는 로그인 후 마이페이지에서 해지하실 수 있습니다.
			</div></div>
		</div>
	</div>

	<div class="list01 mgT20">
		<ul>
			<li>아직 싸이월드 회원이 아니신가요? <a href="<%=cp%>/cy/createUser.action" class="btnF flR">회원가입</a></li>
			<li>회원 아이디 또는 비밀번호를 잊으셨나요? <a href="javascript:alert('서비스 준비중입니다.');" class="btnF flR">아이디/비밀번호찾기</a></li>
		</ul>
	</div>

</div>
<div class="flR" style="padding-left:10px;">
	<div class="able">
		<a href="<%=cp%>/cy/createUser.action">
		<img src="<%=cp %>/resources/images/able1.png"
			style="opacity:0;"
		/></a>
	</div>
</div>	
</div>

	<div class="btnWrap otherAcc" style="border:none;">
		<a href="javascript:alert('준비중입니다.');" class="btnLogin naver"><img src="http://www.goobnemall.com/static-root/resources/pc/img/member/btn_login_naver.png" alt="네이버 계정으로 로그인"></a>
		<a href="javascript:alert('준비중입니다.');" class="btnLogin kakao"><img src="http://www.goobnemall.com/static-root/resources/pc/img/member/btn_login_kakao.png" alt="카카오 계정으로 로그인"></a>
	</div>
<br/><br/>
		</div>
	</form>
</div>

<%@ include file="../shop/footer.jsp"%>
