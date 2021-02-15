<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<% /* post방식일때 한글깨짐 방지 */
	request.setCharacterEncoding("UTF-8");  
%>
<%
Calendar cal = Calendar.getInstance();
int year = cal.get(Calendar.YEAR);

%>

<%@ include file="../shop/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
var check = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
function createDay(){
	document.forms["myForm"].userDay.options.length=0;
	var selectYear = document.getElementById("userYear");
	var yearValue = selectYear.options[selectYear.selectedIndex].value;
	var selectMonth = document.getElementById("userMonth");
	var monthValue = selectMonth.options[selectMonth.selectedIndex].value;
	if(yearValue==""||monthValue==""){
		return;
	}
	var lastDay = new Date(yearValue,monthValue,0).getDate();
	for(var i = 1; i <=lastDay ; i++){
		var op = new Option();
		op.value= i;
		op.text = i;
		document.forms["myForm"].userDay.add(op);
	}
}

function inputCheck(){
	var str = document.myForm.userId.value;
	if(str==""){
		alert("아이디 입력!");
		document.myForm.userId.focus();
		return;
	}
	str = document.myForm.userPw.value;
	if(str==""){
		alert("비밀번호 입력!");
		document.myForm.userPw.focus();
		return;
	}
	str = document.myForm.userName.value;
	if(str==""){
		alert("이름 입력!");
		document.myForm.userName.focus();
		return;
	}
	str = document.myForm.userDay.value;
	if(str==""){
		alert("생년월일 입력!");
		return;
	}
	
}

function createUser(){
	
	var str = document.myForm.userId.value;
	if(str==""){
		alert("아이디 입력!");
		document.myForm.userId.focus();
		return;
	}
	str = document.myForm.userPw.value;
	if(str==""){
		alert("비밀번호 입력!");
		document.myForm.userPw.focus();
		return;
	}
	str = document.myForm.userName.value;
	if(str==""){
		alert("이름 입력!");
		document.myForm.userName.focus();
		return;
	}
	str = document.myForm.userDay.value;
	if(str==""){
		alert("생년월일 입력!");
		return;
	}
		
	/* 2021-01-15 현종추가 */
	// 아이디 한글검사
	var str = document.myForm.userId.value;
	if(check.test(str)) {
		alert("아이디는 영, 숫자 혼용만 가능합니다.");
		document.myForm.userId.focus();
		return;
	}
	
	// 이름 한글검사
	str = document.myForm.userName.value;
	if(!check.test(str)) {
		alert("이름은 한글만 가능합니다.");
		document.myForm.userName.focus();
		return;
	}
	
	document.myForm.action = "createUser_ok.action";
	document.myForm.submit();
	
}


	function idch() {
		
		var f = document.myForm;
		f.action = "checkUser.action";
		f.submit();
		
	}
	
	function dupliCheck() {
		alert("아이디 중복 체크가 필요합니다."); 
		document.myForm.userId.focus();
	}
	

</script>

</head>
<body>

<form action="" method="post" name="myForm">

