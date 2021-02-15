$(document).ready(function () {
	//브라우저 체크 스크립트
	var agent = navigator.userAgent.toLowerCase();
	((agent.match('msie 11')) ? $('body').addClass('ie11') : false);
	((agent.match('msie 10')) ? $('body').addClass('ie10') : false);
	((agent.match('msie 9')) ? $('body').addClass('ie9') : false);
	((agent.match('msie 8')) ? $('body').addClass('ie8') : false);
	((agent.match('msie 7')) ? $('body').addClass('ie7') : false);
	((agent.match('applewebkit')) ? $('body').addClass('webkit') : false);
	((agent.match('firefox')) ? $('body').addClass('moz') : false);
	((agent.match('mac os')) ? $('body').addClass('mac') : false);

	closeLayer();

	// toTop 버튼
	gotoTop();

	//gnb Fix
	gnbFix();

	//gnb Event
	gnb();

	//quick Fix
	quickFix();

	//path
	pathCategory();


	//툴팁(가이드)뷰
	if ($(".tooltipBtn").length) {
		tooltipView();
	}

	//상품, 옵션 선택
	if ($(".selectOption").length) {
		selectPrdtOption();
	}

	//열림/닫힘 테이블
	if ($(".listViewTbl").length) {
		listViewTbl();
	}

	//요일 선택
	if ($(".selectWeekday").length) {
		selectWeekday();
	}

	//영양선분 레이어
	if ($(".nutritionWrap").length) {
		openCloseLy();
	}

	//tabMenu
	if ($(".tabMenu").length) {
		var $tabMenu = $(".tabMenu ul li");
		var $tabCont = null;

		$tabMenu.on("click", function (e) {

			e.preventDefault();
			var tabIndex = $(this).index();
			$tabCont = $(this).parents(".tabMenu").next(".tabCont").children("div");

			console.log($tabCont + tabIndex);
			$(this).addClass("on").siblings().removeClass("on");
			$tabCont.eq(tabIndex).addClass("on").siblings().removeClass("on");
		});
	}

	//리스트 주문영역 fix
	if ($(".listOrderWrap").length) {
		listOrderFix();
	}

	//배송메시지
	if ($(".deliMsgWrap").length) {
		deliveryMsg();
	}

	//placeholder
	if ($(".placeholder").length) {
		if ($("body").hasClass("ie8") || $("body").hasClass("ie9")) {//ie8, ie9에서만 실행함
			$('.placeholder').placeholder({customClass: 'my-placeholder'});
			//$('input, textarea').placeholder();
		}
	}

	//페이징 체크(내용 없을 경우 체크)
	if ($(".paging").length) {
		pagingCheck();
	}


	//정기배송일정현황 달력
	if ($(".calendar").length) {
		orderCalendar();
	}

	//첨부파일 레이어 팝업
	if ($(".lyAttachedFile ul li").length) {
		attachedFileDownload();
	}


	if ($(".sortMove").length > 0) {
		sortMove();
	}


	if ($(".replyWrap").length) {
		relayTalkReply();
	}

	$("#btnGnbOpen, #btnGnbClose").click(function () {
		$(".sub_ctg_dep").toggle();
		$(".gnb li").eq(0).toggleClass(function () {
			return "sub_ctg_on";
		});
	});

	$(document).on({
		'click': function (e) {
			if ($('.sub_ctg_dep').has(e.target).length === 0 && e.target.id !== 'btnGnbOpen' && e.target.className !== 'ctg_dep_tit') {
				$(".sub_ctg_dep").hide();
				$(".gnb li").eq(0).removeClass("sub_ctg_on");
			}
		}
	});

});//////////////// document.ready()


var $listOrderWrap = $(".listOrderWrap"); //정기배송 우측 패널

$(window).on("scroll", function () {

	gnbFix();
	quickFix();

	$listOrderWrap = $(".listOrderWrap");
	if ($listOrderWrap.length > 0) {
		listOrderFix();
		//listOrderSize();
	}

});//////////// window.scroll


/***************************************************************
 FUNCTION
 ***************************************************************/


// 상단으로 이동
function gotoTop() {
	//var gotoTop = $("#toTop");
	var gotoTop = $(".quick.qRight a.gotoTop");
	gotoTop.click(function (e) { //상단으로 이동
		e.preventDefault();
		$('body,html').animate({scrollTop: 0}, 200);
		return false;
	});
}


