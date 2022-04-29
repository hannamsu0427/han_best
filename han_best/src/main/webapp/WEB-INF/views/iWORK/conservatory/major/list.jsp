<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<%@ include file="/iWORK/conservatory/include/meta.jsp"%>
<%@ include file="/iWORK/conservatory/include/link.jsp"%>
<link href="/css/front/sub.css" rel="stylesheet" type="text/css">
<%@ include file="/iWORK/conservatory/include/script.jsp"%>
</head>
<body>
	<%@ include file="/iWORK/conservatory/include/u_skip.jsp"%>
	<%@ include file="/iWORK/conservatory/include/header.jsp"%>
	<!-- //header -->
	<div id="container">
		<div class="sub_container" id="major_wrap">
			<section>
				<article>
					<c:if test="${not empty about.attachFileList}">
						<script type="text/javascript">
							$(document).ready(function() {
								$(".visual01").css("background" , "transparent url(/file/imgView.do?seq=${about.attachFileList[0].seq}) no-repeat 50% 0");
							});
						</script>
					</c:if>
					<div class="page_header visual01">
						<div class="page_header_inner">
							<%@ include file="/iWORK/conservatory/include/location.jsp"%>
							<h2 class="gnb_depth1_tit"><c:out value="${curTitle }"/></h2>
						</div>
					</div>
				</article>
				
				<article class="page_conts">
					<div class="page_conts_tit">
						<h3 class=""><c:out value="${curTitle }"/></h3>
						<div class="page_top">
							<c:if test="${'Y' eq about.icoCurriculum }">
								<a href="#major_course" class="bt_ico" title="교과과정 이동"><span class="ico_curriculum">교과과정보기</span></a>
							</c:if>
							<c:if test="${'Y' eq about.icoApply }">
								<a href="#" class="bt_ico" title="원서접수 이동"><span class="ico_apply">원서접수</span></a>
							</c:if>
						</div>
					</div>
			
					<div id="major_about">
						<div class="about_inner">
							<span class="major_name"><c:out value="${about.majorName}"/></span>
							<div class="major_about_txt">${cmutls:nl2br(about.majorAboutTxt) }</div>
							<c:if test="${not empty about.dash }">
								<h4>취득가능 학위</h4>
								<ul class="dash">
									<c:forTokens items="${about.dash }" delims="||" var="dash">
										<li><c:out value="${cmutls:removeScript(dash) }"/></li>
									</c:forTokens>
								</ul>
							</c:if>
						</div>
					</div>
					<!-- //major_about -->
					
					<c:if test="${not empty introList}">
						<div id="major_intro">
							<h4>전공소개</h4>
							<div class="sns_link">
								<c:if test="${'Y' eq about.icoYtube }"><a href="${about.ytubeUrl }" target="_blank" title="새창으로 이동" class="ico_ytube">유투브</a></c:if>
								<c:if test="${'Y' eq about.icoFace }"><a href="${about.faceUrl }" target="_blank" title="새창으로 이동" class="ico_face">페이스북</a></c:if>
								<c:if test="${'Y' eq about.icoInsa }"><a href="${about.insaUrl }" target="_blank" title="새창으로 이동" class="ico_insa">인스타그램</a></c:if>
								<c:if test="${'Y' eq about.icoBlog }"><a href="${about.blogUrl }" target="_blank" title="새창으로 이동" class="ico_blog">블로그</a></c:if>
							</div>
							<div class="intro_inner">
								<div class="intro_list column">
									<c:forEach items="${introList}" var="intro">
										<div class="intro_inner_item">
											<div class="foremost">
												<c:if test="${not empty intro.attachFileList}"><img src="<c:url value="/file/fileDownLoad.do?seq=${intro.attachFileList[0].seq}"/>" alt="${intro.attachFileList[0].realFileName}"></c:if>
											</div>
											<div class="description">
												<div class="intro_name">
													<span class="intro_tit"><c:out value="${intro.introName}"/></span>
													<span class="intro_class">${cmutls:nl2br(intro.introClass) }</span>
												</div>
												<div class="intro_txt">${cmutls:nl2br(intro.introTxt) }</div>
												<div class="sns_link">
													<c:if test="${'Y' eq intro.icoYtube }"><a href="${intro.ytubeUrl }" target="_blank" title="새창으로 이동" class="ico_ytube">유투브</a></c:if>
													<c:if test="${'Y' eq intro.icoFace }"><a href="${intro.faceUrl }" target="_blank" title="새창으로 이동" class="ico_face">페이스북</a></c:if>
													<c:if test="${'Y' eq intro.icoInsa }"><a href="${intro.insaUrl }" target="_blank" title="새창으로 이동" class="ico_insa">인스타그램</a></c:if>
													<c:if test="${'Y' eq intro.icoBlog }"><a href="${intro.blogUrl }" target="_blank" title="새창으로 이동" class="ico_blog">블로그</a></c:if>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
						<!-- //major_intro -->
					</c:if>
					
					<c:if test="${not empty professorList }">
						<script type="text/javascript" src="/common/js/jquery.cssslider.js"></script>
						<link href="/common/js/animated-slider.css" rel="stylesheet" type="text/css">
						
						<div id="major_professor">
							<h4>교수진</h4>
							<div class="professor_list">	
								<div class="choose_slider">
									<div class="choose_slider_items">
										<ul <c:if test="${fn:length(professorList) > 1}">id="mySlider"</c:if> class="mySlider">
											<c:forEach items="${professorList}" var="professor">
												<li class="current_item" id="${professor.seq }">
													<a href="#" onclick="return false;">
														<span class="photo">
															<c:forEach items="${professor.attachFileList }" var="file">
																<c:if test="${'교수사진' eq file.explanation}">
																	<img src="/images/front/sub/blank_professor.png" style="background-image:url(<c:url value='/file/imgResizeView.do?seq=${file.seq}'/>)" onerror="javascript:this.src='/images/front/sub/blank_professor.png'" alt="${professor.title }"/>
																</c:if>
															</c:forEach>
															<span class="name_kor"><strong>${professor.title }</strong><span>${professor.position }</span></span>
														</span>
														<span class="brief animation  fadeIn delay-02s">
															<span class="name_eng"><span>prof.</span><strong>${professor.enTitle }</strong></span>
															<span class="name_kor"><strong>${professor.title }</strong><span>${professor.position }</span></span>
															<span class="name_class"><span>전공</span><strong>${professor.major }</strong></span>
														</span>
													</a>
												</li>
											</c:forEach>
											<c:if test="${fn:length(professorList) > 1}">
												<c:forEach items="${professorList}" var="professor">
													<li class="current_item" id="${professor.seq }">
														<a href="#" onclick="return false;">
															<span class="photo">
																<c:forEach items="${professor.attachFileList }" var="file">
																	<c:if test="${'교수사진' eq file.explanation}">
																		<img src="/images/front/sub/blank_professor.png" style="background-image:url(<c:url value='/file/imgResizeView.do?seq=${file.seq}'/>)" onerror="javascript:this.src='/images/front/sub/blank_professor.png'" alt="${professor.title }"/>
																	</c:if>
																</c:forEach>
																<span class="name_kor"><strong>${professor.title }</strong><span>${professor.position }</span></span>
															</span>
															<span class="brief animation  fadeIn delay-02s">
																<span class="name_eng"><span>prof.</span><strong>${professor.enTitle }</strong></span>
																<span class="name_kor"><strong>${professor.title }</strong><span>${professor.position }</span></span>
																<span class="name_class"><span>전공</span><strong>${professor.major }</strong></span>
															</span>
														</a>
													</li>
												</c:forEach>
											</c:if>
										</ul>
										<c:if test="${fn:length(professorList) > 1}">
											<div class="bt_prof_prev">
												<a id="btn_prev1" href="#"><img src="/images/front/sub/prev_professor.png" alt="이전"></a>
											</div>
											<div class="bt_prof_next">
												<a id="btn_next1" href="#"><img src="/images/front/sub/next_professor.png" alt="다음"></a>
											</div>
										</c:if>
									</div>
									<!-- //choose_slider_items -->
									<div class="go_detail"><img src="/images/front/sub/down_professor.png" alt="교수소개 상세보기"></div>
								</div>
							</div>
							<!-- // professor_list -->
							<div class="professor_info">
								<div class="professor_info_inner">
								</div>
								<!-- //professor_info_inner -->
							</div>
							<!-- // professor_info -->
						</div>
						<!-- //major_professor -->
						<script type="text/javascript">
							$(function() {
								var seq = $('.current_item').attr('id');
								$("#mySlider").AnimatedSlider({
									prevButton : "#btn_prev1",
									nextButton : "#btn_next1",
									visibleItems : 3,
									infiniteScroll : true
								});
								
								var configSeq = "${configSeq}"
								$('#btn_prev1').click(function(e) {
									var seq = $('.current_item').attr('id');
									professorAjax(configSeq, seq);
								});
								
								$('#btn_next1').click(function(e) {
									var seq = $('.current_item').attr('id');
									professorAjax(configSeq, seq);
								});
								
								
								var professorAjax = function(configSeq, seq) {
									itsp.ajax.doPostJSON('/Major/Professor/selectData', {aboutSeq : configSeq, seq : seq }, function(data) {
										var resultList = data.body;
										var dataObj = $('.professor_info_inner');
										dataObj.empty();
										for (var inx=0; inx<resultList.length; inx++) {
											var info = resultList[inx];
											var html = '';
											
											var attachFile = info.attachFileList;
											html += '<div class="professor_photo">';
											var fileYn = "N"
											for (var i=0; i<attachFile.length; i++) {
												var file = attachFile[i];
												if("활동사진" == file.explanation){
													html += '	<img src="/file/imgResizeView.do?seq='+file.seq+'" alt="'+info.title+'_활동사진"/>';
													fileYn = "Y";
												}
											}
											
											if(fileYn == "N"){
												html += '	<img src="/images/front/common/blank_photo.png" class="hide" alt=""/>';
											}
											
											html += '</div>';
											html += '<div class="professor_detail">';
											html += '		<h5>학력</h5>';
											html += '			<ul class="professor_career">';
											if(info.academicBackground != null){
												var academicBackground = info.academicBackground.split('||');
												for (i2 = 0; i2 < academicBackground.length; i2++){
													html += '	<li>'+academicBackground[i2]+'</li>';
												}
											}else{
												html += '	<li>등록 된 데이터가 없습니다.</li>';
											}
											
											html += '			</ul>';
											html += '		<h5>경력</h5>';
											html += '		<ul class="professor_career">';
											if(info.career != null){
												var career = info.career.split('||');
												for (i3 = 0; i3 < career.length; i3++){
													html += '	<li>'+career[i3]+'</li>';
												}
											}else{
												html += '	<li>등록 된 데이터가 없습니다.</li>';
											}
											html += '		</ul>';
											html += '	</div>';
											dataObj.append(html);
										};
									});
								};
								
								var seq = $('.current_item').attr('id');
								professorAjax(configSeq, seq);
								
							});
						</script>
						
					</c:if>
					
					<c:if test="${not empty goalList }">
						<div id="major_goal">
							<h4>교육목표</h4>
							<div class="goal_inner">
								<div class="goal_column">
									<c:forEach items="${goalList}" var="goal" varStatus="status">
										<p class="goal_ico0${status.count}"><span>${cmutls:nl2br(goal.goalTxt) }</span></p>
									</c:forEach>
								</div>
							</div>
						</div>
						<!-- //major_goal -->
					</c:if>
					
					<c:if test="${not empty curriculumList }">
						<div id="major_curriculum">
							<h4>교육과정</h4>
							<div class="curriculum_inner">
								<div class="curriculum_info">
									<div class="degree_course">
										<p><span><strong class="extra">학사학위과정</strong>
											<c:forTokens items="${about.dash }" delims="||" var="dash" varStatus="status">
												<c:out value="${cmutls:removeScript(dash) }"/><c:if test="${!status.last}"> / </c:if>
											</c:forTokens>
										</span></p>
									</div>
									<ul class="program">
										<c:forEach items="${curriculumList }" var="curriculum" varStatus="status">
											<li><span><strong class="extra">${curriculum.title }</strong>${cmutls:nl2br(curriculum.contents) }</span></li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						<!-- //major_curriculum -->
					</c:if>
					
					<c:if test="${not empty course }">
						<div id="major_course">
							<h4>교과과정</h4>
							<div class="course_inner">
								<div class="course_column">	
									<c:if test="${'Editor' eq course.type }">${cmutls:removeScript(course.contents) }</c:if>
									<c:if test="${'Include' eq course.type }">
										<jsp:include page="/iWORK/conservatory/path/${course.includePath}" flush="true"/>
									</c:if>
								</div>
								<div class="bt_scroll"><img src="/images/front/sub/course_scroll.png" alt="교과과정 상세보기"></div>
							</div>
						</div>
						<!-- //major_course -->
					</c:if>
					
					<c:if test="${not empty fieldList }">
						<div id="major_field">
							<h4>진로 및 활동분야</h4>
							<div class="field_inner">
								<div class="field_list">
									<c:forEach items="${fieldList}" var="field" varStatus="status">
										<c:choose>
											<c:when test="${not empty field.groupInfo}">
												<dl>
													<dt class="group"><span>${cmutls:nl2br(field.group) }</span></dt>
													<dd class="group_info"><span>${cmutls:nl2br(field.groupInfo) }</span></dd>
												</dl>
											</c:when>
											<c:otherwise>
												<c:if test="${status.first }">
													<ul>
												</c:if>
													<li><span>${cmutls:nl2br(field.group) }</span></li>
												<c:if test="${status.last }">
													</ul>
												</c:if>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>
							</div>
						</div>
						<!-- //major_field -->
					</c:if>
					
					<c:if test="${not empty facilitiesList }">
						<div id="major_facilities">
							<h4>실습실</h4>
							<div class="facilities_gallery">
								<ul id="pinterest">
									<c:forEach items="${facilitiesList}" var="facilities" varStatus="status">
										<li>
											<a rel="gallery_${status.count }" href="<c:url value="/fileImg/${fn:replace(facilities.attachFileList[0].realFilePath, '\\\\', '/')}/${facilities.attachFileList[0].savedFileName }"/>" title="${facilities.attachFileList[0].realFileName}" class="fancybox">
												<img src="<c:url value="/fileImg/${fn:replace(facilities.attachFileList[0].realFilePath, '\\\\', '/')}/${facilities.attachFileList[0].savedFileName }"/>" alt="${facilities.attachFileList[0].realFileName}" style="width: 100%;">
											</a>
											<div class="hidden">
												<c:forEach items="${facilities.attachFileList}" var="file">
													<a rel="gallery_${status.count }" title="${file.realFileName}" href="<c:url value="/fileImg/${fn:replace(file.realFilePath, '\\\\', '/')}/${file.savedFileName }"/>" class="fancybox" >
														<img src="<c:url value="/fileImg/${fn:replace(file.realFilePath, '\\\\', '/')}/${file.savedFileName }"/>" alt="${file.realFileName}">
													</a>
												</c:forEach>
											</div>
										</li>
									</c:forEach>
								 </ul>
							</div>
						</div>
						<!-- //major_field -->
						
						<!-- 실습실 -->
						
						<script type="text/javascript" src="/common/js/newWaterfall.js"></script>
						<script type="text/javascript">
							$(document).ready(function() {
								$('#pinterest').NewWaterfall({
									width : 360,
									delay : 100,
								});
							});
			
							function random(min, max) {
								return min + Math.floor(Math.random() * (max - min + 1))
							}
					
							var loading = false;
							setInterval(function() {
								if ($(window).scrollTop() >= $(document).height() - $(window).height() && !loading) {
									$("#pinterest").append("<li><a><img style='height:" + random(50, 300) + "px'>" + "</a></li>");
									loading = true;
								}
							}, 30);
						</script>
						
						<script type="text/javascript" src="/common/js/fancybox/jquery.fancybox.pack.js"></script>
						<link rel="stylesheet" href="/common/js/fancybox/jquery.fancybox.css" type="text/css" media="screen" />
						<script type="text/javascript">
							$(document).ready(function() {
								$(".fancybox").fancybox({
									openEffect	: 'none',
									closeEffect	: 'none',
								});
							});
						</script>
					</c:if>
					
				</article>
				<!-- // page_conts -->
			</section>
		</div>
		<!-- //sub_container -->
		<aside>
			<%@ include file="/iWORK/conservatory/include/floatMenu.jsp"%>
		</aside>
	</div>
	<!-- // container -->
	
	<%@ include file="/iWORK/conservatory/include/footer.jsp"%>
</body>
</html>