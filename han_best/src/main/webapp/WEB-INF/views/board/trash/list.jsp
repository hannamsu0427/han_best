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
	injectFile("/js/board/record.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>게시판 관리</h3>
					<ul class="location">
						<li>기능 관리 </li>
						<li>게시판 관리 </li>
						<li>${dataConfig.title} </li>
						<li class="here">게시물 관리</li>
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
									<li class="p25"><a href="#" data-itsp-config-link="<c:out value="${BOARD_CONFIG_SEQ}"/>"><span>기본 설정</span></a></li>
									<li class="p25"><a href="#" data-itsp-category-link="<c:out value="${BOARD_CONFIG_SEQ}"/>" ><span>카테고리 관리</span></a></li>
									<li class="p25"><a href="#" data-itsp-record-link="<c:out value="${BOARD_CONFIG_SEQ}"/>" ><span>게시물 관리</span></a></li>
									<li class="p25"><a href="#" data-itsp-trash-link="<c:out value="${BOARD_CONFIG_SEQ}"/>" class="on"><span>휴지통 관리</span></a></li>
								</ul>
							</div>
						
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>${dataConfig.title}</h3></div>
								</div>
								<div class="grid_search">
									<form name="newSearchForm" id="newSearchForm">
										<fieldset>
											<legend>게시판 검색</legend>
											<ul class="dp_inline tc">
												<li class="p15">
													<label for="searchBy" class="blind">검색조건 선택</label>
													<select id="searchBy" title="" class="p100">
														<option value="all" <c:if test="${'all' eq searchBy}">selected="selected"</c:if>>제목 + 내용</option>
														<option value="title" <c:if test="${'title' eq searchBy}">selected="selected"</c:if>>제목</option>
														<option value="subTitle" <c:if test="${'subTitle' eq searchBy}">selected="selected"</c:if>>내용</option>
													</select>
												</li>
												<li class="p30">
													<label for="searchValue" class="blind">검색어 입력</label><input type="text" placeholder="검색어 입력" id="searchValue" value="<c:out value="${searchValue }"/>" class="p100" maxlength="50" data-itsp-len-range=",50" tabindex="1">
												</li>
												<li class="">
													<label for="" class="blind">검색</label><button type="button" class="bt_s bt_search" data-itsp-new-search-link>검색</button>
												</li>
											</ul>
										</fieldset>
									</form>
								</div>
								
								<!-- 게시판 옵션 -->
								<div class="pt20 pb10">
									<ul class="sortings">
										<li>총 <strong><c:out value="${totalCount }"/></strong>건</li>
										<li class="pl10">
											<label for="curPage" class="blind" >리스트 수 선택</label>
											<select name="curPage" id="curPage" title="" class="opt">
												<option value="5" <c:if test="${'5' eq curPage}">selected="selected"</c:if>>5개씩 보기</option>
												<option value="10" <c:if test="${'10' eq curPage}">selected="selected"</c:if>>10개씩 보기</option>
												<option value="15" <c:if test="${'15' eq curPage}">selected="selected"</c:if>>15개씩 보기</option>
												<option value="20" <c:if test="${'20' eq curPage}">selected="selected"</c:if>>20개씩 보기</option>
												<option value="30" <c:if test="${'30' eq curPage}">selected="selected"</c:if>>30개씩 보기</option>
												<option value="40" <c:if test="${'40' eq curPage}">selected="selected"</c:if>>40개씩 보기</option>
												<option value="50" <c:if test="${'50' eq curPage}">selected="selected"</c:if>>50개씩 보기</option>
											</select>
										</li>
									</ul>
								</div>
								
								<div class="post_list">
									<table class="grid">
										<caption>게시물 목록</caption>
										<colgroup><col width="8%"><col width="8%"><col width=""><col width="10%"><col width="10%"><col width="8%"></colgroup>
										<thead>
											<tr>
												<th>선택</th>
												<th>No.</th>
												<th>제목</th>
												<th>작성자</th>
												<th>작성일</th>
												<th>조회</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${dataList}" var="data">
												<tr>
													<td><input type="checkbox" name="chkBoxs" id="chkBox_<c:out value="${data.seq}"/>" value="<c:out value="${data.seq}"/>" ></td>
													<td><c:out value="${totalCount - data.rnum + 1}"/></td>
													<td>
														<div class="subject">
															<c:if test="${not empty data.configSeq }"><span class="list_head">[<c:out value="${data.configName}"/>]</span></c:if>
															<a href="#" class="subject_click"><c:out value="${data.title}"/></a>
														</div>
													</td>
													<td>${data.regName }</td>
													<td>${data.regDate }</td>
													<td>${data.hitCnt }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								
							</div>
							<!-- //post_tbl -->
							
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_on" data-itsp-delete-link="chkBox">삭제</button>
									<button type="button" name="" id="" class="bt bt_on" data-itsp-use-link="chkBox" data-itsp-delYn="N">복구</button>
								</div>
								<%@ include file="/common/include/paginate.jsp" %>
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