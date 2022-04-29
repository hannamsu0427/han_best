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
	injectFile("/js/history/record.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>연혁 관리</h3>
					<ul class="location">
						<li>기능 관리 </li>
						<li>연혁 관리 </li>
						<li>${dataConfig.title} </li>
						<li class="here">연혁 관리</li>
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
									<li class="p33"><a href="#" data-itsp-config-link="<c:out value="${HISTORY_CONFIG_SEQ}"/>"><span>기본 설정</span></a></li>
									<li class="p33"><a href="#" data-itsp-category-link="<c:out value="${HISTORY_CONFIG_SEQ}"/>" ><span>카테고리 관리</span></a></li>
									<li class="p33"><a href="#" data-itsp-record-link="<c:out value="${HISTORY_CONFIG_SEQ}"/>" class="on"><span>연혁 관리</span></a></li>
								</ul>
							</div>
						
							<div class="history_category">
								<a href="#" class="bt_category on">${dataConfig.title}</a>
							</div>
							
							<div class="box_8a">
								<div class="history_tab">
									<ul class="history_group">
										<c:forEach items="${categoryList}" var="category">
											<li><a href="/iWORK/History/RecordList.do?configSeq=<c:out value="${HISTORY_CONFIG_SEQ}"/>&categorySeq=<c:out value="${category.categorySeq}"/>" title="" class="bt_group <c:if test="${categorySeq eq category.categorySeq }">on</c:if>"><strong><c:out value="${category.categoryName }"/> </strong></a></li>
										</c:forEach>
									</ul>
								</div>
							</div>
							
							<div class="pt10">
								<div class="history_cont">
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
												<c:if test="${not empty data.startMonth}"> <c:out value="${data.startMonth}" />월 </c:if>
												<c:if test="${not empty data.startDay}"> <c:out value="${data.startDay}" />일</c:if>
											</li>
											<li class="mm_dd_desc">
												<div class="reg_chk"><input type="checkbox" name="chkBoxs" value="<c:out value="${data.seq}"/>" /></div>
												<span class="desc_item">${cmutls:removeScript(cmutls:nl2br(cmutls:space2nbsp(data.contents))) }</span>
												<div class="bt_list"><a href="#"class="bt_inner" data-itsp-edit-link="<c:out value="${data.seq }"/>">수정</a></div>
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
								<!-- //history_cont -->
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
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- 컨텐츠의 그룹의 정보를 입력 후 , 저장하기 버튼을 클릭하시면 컨텐츠 그룹이 생성됩니다.</li>
									<li>- 컨텐츠 그룹이 생성된후, 생성된 그룹의 컨텐츠를 관리 하실 수 있습니다.</li>
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