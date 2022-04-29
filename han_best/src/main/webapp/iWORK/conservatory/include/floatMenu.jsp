<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#floatMenu .bt_gotop').click( function() {
			$('html, body').animate( { scrollTop : 0 }, 400 );
			return false;
		});
	});
</script>
<div class="aside_link">
	<ul id="floatMenu">
		<li><a href="/Application.do?menuSeq=550&configSeq=280"><button type="button" class="bt_apply"></button><span class="tiptxt">원서접수</span></a></li>
		<li><a href="/BoardList.do?menuSeq=158&configSeq=159"><button type="button" class="bt_download"></button><span class="tiptxt">브로슈어 다운로드</span></a></li>
		<li><a href="https://lifeiis.chugye.ac.kr/2010/login/login.asp" target="_blank"><button type="button" class="bt_lifeiis"></button><span class="tiptxt">학사시스템</span></a></li>
		<li><a href="#"><button type="button" class="bt_gotop"></button><span class="tiptxt">맨위로</span></a></li>
	</ul>
</div>