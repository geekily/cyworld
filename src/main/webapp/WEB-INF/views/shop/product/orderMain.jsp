<%@page import="java.util.List"%>
<%@page import="com.cyworld.dto.CyCartDTO"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("utf-8");
	
	// 총가격
	// 보유 도토리 테스트 시 * 1000
	int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
	
	// 유저가 가진 도토리
	int userDotory = Integer.parseInt( (String)request.getAttribute("userDotory") );
	
	// 결과 값
	int result = userDotory - totalPrice;
	
	// 구매타입
	String buyType = request.getParameter("buyType");
	
	String userId = request.getParameter("userId");
	String[] number = request.getParameterValues("num");
	String num = "";
	
	// javascript 에서는 배열을 못받음으로
	// 구분자를 추가하여 넘긴다.
	for(int i=0; i<number.length; i++){
		num += number[i];
		
		if(i != number.length-1){
			num += ",";
		}
	}

%>
<%@ include file="/WEB-INF/views/shop/header_item.jsp" %>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$("#logo").attr("src", "<%=cp %>/resources/images/logo_gift.png");
	$("#logo").attr(
	"style", "padding-top: 13px; padding-bottom: 20px; width: 250px; height:40px;"
	);
	//선물가게일 경우 선물가게 메인으로 href 변경
	$("#logourl").attr("href", "<%=cp%>/cy/shop/product/list.action");
	
});

	var boolCheck = false;
	var check = false;
	var type = "my";
	
	function sendIt(){
		
		var f = document.myForm;
		var friendName = f.friendName.value;
		var num = "<%=num%>".split(",");
		var html = "";
		var url = "";
		
		
		// 도토리가 부족할시.
		if(<%=result%> < 0){
			alert("보유하고있는 도토리가 부족합니다");
			return;
		}
		
		
		// 친구인지 체크
		if (!boolCheck && check == true){
			swal('검색하신 유저가 없거나 일촌상태가 아닙니다.', 
					'', 
					'error');
			
			return;
		}
		
		
		// 아이템 URL
		for(var i=0; i<num.length; i++) {
			html += "num=" + num[i];
			if(i != num.length-1){
				html += "&";
			}
		}
		
		
		// 선물하기 / 내가 사용하기위해 구매 URL
		if(type == 'my'){
			url += "./order_ok.action?type=my&userId=<%=userId %>&" + html + "&userDotory=<%=userDotory%>";
			if("<%=buyType%>" != "" || "<%=buyType%>" != null) {
				url += "&buyType=<%=buyType%>";
			}
			
		} else {
			url += "./order_ok.action?type=present&userId=<%=userId %>&friendName=" + friendName + "&" + html + "&userDotory=<%=userDotory%>";
			if("<%=buyType%>" != "" || "<%=buyType%>" != null) {
				url += "&buyType=<%=buyType%>";
			}
		
		}
		
		
		f.action= url;
		f.submit();
	}
	
	// 체크박스 보여주기
	function typeCheck() {
		
		if(check == false) {
			var h1 = document.getElementById("hidden1").style.visibility = "visible";
			var h2 = document.getElementById("hidden2").style.visibility = "visible";
			check = true;
			type = "present";
		} else {
			var h1 = document.getElementById("hidden1").style.visibility = "hidden";
			var h2 = document.getElementById("hidden2").style.visibility = "hidden";
			check = false;
			type = "my";
		}	
	}
	
	
	// 친구목록 불러오기
	function sendKeyword1() {
		
		var f = document.myForm;
		var friendId = f.friendId.value;
		var userId = "<%=userId %>";
		var action = './doFriend';
		

		
		// start Ajax
		$.get(
			action,
			{
				userId:userId,
				friendId:friendId
			},
			function(data) {
				
				var id = data.split(',');
				var html = "";
				
				if(friendId == id[0]) {
					boolCheck = true;
				} else {
					boolCheck = false;
				}
				
				
				if(id.length >= 1) {		
					
					html += "<datalist id=\"datas1\" >";
					
					for(var i = 0; i<id.length; i++){
						
						html += "<option value=\"" + id[i] +"\"></option>";						
					
					}
					
					html += "</datalist>";
					$('.result2').html(html);
				}
			}
		);
	}
	
</script>

<form action="" name="myForm" method="post">
<div id="container">

	<ul class="path">
		<li><a href="">홈</a></li>
		<li><a href="">주문하기</a></li>
		<li class="current"><a href="">결제</a></li>
	</ul>
