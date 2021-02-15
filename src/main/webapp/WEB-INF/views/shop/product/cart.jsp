<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String userId = request.getParameter("userId");
	// String cp = request.getContextPath();
	String msg = request.getParameter("msg");
	
%>
<%@ include file="/WEB-INF/views/shop/header_item.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>장바구니 페이지</title>

<script type="text/javascript">


$(document).ready(function() {
	$("#logo").attr("src", "<%=cp %>/resources/images/logo_gift.png");
	$("#logo").attr(
	"style", "padding-top: 13px; padding-bottom: 20px; width: 250px; height:40px;"
	);
	//선물가게일 경우 선물가게 메인으로 href 변경
	$("#logourl").attr("href", "<%=cp%>/cy/shop/product/list.action");
	
});

	// 전역변수
	var totalPrice;
	
	var msgCheck = false;
	
	// 2021-01-04 수정
	
	function sendIt(){
		var f = document.myForm;
		
		// 주문한 상품이 0원 이하면 오류 메세지 출력
		if(totalPrice <= 0) {
			swal('선택하신 상품이 없습니다...',
					'상품을 선택후 사용해주세요.',
					'warning');
			return;
		}
		
		// 주문 URL
		var check_count = document.getElementsByName("prdct_id").length;
		var url = "<%=cp%>/cy/shop/order.action?userId=<%=userId%>&totalPrice="+totalPrice;
        for (var i = 0; i < check_count; i++) {
            if (document.getElementsByName("prdct_id")[i].checked == true) {
            	url += "&num="+document.getElementsByName("prdct_id")[i].value;
            }
        }

		f.action = url;
		f.submit();
	}
	
	// 체크박스 도토리 가격수정
	function itemCheck(itemPrice) {
		
		// 중복된 값이 왔을때 메세지박스 띄우기
		if(<%=msg%> == "1" && msgCheck == false){
			swal('이미 구매한 아이템입니다.',
					'이미 구매한 아이템은 장바구니에서 자동으로 삭제됩니다.',
					'warning');
			msgCheck = true;
		}
		
		
		var check_count = document.getElementsByName("prdct_id").length;
		var itemCount = 0;
		var html = "";
		
		// 도토리 가격이 없을경우 10도토리로 설정
		if (itemPrice != 10){
			itemPrice = 10;
		}
		
		// 체크 되있는 것만 구별
        for (var i = 0; i < check_count; i++) {
            if (document.getElementsByName("prdct_id")[i].checked == true) {
            	itemCount = itemCount + 1;
            }
        }
		
		// 가격
        totalPrice = (itemPrice * itemCount);
        // alert(totalPrice);
        
        // html 뿌리기
        html += "<strong>"+ totalPrice +" </strong>도토리";
        document.getElementById("tp").innerHTML = html;
	}
	
	
	
	function allSelect(selectAll) {
		
		const checkboxes = document.querySelectorAll('input[type="checkbox"]');
		checkboxes.forEach((checkbox) => {
			checkbox.checked = selectAll.checked
		})
		
		// 체크박스 도토리 가격수정
		itemCheck();
	}

</script>

</head>
<body>

