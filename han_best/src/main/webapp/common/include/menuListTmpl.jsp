<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ page session="true" %>
<c:if test="${not empty menuList}">
<ul class="depth_2">
	<c:forEach var="menuVOOne" items="${menuList}" varStatus="status">
		<c:set var="menuVO" value="${menuVOOne}" scope="request" />
		<jsp:include page="menuTmpl.jsp" />
	</c:forEach>
</ul>
</c:if>
