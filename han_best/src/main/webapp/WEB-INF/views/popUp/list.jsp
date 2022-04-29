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
	injectFile("/js/mainScreen/popUp.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>팝업 관리</h3>
					<ul class="location">
						<li>초기화면 관리 </li>
						<li>팝업관리 </li>
						<li class="here">팝업목록</li>
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
										<li class="pl10">
											<label for="curPage" class="blind" >리스트 수 선택</label>
											<select name="curPage" id="curPage" title="" class="opt">
												<option value="5" <c:if test="${'5' eq curPage}">selected="selected"</c:if>>5개씩</option>
												<option value="10" <c:if test="${'10' eq curPage}">selected="selected"</c:if>>10개씩</option>
												<option value="15" <c:if test="${'15' eq curPage}">selected="selected"</c:if>>15개씩</option>
												<option value="20" <c:if test="${'20' eq curPage}">selected="selected"</c:if>>20개씩</option>
												<option value="30" <c:if test="${'30' eq curPage}">selected="selected"</c:if>>30개씩</option>
												<option value="40" <c:if test="${'40' eq curPage}">selected="selected"</c:if>>40개씩</option>
												<option value="50" <c:if test="${'50' eq curPage}">selected="selected"</c:if>>50개씩</option>
											</select>
										</li>
									</ul>
								</div>
								
								<div class="post_list">
									<table class="grid">
										<caption>게시물 목록</caption>
										<colgroup><col width="8%"><col width="8%"><col width=""><col width="10%"><col width="10%"><col width="8%"><col width="15%"></colgroup>
										<thead>
											<tr>
												<th>선택</th>
												<th>No.</th>
												<th>제목</th>
												<th>링크</th>
												<th>기간</th>
												<th>작성일</th>
												<th>기능</th>
											</tr>
										</thead>
										<tbody id="changeOrder">
											<c:forEach items="${dataList }" var="data">
												<c:set var="tempRegDate" value="${fn:replace(data.regDate , '-', '')}" />
												<fmt:parseNumber integerOnly="true" type="number" value="${tempRegDate}" var="regDate" />
												<tr data-itsp-seq="<c:out value="${data.seq}"/>" data-itsp-orderNum="<c:out value="${data.orderNum}"/>">
													<td><input type="checkbox" class="list_check" name="chkBoxs" id="chkBox_<c:out value="${data.seq}"/>" value="<c:out value="${data.seq}"/>" ></td>
													<td><c:out value="${totalCount - data.rnum + 1}"/></td>
													<td>
														<div class="subject">
															<span class="list_head">[${'Y' eq data.useYn ? '사용':'미사용' }]</span>
															<a href="#" class="subject_click" data-itsp-edit-link="${data.seq}">${data.title }</a><c:if test="${currentDate < regDate }"><span class="list_i_new"></span></c:if>
														</div>
													</td>
													<td>
														<c:choose>
															<c:when test="${'N' eq data.linkYn }">미연결</c:when>
															<c:otherwise>${data.linkUrl}</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${'A' eq data.setting }">항상</c:when>
															<c:otherwise>${data.startDate } ~ ${data.endDate }</c:otherwise>
														</c:choose>
													</td>
													<td>${data.regDate }</td>
													<td>
														<ul class="dp_inline">
															<li><a href="#" class="bt_inner w50" data-itsp-edit-link="${data.seq}">수정</a></li>
															<li><button type="button" name="" value="" title="" class="bt_inner w50" data-itsp-delete-link="${data.seq}">삭제</button></li>
														</ul>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
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
									<li>- 팝업 이름을 클릭하면 보기 및 수정 하실 수 있습니다.</li>
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