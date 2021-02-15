<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String searchKey = request.getParameter("searchKey");
	String searchValue = request.getParameter("searchValue");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>싸이월드 선물가게</title>

<!-- 메인 폰트, 데이터 담는 틀 UI 정보 다 들어가 있음 -->
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/main.css">
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/common.css">
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/jquery.ui.css">
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/jquery.ui.gnmall.css">
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/contents.css">
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/swiper.min.css">
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/easy-autocomplete.css">
<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/easy-autocomplete.themes.css">
<link rel="icon" type="image/png" href="<%=cp %>/resources/images/favicon.ico" />

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script type="text/javascript" src="<%=cp %>/resources/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="<%=cp %>/resources/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=cp %>/resources/js/jquery.ui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- 메세지박스 CSS -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<!-- 이미지 슬라이딩 기능 포함-->
<script type="text/javascript" src="<%=cp %>/resources/js/style.js"></script>
<script type="text/javascript" src="<%=cp %>/resources/js/slides.jquery.js"></script>
<script type="text/javascript" src="<%=cp %>/resources/js/swiper.min.js"></script>

<script type="text/javascript">

	// boolean 전역변수
	var boolCheck = false;
	var userId;
	function Enter_Check(){
		if(event.keyCode == 13){
			search();
		}
	}

	
	// 검색한 유저페이지로 이동
	function search() {
		f = document.myForm2;
		userKeyword = f.userKeyword.value;
		userKeyword = userKeyword.trim();
		
		// 데이터 유결성 검사
		if (!userKeyword) {
			swal('검색어창이 비여있어요.',
					'검색어를 입력후 사용해주세요.',
					'warning');

			return;
		}
		
		
		// 존재하는 유저를 검색하는지 체크
		if (!boolCheck){

			swal('404 NOT FOUND', 
					'검색한 아이템이 존재하지 않습니다.', 
					'error');
			
			return;
		}
		
	}
	
	$(document).ready(function() {
		$("#logo").attr("src", "<%=cp %>/resources/images/logo_gift.png");
		$("#logo").attr(
		"style", "padding-top: 13px; padding-bottom: 18px; width: 250; height:70;"
		);
		//선물가게일 경우 선물가게 메인으로 href 변경
		$("#logourl").attr("href", "<%=cp%>/cy/shop/product/list.action");		
	});

	
	// ajax 검색시 사람 불러오기
	function sendKeyword() {
		
		var f = document.myForm2;
		var userKeyword = encodeURIComponent(f.userKeyword.value);
		var action = "./doSearch";
		// alert(action);
		
		// start Ajax
		$.get(
			action,
			{
				userKeyword:userKeyword
			},
			function(data) {
				
				// 배열화
				data = data.split('/');
				var num = data[0].split(',');
				var itemName = data[1].split(',');

				var type = data[2].split(',');
				var html = "";
				// 검색어가 있는지 없는지
				if(userKeyword == decodeURIComponent(itemName[0]) || userKeyword == type[0]) {
					boolCheck = true;
				} else {
					boolCheck = false;
				}
				
				if(itemName.length >= 1) {		
					
					html += "<datalist id=\"datas\" >";
					
					for(var i = 0; i<itemName.length-1; i++){
						
						num[i] = num[i]
						itemName[i] = decodeURIComponent(itemName[i]);
						type[i] = decodeURIComponent(type[i]);
						
						html += "<option value=\"" + itemName[i] + "\" label=\"상품타입 : " + type[i] + "\"></option>";
					}
					
					html += "</datalist>";
					$('.result').html(html);
				}
				
			},
			'html'
		); // end Ajax
		
	}

	function onInput() {
		var val = document.getElementById("top_keyword").value;
		var opts = document.getElementById('datas').childNodes;
		
		var action = "./doGetItemNum";
		
		
		for (var i = 0; i < opts.length; i++) {
			if (opts[i].value === val) {
				$.get(
						action,
		    			{
		    				userKeyword:opts[i].value
		    			},
		    			function(data) {
		    				// alert(data);
		    				location.href = "<%=cp%>/cy/shop/product/listdetail.action?num=" + data;
		    			}
		    		);
		        break;
		    }
		}
	}
	
