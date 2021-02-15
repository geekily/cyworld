<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("UTF-8");
	
%>

<%@ include file="../shop/header.jsp"%>

<script type="text/javascript">

	//01-08 현종 추가
	// 받은 선물함 팝업창
	function popup() {
		window.open('popup.action','${session.getUserId() }_gift',
		'width=312,height=390,location=no,status=no,scrollbars=no');
		
		// 0.650초 뒤에 페이지 리로드
		// 선물함 최신화 하기 위한 것
		setTimeout(() => location.reload(true), 650);
		
		
	}
	
	function popupMember(){
		window.open('my_member_list.action','member_list','width=312,height=390,location=no,status=no,scrollbars=no');
	}
	
</script>

<link type="text/css" rel="stylesheet" href="<%=cp %>/resources/css/covid19.css">

<!-- 이미지 슬라이딩 -->
<div class="mainBanner">
	<div class="imgSliderWrap listW">
		<ul class="sliderImg">
			<li
				style="background-color:; background-image: url('${resourcePath }/img/admin/main-bg/gunchim_full.png'); background-position: center; background-repeat: no-repeat;">
				<a href="<%=cp%>/cy/shop/product/list.action?type=스킨" title="군침이 터져나오는 신상 스킨 구경가기"></a>
			</li>

			<li
				style="background-color:; background-image: url('${resourcePath }/img/admin/main-bg/fimally_full.png'); background-position: center; background-repeat: no-repeat">
				<a href="<%=cp%>/cy/shop/product/list.action?type=배경음악" title="마참내 배경음악 구경가기"></a>
			</li>

			<li
				style="background-color:; background-image: url('${resourcePath }/img/admin/main-bg/maplefont_full.png'); background-position: center; background-repeat: no-repeat">
				<a href="<%=cp%>/cy/shop/product/list.action?type=글꼴"
				title="메이플스토리체 출시">
				</a>
			</li>

			<li
				style="background-color:; background-image: url('${resourcePath }/img/admin/main-bg/minimiclub_full.png'); background-position: center top; background-repeat: no-repeat">	
				<a
				href="<%=cp%>/cy/shop/product/list.action?type=미니미"
				title="댄스댄스 미니미 보러가기">
				<img src="${resourcePath }/img/admin/minimi/test6.gif"
				width="200" height="200"
				style="position:relative; top:210px; left:-100px;">
				<img src="${resourcePath }/img/admin/minimi/test5.gif"
				width="200" height="200"
				style="position:relative; top:210px; left:-100px;">
				<img src="${resourcePath }/img/admin/minimi/test21.gif"
				width="200" height="200"
				style="position:relative; top:210px; left:-100px;">
				<img src="${resourcePath }/img/admin/minimi/test13.gif"
				width="200" height="200"
				style="position:relative; top:210px; left:-100px;">
				</a>
			</li>

			<li
				style="background-color:; background-image: url('${resourcePath }/img/admin/main-bg/minimiroom_full.png'); background-position: center top; background-repeat: no-repeat">
				<a href="<%=cp%>/cy/shop/product/list.action?type=스토리룸" 
				title="스토리룸 보러가기">
				<img src="${resourcePath }/img/admin/minimi/test21.gif"
				width="200" height="200"
				style="position:relative; top:260px; left:230px;">
				</a>
			</li>
		</ul>
	</div>

	<div class="ctrl">
		<span class="prev"><a href="#prevB">&nbsp;</a></span> <span><strong>1</strong>/5</span>
		<span class="next"><a href="#nextB">&nbsp;</a></span> <span
			class="pause" id="pause">&nbsp;</span>
	</div>
	<div class="pagingW"></div>

</div>

<script type="text/javascript">
	bannerSlider(".mainBanner", 3500, "fade");
</script>