// gnb 메뉴바 화면 상단  고정
function gnbFix() {
	var scrollY = window.pageYOffset || document.documentElement.scrollTop;
	var $gnbMenu = $("#gnb");
	var fixedStartY = $gnbMenu.offset().top;

	if (scrollY > fixedStartY) {
		$gnbMenu.addClass("fixed");
	} else {
		$gnbMenu.removeClass("fixed");
	}
}

//퀵메뉴 고정
function quickFix() {
	var scrollY = window.pageYOffset || document.documentElement.scrollTop;
	var $gnbMenu = $("#gnb");
	var $quickMenu = $(".quick");
	var fixedStartY = $gnbMenu.offset().top;

	if ($(".mainBanner").html() != undefined) {
		fixedStartY = 700;
	}

	if (scrollY > fixedStartY) {
		$quickMenu.addClass("fixed");
	} else {
		$quickMenu.removeClass("fixed");
	}
}

// gnb Action
function gnb() {
	var gnbMenu = $(".gnb li");
	gnbMenu.on({
		mouseenter: function () {
			var tg = $(this);
			tg.addClass("on");
		},
		mouseleave: function () {
			var tg = $(this);
			tg.removeClass("on");
		}
	});
}


//헤더 상단배너 닫기
function topBannerClose() {
	$("#topAd").animate({height: 0}, 250, function () {
		$(this).hide();
	});
}


// Path(location) 서브 메뉴 열림/닫힘
function pathCategory() {
	var $pathMenu = $(".path > li");
	var $pathSubList = $pathMenu.children(".subList");

	$pathMenu.on({
		mouseenter: function () {
			$(this).addClass("subOn");
		},
		mouseleave: function () {
			$(this).removeClass("subOn");
		}
	});
}


//안내(툴팁) 보기
function tooltipView() {
	var $tooltipBtn = $(".tooltipBtn");

	$tooltipBtn.on({
		mouseenter: function () {
			var $tg = $(this);
			var $tgWrap = $tg.parent();
			var $tgCont = $tg.next(".tooltipLyr");

			$tgWrap.addClass("on");
			if ($tgCont.hasClass("posB")) {
				$tgCont.css("bottom", "20px");
			} else {
				$tgCont.css("top", "20px");
			}
		},
		mouseleave: function () {
			var $tg = $(this);
			var $tgWrap = $tg.parent();
			var $tgCont = $tg.next(".tooltipLyr");

			$tgWrap.removeClass("on");
			/*
			$tgCont.css({
				"bottom":"",
				"top":""
			});*/
		}
	});

	/*
	$tooltipWrap.on({
		mouseenter:function(){
			$(this).addClass("on");
		},
		mouseleave:function(){
			$(this).removeClass("on");
		}
	});*/
}


//옵션선택 함수
function selectPrdtOption() {
	var $selectOptionBtn = $(".selectOption a.selectOptionBtn");
	var $selectOptionWrap = $selectOptionBtn.parent(".selectOption");
	var $prdtOption = $(".optionList li a.optionWrap");//옵션 리스트의 항목

	$selectOptionBtn.on("click", function () {//옵션선택 버튼 클릭시 리스트 열림
		$selectOptionWrap = $(this).parent(".selectOption");

		if ($selectOptionWrap.hasClass("soldOut")) return false;

		$selectOptionWrap.addClass("active");
		$selectOptionWrap.css({
			"z-index": "300"
		});
	});

	$prdtOption.on("click", function (e) { //옵션항목 클릭시
		e.preventDefault();

		var $tgOption = $(this);
		$selectOptionWrap = $tgOption.parents(".selectOption");
		var $optionTxt = $tgOption.children("span").text();//선택된 상품 내용
		var $selectOptionBtnTxt = $selectOptionWrap.children("a.selectOptionBtn").children("span");//옵션 선택 버튼의 text

		//$selectOptionBtnTxt.text($optionTxt);
		$selectOptionWrap.removeClass("active");
	});


	//옵션선택 벗어날 경우 리스트 닫힘
	$selectOptionWrap.on("mouseleave", function () {
		$(this).removeClass("active")
			.css({
				"z-index": ""
			});
	});

}//selectPrdtOption

