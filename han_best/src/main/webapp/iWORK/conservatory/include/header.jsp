<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script type="text/javascript">
var popUpCnt = "${popUpCount}";
var flag = "${flag}";

function setCookie(name, value, expiredays) {
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function getCookie(){
	if(popUpCnt > 0){
		var cookiedata = document.cookie;
		if (cookiedata.indexOf("todayCookie=done") > 0){
			$('.hidden_popup_wrap').removeClass('open');
			$('.bt_popup').css('visibility', 'hidden');
		}
	}
}

function popOpen(){
	$('.hidden_popup_wrap').addClass('open');
}

function popClose(){
	$('.hidden_popup_wrap').removeClass('open');
}

$(document).ready(function() {
	getCookie();
	$('.hidden_popup_top>.bt_popup').click(function(event) {
		popOpen();
	});
	
	$('.popup_bottom_pc>.bt_popup>.close').click(function(event) {
		var onedayClose = $("#oneDayClose").prop("checked");
		if (onedayClose == true) {
			setCookie("todayCookie", "done" , 1);
			$('.bt_popup').css('visibility', 'hidden');
		}
		popClose();
	});
	
	$('.popup_bottom_pc .oneday_close').click(function(event) {
		var onedayClose = $("#oneDayClose").prop("checked");
		if (onedayClose == true) {
			setCookie("todayCookie", "done" , 1);
			$('.bt_popup').css('visibility', 'hidden');
		}
		popClose();
	});
	
	
	$('.header_inner_mobile .bt_popup').click(function(event) {
		popOpen();
	});
	
	$('.popup_bottom_mobile ul li').click(function(event) {
		var inx = $(".popup_bottom_mobile ul li").index(this);
		 if(0 == inx){
			setCookie("todayCookie", "done" , 1);
			$('.bt_popup').css('visibility', 'hidden');
		}
		popClose();
	});
	
	
	//
	var floatPosition = parseInt($("#floatMenu").css('top'));
	$(window).scroll(function() {
		var scrollTop = $(window).scrollTop();	
		 if(0 < scrollTop){
			$("#header").css("background-color", "rgba(0, 0, 0, 0.7)");
		}else{
			$("#header").css("background-color", "rgba(0, 0, 0, 0)");
		}
		
		var newPosition = scrollTop + floatPosition + "px";
		$("#floatMenu").stop().animate({
			"top" : newPosition
		}, 500);

	}).scroll();
	
	var responsive;
	$(window).on('load', function() {
		setResponsive();
	});

	$(window).on('resize', function() {
		setResponsive();
	});
});

function setResponsive() {
	var windowWidth = $(window).width();
	if(windowWidth < 1200) {
		console.log('모바일 화면 입니다.');
		if(windowWidth < 768){
			$("#search_select").click(function() {
				$("#select_item").show();
			});
		}
		
		//모바일 메뉴열기
		$('.header_inner_mobile .bt_nav_open').click(function(event) {
			$('.gnb_mobile').addClass('on');
		});
		
		//모바일 메뉴닫기
		$('.gnb_mobile_header .bt_nav_open').click(function(event) {
			$('.gnb_mobile').removeClass('on');
		});
		
		$(".gnb_inner .gnb_depth1 > li").click(function() {
			if ($(this).hasClass("on")) {
				$(this).removeClass("on");
				$(this).find(".gnb_depth2").hide();
			} else {
				$('.gnb_inner .gnb_depth1 > li').removeClass("on");
				$('.gnb_mobile .nav .gnb_depth2').hide();
				$(this).addClass("on");
				$(this).find(".gnb_depth2").show();
			}
		});
		
	} else {
		console.log('데스크탑 화면 입니다.');
		$('#nav').bind({
			"mouseenter focusin": function() {		
				$(".gnb_inner > .nav > ul> li > div").css("display","block");
				$(".gnb_inner > .nav > ul> li > a").css("color","#000");
				$(".nav").addClass("white");
				$(".header_inner_pc > h1 > a").css({ 'background' : 'url(/images/front/common/h1_logo_on.png) no-repeat 0 50%','background-size' : '100% 32px'});
				$(".header_inner_pc > .graduate > a").css({ 'background' : 'url(/images/front/common/graduate_on.png) no-repeat'});
				$(".hidden_popup_top > .bt_popup > .open").css({ 'color' : '#f48729','background' : 'url(/images/front/common/bt_ico_popup_on.png) no-repeat 100% 50%'});
			},
			"mouseleave focusout": function() { 
				$(".gnb_inner > .nav > ul > li > div").css("display","none");
				$(".gnb_inner > .nav > ul> li > a").css("color","#fff");
				$(".nav").removeClass("white");
				$(".header_inner_pc > h1 > a").css({ 'background' : 'url(/images/front/common/h1_logo.png) no-repeat 0 50%','background-size' : '100% 32px'});
				$(".header_inner_pc > .graduate > a").css({ 'background' : 'url(/images/front/common/graduate.png) no-repeat'});
				$(".hidden_popup_top > .bt_popup > .open").css({ 'color' : '#ffffff', 'opacity' : '0.5','background' : 'url(/images/front/common/bt_ico_popup_on.png) no-repeat 100% 50%'});
			}
		});
	}
}
</script>
<c:if test="${popUpCount > 0 }">
	<div id="hidden_popup">
		<div class="hidden_popup_top">
			<div class="bt_popup"><a href="#" title="팝업창 열기" class="open">팝업 열기</a></div>
		</div>
		<div class="hidden_popup_wrap <c:if test="${'main' eq flag }">open</c:if>"><!-- display:none 기본값, open 클래스로 활성화 -->
			<div class="hidden_inner">
				<div class="hidden_popup_list">
					<c:forEach items="${PopUpVO_List }" var="popUp">
						<div id="color01" class="popup_conts">
							<ul>
								<li class="txt_head"><span class="txt_field">${popUp.title }</span></li>
								<li class="txt_year"><span class="txt_field">${popUp.contents }</span></li>
							</ul>
							<c:if test="${'Y' eq popUp.linkYn }">
								<a href='<c:url value="${popUp.linkUrl }"/>' title="페이지 이동" class="txt_link"><span>바로 가기</span></a>
							</c:if>
						</div>
					</c:forEach>
				</div>
				<div class="popup_bottom_pc">
					<div class="oneday">
						<input type="checkbox" id="oneDayClose" name="oneDayClose"><label for="oneDayClose" class="pl10">오늘하루 이창을 열지 않겠습니다.</label><button type="button" class="oneday_close">닫기</button>
					</div>
					<div class="bt_popup"><a href="#" title="팝업창 닫기" class="close">팝업 닫기</a></div>
				</div>
				<div class="popup_bottom_mobile">
					<ul>
						<li><a href="#" title="오늘하루 닫기" class="close">오늘하루 닫기</a></li>
						<li><a href="#" title="팝업창 닫기" class="close">닫기</a></li>
					</ul>
				</div>
			</div>
			<!-- // hidden_inner -->
		</div>
		<!-- // hidden_popup_wrap -->
	</div>
	<!-- // hidden_popup -->
</c:if>
<div id="header" class="animation fadeInDown">
	<div class="header_inner">
		<div class="header_inner_mobile">
			<h1 class="h1_logo"><a href="/">글로벌문화예술교육원</a></h1>
			<div class="bt_group">
				<c:if test="${popUpCount > 0 }">
					<div class="bt_popup"><a href="#" title="팝업열기">팝업열기</a></div>
				</c:if>
				<div class="bt_nav_open trans300"><a href="#" title="메뉴열기">전체메뉴 열기</a></div>
			</div>
		</div>
	</div>
	
	<div class="gnb">
		<div id="gnb_mobile" class="gnb_mobile"><!-- display:none 기본값, on 클래스로 활성화 -->
			<div class="gnb_mobile_header">
				<h1 class="h1_logo"><a href="#">글로벌문화예술교육원</a></h1>
				<div class="bt_group">
					<c:if test="${popUpCount > 0 }">
						<div class="bt_popup"><a href="#" title="팝업열기">팝업열기</a></div>
					</c:if>
					<div class="bt_nav_open"><a href="#" title="메뉴열기">전체메뉴 열기</a></div>
				</div>
			</div>
			<div class="gnb_inner trans400" id="nav">
				<div class="header_inner_pc">
					<h1 class="h1_logo"><a href="/">글로벌문화예술교육원</a></h1>
					<div class="graduate"><a href="#">학점은행제 학사학위과정 평가인정 훈련기관</a></div>
				</div>
				
				<div class="nav trans400">
					<ul class="gnb_depth1">
						<c:forEach var="menuVOOne" items="${menuList}" varStatus="status">
							<li>
								<c:if test="${cmutls:contains(curMenuSeqPath, menuVOOne.seq)}">
									<c:set var="menuTitle" value="${menuVOOne.title }" scope="request" />
									<c:set var="menuCount" value="${status.count}" scope="request" /> 
								</c:if>
								<c:set var="linkUrl" value="#" scope="request" />
								<c:choose>
									<c:when test="${empty menuVOOne.children[0]}">
										<c:choose>
											<c:when test="${'Link' eq menuVOOne.type}">
												<c:set var="linkUrl" value="${menuVOOne.url}" scope="request" />
											</c:when>
											<c:otherwise>
												<c:set var="linkUrl" value="${menuVOOne.type}.do?menuSeq=${menuVOOne.seq}&configSeq=${menuVOOne.configSeq}" scope="request" />
											</c:otherwise>
										</c:choose>
										<c:set var="linkTarget" value="${menuVOOne.linkTarget}" scope="request" />
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${'Link' eq menuVOOne.children[0].type}">
												<c:set var="linkUrl" value="${menuVOOne.children[0].url}" scope="request" />
											</c:when>
											<c:otherwise>
												<c:set var="linkUrl" value="${menuVOOne.children[0].type}.do?menuSeq=${menuVOOne.children[0].seq}&configSeq=${menuVOOne.children[0].configSeq}" scope="request" />
											</c:otherwise>
										</c:choose>
										<c:set var="linkTarget" value="${menuVOOne.children[0].linkTarget}" scope="request" />
									</c:otherwise>
								</c:choose>
								<a href="#" class="link_gnb" data-itsp-link-url="${linkUrl }" data-itsp-link-target="${linkTarget }"><span>${menuVOOne.title}</span></a>
								<c:set var="menuVO" value="${menuVOOne}" scope="request" /> 
								<c:set var="menuList" value="${menuVOOne.children}" scope="request" />
								<div class="gnb_div">
									<jsp:include page="/iWORK/conservatory/include/headerMenuListTmpl.jsp" />
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>	
</div>