<!-- 아이템 리스트 -->
<br />
<br />
<div class="mainContBox">
	<div class="titWrap">
		
		<h1 class="tit" style="font-size: 20px;">
		<c:if test="${session.getUserId()!=null }">
		${session.getUserName() } 
		</c:if>
		<c:if test="${session.getUserId()==null }">
		guest
		</c:if>
		</h1>
		<h1 class="tit" style="font-weight:lighter;">님, 환영합니다. :)</h1>
		<br />
		<br /> <br />
	</div>

<!-- 마이페이지를 가운데에 둘거면 div 배치 가운데로 오게 해야함 -->
<div class="prdtList saleList">
	<ul>
		<li class="fstRow myCyworld"
			style="width:516px; ">
			<div class="itemWrap" 
				style="height:360px; border:5px solid lightgray;"> 
				
				<c:if test="${session.getUserId()!=null }">
					<div class="itemTit" style="float:left; width:70%;">
						<p class="name" style="font-size: 20px; color:black;">
						<b>${session.getUserName() }</b> 님의 싸이월드</p>
					</div>
					<div class="itemTit" style="float:left; width:30%;">
					<button type="button"
						style="font-size: 15px; padding: 5px 15px 5px 15px; box-shadow: 1px 1px 2px 1px lightgray; color: gray; border-radius: 2px;"
						onclick="javascript:location.href='<%=cp%>/cy/logout.action';">
						<b>로그아웃</b>
						</button>
					</div><br/>
					<div style="padding-top:10px;">
						<hr style="display:block; clear:both; padding: 0;
						border-top: orange 5px solid; height: 0px; opacity: 1;
						margin:0; margin-bottom: 5px;"/>
					</div>
				</c:if>
				
				
				<!-- 회원 정보가 있을 경우의 마이페이지 좌측-->
				<c:if test="${session.getUserId()!=null }">
				<div>
					<div style="float:left; width:50%">
					<a href="" onclick="window.open('my_main.action','${session.getUserId() }',
						'width=1090,height=600,location=no,status=no,scrollbars=no');"
						class="itemImg" title="${session.getUserName() } 님의 싸이월드"
						style="width:218; height:218;">	
					<img
						src="${resourcePath }/img${myMinimi }"
						alt="${session.getUserName() } 님의 미니미">
						<!-- myMinimi가 뜨다 안뜨다 하는 것 같소 -->
					</a>
						<div style="text-align:center;">
						내정보관리 | 사생활보호설정 
						</div>
					</div>
				</div>
					<!-- 마이페이지 우측 --> 	
				<div style="float:left; width:50%; padding-top:40px; 
				font-size:15px; line-height:180%">
					
					<div style="float:left; width:50%;">	
						오늘방문자<br/>
						새게시물<br/>
						일촌신청<br/>
						선물함<br/><br/>
						내 도토리
					</div>
					<div style="float:left; width:50%"> 
					<!-- 이 부분 데이터랑 연동해줘야돼! -->
						
						<font style="color:orange;">${today }</font><c:if test="${today ne 0 }"><img alt="" src="${pageContext.request.contextPath}/resources/images/new.png"></c:if><br/>
						
						<font style="color:orange;">${boardListCount }</font><c:if test="${boardListCount ne 0 }"><img alt="" src="${pageContext.request.contextPath}/resources/images/new.png"></c:if><br/>
						
						<a href="javascript:popupMember();"><font style="color:orange;">${memberListCount }</a></font><c:if test="${memberListCount ne 0 }"><img alt="" src="${pageContext.request.contextPath}/resources/images/new.png"></c:if><br/>
						<!-- 01-08 현종 추가 -->
						<a href="javascript:popup();"><font style="color:orange;">${presentList }</font><br/><br/></a>
						
						<img src="${resourcePath }/img/dotori.png" 
							width="18" height="18" name="도토리" />
						<font style="color:orange;">${dotory }</font><br/>						
					</div>
				</div>

				<div class="itemTit"
					style="clear: both; padding-top: 30px; text-align: center;">
					<button type="button"
						style="font-size: 18px; background-color: orange; padding: 10px 20px 10px 20px; box-shadow: 2px 2px 4px 2px lightgray; color: white; border-radius: 5px;"
						onclick="window.open('my_main.action','${session.getUserId() }','width=1090,height=600,location=no,status=no,scrollbars=no');">
						<b>내 미니홈피 가기 ▶</b>
					</button>
				</div>
			</c:if>
							
				<!-- 회원 정보가 없을 경우의 마이페이지 -->
				<c:if test="${session.getUserId()==null }">
					<a href="" onclick="alert('로그인 후 이용하실 수 있습니다.')"
						class="itemImg" title="guest 님의 싸이월드"> 
					<img
						src="${resourcePath }/img/admin/minimi/basic_man.png"
						alt="guest 님의 미니미"> 
					</a>
				</c:if>				
			
			<c:if test="${session.getUserId()==null }">
				<div class="itemTit" style="width:100%; height:50;">
					<p class="name" 
					style="font-size: 18px;">
					다양한 친구들과 일촌이 되어보세요!</p>
				</div>
				<div class="itemTit" style="width:100%;">
					<p class="name" 
					style="font-size: 15px; height:50;">
					싸이월드에 가입하시면 풍성한 혜택을 누리실 수 있습니다.</p>
					<center><button type="button" float="center"
					style="border-radius:10px; border:1px solid gray; background-color:lightgray;
					padding:5px;"
					onclick="javascript:location.href='createUser.action';">
					<b><font style="font-size:15px">가입하러 가기!</font></b>
					</button>
					<button type="button" float="center"
					style="border-radius:10px; border:1px solid gray; background-color:lightgray;
					padding:5px;"
					onclick="javascript:location.href='login.action';">
					<b><font style="font-size:15px">로그인</font></b>
					</button>
					</center>
				</div>
			</c:if>
			
			</div>
		</li>
		
		
		<li class="fstRow" style="width: 500px; height: 408px; padding: 10px;
			padding-top:none;">
			
			<!-- weather widget -->
			<a
				class="weatherwidget-io"
				href="https://forecast7.com/en/37d57126d98/seoul/"
				data-label_1="SEOUL" data-label_2="South Korea"
				data-icons="Climacons Animated" data-days="5" data-theme="retro-sky">SEOUL
					South Korea</a>
			<script>
				!function(d, s, id) {
					var js, fjs = d.getElementsByTagName(s)[0];
					if (!d.getElementById(id)) {
						js = d.createElement(s);
						js.id = id;
						js.src = 'https://weatherwidget.io/js/widget.min.js';
						fjs.parentNode.insertBefore(js, fjs);
					}
				}(document, 'script', 'weatherwidget-io-js');
			</script>
			
			<!-- Covid19 Widget -->
			<h2 class="tit" style="text-align:center; color:black; padding-top:8px;">
				Covid-19 Stats in South Korea</h2>
			<div id="covid-widget" style="padding-top:5px; padding-bottom:5px;"></div>	
			<script type="text/javascript" src="<%=cp %>/resources/js/covid19stats.js"></script>
			<script>
			  $.covid19stats({
			  element: "#covid-widget", // Target element ID
			  countryCode: "KR", // ISO Country Code (get the list here : https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
			  showImg: true, // True or false to show the left covid image
			  showCases: true, // True or false to show the "cases" stat
			  showDeaths: true, // True or false to show the "deaths" stat
			  showRecovered: true, // True or false to show the "recovered" stat
			  showRightLabel: true // True or false to show the "right label"
			});
			</script>
			
		</li><br/>
	</ul>
		<div class="titWrap" style="padding-top:50px;">
			<h1 class="tit">싸이월드 회원들과 일촌을 맺어보세요!</h1>
			<br />
			<br /> <br />
		</div>
	<ul>
		
		<c:if test="${userName[0]==null }">	
		<li class="fstRow"></li>
		</c:if>
		
		<c:if test="${userName[0]!=null }">
		<li class="fstRow">
			<div class="itemWrap" style="height:370px;">
				<div class="listIcon">
					<h4>놀러가기!</h4>
				</div>
				
				<!-- Verify login user data -->
				<c:if test="${session.getUserId()!=null }">
					<a href="" onclick="window.open('user_main.action?userId=${userId[0] }', '${userId[0]}',
						'width=1090,height=600,location=no,status=no,scrollbars=no');"
						class="itemImg" title="${userName[0] } 님의 싸이월드"> 
					<input type="hidden" name="userId" value="${userId[0] }"/>
					<img
						src="${resourcePath }/img${minimi[0] }"
						alt="${userName[0] } 님의 미니미"> 
					<span class="discountThumb">
					<span class="discount" style="color:yellow;"><strong>new</strong></span></span>
					</a>
				</c:if>
				<c:if test="${session.getUserId()==null }">
					<a href="login.action" onclick="alert('로그인 후 이용하실 수 있습니다.')"
						class="itemImg" title="${userName[0] } 님의 싸이월드"> 
					<input type="hidden" name="userId" value="${userId[0] }"/>
					<img
						src="${resourcePath }/img${minimi[0] }"
						alt="${userName[0] } 님의 미니미"> 
					<span class="discountThumb">
					<span class="discount" style="color:yellow;"><strong>new</strong></span></span>
					</a>
				</c:if>
				
				<div class="barBg01">
					<p>
						<span class="barTit">${userName[0] }</span> <span class="barText">신규회원</span>
					</p>
				</div>
				<div class="itemTit">
					<p class="name" style="font-size: 18px;"><b>${userName[0] }</b> 님의 싸이월드</p>
				</div>
				<div class="dpIconWrap alC" style="height: 18px;">
					<div class="dpIconWrap">
						<span class="icoDisplay typeC">NEW</span>
					</div>
				</div>
			</div>
		</li>
		</c:if>
		
		<c:if test="${userName[1]==null }">	
		<li class="fstRow"></li>
		</c:if>
		
		<c:if test="${userName[1]!=null }">
		<li class="fstRow">
		
			<div class="itemWrap" style="height:370px;">
				<div class="listIcon">
					<h4>놀러가기!</h4>
				</div>
				
				<!-- Verify login user data -->
				<c:if test="${session.getUserId()!=null }">
					<a href="" onclick="window.open('user_main.action?userId=${userId[1] }', '${userId[1]}',
						'width=1090,height=600,location=no,status=no,scrollbars=no');"
						class="itemImg" title="${userName[1] } 님의 싸이월드"> 
					<input type="hidden" name="userId" value="${userId[1] }"/>
					<img
						src="${resourcePath }/img${minimi[1] }"
						alt="${userName[1] } 님의 미니미"> 
					<span class="discountThumb">
					<span class="discount"><strong>HIT</strong></span></span>
					</a>
				</c:if>
				<c:if test="${session.getUserId()==null }">
					<a onclick="alert('로그인 후 이용하실 수 있습니다.');"
						href="login.action"
						class="itemImg" title="${userName[1] } 님의 싸이월드"> 
					<input type="hidden" name="userId" value="${userId[1] }"/>
					<img
						src="${resourcePath }/img${minimi[1] }"
						alt="${userName[1] } 님의 미니미"> 
					<span class="discountThumb">
					<span class="discount"><strong>HIT</strong></span></span>
					</a>
				</c:if>
				
				<div class="barBg01">
					<p>
						<span class="barTit">${userName[1] }</span> <span class="barText">인기회원</span>
					</p>
				</div>
				<div class="itemTit">
					<p class="name" style="font-size: 18px;"><b>${userName[1] }</b> 님의 싸이월드</p>
				</div>
				<div class="dpIconWrap alC" style="height: 18px;">
					<div class="dpIconWrap">
						<span class="icoDisplay typeD">HIT</span>
					</div>
				</div>
			</div>
		</li>
		</c:if>
		
		<c:if test="${userName[2]==null }">	
		<li class="fstRow"></li>
		</c:if>
		
		<c:if test="${userName[2]!=null }">
		<li class="fstRow">
		
			<div class="itemWrap" style="height:370px;">
				<div class="listIcon">
					<h4>놀러가기!</h4>
				</div>
				
				<!-- Verify login user data -->
				<c:if test="${session.getUserId()!=null }">
					<a href="" onclick="window.open('user_main.action?userId=${userId[2] }', '${userId[2]}',
						'width=1090,height=600,location=no,status=no,scrollbars=no');"
						class="itemImg" title="${userName[2] } 님의 싸이월드"> 
					<input type="hidden" name="userId" value="${userId[2] }"/>
					<img
						src="${resourcePath }/img${minimi[2] }"
						alt="${userName[2] } 님의 미니미"> 
					<span class="discountThumb">
					<span class="discount"><strong>HIT</strong></span></span>
					</a>
				</c:if>
				<c:if test="${session.getUserId()==null }">
					<a href="login.action" onclick="alert('로그인 후 이용하실 수 있습니다.')"
						class="itemImg" title="${userName[2] } 님의 싸이월드"> 
					<input type="hidden" name="userId" value="${userId[2] }"/>
					<img
						src="${resourcePath }/img${minimi[2] }"
						alt="${userName[2] } 님의 미니미"> 
					<span class="discountThumb">
					<span class="discount"><strong>HIT</strong></span></span>
					</a>
				</c:if>
				
				<div class="barBg01">
					<p>
						<span class="barTit">${userName[2] }</span> <span class="barText">인기회원</span>
					</p>
				</div>
				<div class="itemTit">
					<p class="name" style="font-size: 18px;"><b>${userName[2] }</b> 님의 싸이월드</p>
				</div>
				<div class="dpIconWrap alC" style="height: 18px;">
					<div class="dpIconWrap">
						<span class="icoDisplay typeD">HIT</span>
					</div>
				</div>
			</div>
		</li>
		</c:if>
		
		<c:if test="${userName[3]==null }">	
		<li class="fstRow"></li>
		</c:if>
		
		<c:if test="${userName[3]!=null }">
		<li class="fstRow">
		
			<div class="itemWrap" style="height:370px;">
				<div class="listIcon">
					<h4>놀러가기!</h4>
				</div>
				
				<!-- Verify login user data -->
				<c:if test="${session.getUserId()!=null }">
					<a href="" onclick="window.open('user_main.action?userId=${userId[3] }', '${userId[3]}',
						'width=1090,height=600,location=no,status=no,scrollbars=no');"
						class="itemImg" title="${userName[3] } 님의 싸이월드"> 
					<input type="hidden" name="userId" value="${userId[3] }"/>
					<img
						src="${resourcePath }/img${minimi[3] }"
						alt="${userName[3] } 님의 미니미"> 
					<span class="discountThumb">
					<span class="discount"><strong>★</strong></span></span>
					</a>
				</c:if>
				<c:if test="${session.getUserId()==null }">
					<a href="login.action" onclick="alert('로그인 후 이용하실 수 있습니다.')"
						class="itemImg" title="${userName[3] } 님의 싸이월드"> 
					<input type="hidden" name="userId" value="${userId[3] }"/>
					<img
						src="${resourcePath }/img${minimi[3] }"
						alt="${userName[3] } 님의 미니미"> 
					<span class="discountThumb">
					<span class="discount"><strong>★</strong></span></span>
					</a>
				</c:if>
				
				<div class="barBg01">
					<p>
						<span class="barTit">${userName[3] }</span> <span class="barText">오늘의회원</span>
					</p>
				</div>
				<div class="itemTit">
					<p class="name" style="font-size: 18px;"><b>${userName[3] }</b> 님의 싸이월드</p>
				</div>
				<div class="dpIconWrap alC" style="height: 18px;">
					<div class="dpIconWrap">
						<span class="icoDisplay typeC">TODAY</span>
					</div>
				</div>
			</div>
		</li>
		</c:if>
		
		</ul>
	</div>
</div>

<%@ include file="../shop/footer.jsp"%>


