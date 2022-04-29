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
	injectFile("/js/board/category.js");
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
						<li class="here">카테고리 관리</li>
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
									<li class="p25"><a href="#" data-itsp-category-link="<c:out value="${BOARD_CONFIG_SEQ}"/>" class="on"><span>카테고리 관리</span></a></li>
									<li class="p25"><a href="#" data-itsp-record-link="<c:out value="${BOARD_CONFIG_SEQ}"/>"><span>게시물 관리</span></a></li>
									<li class="p25"><a href="#" data-itsp-trash-link="<c:out value="${BOARD_CONFIG_SEQ}"/>"><span>휴지통 관리</span></a></li>
								</ul>
							</div>
						
							<div class="post_tbl">
								<div class="grid_top"></div>
								<table class="grid">
									<caption>카테고리 리스트</caption>
									<colgroup><col width="*"><col width="15%"><col span="2" width="10%"></colgroup>
									<thead>
										<tr>
											<th>카테고리명</th>
											<th>등록일</th>
											<th>사용여부</th>
											<th>기능설정</th>
										</tr>
									</thead>
									<tbody id="changeOrder">
										<c:forEach items="${dataList }" var="data">
											<tr data-itsp-seq="<c:out value="${data.categorySeq}"/>" data-itsp-orderNum="<c:out value="${data.orderNum}"/>">
												<td>${data.categoryName }</td>
												<td>${data.regDate }</td>
												<td>${'Y' eq data.useYn ? '사용': '미사용'}</td>
												<td>
													<ul class="dp_inline">
														<li><a href="#" class="bt_inner w50" data-itsp-edit-link="<c:out value="${data.categorySeq }"/>">수정</a></li>
														<li><button type="button" name="" value="" title="" class="bt_inner w50" data-itsp-delete-link="<c:out value="${data.categorySeq }"/>">삭제</button></li>
													</ul>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- //post_tbl -->
							
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_nor" data-itsp-orderNum-link>순서 변경</button>
									<button type="button" name="" id="" class="bt bt_on" data-itsp-edit-link="0">생성</button>
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
</body>
</html>