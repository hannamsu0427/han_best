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
<title>계열 관리</title>
<!-- PC Favicon -->
<link href="/css/admin/style.css" rel="stylesheet" type="text/css">
<%@ include file="/common/include/script.jsp" %>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임) 
	injectFile("/js/major/course.js");
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
						<li class="here">교과과정 목록</li>
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
									<li style="width: 12.5%"><a href="IntroList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>"><span>전공소개</span></a></li>
									<li style="width: 12.5%"><a href="ProfessorList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>"><span>교수진</span></a></li>
									<li style="width: 12.5%"><a href="GoalList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>교육목표</span></a></li>
									<li style="width: 12.5%"><a href="CurriculumList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>교육과정</span></a></li>
									<li style="width: 12.5%"><a href="CourseList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" class="on"><span>교과과정</span></a></li>
									<li style="width: 12.5%"><a href="FieldList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>진로 및 활동분야</span></a></li>
									<li style="width: 12.5%"><a href="FacilitiesList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>실습실</span></a></li>
								</ul>
							</div>
						
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>교과과정 목록</h3></div>
								</div>
								
								<div class="post_list">
									<table class="grid">
										<caption>게시물 목록</caption>
										<colgroup><col width="6%"><col width="6%"><col width="*"><col width="10%"><col width="15%"><col width="10%"></colgroup>
										<thead>
											<tr>
												<th>선택</th>
												<th>No.</th>
												<th>제목</th>
												<th>기능설정</th>
												<th>생성일</th>
												<th>사용여부</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${empty dataList }">
												<tr>
													<td colspan="6">생성된 데이터가 없습니다.</td>
												</tr>
											</c:if>
											<c:forEach items="${dataList}" var="data">
												<tr>
													<td><input type="checkbox" name="chkBoxs" id="chkBox_<c:out value="${data.seq}"/>" value="<c:out value="${data.seq}"/>" ></td>
													<td><c:out value="${totalCount - data.rnum + 1}"/></td>
													<td><c:out value="${data.title}"/></td>
													<td><button type="button" name="" value="" title="" class="bt_inner" data-itsp-edit-link="<c:out value="${data.seq }"/>">기능설정</button></td>
													<td><c:out value="${data.regDate}"/></td>
													<td>${'Y' eq data.useYn ? '사용' : '미사용'}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								
							</div>
							<!-- //post_tbl -->
							
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_on" data-itsp-edit-link="0">생성하기</button>
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