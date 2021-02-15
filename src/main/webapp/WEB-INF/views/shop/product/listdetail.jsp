<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("UTF-8");

	String msg = request.getParameter("msg");
%>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<%@ include file="/WEB-INF/views/shop/header_item.jsp" %>
<!-- Ajax -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- 메세지박스 CSS -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<style>
	
.able {
	background:url("<%=cp %>/resources/images/able1.png") no-repeat; 
	background-size: 300px;
	width:300px;
	height:230px;
}
	
.able:hover {
	background:url("<%=cp %>/resources/images/able2.png") no-repeat;
	background-size: 300px; 
	width:300px;
	height:230px;
}
	
</style>

<script type="text/javascript">

	var msgCheck = false;
	

	window.onload = function () {
		
		if(<%=msg%> == "1" && msgCheck == false){
			swal('이미 구매한 아이템입니다.',
					'',
					'warning');
			msgCheck = true;
			return;
		}
	}


	function sendIt_Cart(itemName, itemPrice, type, saveFileName, originalFileName, num) {
		
		if('${session.getUserId() }' == "") {
			swal('로그인이 필요합니다', '로그인 창으로 이동합니다.', 'warning', {
        		closeOnClickOutside: false,
            	closeOnEsc: false,
           		buttons : {
                confirm : {
                	text : '확인',
                	value : true,
                	className : 'btn btn-outline-primary'
                	}
        	}
        	}).then((result) => {
        		if(result){
        			location.href='../../login.action';
        			}
        		});
        	return;
        	}
		
		// Test
		// alert('${session.getUserId() }');
		// alert(itemName + itemPrice + type +saveFileName + originalFileName);
		
		var action = "./doInsertCart";
		
		// Ajax Start
		$.get(
			action,
			{
				userId:'${session.getUserId() }',
				itemName:itemName,
				itemPrice:itemPrice,
				type:type,
				saveFileName:saveFileName,
				originalFileName:originalFileName,
				num:num
			},
			function(data) {
				// alert(data)
				if(data != 'true'){
					swal('이미 추가된 ' + type + '입니다.',
							'',
							'warning');
					return;
				}
				
			}
		
		);
	
	}
	
	
	function goCart(one) {
		
		var f = document.myForm;
		
		f.action = '../cart.action?userId=' + '${session.getUserId() }';
		f.submit();
		
	}
	
	
	function sendIt_Order(num) {
		
		if('${session.getUserId() }' == "") {
			swal('로그인이 필요합니다', '로그인 창으로 이동합니다.', 'warning', {
        		closeOnClickOutside: false,
            	closeOnEsc: false,
           		buttons : {
                confirm : {
                	text : '확인',
                	value : true,
                	className : 'btn btn-outline-primary'
                	}
        	}
        	}).then((result) => {
        		if(result){
        			location.href='../../login.action';
        			}
        		});
        	return;
        	}
		
		
		var f = document.myForm;
		f.action = '../order.action?userId=' + '${session.getUserId() }&totalPrice=10&num=' + num +'&buyType=buy';
		f.submit();
		
		// http://localhost:8080/cyworld/cy/shop/order.action?userId=duswhd114&totalPrice=10&num=58
				
		// http://localhost:8080/cyworld/cy/shop/cart.action?userId=duswhd114
	}
	
	
</script>

</head>
<body>
<br/>
<form action="" name="myForm" method="post" >