<div id="container">
	<ul class="path">
				<li><a href="">홈</a></li>
				<li class="current"><a href="">장바구니</a></li><!-- 현재 카테고리 li.current  -->
	</ul>
	
	<div id="contents">
	<form action="" name="myForm" method="post">
	<div class="titWrap">
		<h2 class="pTit">장바구니</h2>
		
		<div class="flR">
			<ul class="cartStep">
				<li class="on">
				<div class="step"><span>01</span><strong>장바구니</strong></div>
				</li><!-- 현재단계 : li.on -->
				
				<li>
				<div class="step"><span>02</span><strong>주문서작성/결제</strong></div>
				</li>
				
				<li class="last">
				<div class="step"><span>03</span><strong>주문완료</strong></div>
				</li>
			</ul>
		</div>
	</div>
	
	<div class="cartTab">
				<ul>
					<li class="on"><a href="./cart.action?userId=<%=userId%>">담긴상품 <strong>${lists.size() }</strong></a></li>
				</ul>
	</div>
	


	<div class="titWrap type02">
		<h3 class="tit02">장바구니</h3>
	</div>
	
	<div class="cartTbl">
		<table>
			<caption>장바구니</caption>
				<colgroup>
					<col width="30px">
					<col width="*">
					<col width="140px">
					<col width="110px">
				</colgroup>
			
			<thead>
				<tr>
					<th scope="col" class="alR"><input type="checkbox" title="전체선택" 
					class="bskPrdctChk bskPrdctAll" checked="true" onclick="allSelect(this);">
					</th>
					
					<th scope="col">상품</th>
					<th scope="col">상품금액</th>
				</tr>
			</thead>
			
			<tbody>
	<c:forEach var="dto" items="${lists }" varStatus="status" >
	
	<tr class="productRow">
				<td class="alR">
				<input type="checkbox" title="상품선택" class="bskPrdctChk bskPrdctItm"
				name="prdct_id" value="${dto.num }" checked="checked" onclick="itemCheck('${dto.itemPrice }');">
				
				</td>
			<td class="item ">				
			<!-- 상품정보 -->
			<div class="itemWrap">
				<div class="imgArea">
					<%-- <a href="<%=cp %>/goobnemall/product/listdetail.do_cart?num=${dto.num}" class="itemImg"> --%>
					<%-- <img src="${dto.saveFileName}"> --%>
					<img src="${resourcePath }/img${dto.saveFileName }"/>
					</a>
				</div><!-- //imgArea -->
				
		<div class="infoArea">
				<div class="itemTit">
					<p class="name">
					${dto.itemName }
					</p>
				</div><!-- //itemTit -->

			<div class="selectedItem">
				<!-- 선택된 상품 리스트 -->
			<ul>
				<li class="productItemRow">
				
				<!-- 여기서부턴 반복하면 될듯 -->
					<div class="itemName">
						<%-- <a href="<%=cp %>/goobnemall/product/listdetail.do_cart?num=${dto.num}">${dto.productName }</a> --%>
						${dto.type }
					</div>
	
					<div class="itemQuantity">
						<%-- <div class="itemQuant">
							<input type="text" name="qty" value="${dto.qty }" size="3">
								<button class="btnPlus" type="button"
								onclick="javascript:location.href='<%=cp%>/goobnemall/order/cart_update.do?num=${dto.num}&qty=${dto.qty+1 }';">
								</button>
								<button class="btnMinus" type="button"
								onclick="javascript:location.href='<%=cp%>/goobnemall/order/cart_update.do?num=${dto.num}&qty=${dto.qty-1 }';">
								</button>
						</div> --%>
								<!-- <button class="btnE sub" type="button">&nbsp;<span class="chngQty">변경</span></button> -->
					</div>
		
					<div class="itemPrice"><span class="price"><strong><fmt:formatNumber value="${dto.itemPrice }" pattern=" #,###" /></strong>도토리</span></div>
	
					<div class="itemDelete">
						<script>
						// 삭제 링크
							function deleteCheck(num) {
								
								var f = document.myForm;								
								var link = '<%=cp%>/cy/shop/cartDelete.action?num='+num+'&userId=<%=userId %>';
																
								var result = confirm("장바구니에서 삭제하시겠습니까?");
								if(result) {
									location.href=link;
									
									
								}
							}
						</script>
						
						<a href="javascript:deleteCheck('${dto.num}');" 
						class="btnImg delItem removeProductItemBtn"><span>제품 삭제</span></a>
					</div>
				</li>
			</ul>
			</div>
		</div>
	</div>
			</td>
		<td><span class="price"><strong><fmt:formatNumber value="${dto.itemPrice }" pattern=" #,###" /></strong>도토리</span></td>
		
	</tr>
	</c:forEach>
		</tbody>
	</table>
	</div><!-- cart 닫힘 -->
	
	<div class="cartPriceSum" id="cartPriceSum">
		
		<dl class="total"><dt> 총주문금액</dt>
		<dd>		

		<span class="price" id="tp"><strong>0</strong>도토리</span>

		</dd></dl>
		

	</div>
	

	
	<div class="btnWrap cart">
		<a href="#" class="btnM sub2" id="continueShopping" onclick="history.back(-1); return false;">쇼핑계속하기</a>
		<a href="#" class="btnM" id="orderAllPrdct" onclick="sendIt();">전체상품주문</a>
	</div>
	
	<div class="btnWrap cart" style="margin-top: 20px;display:block" id="noNpayPosition"></div>
	<a href="#">
		<center><img src="http://www.goobnemall.com/static-root/resources/pc/img/member/bn_Npay_2019.jpg" alt="첫구매 100원 딜"></center>
		</a>
	<br/><br/>
	
	</form>
	</div>
	</div>

<!-- 페이지 로드시 스크립트 실행 -->
<script>
	window.onload = function () {
		
		if('${session.getUserId() }' == ""){
			location.href='<%=cp %>/cy/index.action';
			return;
		}
		itemCheck();
	}
</script>

</body>
</html>
<%@ include file="/WEB-INF/views/shop/footer.jsp" %>

