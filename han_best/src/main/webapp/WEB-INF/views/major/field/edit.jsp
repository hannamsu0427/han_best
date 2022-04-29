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
<title>계열 관리_게시물관리 목록</title>
<!-- PC Favicon -->
<link href="/css/admin/style.css" rel="stylesheet" type="text/css">
<%@ include file="/common/include/script.jsp" %>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임) 
	injectFile("/js/major/field.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>계열 관리</h3>
					<ul class="location">
						<li>기능 관리 </li>
						<li>계열 관리 </li>
						<li>${dataAbout.title } </li>
						<li class="here">진로 및 활동분야</li>
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
									<li style="width: 12.5%"><a href="About.do?seq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>기본 설정</span></a></li>
									<li style="width: 12.5%"><a href="IntroList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>전공소개</span></a></li>
									<li style="width: 12.5%"><a href="ProfessorList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>"><span>교수진</span></a></li>
									<li style="width: 12.5%"><a href="GoalList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>교육목표</span></a></li>
									<li style="width: 12.5%"><a href="CurriculumList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>교육과정</span></a></li>
									<li style="width: 12.5%"><a href="CourseList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>교과과정</span></a></li>
									<li style="width: 12.5%"><a href="FieldList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" class="on"><span>진로 및 활동분야</span></a></li>
									<li style="width: 12.5%"><a href="FacilitiesList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>실습실</span></a></li>
								</ul>
							</div>

							<form action="/Major/Field/saveProcData.do" name="fm" id="fm" method="post">
							<input type="hidden" name="aboutSeq" value="<c:out value="${empty data ? MAJOR_ABOUT_SEQ : data.aboutSeq}"/>" readonly="readonly">
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
											<td>
												<div class="pt5 pb5">
													<textarea name="group" id="group" rows="2" class="p100" placeholder="제목"><c:out value="${cmutls:removeScript(data.group) }"/></textarea>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="pl10">내용</span></th>
											<td>
												<div class="pt5 pb5">
													<textarea name="groupInfo" id="groupInfo" rows="5" class="p100" placeholder="내용"><c:out value="${cmutls:removeScript(data.groupInfo) }"/></textarea>
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
									<button type="button" name="" id="" class="bt bt_nor" onclick="location.href='/iWORK/Major/FieldList.do'">목록</button>
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
</body>
</html>