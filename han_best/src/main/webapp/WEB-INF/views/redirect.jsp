<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script type="text/javascript">
	<c:if test="${not empty msg}">
	alert("${msg}");
	</c:if>
	<c:if test="${not empty redirectUrl}">
	location.href = "${redirectUrl}";
	</c:if>
</script>