<div id="container">

		<ul class="path">
			<li><a href="#none">홈</a></li>
			<li class="current"><a href="#none">회원가입</a></li>
		</ul>

		<div id="contents">
			<div id="personInpt01">
				<div class="titWrap">
					<h2 class="pTit">회원가입</h2>
				</div><br/>


				<div id="personView">
					<div class="joinCopy">
						싸이월드에 오신 것을 환영합니다. <br>
						<strong>지금 회원 가입하신 후 <span class="fc01">싸이월드의 다양한 서비스</span>를 만나보세요.</strong>
					</div>
				</div><br/>

				<div class="mgT30" id="def-info">
					<strong class="dpIB flL fs17 fc33">기본정보입력</strong>
					<span class="dpIB flR"><i class="icon required">필수</i> 항목은 필수입력 값입니다.</span>
				</div>
			</div>
			<br/><br/><br/>
			<div id="personInpt02">
				<div class="infoTbl clB">
					<table>
						<caption>회원가입 기본정보</caption>
						<colgroup>
							<col width="150px">
							<col width="*">
						</colgroup>
						<tbody>
						<tr>
							<th scope="row"><i class="icon required" aria-required="true">필수</i> 아이디</th>
							<td>
								<input type="text" id="lgn_id" name="userId" style="width:235px;" title="아이디" value="${idVal }"/>
								<a href="#"  onclick="idch();" class="btnB w95" id="checkDuplicateId">중복확인</a><span class="cmnt">
								<c:if test="${chk==2 }">
									<font color="red">사용 불가능한 아이디입니다.&nbsp;
									<script>document.myForm.userId.focus();</script></font>
								</c:if>
								<c:if test="${chk==1 }">
									<font color="red">이미 사용중인 아이디입니다.&nbsp;
									<script>document.myForm.userId.focus();</script></font>
								</c:if>
								<c:if test="${chk==0 }">
									<font color="blue">사용 가능한 아이디입니다.&nbsp;</font>
								</c:if>4~20자의 영, 숫자 혼용하여 사용 가능합니다.</span>
							</td>
						</tr>
						
						<tr>
							<th scope="row"><i class="icon required" aria-required="true">필수</i> 비밀번호</th>
							<td>
								<c:if test="${chk==null }">
									<input type="password" id="lgn_pwd" name="userPw" 
									style="width:235px;" title="비밀번호"
									onclick='dupliCheck();' onkeyup='dupliCheck();'>
								</c:if>
								<c:if test="${chk!=null }">
									<input type="password" id="lgn_pwd" name="userPw" style="width:235px;" title="비밀번호">
								</c:if>
									<span class="cmnt">
									보안을 위해 영문, 숫자, 특수문자 조합을 사용해 주세요.</span>
							</td>
						</tr>
						<tr>
							<th scope="row"><i class="icon required" aria-required="true">필수</i> 이름</th>
							<td>
								<c:if test="${chk==null }">
									<input type="text" id="cst_nm" name="userName" style="width:235px;" title="이름"
									onclick='dupliCheck();' onkeyup='dupliCheck();'>
								</c:if>
								<c:if test="${chk!=null }">
									<input type="text" id="cst_nm" name="userName" style="width:235px;" title="이름">
								</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row"><i class="icon required" aria-required="true">필수</i> 성별</th>
							<td>
								<select name="userGender" style="border: none;">
									<option value="남자">남자</option>
									<option value="여자">여자</option> 
								</select><br/>
							</td>
						</tr>
						<tr>
							<th scope="row"><i class="icon required" aria-required="true">필수</i> 생년월일</th>
							<td>
								<select name="userYear" id="userYear" style="border: none;">
									<option value="">년</option>
									<%for(int i = year-40; i<= year ; i++){ %>
									<option value=<%=i %>><%=i %></option>
									<%} %>
								</select>
									<select name="userMonth" id="userMonth" onchange="createDay();"  style="border: none;">
									<option value="">월</option>
									<%for(int i = 1; i<= 12 ; i++){ %>
									<option value=<%=i %>><%=i %></option>
									<%} %>
								</select>
									<select name="userDay" id="userDay"  style="border: none;">
									<option value="">일</option>
								</select>
							</td>
						</tr>
					</table>
					</tbody>
				</div>
					<div id="btnViewP" class="btnWrap cart">
						
						<c:if test="${chk==1 }">
						<button class="btnBuy" type="button" style="width:150px;"
						onclick="alert('사용중인 아이디로는 가입이 불가능합니다.'); document.myForm.userId.focus();">
						회원가입</button>
						&nbsp;&nbsp;
						</c:if>
						<c:if test="${chk==0 }">
						<button class="btnBuy" type="button" style="width:150px;"
						onclick="createUser();">회원가입</button>
						&nbsp;&nbsp;
						</c:if>
						<c:if test="${chk==null }">
						<button class="btnBuy" type="button" style="width:150px;"
						onclick="inputCheck();">회원가입</button>
						&nbsp;&nbsp;
						</c:if>
						
						<button class="btnBuy" type="button" style="width:150px;"
						onclick="javascript:window.history.back();">돌아가기</button>
						</div><br/><br/><br/><br/>
			</div>
			
		</div>
			


</div>
						
</form>						
</body>
</html>
<%@ include file="../shop/footer.jsp" %>
