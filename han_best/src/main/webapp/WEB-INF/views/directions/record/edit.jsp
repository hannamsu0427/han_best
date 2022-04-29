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
	injectFile("/js/directions/record.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>오시는길 관리</h3>
					<ul class="location">
						<li>기능 관리 </li>
						<li>오시는길 관리 </li>
						<li class="here">내용 관리</li>
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
									<form action="/Directions/Record/saveProcData.do" method="post" name="fm" id="fm">
										<input type="hidden" name="seq" value="${data.seq}" readonly="readonly"/>
										<div class="grid_top">
											<div class="ls"><h3>기본 정보</h3></div>
											<div class="rs"><span class="req fs13">은 필수입력 사항입니다.</span></div>
										</div>
										<table class="grid view">
											<caption>쓰기</caption>
											<colgroup><col width="20%"><col width="80%"></colgroup>
											<tbody>
												<tr>
													<th><span class="req pl10">제목</span></th>
													<td><div class="row"><input type="text" name="title" id="title" value="${data.title }" title="" placeholder="제목" class="p100" data-itsp-title="제목" data-itsp-required></div></td>
												</tr>
												<tr>
													<th><span class="req pl10">주소</span></th>
													<td><div class="row"><input type="text" name="address" id="address" value="${data.address }" title="" placeholder="주소" class="p100" data-itsp-title="주소" data-itsp-required></div></td>
												</tr>
												<tr>
													<th><span class="pl10">상세주소</span></th>
													<td><div class="row"><input type="text" name="detailAddress" id="detailAddress" value="${data.detailAddress }" title="" placeholder="상세주소" class="p100"></div></td>
												</tr>
												<tr>
													<th><span class="pl10">내용</span></th>
													<td><div class="pt5 pb5"><textarea class="itsp" name="contents" id="contents" data-itsp-html-editor data-itsp-title="내용">${cmutls:removeScript(data.contents) }</textarea></div></td>
												</tr>
												<c:if test="${not empty data }">
												<tr>
													<th><span class="pl10">지도</span></th>
													<td>
														<div id="map" class="pt5 pb5" style="width: 99%; height: 400px;"></div>
													</td>
												</tr>
												</c:if>
											</tbody>
										</table>
									</form>
								</div>
								<!-- //post_tbl -->
							</div>
							
							<div class="post_bottom">
								<div class="bt_list">
									<button type="button" name="" id="" class="bt bt_on" data-itsp-save-link>확인</button>
									<button type="button" name="" id="" class="bt bt_nor" onclick="location.href='/iWORK/Directions/RecordList.do'">목록</button>
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
	
	<c:if test="${not empty data }">
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e642f9a683230a911532b9d2b5fa9314&libraries=services"></script>
	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level : 3, // 지도의 확대 레벨
		};

		// 지도를 생성합니다    
		var map = new daum.maps.Map(mapContainer, mapOption);
		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new daum.maps.services.Geocoder();

		// 주소로 좌표를 검색합니다
		geocoder.addressSearch('${data.address}', function(result, status) {
			// 정상적으로 검색이 완료됐으면 
			if (status === daum.maps.services.Status.OK) {
				var coords = new daum.maps.LatLng(result[0].y, result[0].x);
				// 결과값으로 받은 위치를 마커로 표시합니다
				var marker = new daum.maps.Marker({
					map : map,
					position : coords
				});

				// 인포윈도우로 장소에 대한 설명을 표시합니다
				var infowindow = new daum.maps.InfoWindow({
					content : '<div style="width:200px;text-align:center;padding:6px 0;">${data.detailAddress}</div>'
				});
				
				infowindow.open(map, marker);
				// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
				map.setCenter(coords);

				// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
				var mapTypeControl = new daum.maps.MapTypeControl();

				// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
				// daum.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
				map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);

				// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
				var zoomControl = new daum.maps.ZoomControl();
				map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);
			}
		});
	</script>
	</c:if>
<jsp:include page="search.jsp" />	
</body>
</html>