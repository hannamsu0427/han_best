<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ page session="false" %>
<c:if test="${not empty menuList}">
	<ul class="gnb_depth2">
		<c:forEach var="menuVOOne" items="${menuList}" varStatus="status">
			<c:set var="menuVO" value="${menuVOOne}" scope="request" />
			<jsp:include page="headerMenuTmpl.jsp" />
		</c:forEach>
	</ul>
</c:if>
