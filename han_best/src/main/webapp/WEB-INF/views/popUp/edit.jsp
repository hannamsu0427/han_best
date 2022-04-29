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
						<li class="here">팝업작성</li>
					</ul>
					<div class="client"><img src="/images/common/admin_logo.png"></div>
				</div>
			</header>
			<section>
				<div class="container">
					<div class="contents_inner">
						<article>
							<div class="pt20">
								<div class="post_tbl">
									<form action="/iWORK/PopUp/saveDataProc.do" method="post" name="fm" id="fm" enctype="multipart/form-data">
									<input type="hidden" name="seq" value="<c:out value="${data.seq}"/>" readonly="readonly"/>
									<input type="hidden" name="orderNum" value="<c:out value="${data.orderNum}"/>" readonly="readonly"/>
									<div class="grid_top">
										<div class="ls"><h3>일반정보</h3></div>
										<div class="rs"><span class="req fs13">은 필수입력 사항입니다.</span></div>
									</div>
									<table class="grid view">
										<caption>일반정보 쓰기</caption>
										<colgroup><col width="20%"><col width="80%"></colgroup>
										<tbody>
											<tr>
												<th><span class="req pl10">사용여부</span></th>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><input type="radio" name="useYn" id="useY" value="Y" ${'Y' eq data.useYn || empty data ? 'checked="checked"':'' }><label for="useY" class="pl5">사용</label></li>
															<li><input type="radio" name="useYn" id="useN" value="N" ${'N' eq data.useYn ? 'checked="checked"':'' }><label for="useN" class="pl5">미사용</label> <span class="em">(게시 기간 중 이더라도 강제로 출력되지 않습니다.)</span></li>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<th><span class="req pl10">설정</span></th>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><input type="radio" name="setting" id="setting_A" value="A" ${'A' eq data.setting || empty data ? 'checked="checked"':'' }><label for="setting_A" class="pl5">기간 제한 없음</label>  <span class="em">(게시 기간 상관없이 출력됩니다.)</span></li>
															<li><input type="radio" name="setting" id="setting_D" value="D" ${'D' eq data.setting ? 'checked="checked"':'' }><label for="setting_D" class="pl5">기간 출력</label></li>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<th><span class="pl10">기간설정</span></th>
												<td>
													<div class="row">
														<input type="text" class="period" name="startDate" placeholder="시작년-월-일" value="${data.startDate }" title="시작년-월-일" id="startDate" data-itsp-type-date>
														<span class="pl10 pr10">~</span>
														<input type="text" class="period" name="endDate" placeholder="종료년-월-일" value="${data.endDate }" title="종료년-월-일" id="endDate" data-itsp-type-date>
													</div>
												</td>
											</tr>
											<tr>
												<th><span class="req pl10">제 목</span></th>
												<td><div class="row"><input type="text" name="title" id="title" value="${data.title }" title="" placeholder="제목" class="p100" data-itsp-title="제목" data-itsp-required></div></td>
											</tr>
											<tr>
												<th rowspan="3"><span class="pl10">연결정보</span></th>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><strong>연결유무</strong></li>
															<li><input type="radio" name="linkYn" id="linkN" value="N" ${'Y' ne data.linkYn ? 'checked="checked"':'' }><label for="linkN" class="pl5">미사용</label></li>
															<li><input type="radio" name="linkYn" id="linkY" value="Y" ${'Y' eq data.linkYn ? 'checked="checked"':'' }><label for="linkY" class="pl5">사용</label></li>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><strong>Url</strong></li>
															<li class="p50"><input type="text" class="p99" name="linkUrl" id="linkUrl" value="${not empty data.linkUrl ? data.linkUrl : '#' }"></li>
															<li class="em">*URL은 반드시 http:// 또는 https:// 로 시작하셔야 합니다.</li>
														</ul>
														
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><strong>연결화면</strong></li>
															<li><input type="radio" name="linkTarget" id="linkTarget_S" value="_self" ${'_self' eq data.linkTarget || empty data ? 'checked="checked"':'' }><label for="linkTarget_S" class="pl5">현재 창으로 띄우기</label></li>
															<li><input type="radio" name="linkTarget" id="link_targe_B" value="_blank" ${'_blank' eq data.linkTarget ? 'checked="checked"':'' }><label for="link_targe_B" class="pl5">새 창으로 띄우기</label></li>
														</ul>
													</div>
												</td>
											</tr>
											
										</tbody>
									</table>
									
									<div class="grid_top">
										<div class="ls"><h3>상세설정</h3></div>
									</div>
									<table class="grid view">
										<caption>상세설정 쓰기</caption>
										<colgroup><col width="20%"><col width="80%"></colgroup>
										<tbody>
											<tr>
												<th><span class="req pl10">노출형태</span></th>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><input type="radio" name="type" id="type_I" value="img" ${'img' eq data.type ? 'checked="checked"':'' }><label for="type_I" class="pl5">이미지</label></li>
															<li><input type="radio" name="type" id="type_T" value="txt" ${'txt' eq data.type || empty data ? 'checked="checked"':'' }><label for="type_T" class="pl5">텍스트</label></li>
														</ul>
													</div>
												</td>
											</tr>
											
											
											<tr>
												<th><span class="pl10">파일첨부</span></th>
												<td>
													<div class="row">
														<ul class="pb5">
															<li class="add_field">
																<c:choose>
																	<c:when test="${empty data.attachFileList }">
																		<input type="file" class="p35" accept="image/*" name="file_1" id="file_1">
																		<input type="text" placeholder="설명글" class="p40" name="fileEtc_${empty data.attachFileList ? '1' : data.attachFileList[0].seq}" value="${data.attachFileList[0].etc}">
																	</c:when>
																	<c:otherwise>
																		<a href="<c:url value="/file/fileDownLoad.do?seq=${data.attachFileList[0].seq}"/>" class="fl">${data.attachFileList[0].realFileName }</a>
																		<ul class="add_edit">
																			<li><a href="#" class="bt_inner" data-itsp-file-delete-link="${data.attachFileList[0].seq}">삭제</a></li>
																		</ul>
																	</c:otherwise>
																</c:choose>
															</li>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<th><span class="pl10">내 용</span></th>
												<td><div class="row"><input type="text" name="contents" id="contents" value="${data.contents }" title="" placeholder="내용" class="p100"></div></td>
											</tr>
										</tbody>
									</table>
									
									</form>
								</div>
								<!-- //post_tbl -->
							</div>
							
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_on" data-itsp-save-link>확인</button>
									<c:if test="${not empty data }">
										<button type="button" name="" id="" class="bt bt_nor" data-itsp-delete-link="${data.seq }">삭제</button>
									</c:if>
									<button type="button" name="" id="" class="bt bt_nor" data-itsp-list-link>목록</button>
								</div>
							</div>
							
							<div class="comment_box">
								<ul>
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- URL은 반드시 http://로 시작하셔야 합니다.</li>
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