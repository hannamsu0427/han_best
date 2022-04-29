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
						<li class="here">비쥬얼 작성</li>
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
									<form action="/iWORK/Visual/saveDataProc.do" method="post" name="fm" id="fm" enctype="multipart/form-data">
									<input type="hidden" name="seq" value="<c:out value="${data.seq}"/>" readonly="readonly"/>
									<input type="hidden" name="orderNum" value="<c:out value="${data.orderNum}"/>" readonly="readonly"/>
									<div class="grid_top">
										<div class="ls"><h3>1. 일반정보</h3></div>
										<div class="rs"><span class="req fs13">은 필수입력 사항입니다.</span></div>
									</div>
									<table class="grid view">
										<caption>일반정보 쓰기</caption>
										<colgroup><col width="20%"><col width="80%"></colgroup>
										<tbody>
											<tr>
												<th><span class="req pl10">사용 유무</span></th>
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
												<th><span class="req pl10">제목</span></th>
												<td><div class="row"><input type="text" name="title" id="title" value="${data.title }" title="" placeholder="제목" class="p100" data-itsp-title="제목" data-itsp-required></div></td>
											</tr>
											<tr>
												<th><span class="req pl10">게시기간</span></th>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><input type="radio" name="setting" id="setting_A" value="A" ${'A' eq data.setting || empty data ? 'checked="checked"':'' }><label for="setting_A" class="pl5">기간 제한 없음</label> <span class="em">(게시 기간 상관없이 출력됩니다.)</span></li>
															<li><input type="radio" name="setting" id="setting_D" value="D" ${'D' eq data.setting ? 'checked="checked"':'' }><label for="setting_D" class="pl5">기간 출력</label>
																<span>
																	<input type="text" class="period" placeholder="시작년-월-일" title="시작년-월-일" name="startDate" id="startDate" value="${data.startDate }" data-itsp-type-date>
																	<span class="pl10 pr10">~</span>
																	<input type="text" class="period" placeholder="종료년-월-일" title="종료년-월-일" name="endDate" id="endDate" value="${data.endDate }" data-itsp-type-date>
																</span>
															</li>
														
														</ul>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
									
									<div class="grid_top">
										<div class="ls"><h3>2. 상세설정</h3></div>
									</div>
									<table class="grid view">
										<caption>상세설정 쓰기</caption>
										<colgroup><col width="20%"><col width="80%"></colgroup>
										<tbody>
											<tr>
												<th><span class="req pl10">WEB 이미지</span></th>
												<td>
													<div class="row">
														<ul class="pb5">
															<c:set var="loop_flag_web" value="false" />
															<c:set var="webPic" value="0" />
															<c:if test="${not empty data.attachFileList}">
																<c:forEach items="${data.attachFileList }" var="file">
																	<c:if test="${not loop_flag_web }">
																		<c:if test="${'WEB' eq file.explanation}">
																			<c:set var="loop_flag_web" value="true" />
																			<c:set var="webPic" value="1" />
																			<li class="add_field">
																				<a href="<c:url value="/file/fileDownLoad.do?seq=${file.seq}"/>" class="fl">${file.realFileName }</a>
																				<input type="hidden" name="fileEtc_${file.seq}" id="fileEtc_${file.seq}" placeholder="설명글" class="p40" value="WEB">
																				<span class="em pl10">(width:1920px, height:900px)</span>
																				<ul class="add_edit">
																					<li><a href="#" class="bt_inner" data-itsp-file-delete-link="${file.seq}">삭제</a></li>
																				</ul>
																			</li>
																		</c:if>
																	</c:if>
																</c:forEach>
															</c:if>
															<c:if test="${'0' eq webPic}">
																<li class="add_field">
																	<input type="file" name="file_1" id="file_1" class="p35">
																	<input type="hidden" name="fileEtc_1" id="fileEtc_1" placeholder="설명글" class="p40" value="WEB">
																	<span class="em pl10">(width:1920px, height:900px)</span>
																</li>
															</c:if>
														</ul>
													</div>
												</td>
											</tr>
											
											<tr>
												<th><span class="req pl10">MOBILE 이미지</span></th>
												<td>
													<div class="row">
														<ul class="pb5">
															<c:set var="loop_flag_mobile" value="false" />
															<c:set var="mobilePic" value="0" />
															<c:if test="${not empty data.attachFileList}">
																<c:forEach items="${data.attachFileList }" var="file">
																	<c:if test="${not loop_flag_mobile }">
																		<c:if test="${'MOBILE' eq file.explanation}">
																			<c:set var="loop_flag_mobile" value="true" />
																			<c:set var="mobilePic" value="1" />
																			<li class="add_field">
																				<a href="<c:url value="/file/fileDownLoad.do?seq=${file.seq}"/>" class="fl">${file.realFileName }</a>
																				<input type="hidden" name="fileEtc_${file.seq}" id="fileEtc_${file.seq}" placeholder="설명글" class="p40" value="MOBILE">
																				<span class="em pl10">(width:720px, height:1140px)</span>
																				<ul class="add_edit">
																					<li><a href="#" class="bt_inner" data-itsp-file-delete-link="${file.seq}">삭제</a></li>
																				</ul>
																			</li>
																		</c:if>
																	</c:if>
																</c:forEach>
															</c:if>
															<c:if test="${'0' eq mobilePic}">
																<li class="add_field">
																	<input type="file" name="file_2" id="file_2" class="p35">
																	<input type="hidden" name="fileEtc_2" id="fileEtc_2" placeholder="설명글" class="p40" value="MOBILE">
																	<span class="em pl10">(width:720px, height:1140px)</span>
																</li>
															</c:if>
														</ul>
													</div>
												</td>
											</tr>
											
										
											
										</tbody>
									</table>
									
									<div class="grid_top">
										<div class="ls"><h3>3. 연결정보</h3></div>
									</div>
									<table class="grid view">
										<caption>연결정보 쓰기</caption>
										<colgroup><col width="20%"><col width="80%"></colgroup>
										<tbody>
											<tr>
												<th><span class="req pl10">링크 사용유무</span></th>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><input type="radio" name="linkYn" id="linkN" value="N" ${'Y' ne data.linkYn ? 'checked="checked"':'' }><label for="linkN" class="pl5">미사용</label></li>
															<li><input type="radio" name="linkYn" id="linkY" value="Y" ${'Y' eq data.linkYn ? 'checked="checked"':'' }><label for="linkY" class="pl5">사용</label></li>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<th><span class="pl10">연결주소</span></th>
												<td><div class="row"><input type="text" name="linkUrl" id="linkUrl" value="${not empty data.linkUrl ? data.linkUrl : 'http://' }" title="" placeholder="" class="p100"></div></td>
											</tr>
											<tr>
												<th><span class="pl10">연결방식</span></th>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li><input type="radio" name="linkTarget" id="linkTarget_S" value="_self" ${'_self' eq data.linkTarget || empty data ? 'checked="checked"':'' }><label for="linkTarget_S" class="pl5">현재창</label></li>
															<li><input type="radio" name="linkTarget" id="link_targe_B" value="_blank" ${'_blank' eq data.linkTarget ? 'checked="checked"':'' }><label for="link_targe_B" class="pl5">새창</label></li>
														</ul>
													</div>
												</td>
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
									<li>- URL은 반드시 http:// 또는 https:// 로 시작하셔야 합니다.</li>
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