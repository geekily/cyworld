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
	
<%@ include file="/WEB-INF/views/shop/header_item.jsp" %>

<style>
	.logo {
	padding-top:10px;
	padding-left: 25px;
	height:45;
	}
</style>

<script type="text/javascript">

	function sendIt(){
		var f = document.myForm;
		f.action = "<%=cp%>/cy/shop/product/listdetail.action";
		f.submit();

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
	
	function sent_Cart() {
		var userId = '${session.getUserId() }';
		if(userId == ""){
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
		swal('장바구니에 상품이 추가되었습니다.', '장바구니로 이동하시겠습니까?', {
			icon: "success",
			buttons: {
				cancel: '아니오',
				confirm: {
		        	text: '네',
		            value: true,
		        },
			},
		}).then((result => {
			if(result) {
				goCart('ONE'); 
				return false;
			}
		}));
	}
	
	function goCart(one) {
		
		var f = document.myForm;
		
		f.action = '../cart.action?userId=' + '${session.getUserId() }';
		f.submit();
		
	}
	
	
</script>

	<br />
	<br />
	<form action="" name="myForm" method="post">

		<div class="mainContBox">
			<div class="titWrap">
				<h1 class="tit">일촌들에게 아이템을 선물해보세요!</h1>
				<p class="cmnt">
					<a href="<%=cp%>/cy/shop/product/list.action">전체보기</a>
				</p>
				<br />
				<br />
			</div>

			<div class="row">
		<c:forEach var="dto" items="${lists }">
		<div class="col-lg-4 col-md-4 mb-4">
			<div class="card h-100">
				<a href="javascript:location.href='<%=cp%>/cy/shop/product/listdetail.action?num=${dto.num}';">
					<img class="card-img-top" src="${resourcePath }/img${dto.saveFileName }"/></a>
	
					<div class="card-body">
						<h5 class="card-title">
							<a href="javascript:location.href='<%=cp%>/cy/shop/product/listdetail.action?num=${dto.num}';">
							<input type="hidden" value="${dto.num }" name="num"/>
							[${dto.type}] ${dto.itemName }<span class="ic_new_ss"></span></a>
						</h5>
						<h6>
							<!-- fmt option 활용하여 가격 출력 format 수정 -->
							<img src="${resourcePath }/img/dotori.png" width="18" height="18" name="도토리" />
							<b>${dto.itemPrice }</b>
						</h6>
					</div>
				<div class="card-footer">
					<input type="button"
					onclick="sendIt_Cart('${dto.itemName }','${dto.itemPrice }','${dto.type }',
					'${dto.saveFileName }','${dto.originalFileName }','${dto.num}'); sent_Cart();" 
					value="장바구니에 담기"/>
				</div>
			</div>
		</div>
		</c:forEach>
	</div>
		</div>
	</form>
<%@ include file="/WEB-INF/views/shop/footer.jsp" %>

