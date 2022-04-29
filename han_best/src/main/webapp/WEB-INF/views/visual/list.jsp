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
	injectFile("/js/mainScreen/visual.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>비쥬얼 관리</h3>
					<ul class="location">
						<li>초기화면 관리 </li>
						<li>비쥬얼 관리 </li>
						<li class="here">비쥬얼 목록</li>
					</ul>
					<div class="client"><img src="/images/common/admin_logo.png"></div>
				</div>
			</header>
			<section>
				<div class="container">
					<div class="contents_inner">
						<article>
							<div class="post_tbl">
								<div class="pt20 pb10">
									<ul class="sortings">
										<li>총 <strong>${totalCount }</strong>건</li>
									</ul>
								</div>
								<div class="post_list">
									<ul class="list_thumb" id="changeOrder">
										<c:forEach items="${dataList }" var="data">
											<c:set var="tempRegDate" value="${fn:replace(data.regDate , '-', '')}" />
											<fmt:parseNumber integerOnly="true" type="number" value="${tempRegDate}" var="regDate" />
											<li class="list_thumb_item" data-itsp-seq="<c:out value="${data.seq}"/>" data-itsp-orderNum="<c:out value="${data.rnum}"/>">
												<div class="list_thumb_cont">
													<div class="subject">
														<input type="checkbox" class="list_check" name="chkBoxs" id="chkBox_<c:out value="${data.seq}"/>" value="<c:out value="${data.seq}"/>" >
														<span class="list_head">[${'Y' eq data.useYn ? '사용':'미사용' }]</span>
														<a href="#" class="subject_click">${data.title }</a><c:if test="${currentDate < regDate }"><span class="list_i_new"></span></c:if>
													</div>
													<div class="thumb_img">
														<a href="#" class="thumb_link"><img src="<c:url value="/file/imgView.do?seq=${data.attachFileList[0].seq }"/>" onerror="javascript:this.src='/images/common/blank_image.png'" alt=""></a>
													</div>
													<div class="subject_info">
														<ul>
															<li class="dp_inline"><span class="term">Url</span><span class="desc">
																<c:choose>
																	<c:when test="${'N' eq data.linkYn }">미연결</c:when>
																	<c:otherwise>${data.linkUrl}</c:otherwise>
																</c:choose>
															</span></li>
															<li class="dp_inline"><span class="term">게시기간</span><span class="desc">
																<c:choose>
																	<c:when test="${'A' eq data.setting }">항상</c:when>
																	<c:otherwise>${data.startDate } ~ ${data.endDate }</c:otherwise>
																</c:choose>
															</span></li>
															<li class="dp_inline"><span class="term">작성일</span><span class="desc">${data.regDate }</span></li>
														</ul>
													</div>
												</div>
												<!-- //list_thumb_cont -->
												<div class="bt_list">
													<button type="button" name="" id="" class="bt_inner" data-itsp-delete-link="${data.seq}">삭제</button>
													<button type="button" name="" id="" class="bt_inner" data-itsp-edit-link="${data.seq}">수정</button>
												</div>
											</li>
											<!-- //list_thumb_item -->
										</c:forEach>
									</ul>
									<!-- //list_thumb -->
								</div>
							</div>
							<!-- //post_tbl -->
							<div class="post_bottom">
								<div class="bt_list">
									<ul>
										<li><button type="button" name="" id="" class="bt bt_nor" data-itsp-delete-link="chkBox">선택 삭제</button></li>
										<li><button type="button" name="" id="" class="bt bt_nor" data-itsp-orderNum-link>순서 변경</button></li>
										<li><button type="button" name="" id="" class="bt bt_on" data-itsp-edit-link="0">생성</button></li>
									</ul>
								</div>
								<%@ include file="/common/include/paginate.jsp" %>
							</div>
							
							<div class="comment_box">
								<ul>
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- 비쥬얼 제목을 클릭하시면 보기 및 수정 하실 수 있습니다.</li>
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