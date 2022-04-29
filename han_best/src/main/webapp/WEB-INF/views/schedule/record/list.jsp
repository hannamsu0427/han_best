<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no, address=no, email=no">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta name = "keywords" content ="">
<meta name = "description" content ="">
<title>게시판 관리_게시물관리 목록</title>
<!-- PC Favicon -->
<link href="/css/admin/style.css" rel="stylesheet" type="text/css">
<%@ include file="/common/include/script.jsp" %>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임) 
	injectFile("/js/schedule/record.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>일정 관리</h3>
					<ul class="location">
						<li>기능 관리 </li>
						<li>일정 관리 </li>
						<li>${dataConfig.title} </li>
						<li class="here">일정 관리</li>
					</ul>
					<div class="client"><img src="/images/common/admin_logo.png"></div>
				</div>
			</header>
			
			<section>
				<div class="container">
					<div class="contents_inner">
						<article>
							<div class="tabs">
								<ul>
									<li class="p33"><a href="#" data-itsp-config-link="<c:out value="${SCHEDULE_CONFIG_SEQ}"/>"><span>기본 설정</span></a></li>
									<li class="p33"><a href="#" data-itsp-record-link="<c:out value="${SCHEDULE_CONFIG_SEQ}"/>" class="on"><span>일정 관리</span></a></li>
								</ul>
							</div>
						
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>${dataConfig.title}</h3></div>
								</div>
								
								<div class="box_8a">
									<div class="history_tab">
										<ul class="history_group">
											<c:set var="today" value="<%=new java.util.Date()%>" />
											<fmt:formatDate value="${today}" pattern="yyyy" var="nowYear"/> 
											<li><a href="/iWORK/Schedule/RecordList.do?configSeq=<c:out value="${SCHEDULE_CONFIG_SEQ}"/>&startYear=<c:out value="${nowYear + 1}"/>" title="" class="bt_group <c:if test="${startYear eq nowYear + 1 }">on</c:if>"><strong>${nowYear + 1}년</strong></a></li>
											<c:forEach var="i" begin="0" end="${nowYear - 2004}">
												<c:set var="yearOption" value="${nowYear - i}" />
												<li><a href="/iWORK/Schedule/RecordList.do?configSeq=<c:out value="${SCHEDULE_CONFIG_SEQ}"/>&startYear=<c:out value="${yearOption}"/>" title="" class="bt_group <c:if test="${startYear eq yearOption}">on</c:if>"><strong>${yearOption}년</strong></a></li>
											</c:forEach>
										</ul>
									</div>
								</div>
								
								<div class="pt10">
									<div class="post_list">
										<table class="grid">
											<caption>목록</caption>
											<colgroup><col width="10%"><col width="30%"><col width="30%"><col width="10%"><col width="10%"></colgroup>
											<thead>
												<tr>
													<th>선택</th>
													<th>일자</th>
													<th>내용</th>
													<th>작성일</th>
													<th>기능설정</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="data" items="${dataList}" varStatus="status">
													<tr>
														<td><input type="checkbox" name="chkBoxs" id="chkBox_<c:out value="${data.seq}"/>" value="<c:out value="${data.seq}"/>" ></td>
														<td>
															<c:out value="${data.startYear}" />.
															<c:if test="${not empty data.startMonth}"> <c:out value="${data.startMonth}" />. </c:if>
															<c:if test="${not empty data.startDay}"> <c:out value="${data.startDay}" /></c:if>
															<c:if test="${not empty data.endYear}"> ~ </c:if>
															<c:if test="${data.startYear ne data.endYear}"> <c:out value="${data.endYear}" />.</c:if>
															<c:if test="${not empty data.endMonth}"> <c:out value="${data.endMonth}" />. </c:if>
															<c:if test="${not empty data.endDay}"> <c:out value="${data.endDay}" /></c:if>
														</td>
														<td>${cmutls:nl2br(data.contents) }</td>
														<td>${data.regDateFmt }</td>
														<td>
															<ul class="dp_inline">
																<li><a href="#" class="bt_inner w50" data-itsp-edit-link="<c:out value="${data.seq }"/>">수정</a></li>
																<li><button type="button" name="" value="" title="" class="bt_inner w50" data-itsp-delete-link="<c:out value="${data.seq }"/>">삭제</button></li>
															</ul>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							
							<div class="post_bottom">
								<div class="bt_list">
									<ul>
										<li><button type="button" name="" id="" class="bt bt_fnc" data-itsp-delete-link="chkBox">선택삭제</button></li>
										<li><button type="button" name="" id="" class="bt bt_on" data-itsp-edit-link="0">등록하기</button></li>
										<li><button type="button" name="" id="" class="bt bt_nor" onclick="location.href='/iWORK/History/ConfigList.do'">기능목록</button></li>
									</ul>
								</div>
							</div>
							
							<div class="comment_box">
								<ul>
									<li class="pb10"><strong class="em">반드시 읽어보세요</strong></li>
								</ul>
							</div>
						</article>
					</div>
				</div>
			</section>
		</div>
		<!-- //wrap -->
	</div>
	<%@ include file="/common/include/left_column.jsp" %>
	<!-- //left_column -->	
<jsp:include page="search.jsp" />
</body>
</html>