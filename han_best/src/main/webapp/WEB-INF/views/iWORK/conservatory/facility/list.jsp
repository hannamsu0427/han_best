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
		<div class="sub_container">
			<section>
				<article>
					<div class="page_header visual<fmt:formatNumber value="${menuCount}" minIntegerDigits="2"/>">
						<div class="page_header_inner">
							<%@ include file="/iWORK/conservatory/include/location.jsp"%>
							<h2 class="gnb_depth1_tit"><c:out value="${curTitle }"/></h2>
						</div>
					</div>
				</article>
				<article class="page_conts">
					<div class="page_conts_tit">
						<h3 class=""><c:out value="${curTitle }"/></h3>
					</div>
					<div>
						<jsp:include page="/iWORK/conservatory/include/post_tabs.jsp" />
						<!-- // post_tabs -->
					</div>
					<div class="facility">	
						<div class="facilty_top">
							<div class="select_wrap">
								<div class="select_option" id="select_item">
									<ul>
										<c:forEach items="${dataList}" var="dataList" varStatus="status">
											<li class="<c:if test="${dataList.seq eq data.seq }">option_selected</c:if><c:if test="${status.last }"> last</c:if>" onclick="location.href='/Facility.do?menuSeq=<c:out value="${menuSeq}"/>&configSeq=<c:out value="${dataList.configSeq}"/>&seq=<c:out value="${dataList.seq}"/>'"><c:out value="${dataList.title}"/></li>
										</c:forEach>
									</ul>
								</div>
								<div class="select_form">
									<ul>
										<li class="option_selected" id="search_select">
											<span class="option_txt" id="selected_item"><c:out value="${data.title}"/></span>
										</li>
									</ul>
								</div>
							</div>
						</div>
						
						<h4><c:out value="${data.title}"/></h4>
						<c:if test="${not empty data.etcList}">
							
							<div class="facilty_list">
								<div class="column">
									<c:forEach items="${data.etcList }" var="etc" varStatus="status">
										<p class="tbl_cell"><span class="term"><c:out value="${etc.etc1}"/></span><span class="desc"><c:out value="${etc.etc2}"/></span></p>
									</c:forEach>
								</div>
							</div>
						</c:if>
						<c:if test="${not empty data.attachFileList}">
							<div class="facilty_photo">
								<ul class="list_thumb">
									<c:forEach items="${data.attachFileList }" var="file">
										<li class="list_thumb_item">
											<div class="list_thumb_cont">
												<a href="#" class="thumb_link">
													<div class="thumb_img">
														<img src="<c:url value="/file/fileDownLoad.do?seq=${file.seq}"/>" onerror="javascript:this.src='/images/front/sub/album_blank_image.png'"  width="100%" height="100%" alt="">
													</div>
												</a>
												<div class="subject">
													<div class="subject_click">${file.explanation }</div>
												</div>
											</div>
											<!-- //list_thumb_cont -->
										</li>
										<!-- //list_thumb_item -->
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
					<!-- // facility -->
					
					
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