//열림/닫힘 테이블
function listViewTbl() {
	var $listViewTable = $(".listViewTbl table");
	var $list = $listViewTable.find("tr.tblList");
	var $view = $listViewTable.find("tr.tblView");
	var $listTitle = $list.find("td.tit").children("a");
	var $closeView = $view.find(".closeView");

	$listTitle.on("click", function (e) {//리스트 타이틀(a)
		e.preventDefault();

		var $tgTitle = $(this);
		var $tgList = $tgTitle.parents(".tblList");
		var $tgView = $tgList.next(".tblView");

		$listViewTable = $tgTitle.parents("table");
		$list = $listViewTable.find("tr.tblList");
		$view = $listViewTable.find("tr.tblView");
		$listTitle = $list.find("td.tit").children("a");
		$closeView = $view.find(".closeView");

		if ($tgTitle.hasClass("on")) {
			$tgTitle.removeClass("on");
			$tgList.removeClass("on");
			$tgView.removeClass("on");

		} else {
			$listTitle.removeClass("on");
			$view.removeClass("on");
			$list.removeClass("on");

			$tgTitle.addClass("on");
			$tgView.addClass("on");
			$tgList.addClass("on");
		}
	});

	$closeView.on("click", function (e) {//닫기버튼
		e.preventDefault();
		$(this).parents(".tblView").removeClass("on");
		$(this).parents(".tblView").prev(".tblList").removeClass("on").find("td.tit").children("a").removeClass("on");
	});


}//listViewTbl


//ie8에서 input:checked 처리 - label.on 넣기
function selectWeekday() {
	var $selectWeekday = $(".selectWeekday");

	$selectWeekday.each(function () {
		var $wDay = $(this).find(".weekday");
		var $input = $wDay.children("input");
		var $label = $wDay.children("label");

		$wDay.click(function () {
			$label.removeClass("on");
			$(this).find("label").addClass("on");
		});
	});
}//selectWeekday


//상품상세 영양분석표 레이어 열기/닫기
function openCloseLy() {
	var $closeLyBtn = $(".closeLyBtn");
	var $openLyBtn = $(".openLyBtn");
	$openLyBtn.on("click", function () {
		$(this).next(".lyWrap").show();
	});
	$closeLyBtn.on("click", function () {
		$(this).parent(".lyWrap").hide();
	});
}



//정기배송 주문패널 fix & size 정의
function listOrderFix() {
	var scrollY = window.pageYOffset || document.documentElement.scrollTop;
	var $listOrder = $(".orderArea");
	var fixedStartY = $(".listOrderWrap").offset().top - 55;

	if (scrollY > fixedStartY) {
		$listOrder.addClass("fixed");
	} else {
		$listOrder.removeClass("fixed");
		//listOrderSizeReset();
	}

	listOrderSize();
}

function listOrderSize() {
	var $footer = $("#footer");
	var footerTop = $footer.offset().top - 90;
	var $cart = $(".orderArea");
	var cartTop = $cart.offset().top;
	var cartH = footerTop - cartTop - 2;
	var winH = $(window).height();
	var checkH = footerTop - cartTop;
	var $itemList = $(".selectedItem");
	var otherHeight = $(".plOrderWrap div .cmnt").outerHeight() + $(".plOrderWrap div .deliveryDate").outerHeight() + $(".plOrderWrap div .orderPrice").outerHeight() + $(".plOrderWrap div .orderBtnWrap").outerHeight() + 145;
	var itemListHeight = cartH - otherHeight;

	if (checkH <= (winH - 55)) {
		cartH = footerTop - cartTop - 2;
		itemListHeight = cartH - otherHeight;

		$cart.css({
			"height": cartH
		});
		$itemList.css({
			"height": itemListHeight
		});
	} else {
		cartH = winH - 67;
		itemListHeight = cartH - otherHeight;

		$cart.css({
			"height": cartH
		});
		$itemList.css({
			"height": itemListHeight
		});
	}
}

function listOrderSizeReset() {
	var $cart = $(".orderArea");
	var $itemList = $(".selectedItem");

	$cart.css({
		"height": ""
	});
	$itemList.css({
		"height": ""
	});
}

$(window).resize(function () {
	$listOrderWrap = $(".listOrderWrap");
	if ($listOrderWrap.length > 0) {
		listOrderSize();
	}
});

//레이어팝업 열기
function openLayer(lyName) {
	var $layer = $(lyName);
	var layerH = $layer.outerHeight(true);
	var layerW = $layer.outerWidth(true);
	var winH = $(window).height();
	var $lyCont = $layer.find(".lyContainer");

	if (layerH > winH) {
		$layer.show().css({
			"margin-top": -(winH / 2) + 40,
			"margin-left": -(layerW / 2)
		});
		$lyCont.css("height", (winH - 207));// 207 = 레이어팝업에서 컨텐츠 영역을 제외한 영역의 높이값(127) + 상하 여백(40*2)
	} else {
		$layer.show().css({
			"margin-top": -(layerH / 2),
			"margin-left": -(layerW / 2)
		});
	}

	$layer.before('<div class="lyDim"></div>');

	$("body").css({//body 영역 스크롤 방지
		"overflow": "hidden",
		"height": winH
	});

	if ($("#lyPopWrap").is(":hidden")) {
		$("#lyPopWrap").show();
	}

	//closeLayer();
}

