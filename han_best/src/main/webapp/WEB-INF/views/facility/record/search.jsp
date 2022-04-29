<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<form name="curSearchForm" id="curSearchForm">
	<input type="hidden" name="searchBy" value="<c:out value="${searchBy }"/>" />
	<input type="hidden" name="searchValue" value="<c:out value="${searchValue }"/>" />
</form>