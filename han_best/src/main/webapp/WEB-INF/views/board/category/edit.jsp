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
						<li class="here">게시판 목록</li>
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
							<div class="pt20">
								<div class="post_tbl">
									<form action="/iWORK/Board/Category/saveProcData.do" method="post" name="fm" id="fm">
										<input type="hidden" name="configSeq" value="${empty data ? BOARD_CONFIG_SEQ : data.configSeq}" readonly="readonly"/>
										<input type="hidden" name="categorySeq" value="<c:out value="${data.categorySeq}"/>" readonly="readonly"/>
										<table class="grid view">
											<caption>쓰기</caption>
											<colgroup><col width="20%"><col width="80%"></colgroup>
											<tbody>
												<tr>
													<th><span class="req pl10">설 정</span></th>
													<td>
														<div class="row">
															<ul class="dp_inline">
																<li><input type="radio" name="useYn" id="useY" value="Y" ${'Y' eq data.useYn || empty map ? 'checked="checked"':'' }><label for="useY" class="pl5">사용</label></li>
																<li><input type="radio" name="useYn" id="useN" value="N" ${'N' eq data.useYn ? 'checked="checked"':'' }><label for="useN" class="pl5">미사용</label></li>
															</ul>
														</div>
													</td>
												</tr>
												<tr>
													<th><span class="req pl10">제 목</span></th>
													<td><div class="row"><input type="text" name="categoryName" id="categoryName" value="${data.categoryName }" title="" placeholder="제목" class="p100" data-itsp-title="제목" data-itsp-required></div></td>
												</tr>
											</tbody>
										</table>
									</form>
								</div>
								<!-- //post_tbl -->
							</div>
							
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_on" data-itsp-save-link="${empty data ? BOARD_CONFIG_SEQ : data.configSeq}">확인</button>
									<button type="button" name="" id="" class="bt bt_nor" onclick="location.href='/iWORK/Board/CategoryList.do?configSeq=${BOARD_CONFIG_SEQ}'">목록</button>
								</div>
							</div>
							
							<div class="comment_box">
								<ul>
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- 기본설정 페이지이며, 관리자를 지정할 수 있습니다.</li>
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