//레이어 팝업 닫기
function closeLayer() {
	var $btnCloseLayer = $(".closeLayer");

	$btnCloseLayer.on("click", function (e) {
		e.preventDefault();
		var $lyDim = $(".lyDim");
		var openLayerNum = $lyDim.length;

		var $tgLayer = $(this).closest("div.lyPop");
		var $tgLayerCont = $tgLayer.find(".lyContainer");

		$tgLayer.hide().css({"margin-top": "", "margin-left": ""});
		$tgLayerCont.css({"height": ""});
		$tgLayer.prev(".lyDim").remove();

		if (openLayerNum == 1) {
			$("body").css({//body 영역 스크롤 풀기
				"overflow": "",
				"height": ""
			});
		}
	});

	// console.log(player);
	// if ("object" == typeof(player)) {
	// 	if (player.B) {
	// 		console.log("stop");
	// 		stopVideo();
	// 	}
	// }
}

//배송메시지 선택/입력
function deliveryMsg() {
	var $msg = $(".deliMsgWrap .msg");
	var $msgList = $(".deliMsgWrap .msgList li");

	$msg.on("click", function () {
		$(this).parent(".deliMsgWrap").children(".msgList").show();
	});

	$msgList.on("click", function () {
		$(this).parents(".msgList").hide();
	});

	$msg.parent(".deliMsgWrap").on("mouseleave", function () {
		$(this).children(".msgList").hide();
	});
}

//페이지체크 : 페이징 없을 경우 공간 삭제함
function pagingCheck() {
	var $paging = $(".paging");
	var $pagingNum = $paging.children().length;
	if ($pagingNum == 0) {
		$paging.addClass("noPaging");
	} else {

	}
}

//정기배송일정현황 - 주문상세내용
function orderCalendar() {
	var $day = $(".day");

	$day.each(function () {
		var index = $(this).parent("td").index();

		if ($(this).find(".orderDetail").length > 0) {
			$(this).addClass("active");

			var num = parseInt(index % 7);

			if (num == 6 || num == 5) {//금,토 왼쪽에서 열림
				$(this).addClass("posL");
			}
		}
	});

	var $orderDay = $(".day.active .order");

	$orderDay.on({
		mouseenter: function () {
			var tg = $(this).find(".orderDetail");
			tg.addClass("on");
		},
		mouseleave: function () {
			var tg = $(this).find(".orderDetail");
			tg.removeClass("on");
		}
	});
}

// 게시판 상세 - 첨부파일 다운로드 레이어 팝업
function attachedFileDownload() {
	var $lyOpen = $(".viewFileList");
	var $fileList = $(".lyAttachedFile");
	var $lyClose = $(".lyAttachedFile .closeLyBtn");

	$lyOpen.click(function (e) {
		e.preventDefault();
		$fileList.show();
	});

	$lyClose.click(function (e) {
		e.preventDefault();
		$fileList.hide();
	});
}


// 1:1상담문의 주문상품선택 레이어 팝업 초기화
function listViewTbl_reset() {
	var $listViewTable = $(".listViewTbl table");
	var $list = $listViewTable.find("tr.tblList");
	var $view = $listViewTable.find("tr.tblView");
	var $listTitle = $list.find("td.tit").children("a");

	$list.removeClass("on");
	$view.removeClass("on");
	$listTitle.removeClass("on");
}

