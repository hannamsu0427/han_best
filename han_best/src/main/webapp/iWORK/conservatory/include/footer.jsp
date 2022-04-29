<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<footer id="footer">
	<div class="footer_inner">
		<div class="link">
			<ul class="link_area">
				<li><a href="http://www.chugye.ac.kr/mbs/university/subview.jsp?id=university_080201000000" target="_blank" title="새창으로 이동"><strong class="extra">개인정보처리방침</strong></a></li>
				<li><a href="https://www.chugye.ac.kr/mbs/university/jsp/board/list.jsp?boardId=545845&id=university_080500000000" target="_blank" title="새창으로 이동">개인정보공시</a></li>
				<li><a href="http://www.chugye.ac.kr/mbs/university/subview.jsp?id=university_080301000000" target="_blank" title="새창으로 이동">이메일무단수집거부</a></li>
				<c:choose>
					<c:when test="${empty sessionScopeMember }">
						<li><a href="<c:url value="/logIn.do?${url}"/>" title="새창으로 이동"><span>로그인</span></a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/iWORK/MemberList.do"><span>관리자</span></a></li>
						<li><a href="/logOutProc.do"><span>로그아웃</span></a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="#container" title="위로이동" class="bt_top"><img src="/images/front/common/bt_gotop.png" alt=""></a></li>
			</ul>
		</div>
		<div class="foot_cont">
			<div class="foot_sns">
				<a href="https://www.facebook.com/chugyeCUFA" target="_blank" title="페이스북 - 페이지이동"><img src="http://www.chugye.ac.kr/mbs/conservatory/images/ico/ico_facebook.png" alt="페이스북"></a>
				<a href="https://blog.naver.com/chugyegica" target="_blank" title="블로그 - 페이지이동"><img src="http://www.chugye.ac.kr/mbs/conservatory/images/ico/ico_blog.png" alt="블로그"></a>
				<a href="https://www.instagram.com/chugyegica" target="_blank" title="인스타그램 - 페이지이동"><img src="http://www.chugye.ac.kr/mbs/conservatory/images/ico/ico_insta.png" alt="인스타그램"></a>
			</div>
			<div class="foot_addr">
				<ul>
					<li>30762 서울특별시 서대문구 북아현로 11가길 7 청사관</li>
					<li class="inline"><span>TEL : 02-3147-1230</span><span>FAX : 02-3147-1231</span></li>
					<li class="copyright">Copyright 2019 <em>CHUGYE UNIVERSITY FOR THE ARTS</em> All Right Reserved.</li>
				</ul>
			</div>
		</div>
		<!-- // foot_cont -->
	</div>
</footer>