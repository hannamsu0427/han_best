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
	injectFile("/js/editor/record.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>내용 관리</h3>
					<ul class="location">
						<li>기능 관리 </li>
						<li>에디터 관리 </li>
						<li>${dataConfig.title} </li>
						<li class="here">내용 관리</li>
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
									<li class="p50"><a href="/iWORK/Editor/Config.do?seq=<c:out value="${dataConfig.seq}"/>" ><span>기본 설정</span></a></li>
									<li class="p50"><a href="/iWORK/Editor/Record.do?configSeq=<c:out value="${dataConfig.seq}"/>" class="on"><span>내용 관리</span></a></li>
								</ul>
							</div>
						
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>${dataConfig.title}</h3></div>
								</div>
								<div class="grid_top">
									<div class="ls">
										<ul class="dp_inline">
											<li><label for="seq"><strong>선택한 내용</strong></label></li>
											<li><select name="seq" class="w250">
													<option value="">페이지 편집 히스토리</option>
													<c:forEach var="data" items="${dataList }" varStatus="status">
														<option value="<c:out value="${data.seq}" />" <c:if test="${status.first }">selected="selected"</c:if>>${status.count }_<c:out value="${data.regDateFmt}" /></option>
													</c:forEach>
												</select>
											</li>
											<li><button type="button" name="" id="" class="bt_inner" data-itsp-data-choice>불러오기</button></li>
											<li><button type="button" name="" id="" class="bt_inner" data-itsp-save-link>저장하기</button></li>
										</ul>
									</div>
								</div>
								
								<div class="post_form">
									<form action="/Editor/Record/insertProcData.do" name="fm" id="fm" method="post">
									<input type="hidden" name="configSeq" value="<c:out value="${dataConfig.seq}"/>" readonly="readonly">
									<table class="grid view">
										<caption>기본정보 목록</caption>
										<colgroup><col width="15%"><col width="85%"></colgroup>
										<tbody>
											<tr>
												<th><span class="req pl10">내용</span></th>
												<td>
													<div class="pt10 pb10">
														<textarea name="contents" id="contents" rows="5" class="p100" data-itsp-title="내용" data-itsp-html-editor><c:out value="${cmutls:removeScript(dataList[0].contents) }"/></textarea>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
									</form>
								</div>
								
							</div>
							<!-- //post_tbl -->
							
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_on" data-itsp-save-link>저장</button>
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