//배너 슬라이더 (탑 우측배너 슬라이더)
function topBnnerSlider(itemID, speed, type) {
	var $slidingBanner = $(itemID);
	var slidingSpeed = 0;
	var slidingType = type;

	var currentIndex = 0;
	var $bannerList = null;
	var bannerImageWidth = 0;
	var $pagingList = null;
	var $selectedPage = null;
	var $btnWrap = null;
	var $btnNext = null;
	var $btnPrev = null;
	var $listWrap = null;
	var timerID = -1;

	function init() {
		$bannerList = $slidingBanner.find(".listW li");
		bannerImageWidth = $bannerList.innerWidth();

		$pagingList = $slidingBanner.find(".pagingW li");
		$btnWrap = $slidingBanner.find(".btnW");

		$btnNext = $slidingBanner.find(".btnNext");
		$btnPrev = $slidingBanner.find(".btnPrev");

		$listWrap = $slidingBanner.find(".listW li a");
		$btnWrap = $slidingBanner.find(".btnW");

		if ($bannerList.length < 2) {
			$btnWrap.hide();
		} else {
			$bannerList.css("opacity", 0);
			initEvent();
			showImageAt(currentIndex);
			startAutoPlay();
		}
	}

	function initEvent() {
		$btnNext.click(function () {
			event.preventDefault();
			nextImage();
		});

		$btnNext.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$btnPrev.click(function () {
			event.preventDefault();
			prevImage();
		});

		$btnPrev.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$pagingList.mouseenter(function () {
			var index = $pagingList.index(this);

			if (index != currentIndex) {
				var direction = (index > currentIndex) ? "next" : "prev";
				showImageAt(index, direction);
			}
		});

		$listWrap.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$pagingList.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$btnWrap.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});
	}

	function nextImage() {
		var index = currentIndex + 1;

		if (index >= $bannerList.length) {
			index = 0;
		}

		showImageAt(index, "next");
	}

	function prevImage() {
		var index = currentIndex - 1;

		if (index < 0) {
			index = $bannerList.length - 1;
		}

		showImageAt(index, "prev");
	}

	function showImageAt(newIndex, direction) {
//		$bannerList.eq(index).css("opacity",1);

		var oldIndex = currentIndex;
		var $oldImage = $bannerList.eq(oldIndex);
		var $newImage = $bannerList.eq(newIndex);

		if (direction != "next" && direction != "prev") { //최초 처리
			normalEffect($oldImage, $newImage);
		} else {
			slideEffect($oldImage, $newImage, direction);
		}
		slideEffect($oldImage, $newImage, direction);


		currentIndex = newIndex;
		setPagingAt(newIndex);
	}

	function normalEffect($oldImage, $newImage) {
		$oldImage.css({
			left: 0,
			opacity: 0,
			zIndex: 0
		});
		$newImage.css({
			opacity: 1,
			zIndex: 1
		});
	}

	function slideEffect($oldImage, $newImage, direction) {
		var newImageStartX = 0;
		var oldImageEndX = 0;

		if (direction == "next") {
			newImageStartX = bannerImageWidth;
			oldImageEndX = -bannerImageWidth;
		}

		if (direction == "prev") {
			newImageStartX = -bannerImageWidth;
			oldImageEndX = bannerImageWidth;
		}

		if (slidingType == "fade") {
			newImageStartX = 0;
			oldImageEndX = 0;
		}

		$newImage.css({
			left: newImageStartX,
			opacity: 0,
			zIndex: 0
		});

		$oldImage.stop().animate({
			left: oldImageEndX,
			opacity: 0,
			zIndex: 0
		});

		$newImage.stop().animate({
			left: 0,
			opacity: 1,
			zIndex: 1
		});
	}

	function setPagingAt(index) {
		if ($selectedPage) {
			$selectedPage.removeClass("select");
		}

		$selectedPage = $pagingList.eq(index);
		$selectedPage.addClass("select");
	}


	function startAutoPlay() {
		if (speed == null) {
			slidingSpeed = 3000;
		} else {
			slidingSpeed = speed;
		}

		if (timerID == -1) {
			timerID = setInterval(function () {
				nextImage();
			}, slidingSpeed);
		}
	}

	function stopAutoPlay() {
		if (timerID != -1) {
			clearInterval(timerID);
			timerID = -1;
		}
	}

	init();
}

