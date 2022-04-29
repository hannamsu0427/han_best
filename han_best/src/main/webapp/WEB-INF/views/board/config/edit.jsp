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
	injectFile("/js/board/config.js");
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
									<li class="${not empty data ? 'p25' : 'p100'}"><a href="#" class="on"><span>기본 설정</span></a></li>
									<c:if test="${not empty data }">
										<li class="p25"><a href="#" data-itsp-category-link="<c:out value="${data.seq}"/>"><span>카테고리 관리</span></a></li>
										<li class="p25"><a href="#" data-itsp-record-link="<c:out value="${data.seq}"/>"><span>게시물 관리</span></a></li>
										<li class="p25"><a href="#" data-itsp-trash-link="<c:out value="${data.seq}"/>"><span>휴지통 관리</span></a></li>
									</c:if>
								</ul>
							</div>

							<form action="/iWORK/Board/Config/saveProcData.do" name="fm" id="fm" method="post">
							<input type="hidden" name="seq" value="<c:out value="${data.seq}"/>" readonly="readonly">
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>기본설정</h3></div>
									<div class="rs"><span class="req fs13">은 필수입력 사항입니다.</span></div>
								</div>
								
								<table class="grid view">
									<caption>기본설정 목록</caption>
									<colgroup><col width="20%"><col width="80%"></colgroup>
									<tbody>
										<tr>
											<th><span class="req pl10">사용여부</span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="useYn" id="useY" value="Y" ${'N' ne data.useYn ? 'checked="checked"':'' }><label for="useY" class="pl5">사용</label></li>
														<li><input type="radio" name="useYn" id="useN" value="N" ${'N' eq data.useYn ? 'checked="checked"':'' }><label for="useN" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="req pl10">게시판 유형</span></th>
											<td>
												<div class="row">
													<ul class="dp_inline fl">
														<li><input type="radio" name="type" id="type1" value="list" ${not empty data ? 'disabled="disabled"':'checked="checked"' } ${'list' eq data.type ? 'checked="checked"':'' }><label for="type1" class="pl5">게시판</label></li>
														<li><input type="radio" name="type" id="type2" value="album" ${not empty data ? 'disabled="disabled"':'' } ${'album' eq data.type ? 'checked="checked"':'' }><label for="type2" class="pl5">앨범형</label></li>
														<li><input type="radio" name="type" id="type3" value="thumnail" ${not empty data ? 'disabled="disabled"':'' } ${'thumnail' eq data.type ? 'checked="checked"':'' }><label for="type3" class="pl5">썸네일</label></li>
														<li><input type="radio" name="type" id="type4" value="pdf" ${not empty data ? 'disabled="disabled"':'' } ${'pdf' eq data.type ? 'checked="checked"':'' }><label for="type4" class="pl5">PDF</label></li>
														<li><input type="radio" name="type" id="type5" value="movie" ${not empty data ? 'disabled="disabled"':'' } ${'movie' eq data.type ? 'checked="checked"':'' }><label for="type5" class="pl5">동영상</label></li>
													</ul>
													<span class="fr em">*게시판 유형은 변경이 불가능 합니다.</span>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="req pl10">공지설정</span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="noticeYn" id="noticeY" value="Y" ${'N' ne data.noticeYn ? 'checked="checked"':'' }><label for="useY" class="pl5">사용</label></li>
														<li><input type="radio" name="noticeYn" id="noticeN" value="N" ${'N' eq data.noticeYn ? 'checked="checked"':'' }><label for="useN" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
									
										<tr>
											<th><span class="req pl10">게시판 제목</span></th>
											<td><div class="row"><input type="text" name="title" id="title" value="<c:out value="${data.title}"/>" title="게시판 제목" placeholder="게시판 제목" class="p100" data-itsp-required data-itsp-title="게시판 제목"></div></td>
										</tr>
										<tr>
											<th><span class="pl10">게시판 설명</span></th>
											<td><div class="row"><input type="text" name="subTitle" id="subTitle" value="<c:out value="${data.subTitle}"/>" title="게시판 설명" placeholder="게시판 설명" class="p100"></div></td>
										</tr>
										
										
										<tr>
											<th><span class="pl10">새글출력설정</span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><strong class="pr10">출력일자 :</strong><select name="newDay" id="newDay">
															<option value="7" ${empty data || '7' eq data.newDay ? 'selected="selected"':'' }>7</option>
															<option value="15" ${'15' eq data.newDay ? 'selected="selected"':'' }>15</option>
															<option value="30" ${'30' eq data.newDay ? 'selected="selected"':'' }>30</option>
															<option value="60" ${'60' eq data.newDay ? 'selected="selected"':'' }>60</option>
															<option value="120" ${'120' eq data.newDay ? 'selected="selected"':'' }>120</option>
															<option value="240" ${'240' eq data.newDay ? 'selected="selected"':'' }>240</option>
															<option value="365" ${'365' eq data.newDay ? 'selected="selected"':'' }>365</option>
														</select> 일</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="pl10">이전글/다음글</span></th>
											<td><div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="preNextYn" id="preNextY" value="Y" ${'N' ne data.preNextYn ? 'checked="checked"':'' }><label for="preNextY" class="pl5">사용</label></li>
														<li><input type="radio" name="preNextYn" id="preNextN" value="N" ${'N' eq data.preNextYn ? 'checked="checked"':'' }><label for="preNextN" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
										
										<tr>
											<th><span class="pl10">비회원 사용여부</span></th>
											<td><div class="row">
													<ul class="dp_inline fl">
														<li><input type="radio" name="nonMember" id="non_memberY" value="Y" ${'Y' eq data.nonMember ? 'checked="checked"':'' } ><label for="non_memberY" class="pl5">사용</label></li>
														<li><input type="radio" name="nonMember" id="non_memberN" value="N" ${'Y' ne data.nonMember ? 'checked="checked"':'' }><label for="non_memberN" class="pl5">미사용</label></li>
													</ul>
													<span class="fr em">* 비회원도 작성이 가능합니다.</span>
												</div>
											</td>
										</tr>
										
									</tbody>
								</table>
							</div>
							
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>상세설정</h3></div>
									<div class="rs"><span class="req fs13">일반게시판 및 답변게시판의 경우에만 설정가능</span></div>
								</div>
								
								<table class="grid view">
									<caption>기본설정 목록</caption>
									<colgroup><col width="20%"><col width="80%"></colgroup>
									<tbody>
										<tr>
											<th><span class="req pl10">첨부파일 사용여부 </span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="fileYn" id="attachFileY" value="Y" ${'N' ne data.fileYn ? 'checked="checked"':'' }><label for="attachFileY" class="pl5">사용</label></li>
														<li><input type="radio" name="fileYn" id="attachFileN" value="N" ${'N' eq data.fileYn ? 'checked="checked"':'' }><label for="attachFileN" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="req pl10">첨부파일 갯수</span></th>
											<td>
												<div class="row">
													<select name="fileCnt" id="fileCnt" title="첨부파일 갯수 선택">
														<option value="">선택</option>
														<c:forEach var="i" begin="1" end="10" step="1">
															<option value="${i}" ${i eq data.fileCnt ? 'selected="selected"':'' }>${i}</option>
														</c:forEach>
													</select> 개
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="req pl10">첨부파일 용량</span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="text" name="fileSize" id="fileSize" value="${empty data ? '0' : data.fileSize }" title="" placeholder="" size="6"> MB</li>
														<li class="fr"><span class="em">* 0MB일경우 파일업로드 불가</span></li>
													</ul>	
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="pl10">댓글 사용여부 </span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="commentYn" id="commentY" value="Y" ${'Y' eq data.commentYn ? 'checked="checked"':'' }><label for="commentY" class="pl5">사용</label></li>
														<li><input type="radio" name="commentYn" id="commentN" value="N" ${'Y' ne data.commentYn ? 'checked="checked"':'' }><label for="commentN" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="pl10">답글 사용여부 </span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="replyYn" id="replyY" value="Y" ${'Y' eq data.replyYn ? 'checked="checked"':'' }><label for="replyY" class="pl5">사용</label></li>
														<li><input type="radio" name="replyYn" id="replyN" value="N" ${'Y' ne data.replyYn ? 'checked="checked"':'' }><label for="replyN" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="pl10">편집기 사용여부 </span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="editorYn" id="editorY" value="Y" ${'Y' eq data.editorYn ? 'checked="checked"':'' }><label for="editorY" class="pl5">사용</label></li>
														<li><input type="radio" name="editorYn" id="editorN" value="N" ${'Y' ne data.editorYn ? 'checked="checked"':'' }><label for="editorN" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="pl10">휴지통 사용여부 </span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="delYn" id="delN" value="N" ${'Y' ne data.delYn ? 'checked="checked"':'' }><label for="delN" class="pl5">사용</label></li>
														<li><input type="radio" name="delYn" id="delY" value="Y" ${'Y' eq data.delYn ? 'checked="checked"':'' }><label for="delY" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
										
									</tbody>
								</table>
							</div>
							<!-- //post_tbl -->
							
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>부가기능 설정</h3></div>
									<div class="rs"><span class="req fs13">*일반게시판 및 답변게시판의 경우에만 설정가능</span></div>
								</div>
								
								<table class="grid view">
									<caption>기본설정 목록</caption>
									<colgroup><col width="20%"><col width="80%"></colgroup>
									<tbody>
										<tr>
											<th><span class="pl10">프린트 기능</span></th>
											<td>
												<div class="row">
													<ul class="dp_inline">
														<li><input type="radio" name="printYn" id="printY" value="Y" ${'Y' eq data.printYn ? 'checked="checked"':'' }><label for="printY" class="pl5">사용</label></li>
														<li><input type="radio" name="printYn" id="printN" value="N" ${'Y' ne data.printYn ? 'checked="checked"':'' }><label for="printN" class="pl5">미사용</label></li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="pl10">IP 정보 사용여부</span></th>
											<td>
												<div class="row">
													<ul class="dp_inline fl">
														<li><input type="radio" name="ipYn" id="ipY" value="Y" ${'Y' eq data.ipYn ? 'checked="checked"':'' }><label for="ipY" class="pl5">사용</label></li>
														<li><input type="radio" name="ipYn" id="ipN" value="N" ${'Y' ne data.ipYn ? 'checked="checked"':'' }><label for="ipN" class="pl5">미사용</label></li>
													</ul>
													<span class="fr em">*상세 화면에 작성자의 IP가 표출됩니다.</span>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="pl10">비밀글 기능</span></th>
											<td>
												<div class="row">
													<ul class="dp_inline fl">
														<li><input type="radio" name="secretYn" id="secretY" value="Y" ${'Y' eq data.secretYn ? 'checked="checked"':'' }><label for="secretY" class="pl5">사용</label></li>
														<li><input type="radio" name="secretYn" id="secretN" value="N" ${'Y' ne data.secretYn ? 'checked="checked"':'' }><label for="secretN" class="pl5">미사용</label></li>
													</ul>
													<span class="fr em">*관리자와 작성자 비밀번호를 아는사람만 접근 가능하며, 미사용 변경 시 모든 글이 노출됩니다.</span>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //post_tbl -->
							</form>
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_on" data-itsp-save-link>확인</button>
									<button type="button" name="" id="" class="bt bt_nor" onclick="location.href='/iWORK/Board/ConfigList.do'">목록</button>
								</div>
							</div>
							
							<div class="comment_box">
								<ul>
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- 컨텐츠의 그룹의 정보를 입력 후 , 저장하기 버튼을 클릭하시면 컨텐츠 그룹이 생성됩니다.</li>
									<li>- 컨텐츠 그룹이 생성된후, 생성된 그룹의 컨텐츠를 관리 하실 수 있습니다.</li>
									<li>- 페이지별 만족도 사용여부를 체크하시면, 페이지 만족도 기능을 사용할 수 있습니다.</li>
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