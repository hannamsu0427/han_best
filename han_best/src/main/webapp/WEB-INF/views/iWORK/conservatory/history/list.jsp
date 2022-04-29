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
					<jsp:include page="/iWORK/conservatory/include/post_tabs.jsp" />
					<!-- // post_tabs -->
					
					<div class="history_wrap">
						<div class="history_tabs">
							<ul class="history_group">
								<c:forEach items="${categoryList}" var="category">
									<li><a href="/History.do?menuSeq=<c:out value="${menuSeq}"/>&configSeq=<c:out value="${HISTORY_CONFIG_SEQ}"/>&categorySeq=<c:out value="${category.categorySeq}"/>" title="" class="bt_group <c:if test="${categorySeq eq category.categorySeq }">on</c:if>"><span><c:out value="${category.categoryName }"/> </span></a></li>
								</c:forEach>
							</ul>
						</div>
						
						<div class="history_conts">
							<ul>
								<c:set var="oldHistoryYear" value="" />
								<c:forEach var="data" items="${dataList}" varStatus="status">
								<c:set var="curHistoryYear" value="${data.startYear }" />
								<c:if test="${oldHistoryYear != curHistoryYear}">
									<c:if test="${not empty oldHistoryYear}">
									</div>
									<!-- // year_cont -->
								</li>
								<!-- // year -->
									</c:if>
								<li class="year">
									<div class="year_num"><c:out value="${data.startYear}" /></div>
									<div class="year_cont">
								</c:if>
								<ul class="mm_dd_item">
									<li class="mm_dd">
										<c:if test="${not empty data.startMonth}"> <c:out value="${data.startMonth}" />. </c:if>
										<c:if test="${not empty data.startDay}"> <c:out value="${data.startDay}" />.</c:if>
									</li>
									<li class="mm_dd_desc">
										<span class="desc_item">${cmutls:removeScript(cmutls:nl2br(cmutls:space2nbsp(data.contents))) }</span>
									</li>
								</ul>
								<c:set var="oldHistoryYear" value="${curHistoryYear}" />
								</c:forEach>
								<c:if test="${not empty oldHistoryYear}">
									</div> <!-- // year_cont -->
								</li>
								<!-- // year -->
								</c:if>
			
							</ul>
						</div>
						<!-- //history_inner -->
					</div>	
					<!-- //history_wrap -->
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