<div id="contents">

	<div class="titWrap">
		<h2 class="pTit">주문서작성/결제</h2>
		<div class="flR">
			<ul class="cartStep">
				<li>
					<div class="step"><span>01</span><strong>장바구니</strong></div>
				</li>
				<li class="on">
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
			<li class="on"><a href="/view/bsk/cart">결제 <strong>${lists.size() }</strong></a></li>
		</ul>
	</div>

	<div class="titWrap type02">
		<h3 class="tit02">주문상품정보</h3>
	</div>	
	
	<div class="cartTbl">
		<table>
			<caption>주문상품정보</caption>
				<colgroup>
					<col width="*">
					<col width="140px">
				</colgroup>
			
			<thead>
				<tr>
					<th scope="col">상품</th>
					<th scope="col">상품금액</th>
				</tr>
			</thead>
		</table>
		
	<table border="0" width="1000" align="center" cellspacing="0">
	<c:forEach var="dto" items="${lists }" varStatus="status" >
		<tr height="50">
			<td rowspan="2" width="15%" align="center" style="border-bottom: solid;border-bottom-color: #BDBDBD;border-bottom-width: 1px;">
				<img alt="no found" src="${resourcePath }/img${dto.saveFileName }" style="height: 100px;width: 100px;">
			</td>
			<td width="70%" align="left" style="border-bottom: solid;border-bottom-color: #BDBDBD;border-bottom-width: 1px;">
				<b><font size="3">${dto.itemName }</font></b>
			</td>
			<td rowspan="2"  width="15%" align="center" style="border-bottom: solid;border-bottom-color: #BDBDBD;border-bottom-width: 1px;border-left: solid;border-left-color: #BDBDBD;border-left-width: 1px;">
				<font size="2"><b><fmt:formatNumber value="${dto.itemPrice }" pattern=" #,### "/></b>도토리</font>
			</td>
		</tr>
		<tr height="50">
			<td width="70%" align="left" style="border-bottom: solid;border-bottom-color: #BDBDBD; border-bottom-width: 1px;">
				<font size="2">${dto.type }</font>
			</td>
		</tr>
	</c:forEach>
	</table>
	
	<div class="cp_pay">
		<div class="titWrap type03 mgT20">
			
		</div>	
		
		<div class="titW">
		<table border="0" width="500" align="left" cellspacing="0">
		<tr height="50" style="visibility:hidden;" id="hidden1">
			<td colspan="2" style="border-bottom: solid;border-bottom-color: #BDBDBD;border-bottom-width: 2px;"/>
		</tr>
	
		<input type="checkbox" name="type" style="width: 22px; height: 22px; cursor: pointer;"
		value="present" onclick="typeCheck();">  <font size="4px"><strong>일촌에게 선물하기</strong></font> </input>
		
		<tr height="50" style="visibility:hidden;" id="hidden2">
			<td bgcolor="#EAEAEA" style="border-top: solid;border-top-color: #BDBDBD;border-top-width: 1px;padding-left: 10px;">
				친구 아이디 <input type="text" id="friendName" style="height: 10px;" 
				onkeyup="sendKeyword1();" list="datas1" name="friendId" />
				<div class="result2"></div>
				

			</td>
		</tr>

	</table>
	
	
	</div>
	</div>
	
	<div class="priceEnd">
		<div class="titWrap type02">
			<h3 class="tit02">최종 결제정보</h3>
		</div>
		
		
		<div class="priceArea" style="height:125px;">
		<dl><dt>보유중도토리</dt>
		<dd>
			<span class="price"><strong class="ord_amt_text" id="ord_amt_text">
			<fmt:formatNumber value="" pattern=" #,###" /></strong>${userDotory } 도토리</span>
		</dd></dl>
		
		
		<dl><dt>총상품금액</dt>
		<dd>
			<span class="price"><strong class="ord_amt_text" id="ord_amt_text">
			<fmt:formatNumber value="" pattern=" #,###" /></strong><%=totalPrice %> 도토리</span>
		</dd></dl>
		
		
		
		
		<dl class="total">
			<dt><span class="fb" style="color:#272222;">잔여 도토리</span></dt>
			<dd><span class="price red"><strong class="pay_amt_text" id="last_pay_amt">
				<%=result %></strong> 도토리</span></dd>
		</dl>
		
	</div>
		
	<div class="btnWrap cart" style="width:100%;margin:20px auto 0 !important;">
		<a href="javascript:sendIt();" id="doPay" class="btnM" style="min-width:130px !important;">결제하기</a>
		
		<!-- 01-08 현종 추가 -->
		<a href="<%=cp %>/cy/shop/product/list.action" class="btnM sub2" style="min-width:100px !important;">취소</a>
	</div></div>
	</div>
	</div>
</div>
	
</form>

<br/><br/>

<%@ include file="/WEB-INF/views/shop/footer.jsp" %>