// 배너 슬라이더 (header, quick, mainBanner)
function bannerSlider(itemID, speed, type) {
	var $slidingBanner = $(itemID);
	var slidingSpeed = 0;
	var slidingType = type;

	var currentIndex = 0;
	var $bannerList = null;
	var bannerImageWidth = 0;
	var $pagingList = null;
	var $selectedPage = null;
	var $btnWrap = null;
	var $btnNext = null;
	var $btnPrev = null;
	var $listWrap = null;
	var $pasue = null;
	// var $play = null;
	var timerID = -1;

	function init() {
		$bannerList = $slidingBanner.find(".listW li");
		bannerImageWidth = $bannerList.innerWidth();

		$pagingList = $slidingBanner.find(".pagingW li");
		$btnWrap = $slidingBanner.find(".btnW");
		$pasue = $slidingBanner.find("#pause");
		// $play = $slidingBanner.find(".ctrl .ctplay");

		$btnNext = $slidingBanner.find(".ctrl .next");
		$btnPrev = $slidingBanner.find(".ctrl .prev");

		// $btnNext = $slidingBanner.find(".btnNext");
		// $btnPrev = $slidingBanner.find(".btnPrev");

		$listWrap = $slidingBanner.find(".listW li a");
		$btnWrap = $slidingBanner.find(".btnW");

		if ($bannerList.length < 2) {
			$btnWrap.hide();
		} else {
			$bannerList.css("opacity", 0);
			initEvent();
			showImageAt(currentIndex);
			startAutoPlay();
		}
	}

	function initEvent() {
		// $btnNext.mouseenter(function () {
		// 	stopAutoPlay();
		// }).mouseleave(function () {
		// 	startAutoPlay();
		// });
		//
		// $btnPrev.mouseenter(function () {
		// 	stopAutoPlay();
		// }).mouseleave(function () {
		// 	startAutoPlay();
		// });

		$btnNext.click(function () {
			nextImage();
		});

		$btnPrev.click(function () {
			prevImage();
		});

		$pasue.click(function () {
			if ($(this).hasClass("pause")) {
				stopAutoPlay();
				$(this).removeClass("pause");
				$(this).addClass("ctplay");

			} else {
				startAutoPlay();
				$(this).removeClass("ctplay");
				$(this).addClass("pause");
			}

		});

		// $btnWrap.mouseenter(function () {
		// 	stopAutoPlay();
		// }).mouseleave(function () {
		// 	startAutoPlay();
		// });

		// $pagingList.mouseenter(function () {
		// 	var index = $pagingList.index(this);
		//
		// 	if (index != currentIndex) {
		// 		var direction = (index > currentIndex) ? "next" : "prev";
		// 		showImageAt(index, direction);
		// 	}
		// });

		// $listWrap.mouseenter(function () {
		// 	stopAutoPlay();
		// }).mouseleave(function () {
		// 	startAutoPlay();
		// });

		// $pagingList.mouseenter(function () {
		// 	stopAutoPlay();
		// }).mouseleave(function () {
		// 	startAutoPlay();
		// });

		// $pasue.mouseenter(function () {
		// 	stopAutoPlay();
		// }).mouseleave(function () {
		// 	startAutoPlay();
		// });
	}

	function nextImage() {
		var index = currentIndex + 1;

		if (index >= $bannerList.length) {
			index = 0;
		}

		$(".ctrl strong").html(index + 1);
		showImageAt(index, "next");
	}

	function prevImage() {
		var index = currentIndex - 1;

		if (index < 0) {
			index = $bannerList.length - 1;
		}

		$(".ctrl strong").html(index + 1);
		showImageAt(index, "prev");
	}

	function showImageAt(newIndex, direction) {
		//$bannerList.eq(index).css("opacity",1);

		var oldIndex = currentIndex;
		var $oldImage = $bannerList.eq(oldIndex);
		var $newImage = $bannerList.eq(newIndex);

		if (direction != "next" && direction != "prev") { //최초 처리
			normalEffect($oldImage, $newImage);
		} else {
			slideEffect($oldImage, $newImage, direction);
		}

		currentIndex = newIndex;
		$(".ctrl strong").html(currentIndex + 1);
		setPagingAt(newIndex);
	}

	function normalEffect($oldImage, $newImage) {
		$oldImage.css({
			left: 0,
			opacity: 0,
			zIndex: 0
		});
		$newImage.css({
			opacity: 1,
			zIndex: 1
		});
	}

	function slideEffect($oldImage, $newImage, direction) {
		var newImageStartX = 0;
		var oldImageEndX = 0;

		if (direction == "next") {
			newImageStartX = bannerImageWidth;
			oldImageEndX = -bannerImageWidth;
		}

		if (direction == "prev") {
			newImageStartX = -bannerImageWidth;
			oldImageEndX = bannerImageWidth;
		}

		if (slidingType == "fade") {
			newImageStartX = 0;
			oldImageEndX = 0;
		}

		$newImage.css({
			left: newImageStartX,
			opacity: 0,
			zIndex: 0
		});

		$oldImage.stop().animate({
			left: oldImageEndX,
			opacity: 0,
			zIndex: 0
		});

		$newImage.stop().animate({
			left: 0,
			opacity: 1,
			zIndex: 1
		});
	}

	function setPagingAt(index) {
		if ($selectedPage) {
			$selectedPage.removeClass("select");
		}

		$selectedPage = $pagingList.eq(index);
		$selectedPage.addClass("select");
	}


	function startAutoPlay() {
		if (speed == null) {
			slidingSpeed = 3000;
		} else {
			slidingSpeed = speed;
		}

		if (timerID == -1) {
			timerID = setInterval(function () {
				nextImage();
			}, slidingSpeed);
		}
	}

	function stopAutoPlay() {
		if (timerID != -1) {
			clearInterval(timerID);
			timerID = -1;
		}
	}

	init();
}

