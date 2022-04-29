<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<%@ include file="/iWORK/conservatory/include/meta.jsp"%>
<%@ include file="/iWORK/conservatory/include/link.jsp"%>
<link href="/css/front/main.css" rel="stylesheet" type="text/css">
<link href="/css/front/keyframes.css" rel="stylesheet" type="text/css">
<link type="text/css" href="/common/js/bxslider/jquery.bxslider.css" rel="stylesheet">
<%@ include file="/iWORK/conservatory/include/script.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	var slider;
	var pagerTypeResult;
	
	$(window).on("orientationchange resize", configureSlider); 
	configureSlider(); 
	
	function configureSlider() {
		var windowWidth = $(window).width();
		if (windowWidth < 768) { 
			visualListAjax("MOBILE", windowWidth);
		}else{
			visualListAjax("WEB", windowWidth);
		}
	}
	
	function visualListAjax(key, width) {
		itsp.ajax.doPostJSON('/Visual/selectListData', {}, function(data) {
			var dataList = data.body;
			var dataListObj = $('.bxslider');
			dataListObj.empty();
			for (var inx=0; inx<dataList.length; inx++) {
				var info = dataList[inx];
				var html = '';
				for(var i=0; i<info.attachFileList.length; i++){
					var file = info.attachFileList[i];
					var exp = /WEB/;
					if("MOBILE" == key){
						exp = /MOBILE/;
					}
					if(exp.test(file.explanation)){
						html += '	<li class="main_picture" style="background-image:url(/file/imgResizeView.do?seq='+file.seq+');" >';
						if(info.linkUrl == "#"){
							html += '	<a href="#" title="바로가기">';
						}else{
							html += '	<a href="'+ info.linkUrl+'" title="바로가기" target="'+info.linkTarget+'">';
						}
						html += '	<img src="/images/front/main/main_blank.png" alt="'+ info.title+'">';
						html += '	</a></li>';
						dataListObj.append(html);
					}
				}
			};
		
			var config = buildSliderConfiguration();
			slider.reloadSlider(config);
		});
	};
	
	function buildSliderConfiguration() {
		var windowWidth = $(window).width();
		var autoControlsCombineResult;
		var pagerTypeResult;
		
		if (windowWidth < 768) { 
			autoControlsCombineResult = true;
			pagerTypeResult = 'short';
		} else {
			autoControlsCombineResult = true;
			pagerTypeResult = '';
		}
		
		return {
			auto: true,
			mode:'fade',
			controls:true,
			autoControls: true, 
			stopAutoOnClick: true,
			pager:true,
			
			autoControlsCombine : autoControlsCombineResult,
			pagerType :pagerTypeResult,
			pagerShortSeparator :'		'
		}; 
	} 
	
	slider = $('.bxslider').bxSlider({
		auto: true,
		mode:'fade',
		controls:true,
		autoControls: true, 
		stopAutoOnClick: true,
		pager:true,
		autoControlsCombineResult : true,
		pagerTypeResult : pagerTypeResult,
		pagerShortSeparator :' '
	});
});
</script>

<script type="text/javascript" src="/common/js/jQuery/jquery.ticker.js"></script>
<script type="text/javascript" src="/common/js/jquery.animateNumber.min.js"></script>
<script type="text/javascript">	
	$(document).ready(function() {
		$('#ticker').tickerme();
		
		var comma_separator_number_step = $.animateNumber.numberStepFactories.separator(',');
		$('#figure02').animateNumber(
			{
				number: 8830,
				numberStep: comma_separator_number_step
			}
		);
	
		$('#figure01').animateNumber({number:2004});
		$('#figure03').animateNumber({number:2082});
		$('#figure04').animateNumber({number:119});
		$('#figure05').animateNumber({number:173});
		$('#figure06').animateNumber({number:122});
		$('#figure07').animateNumber({number:111});
		$('#figure08').animateNumber({number:3});
	});
</script>
<script src="/common/js/jquery.malihu.PageScroll2id.min.js"></script>
<script type="text/javascript">
	(function($) {
	
		$(window).load(function() {
			$("#navigation a").mPageScroll2id();
			scrollSpeed:900
		});
	})(jQuery);