<div id="container">
<div id="contents">
	<div class="prdtDtlSum">
	<!-- 상단 왼쪽 -->
	<div class="prdtDtlSumLeft">

	<div class="pdImgArea">
	<div class="prdtImgWrap">
		<img src="${resourcePath }/img${dto.saveFileName }" width="300" height="300" name="img1" />
		<span class="discountWrap_thumb w350">
		<span class="discountWrap"><span class="discount">
		<strong>NEW</strong></span></span></span>
	</div><br/>
	
	<h5 style="color:black;">다른 상품들도 구경해 보세요!</h5><br/>
	
	<div class="prdtImgList">
		<ul style="width: 535px">
		<c:forEach var="dto" items="${lists }">
			<li><a href="javascript:location.href='<%=cp%>/cy/shop/product/listdetail.action?num=${dto.num}';">
				<img src="${resourcePath }/img${dto.saveFileName }" width="80" alt="${resourcePath }/img${dto.saveFileName }"
				height="80" onmouseover="fimg('${resourcePath }/img${dto.saveFileName }')" />
				</a></li>
		</c:forEach>
		</ul>	

	</div>

	</div><br/>

	<!-- 
	<div class="shareArea">
		<p class="tit">공유하기</p>
		<ul><li><a href="#" class="btnImg facebook"><span>페이스북</span></a></li>
		<li><a href="#" class="btnImg naver"><span>네이버블로그</span></a></li>
		<li><a href="#" class="btnImg kakao"><span>카카오스토리</span></a></li>
		<li><a href="#" class="btnImg url"><span>URL</span></a></li></ul>
	</div>
	 -->
	</div>

	<!-- 상단 오른쪽 -->
	<div class="prdtDtlSumRight">

	<!-- 정보 영역 -->
	<div class="pdInfoArea">
	<div class="prdtCode">
		<dl><dt>상품번호 :</dt><dd>${dto.num}</dd></dl>
	</div>
	
	<div class="prdtTitWrap">
		<p class="prdtCmnt"></p>
		<h2 class="prdtTit">[${dto.type }]&nbsp;${dto.itemName }</h2>
	</div>

	<div class="dpIconWrap">
	<span class="icoDisplay typeC">신상</span>
	</div>

	<!-- 영양분석표 -->
	<div class="nutritionWrap">
	<a href="#none" class="btnD openLyBtn">비회원 구매</a>
		<div class="nutritionLy lyWrap">
		<a href="#none" class="btnImg closeLyBtn"><span>레이어 닫기</span></a>
		
		<div class="lyTit">아직 싸이월드 회원이 아니신가요?</div>
		<div class="lyCont"><br/>
		
			<div class="able" style="padding:20px; clear:both;">
				
				<a href="<%=cp%>/cy/createUser.action">
				<img src="<%=cp %>/resources/images/able1.png"
					style="opacity:0;"
					width="300" height="230"
				/></a>
			</div>
		</div>
		</div><!-- 영양정보 끝 -->
	</div>

	<div class="prdtInfoWrap">
		<dl><dt>판매가</dt>
			<dd><span class="price"><strong>${dto.itemPrice }</strong>
				<img src="${resourcePath }/img/dotori.png" width="18" height="18" name="도토리" /></span>

	<div class="nutritionLy saleWrap lyWrap">
		<a href="#none" class="btnImg closeLyBtn"><span>레이어 닫기</span></a>
	
	</div></dd></dl>

	<dl>
		<dt>타입</dt><dd>${dto.type}</dd>
	</dl>
	<dl>
		<dt>컨텐츠 원 제작자</dt><dd>컨텐츠 내부 항목 참조</dd>
	</dl>
	<dl>
		<dt>컨텐츠 배포자</dt><dd>여승현, 임의빈, 유현종, 이예리, 이재진, 최원석</dd>
	</dl>
	<dl>
		<dt>원산지</dt><dd>
		<span class="fc01">South Korea</span></dd>
	</dl>
	</div>
	
	</div><!-- 정보영역 끝 -->
	
	<div class="pdOrderWrap ">

	<div class="selectedItem">
		<ul id="prItemList">
			<li id="pritem_001" class="prdctItmMst">
			<input type="hidden" name="itm_id" value="001">
			<input type="hidden" name="pcs_qty" value="1">
		
		<div class="itemName">
			<h6 style="color:black;">아이템명:&nbsp; ${dto.itemName }</h6></div>
		
		<div class="itemQuantity">
			<div class="itemQuant">
			<input type="text" title="수량" value="1" class="quant" name="qty" readonly/>
				 <button type="button" class="btnPlus"
				 	onclick="javascript:alert('1회 1개만 구입 가능합니다.');">
                      <span>plus</span></button>
                 <button type="button" class="btnMinus"
                 	onclick="javascript:alert('1회 1개만 구입 가능합니다.');">
                      <span>minus</span></button>
			</div>
		</div>
		
		<div class="itemPrice">
			<span class="price">
			<img src="${resourcePath }/img/dotori.png" width="18" height="18" name="도토리" />
			<strong>
				<font style="font-size:15px;">x ${dto.itemPrice }</font>
			</strong>
			</span>
		</div>
		<div class="itemDelete"></div>
			</li>
		</ul>
	</div>

	<div class="deliveryDate">
	<div class="deliDate">
		<dl><dt>환불규약</dt>
		<dd id="dlv_arrvl_dt_txt">2021.01.04 - 환불규약 제 1조 제 1항 기준</dd>
		</dl>
			<p></p>
			<p class="fc04" style="margin-top: 15px;">구입 후 7일 까지 환불이 가능합니다.<br/>
			단, 사장님이 이미 맛있는 걸 사드시고 난 다음에는 환불이 불가능합니다.<br/>
			고객님의 너른 양해 부탁 드립니다.
			</p>
	</div>

	
	</div>

	<div class="totalPrice">
	<div class="buyCount"></div>총주문상품금액&nbsp;
		<span class="price">
			<strong>${dto.itemPrice }</strong>
			<img src="${resourcePath }/img/dotori.png" width="20" height="20" name="도토리" />
		</span>

	</div>
	
	
	<!-- 2021-01-18 현종수정 -->
	<c:choose>
	<c:when test="${dto.num >= 103}">
	
	</c:when>
	<c:otherwise>
	
	<div class="orderBtnWrap btn3 alC">
		<button type="button" class="btnCart openLyBtn" 		
		onclick="sendIt_Cart('${dto.itemName }','${dto.itemPrice }','${dto.type }',
		'${dto.saveFileName }','${dto.originalFileName }','${dto.num}');">장바구니 담기</button>
			<div class="nutritionLy lyWrap cartPop">
				<a href="#none" class="btnImg closeLyBtn"><span>레이어닫기</span></a>
				
				<div class="lyCont mgT10">
					<span class="fs13">상품을 장바구니에 담았습니다. <br>
					<strong class="fc33 fs14">장바구니로 이동하시겠습니까?</strong></span>
					
					<div class="mgT10">
					<a href="#" onclick="$('.closeLyBtn').click(); return false;"
						class="btnD">계속 쇼핑</a> <a href="#"
						onclick="goCart('ONE'); return false;" class="btnD sub" >
						장바구니확인</a>
					</div>
				</div>
			</div>
			
		<button type="button" class="btnBuy" onclick="sendIt_Order('${dto.num}');">구매하기</button>
		<br/><br/>
		</c:otherwise>
	</c:choose>
	
	</div></div></div></div>

	
	
	</div>
</div>
</form>


</body>
</html>