//메인 슬라이드
function mainBannerSlider(itemID, speed, type) {
	var $slidingBanner = $(itemID);
	var slidingSpeed = 0;
	var slidingType = type;

	var currentIndex = 0;
	var $bannerList = null;
	var bannerImageWidth = 0;
	var $pagingList = null;
	var $selectedPage = null;
	var $btnWrap = null;
	var $btnNext = null;
	var $btnPrev = null;
	var $listWrap = null;
	var $pasue = null;
	var timerID = -1;

	function init() {
		$bannerList = $slidingBanner.find(".slides_container li");
		bannerImageWidth = $bannerList.innerWidth();

		$pagingList = $slidingBanner.find(".pagination li");
		$btnWrap = $slidingBanner.find(".btnW");
		$pasue = $slidingBanner.find(".ctrl .pause");
		$btnNext = $slidingBanner.find(".ctrl .next");
		$btnPrev = $slidingBanner.find(".ctrl .prev");

		//console.log($btnPrev.html());
		//console.log($btnNext.html());

		// $btnNext = $slidingBanner.find(".btnNext");
		// $btnPrev = $slidingBanner.find(".btnPrev");

		$listWrap = $slidingBanner.find(".listW li a");
		$btnWrap = $slidingBanner.find(".btnW");

		if ($bannerList.length < 2) {
			$btnWrap.hide();
		} else {
			$bannerList.css("opacity", 0);
			initEvent();
			showImageAt(currentIndex);
			startAutoPlay();
		}
	}

	function initEvent() {
		$btnNext.click(function () {
			nextImage();
		});

		$btnNext.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$btnPrev.click(function () {
			prevImage();
		});

		$btnPrev.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$pagingList.mouseenter(function () {
			var index = $pagingList.index(this);

			if (index != currentIndex) {
				var direction = (index > currentIndex) ? "next" : "prev";
				showImageAt(index, direction);
			}
		});

		$listWrap.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$pagingList.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$pasue.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});

		$btnWrap.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});
	}

	function nextImage() {
		var index = currentIndex + 1;

		if (index >= $bannerList.length) {
			index = 0;
		}

		showImageAt(index, "next");
	}

	function prevImage() {
		var index = currentIndex - 1;

		if (index < 0) {
			index = $bannerList.length - 1;
		}

		showImageAt(index, "prev");
	}

	function showImageAt(newIndex, direction) {
		//$bannerList.eq(index).css("opacity",1);

		var oldIndex = currentIndex;
		var $oldImage = $bannerList.eq(oldIndex);
		var $newImage = $bannerList.eq(newIndex);

		if (direction != "next" && direction != "prev") { //최초 처리
			normalEffect($oldImage, $newImage);
		} else {
			slideEffect($oldImage, $newImage, direction);
		}

		currentIndex = newIndex;
		setPagingAt(newIndex);
	}

	function normalEffect($oldImage, $newImage) {
		$oldImage.css({
			left: 0,
			opacity: 0,
			zIndex: 0
		});
		$newImage.css({
			opacity: 1,
			zIndex: 1
		});
	}

	function slideEffect($oldImage, $newImage, direction) {
		var newImageStartX = 0;
		var oldImageEndX = 0;

		if (direction == "next") {
			newImageStartX = bannerImageWidth;
			oldImageEndX = -bannerImageWidth;
		}

		if (direction == "prev") {
			newImageStartX = -bannerImageWidth;
			oldImageEndX = bannerImageWidth;
		}

		if (slidingType == "fade") {
			newImageStartX = 0;
			oldImageEndX = 0;
		}

		$newImage.css({
			left: newImageStartX,
			opacity: 0,
			zIndex: 0
		});

		$oldImage.stop().animate({
			left: oldImageEndX,
			opacity: 0,
			zIndex: 0
		});

		$newImage.stop().animate({
			left: 0,
			opacity: 1,
			zIndex: 1
		});
	}

	function setPagingAt(index) {
		if ($selectedPage) {
			$selectedPage.removeClass("select");
		}

		$selectedPage = $pagingList.eq(index);
		$selectedPage.addClass("select");
	}


	function startAutoPlay() {
		if (speed == null) {
			slidingSpeed = 3000;
		} else {
			slidingSpeed = speed;
		}

		if (timerID == -1) {
			timerID = setInterval(function () {
				nextImage();
			}, slidingSpeed);
		}
	}

	function stopAutoPlay() {
		if (timerID != -1) {
			clearInterval(timerID);
			timerID = -1;
		}
	}

	init();
}