</script>

</head>
<body>

<div id="header">
	<div class="headerCont LGN_TP_GUEST clB">
		<h1>
			<c:if test="${session.getUserId()!=null }">
			<a id="logourl" href="<%=cp%>/cy/index.action?loginOk=ok">
				<img id="logo" src="<%=cp %>/resources/images/logo.png" 
				style="padding-top:10px; padding-left:45px;	height:45;"
				alt="싸이월드 메인">
			</a>
			</c:if>
			<c:if test="${session.getUserId()==null }">
			<a id="logourl" href="<%=cp%>/cy/index.action">
				<img id="logo" src="<%=cp %>/resources/images/logo.png" 
				style="padding-top:10px; padding-left:45px;	height:45;"
				alt="싸이월드 메인">
			</a>
			</c:if>
		</h1>
	<div id="util">
		<ul>
		<c:choose>
			<c:when test="${session.getUserId()==null }">
			<li><a href="<%=cp%>/cy/login.action">로그인</a></li>
			</c:when>
			<c:otherwise>
			<li><a href="<%=cp%>/cy/logout.action">로그아웃</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${session.getUserId()==null }">
			<li><a href="<%=cp%>/cy/index.action">싸이월드</a></li>
			</c:when>
			<c:otherwise>
			<li><a href="<%=cp%>/cy/index.action?loginOk=ok">싸이월드</a></li>
			</c:otherwise>
		</c:choose>
			<li><a href="<%=cp%>/cy/shop/product/list.action">선물가게</a></li>				
		<c:choose>
			<c:when test="${session.getUserId()==null }">
			<li><a href="<%=cp%>/cy/createUser.action">회원가입</a></li>
			</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${session.getUserId()!=null }">
			<li><a href="<%=cp%>/cy/shop/cart.action">장바구니</a></li>
			</c:when>
		</c:choose>
		</ul>
	</div>
	<div class="headerBanner">
		<a href="#"> 
		<img src="<%=cp %>/resources/images/sale.png"
			alt="1">
		</a>
	</div>
		
	<form id="topSearchForm" name="myForm2" method="post">
		<input type="text" name="dummy" id="dummy" style="display: none">
		<div class="srchArea">
			<div class="active srchWrap">
				<input type="text" id="top_keyword" oninput='onInput()'  autocomplete="off" name="userKeyword" onkeyup="sendKeyword();" 
					onkeydown="JavaScript:Enter_Check();" list="datas"
						class="keyword" title="검색어" value="" placeholder="검색어를 입력하세요"/>
				<button type="button" class="btnImg btnSrch" id="srchBtn"
					onclick="search();">
					<span>검색</span>
				</button>

				<div class="result"></div>         
            		
			</div>
		</div>
	</form>
</div>

<div id="gnb">
	<div class="inner">
		
	<ul class="gnb">
		<li class=""><a href="javascript:location.href='<%=cp%>/cy/shop/product/list.action?type=배경음악';"
			class="gnbDep1">배경음악
			</a></li>
		<li class=""><a href="javascript:location.href='<%=cp%>/cy/shop/product/list.action?type=스킨';"
			class="gnbDep1">스킨
			</a></li>
		<li class=""><a href="javascript:location.href='<%=cp%>/cy/shop/product/list.action?type=글꼴';"
			class="gnbDep1">글꼴
			</a></li>
		<li class=""><a href="javascript:location.href='<%=cp%>/cy/shop/product/list.action?type=미니미';"
			class="gnbDep1">미니미
			</a></li>
		<li class=""><a href="javascript:location.href='<%=cp%>/cy/shop/product/list.action?type=스토리룸';"
			class="gnbDep1">스토리룸
			</a></li>
	</ul><div class="bgDep2"></div>
	</div>
</div>	
</div>