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
	injectFile("/js/swearWord/swearWord.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>비속어 관리</h3>
					<div class="client"><img src="/images/common/admin_logo.png"></div>
				</div>
			</header>
			<section>
				<div class="container">
					<div class="contents_inner">
						<article>
							<div class="">
								<div class="post_tbl">
									<form action="/iWORK/SwearWord/saveDataProc.do" method="post" name="fm" id="fm">
										<input type="hidden" name="seq" value="<c:out value="${data.seq}"/>" readonly="readonly"/>
										<div class="grid_top">
											<div class="ls"><h3>설정</h3></div>
											<div class="rs"><span class="req fs13">은 필수입력 사항입니다.</span></div>
										</div>
										<table class="grid view">
											<caption>설정 입력</caption>
											<colgroup><col width="20%"><col width="80%"></colgroup>
											<tbody>
												<tr>
													<th><span class="req pl10">사용 유무</span></th>
													<td>
														<div class="row">
															<ul class="dp_inline">
																<li><input type="radio" name="useYn" id="useY" value="Y" ${'Y' eq data.useYn || empty data ? 'checked="checked"':'' }><label for="useY" class="pl5">사용</label></li>
																<li><input type="radio" name="useYn" id="useN" value="N" ${'N' eq data.useYn ? 'checked="checked"':'' }><label for="useN" class="pl5">미사용</label></li>
															</ul>
														</div>
													</td>
												</tr>
												<tr>
													<th><span class="pl10">제한단어</span></th>
													<td><div class="row pt5 pb5"><textarea style="width: 100%; height: 300px" name="contents">${data.contents}</textarea></div></td>
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
								</div>
							</div>
							
							<div class="comment_box">
								<ul>
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- 낱말은 '<strong>|</strong>' 로 구분하여 등록 해주시기 바랍니다.</li>
									<li>- 한글 2자, 영문 4자 이상으로 등록 해주시기 바랍니다.</li>
									<li>- 비속어 사용 시 비속어는 자동으로 *로 변형합니다.</li>
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