//정렬 버튼 클릭시 화면 전환 후 상단으로 이동
function sortMove() {
	var $btnSort = $(".sort li a");
	var $sort = $(".sort");
	var moveTop = $sort.offset().top - 65;

	$('body,html').animate({scrollTop: moveTop}, 1);

}

// footer 공지사항
function footerNotice() {
	var $Notice = $(".footerNotice");
	var $List = $Notice.find("ul").children("li");
	var listH = $List.innerHeight();
	var $btnW = $Notice.find(".btnW");
	var $btnPrev = $btnW.children(".btnPrev");
	var $btnNext = $btnW.children(".btnNext");
	var currentIndex = 0;
	var timerID = -1;

	function initEvent() {
		$btnNext.click(function () {
			nextLi();
		});
		$btnPrev.click(function () {
			prevLi();
		});
		$btnW.click(function (e) {
			e.preventDefault();
		});
		$btnW.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});
		$List.mouseenter(function () {
			stopAutoPlay();
		}).mouseleave(function () {
			startAutoPlay();
		});
	}

	function nextLi() {
		var index = currentIndex + 1;
		if (index >= $List.length) {
			index = 0;
		}
		showListAt(index, "next");
	}

	function prevLi() {
		var index = currentIndex - 1;
		if (index < 0) {
			index = $List.length - 1;
		}
		showListAt(index, "prev");
	}

	function showListAt(newIndex, direction) {
		var oldIndex = currentIndex;
		var $old = $List.eq(oldIndex);
		var $new = $List.eq(newIndex);

		if (direction != "next" && direction != "prev") {
			normalEffect($old, $new);
		} else {
			slideEffect($old, $new, direction);
		}
		currentIndex = newIndex;
	}

	function normalEffect($old, $new) {
		$old.css({top: 0});
		//$new.css({top:listH});
	}

	function slideEffect($old, $new, direction) {
		var newStartY = 0;
		var oldEndY = 0;

		if (direction == "next") {
			newStartY = listH;
			oldEndY = -listH;
		}

		if (direction == "prev") {
			newStartY = -listH;
			oldEndY = listH;
		}

		$new.css({top: newStartY});//신규 초기화
		$old.stop().animate({top: oldEndY});//기존 비활성화
		$new.stop().animate({top: 0});//신규 활성화
	}

	function startAutoPlay() {
		if (timerID == -1) {
			timerID = setInterval(function () {
				nextLi();
			}, 2000);
		}
	}

	function stopAutoPlay() {
		if (timerID != -1) {
			clearInterval(timerID);
			timerID = -1;
		}
	}

	initEvent();
	showListAt(currentIndex);
	startAutoPlay();
}


// 릴레이톡 답글 열림/닫힘
function relayTalkReply() {
	var $btnReply = $(".btnReply");

	$btnReply.click(function (e) {
		e.preventDefault();

		var $btn = $(this);
		var $replay = $btn.parents(".replyTit").siblings(".replyCont");

		$replay.toggle();
	});
}

//셰프의 레시피 탭메뉴
function recipeTab() {
	//alert("a");
	var $recipeTabWrap = $(".recipeTabWrap");
	var $recipeTab = $(".recipeTab");
	var $recipeTabLi = $recipeTab.children().children("li");
	var $btn = $(".btnRcpTab");
	var $fstLi = $recipeTab.children().children("li:nth-child(5n+1)");

	for (i = 0; i < 5; i++) {
	}

	$btn.click(function (e) {
		e.preventDefault();
		var $tg = $(this);
		var $tgLabel = $(this).text();
		console.log($recipeTab.height());

		console.log($tgLabel);
		$recipeTabWrap.toggleClass("on");

		$tg.toggleClass("on");
		if ($tg.hasClass("on")) {
			$tg.text("접기");
		} else {
			$tg.text("펼쳐보기");
		}
	});
}
