<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<%@ include file="/WEB-INF/views/shop/header_item.jsp" %>
<script>

$(document).ready(function() {
	$("#logo").attr("src", "<%=cp %>/resources/images/logo_gift.png");
	$("#logo").attr(
	"style", "padding-top: 13px; padding-bottom: 20px; width: 250px; height:40px;"
	);
	//선물가게일 경우 선물가게 메인으로 href 변경
	$("#logourl").attr("href", "<%=cp%>/cy/shop/product/list.action");
	
});

</script>
</head>
<body>
<div id="container">

	<ul class="path">
		<li><a href="">홈</a></li>
		<li><a href="">주문하기</a></li>
		<li class="current"><a href="">주문완료</a></li>
	</ul>

	<div id="contents">
		<div class="titWrap">
			<h2 class="pTit">결제완료</h2>
			<div class="flR">
				<ul class="cartStep">
					<li>
						<div class="step"><span>01</span><strong>장바구니</strong></div>
					</li>
					<li>
						<div class="step"><span>02</span><strong>주문서작성/결제</strong></div>
					</li>
					<li class="on">
						<div class="step"><span>03</span><strong>주문완료</strong></div>
					</li>
				</ul>
			</div>
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
				<td rowspan="2" width="15%" align="center" style="border-top: solid;border-top-color: #BDBDBD;border-top-width: 1px;border-bottom: solid;border-bottom-color: #BDBDBD;border-bottom-width: 1px;">
					<img alt="no found" src="${resourcePath }/img${dto.saveFileName }" style="height: 100px;width: 100px;">
				</td>
				<td width="70%" align="left" style="border-top: solid;border-top-color: #BDBDBD;border-top-width: 1px;border-bottom: solid;border-bottom-color: #BDBDBD;border-bottom-width: 1px;">
					<b><font size="3">&nbsp;&nbsp;${dto.itemName }</font></b>
				</td>
				<td rowspan="2"  width="15%" align="center" style="border-top: solid;border-top-color: #BDBDBD;border-top-width: 1px;border-bottom: solid;border-bottom-color: #BDBDBD;border-bottom-width: 1px;border-left: solid;border-left-color: #BDBDBD;border-left-width: 1px;">
					<font size="2"><b>
					<fmt:formatNumber value="${dto.itemPrice }" pattern=" #,###" /> 도토리</b></font>
				</td>
			</tr>
			<tr height="50">
				<td width="70%" align="left" style="border-bottom: solid;border-bottom-color: #BDBDBD; border-bottom-width: 1px;">
					<font size="2">${dto.type }</font>
				</td>
			</tr>
		</c:forEach>
		</table>
		
		
		<div class="btnWrap cart" style="width:100%;margin:20px auto 0 !important; border: hidden;">
			<a href="<%=cp%>/cy/index.action?loginOk=ok;" id="doPay" class="btnM" style="min-width:130px !important;">돌아가기</a><br/><br/>
		</div>
		
	</div>
</div>
	<br/><br/><br/><br/>
<%@ include file="/WEB-INF/views/shop/footer.jsp" %>

