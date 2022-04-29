<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<%@ include file="/iWORK/conservatory/include/meta.jsp"%>
<%@ include file="/iWORK/conservatory/include/link.jsp"%>
<link href="/css/front/sub.css" rel="stylesheet" type="text/css">
<%@ include file="/iWORK/conservatory/include/script.jsp"%>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임) 
	injectFile("/iWORK/conservatory/js/schedule/schedule.js");
	injectFile("/js/ready.js");
</script>
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
					
					<div class="schedule">
						<div class="header_tool">
							<div class="header_tool_center">
								<button type="button" name="" title="이전달" class="ic_bt ico_prev_month" onclick="location.href='/Schedule.do?menuSeq=<c:out value="${menuSeq }"/>&configSeq=<c:out value="${SCHEDULE_CONFIG_SEQ }"/>&year=<c:out value="${year-1 }"/>'"><span>이전년도</span></button>
								<h2 class="this_month">${year }</h2>
								<button type="button" name="" title="다음달" class="ic_bt ico_next_month" onclick="location.href='/Schedule.do?menuSeq=<c:out value="${menuSeq }"/>&configSeq=<c:out value="${SCHEDULE_CONFIG_SEQ }"/>&year=<c:out value="${year+1 }"/>'"><span>다음년도</span></button>
							</div>
						</div>
						
						<div class="view_container">
							<div class="post_tbl">
								<p class="req tr txt_ty2">상기 일정은 본 교육원의 사정에 의해 변경 될 수 있습니다.</p>
								<c:if test="${empty dataList }">
									<div class="post_view">
										<div class="post_view_head">
											<table class="grid">
												<caption>학사일정 목록</caption>
												<colgroup><col width="28%"><col width="78%"></colgroup>
												<thead>
													<tr>
														<th scope="col" colspan="2" class="tl"><span>등록 된 데이터가 없습니다.</span></th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</c:if>
								
								<c:set var="oldData" value="" />
								<c:forEach var="data" items="${dataList}" varStatus="status">
								<c:set var="curData" value="${data.startMonth }" />
								<c:if test="${oldData != curData}">
									<c:if test="${not empty oldData}">
									</tbody>
										</table>
									</div>
									<!-- //post_view_head -->
								</div>
								<!-- //post_view -->
									</c:if>
								<div class="post_view">
									<div class="post_view_head">
										<table class="grid">
											<caption><c:out value="${data.startMonth}" />월 학사일정 목록</caption>
											<colgroup><col width="28%"><col width="78%"></colgroup>
											<thead>
												<tr>
													<th scope="col" colspan="2" class="tl"><span><c:out value="${data.startMonth}" />월</span></th>
												</tr>
											</thead>
											<tbody>
								</c:if>
								<tr>
									<th scope="row">
										<span>
											<c:if test="${not empty data.startMonth}"> <c:out value="${data.startMonth}" />. </c:if>
											<c:if test="${not empty data.startDay}"> <c:out value="${data.startDay}" /></c:if>
											<c:if test="${not empty data.endYear}"> ~ </c:if>
											<c:if test="${data.startYear ne data.endYear}"> <c:out value="${data.endYear}" />.</c:if>
											<c:if test="${not empty data.endMonth}"> <c:out value="${data.endMonth}" />. </c:if>
											<c:if test="${not empty data.endDay}"> <c:out value="${data.endDay}" /></c:if>
										</span>
									</th>
									<td>
										<span>${cmutls:nl2br(data.contents) }</span>
										<c:if test="${not empty sessionScopeMember}">
											<span class="fr">
											<a href="#" class="bt_s bt_inner" data-itsp-edit-link="<c:out value="${data.seq }"/>">수정</a>
											<a href="#" class="bt_s bt_inner" data-itsp-delete-link="<c:out value="${data.seq }"/>">삭제</a>
											</span>
										</c:if>
									</td>
								</tr>
								<c:set var="oldData" value="${curData}" />
								</c:forEach>
								<c:if test="${not empty oldData}">
											</tbody>
										</table>
									</div>
									<!-- //post_view_head -->
								</div>
								<!-- //post_view -->
								</c:if>
								
								
							</div>
							<!-- //post_tbl -->
							
							<div class="post_bottom">
								<div class="ls"></div>
								<div class="rs">
									<c:if test="${not empty sessionScopeMember}">
									<div class="bt_list">
										<ul>
											<li><label for="" class="blind">일정등록</label><button type="button" name="" id="" class="bt bt_on" data-itsp-edit-link="0">일정등록</button></li>
										</ul>
									</div>
									<!-- // bt_list -->
									</c:if>
								</div>
							</div>
							<!-- //post_bottom -->
						</div>

					</div>
					<!-- // schedule -->
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
<jsp:include page="search.jsp" />
</body>
</html>