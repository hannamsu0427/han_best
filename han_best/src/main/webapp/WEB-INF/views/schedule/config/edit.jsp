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
<title>일정 관리</title>
<!-- PC Favicon -->
<link href="/css/admin/style.css" rel="stylesheet" type="text/css">
<%@ include file="/common/include/script.jsp" %>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임) 
	injectFile("/js/schedule/config.js");
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
						<li class="here">일정 목록</li>
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
									<li class="${not empty data ? 'p50' : 'p100'}"><a href="#" class="on"><span>기본 설정</span></a></li>
									<c:if test="${not empty data }">
										<li class="p50"><a href="#" data-itsp-record-link="<c:out value="${data.seq}"/>"><span>연혁 관리</span></a></li>
									</c:if>
								</ul>
							</div>

							<form action="/iWORK/Schedule/Config/saveProcData.do" name="fm" id="fm" method="post">
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
											<th><span class="req pl10">제목</span></th>
											<td><div class="row"><input type="text" name="title" id="title" value="<c:out value="${data.title}"/>" title="제목" placeholder="제목" class="p100" data-itsp-required data-itsp-title="제목"></div></td>
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
									</tbody>
								</table>
							</div>
							
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>부가기능 설정</h3></div>
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
									</tbody>
								</table>
							</div>
							<!-- //post_tbl -->
							</form>
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_on" data-itsp-save-link>확인</button>
									<button type="button" name="" id="" class="bt bt_nor" onclick="location.href='/iWORK/Schedule/ConfigList.do'">목록</button>
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