</script>
</head>
<body>
	<%@ include file="/iWORK/conservatory/include/u_skip.jsp"%>
	<%@ include file="/iWORK/conservatory/include/header.jsp"%>
	<!-- //header -->
	<div id="container">
		<div class="main_container">
			<section id="main_slide">
				<h3 class="blind">슬라이드</h3>
				<div class="main_visual">
					<ul class="bxslider visual_slide" ></ul>
				</div>
				<div id="main_notice">
					<div class="main_notice_inner">
						<h1><span>공지사항</span></h1>
						<ul class="ticker_list" id="ticker">
							<c:forEach items="${BoardVOList49 }" var="data">
							<c:set var="tempRegDate" value="${fn:replace(data.regDate , '-', '')}" />
							<fmt:parseNumber integerOnly="true" type="number" value="${tempRegDate}" var="regDate" />
								<li class="subject">
									<a href="/BoardView.do?menuSeq=87&configSeq=49&seq=${data.seq }" class="subject_click"><c:if test="${not empty data.categoryName}">[${data.categoryName }]</c:if>${data.title }</a>
									<span class="write_date">${data.regDate}</span>
								</li>
							</c:forEach>
							<c:if test="${empty BoardVOList49 }">
								<li class="subject">
									<a href="#" class="subject_click">등록 된 데이터가 없습니다.</a>
								</li>
							</c:if>
						</ul>
						<c:if test="${fn:length(BoardVOList49) > 0}">
							<div class="bt_control">
								<a href="#" title="이전글" class="bt_prev">이전글</a>
								<a href="#" title="다음글" class="bt_next">다음글</a>
							</div>
						</c:if>
					</div>
				</div>
			</section>
			
			<section id="main_about">
				<h1 class="animation fadeInDown" id="animation1">오직예술</h1>
				<div class="main_about_inner">
					<div class="about_video">
						<div class="about_video_text">
							<dl>
								<dt>추계예술대학교 글로벌문화예술교육원은</dt>
								<dd>
									<span>젊은 예술인들이 꿈을 펼치는, 21세기 문화 예술의 상아탑,</span>
									<span>지식과 기량의 예술교육이 조화를 이루는 곳!</span>
									<span>미래지향적이고 창조적인 전문가를 양성하는 세계의 중심으로 </span>
									<span>향하는 시작점입니다.</span>
									</dd>
							</dl>
						</div>
						<div class="about_video_clip">
							<div class="vod">
								<video src="/iWORK/conservatory/file/cgcChugye.mp4" controls width="100%" height="auto">비디오 태그를 지원하지 않는경우<br />여기를<a href="/iWORK/conservatory/file/cgcChugye.mp4">클릭</a>해주세요.</video>
							</div>
						</div>
					</div>
					<div class="about_figure">
						<div class="about_figure_detail">
							<div class="figure_item">
								<ul>
									<li class="figure_tit">설립년도</li>
									<li class="figure_ico">
										<img src="/images/front/main/ico_info01.png">
									</li>
									<li class="figure_txt">
										<span id="figure01" class="em">0</span>년
									</li>
								</ul>
							</div>
							<div class="figure_item">
								<ul>
									<li class="figure_tit">연면적</li>
									<li class="figure_ico">
										<img src="/images/front/main/ico_info02.png">
									</li>
									<li class="figure_txt">
										<span id="figure02" class="em">0</span>m<sup>2</sup>
									</li>
								</ul>
							</div>
							<div class="figure_item">
								<ul>
									<li class="figure_tit">학습자수</li>
									<li class="figure_ico">
										<img src="/images/front/main/ico_info03.png">
									</li>
									<li class="figure_txt">
										<span id="figure03" class="em">0</span>명
									</li>
								</ul>
							</div>
							<div class="figure_item">
								<ul>
									<li class="figure_tit">교원수</li>
									<li class="figure_ico">
										<img src="/images/front/main/ico_info04.png">
									</li>
									<li class="figure_txt">
										<span id="figure04" class="em">0</span>명
									</li>
								</ul>
							</div>
							<div class="figure_item">
								<ul>
									<li class="figure_tit">보유과목</li>
									<li class="figure_ico">
										<img src="/images/front/main/ico_info05.png">
									</li>
									<li class="figure_txt">
										<span id="figure05" class="em">0</span>과목
									</li>
								</ul>
							</div>
							<div class="figure_item">
								<ul>
									<li class="figure_tit">스튜디오&middot;실습실</li>
									<li class="figure_ico">
										<img src="/images/front/main/ico_info06.png">
									</li>
									<li class="figure_txt">
										<span id="figure06" class="em">0</span>개
									</li>
								</ul>
							</div>
							<div class="figure_item">
								<ul>
									<li class="figure_tit">학습과정별 개설학급수</li>
									<li class="figure_ico">
										<img src="/images/front/main/ico_info08.png">
									</li>
									<li class="figure_txt">
										<span id="figure07" class="em">0</span>개
									</li>
								</ul>
							</div>
							<div class="figure_item">
								<ul>
									<li class="figure_tit">공연장</li>
									<li class="figure_ico">
										<img src="/images/front/main/ico_info07.png">
									</li>
									<li class="figure_txt">
										<span id="figure08" class="em">0</span>개
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			
			</section>
			
			<section id="main_department">
				<h1 class="animation fadeInDown">계열안내</h1>
				<div class="department_text">학점은행제 추계예술대학교 글로벌문화예술교육원 계열안내 정보입니다.</div>
				<div class="department_inner">
					<div class="college">
						<dl>
							<dt><a href="/Major.do?menuSeq=160&configSeq=740">실용음악 계열</a></dt>
							<dd>
								<span>보컬</span>
								<span>싱어송라이터</span>
								<span>K-pop</span>
								<span>힙합</span>
								<span>기악</span>
								<span>컴퓨터음악/작곡</span>
								<span>음향</span>
							</dd>
						</dl>
					</div>
					<div class="college">
						<dl>
							<dt><a href="/Major.do?menuSeq=161&configSeq=280">무용 계열</a></dt>
							<dd>
								<span>순수무용</span>
								<span>스트릿댄스</span>
								<span>실용무용</span>
							</dd>
						</dl>
					</div>
					<div class="college">
						<dl>
							<dt><a href="/Major.do?menuSeq=163&configSeq=783">문예창작 계열</a></dt>
							<dd>
								<span>웹소설</span>
								<span>영상스토리</span>
							</dd>
						</dl>
					</div>
					<div class="college">
						<dl>
							<dt><a href="/Major.do?menuSeq=162&configSeq=781">CCM 계열</a></dt>
							<dd>
								<span>워십리더</span>
								<span>보컬</span>
								<span>피아노</span>
								<span>기악 (기타, 베이스, 드럼)</span>
								<span>작곡</span>
							</dd>
						</dl>
					</div>
					<div class="college">
						<dl>
							<dt><a href="/Major.do?menuSeq=164&configSeq=785">뮤지컬 계열</a></dt>
							<dd>
								<span>뮤지컬</span>
							</dd>
						</dl>
					</div>
					<div class="college">
						<dl>
							<dt><a href="/Major.do?menuSeq=166&configSeq=789">연기 계열</a></dt>
							<dd>
								<span>연극&middot;연기</span>
								<span>연출</span>
								<span>촬영&middot;조명</span>
								<span>편집&middot;CG특수효과</span>
								<span>마케팅&middot;배급</span>
							</dd>
						</dl>
					</div>
					<div class="college">
						<dl>
							<dt><a href="/Major.do?menuSeq=165&configSeq=787">모델 계열</a></dt>
							<dd>
								<span>광고모델</span>
								<span>패션모델</span>
							</dd>
						</dl>
					</div>
					
					<%--
					<div class="college">
						<dl>
							<dt><a href="#">K-POP 계열</a></dt>
							<dd>
								<span>연기</span>
								<span>방송</span>
							</dd>
						</dl>
					</div>
					 --%>
				</div>
			</section>
			
			<section id="main_gallery">
				<h1 class="animation fadeInDown">행사 갤러리</h1>
				<div class="main_gallery_inner">
					<ul class="list_thumb">
						<c:forEach items="${BoardVOList }" var="data">
							<c:set var="tempRegDate" value="${fn:replace(data.regDate , '-', '')}" />
							<fmt:parseNumber integerOnly="true" type="number" value="${tempRegDate}" var="regDate" />
							<li class="list_thumb_item">
								<div class="list_thumb_cont">
									<a href="/BoardView.do?menuSeq=${'170' eq  data.configSeq ? '309' : '311'}&configSeq=${data.configSeq }&seq=${data.seq }" class="thumb_link">
										<div class="thumb_img">
											<c:choose>
												<c:when test="${'movie' eq data.configType }">
													<c:choose>
														<c:when test="${'#' ne data.url && not empty data.url }">
															<iframe id="ytplayer_${seq}" type="text/html" width="100%" height="100%" src="https://www.youtube.com/embed/${fn:substringAfter(data.url, 'be/') }?rel=0&amp;controls=1&amp;showinfo=0" frameborder="0" allowfullscreen> </iframe>  
														</c:when>
														<c:otherwise>
															<img src="<c:url value="/file/imgResizeView.do?seq=${data.attachFileList[0].seq}"/>" onerror="javascript:this.src='/images/front/sub/album_blank_image.png'" alt="">
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${not empty data.attachFileList}">
															<c:set var="photoListLoop" value="false" />
															<c:forEach var="file" items="${data.attachFileList}">
																<c:if test="${not photoListLoop}">
																	<c:if test="${ file.fileType eq 'jpeg' || file.fileType eq 'pjpeg' || file.fileType eq 'jpg' || file.fileType eq 'gif' || file.fileType eq 'png' || file.fileType eq 'bmp' }">
																		<img alt="${file.realFileName}" src="/file/imgResizeView.do?seq=${file.seq }" onerror="javascript:this.src='/images/front/sub/album_blank_image.png'" width="100%" height="100%" alt=""/>
																		<c:set var="photoListLoop" value="true" />
																	</c:if>
																</c:if>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<div data-itsp-temp>
																<div style="visibility: hidden; width: 0px; height: 0px" data-itsp-temp-contents><c:out value="${data.contents}" escapeXml="false"/></div>
																<img src="" onerror="javascript:this.src='/images/front/sub/album_blank_image.png'" width="100%" height="100%">
															</div>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</div>
									</a>
									<div class="subject">
										<div class="subject_click"><c:if test="${not empty data.categoryName}"> [${data.categoryName }] </c:if><c:out value="${data.title}"/></div>
										<div class="write_date">${data.regDate}</div>
									</div>
								</div>
								<!-- //list_thumb_cont -->
							</li>
							<!-- //list_thumb_item -->
						</c:forEach>
						<script type="text/javascript">
							$(document).ready(function() {
								$("[data-itsp-temp]").each(function(){
									var imgSrc = $(this).find('[data-itsp-temp-contents]').find('img').eq(0).attr('src');
									if(imgSrc != "" && imgSrc != null && typeof imgSrc != "undefined"){
										$(this).find('img').last().attr("src", imgSrc);
									}
								});
							});
						</script>
					</ul>
					<div class="bt_more"><a href="/BoardList.do?menuSeq=309&configSeq=170" title="전체보기">더보기</a></div>
				</div>
				<!-- //main_gallery_inner -->
			</section>
			
			<section id="main_facilities">
				<h1 class="animation fadeInDown">시설안내</h1>
				<div class="main_facilities_inner">
					<div class="main_facilities_txt animation fadeInDown">추계예술대학교 글로벌문화예술교육원은 계열별 실습실 및 학생 편의시설을 갖추고 있습니다.</div>
					<a href="/Facility.do?menuSeq=153&configSeq=179" class="bt_link animation fadeInDown" title="시설안내 전체보기"><span>보러가기</span></a>
				</div>
			</section>
			
			<section id="main_post">
				<div class="main_post_inner">
					<div class="post_list">
						<h1>행사안내</h1>
						<ul>
							<c:forEach items="${BoardVOList51 }" var="data">
							<c:set var="tempRegDate" value="${fn:replace(data.regDate , '-', '')}" />
							<fmt:parseNumber integerOnly="true" type="number" value="${tempRegDate}" var="regDate" />
								<li class="post_tit">
									<a href="/BoardView.do?menuSeq=169&configSeq=51&seq=${data.seq }">
										<div class="subject <c:if test="${today < regDate }">new</c:if>">
											<span class="subject_click">
											<c:if test="${not empty data.categoryName}"><strong>[${data.categoryName }]</strong></c:if>
											<c:out value="${data.title}"/>
											</span>
										</div>
										<div class="subject_info"><span class="write_date">${data.regDate}</span></div>
									</a>
								</li>
							</c:forEach>
						</ul>
						<div class="bt_more"><a href="/BoardList.do?menuSeq=169&configSeq=51" title="행사안내 전체보기">더보기</a></div>
					</div>
					
					<div class="post_schedule">
						<h1>학사일정</h1>
						<ul>
							<c:forEach var="data" items="${ScheduleList}" varStatus="status">
								<c:if test="${status.index < 5 }">
									<li class="post_tit">
										<div class="subject_info"><span class="write_date">${data.startYear}-${data.startMonth}-${data.startDay}</span></div>
										<div class="subject"><a href="/Schedule.do?menuSeq=268&configSeq=263&year=${data.startYear}" class="subject_click">${data.contents}</a></div>
									</li>
								</c:if>
							</c:forEach>
						</ul>
						<div class="bt_more"><a href="/Schedule.do?menuSeq=268&configSeq=263" title="학사일정 전체보기">더보기</a></div>
					</div>
					
					<div class="application animation fadeInRight">
						<ul class="application_inner">
							<li><a href="https://www.youtube.com/channel/UCw1z21mzUAJs12eate-pWzw?view_as=subscriber" target="_blank" title="유튜브- 페이지이동"><span class="sns icon_ytube"></span>유튜브 바로가기</a></li>
							<li><a href="https://www.facebook.com/chugyeCUFA" target="_blank" title="페이스북 - 페이지이동"><span class="sns icon_facebook"></span>페이스북 바로가기</a></li>
							<li><a href="https://www.instagram.com/chugyegica" target="_blank" title="인스타그램 - 페이지이동"><span class="sns icon_insta"></span>인스타그램 바로가기</a></li>
							<li><a href="https://blog.naver.com/chugyegica" target="_blank" title="블로그 - 페이지이동"><span class="sns icon_blog"></span>블로그 바로가기</a></li>
						</ul>
					</div>
				</div>
			</section>
		</div>
		<!-- //main_container -->
		
		<aside>
			<div class="main_section">
				<ul id="navigation">
					<li><a href="#container" title="처음으로 이동"><span class="rightbar-bullet-txt">홈</span></a></li>
					<li><a href="#main_about" title="교육원소개 이동"><span class="rightbar-bullet-txt">교육원소개</span></a></li>
					<li><a href="#main_department" title="계열안내로 이동"><span class="rightbar-bullet-txt">계열안내</span></a></li>
					<li><a href="#main_gallery" title="행사갤러리로 이동"><span class="rightbar-bullet-txt">행사갤러리</span></a></li>
					<li><a href="#main_facilities" title="시설안내로 이동"><span class="rightbar-bullet-txt">시설안내</span></a></li>
					<li><a href="#main_post" title="시설안내로 이동"><span class="rightbar-bullet-txt">행사안내</span></a></li>
				</ul>
			</div>
			<%@ include file="/iWORK/conservatory/include/floatMenu.jsp"%>
		</aside>
	</div>
	<!-- // container -->
	<%@ include file="/iWORK/conservatory/include/footer.jsp"%>
	
<script type="text/javascript" src="/common/js/bxslider/jquery.bxslider.js"></script>
</body>
</html>