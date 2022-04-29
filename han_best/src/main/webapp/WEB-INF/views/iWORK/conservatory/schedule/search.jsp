<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<form name="curSearchForm" id="curSearchForm">
	<input type="hidden" name="menuSeq" value="<c:out value="${menuSeq }"/>" />
	<input type="hidden" name="configSeq" value="<c:out value="${configSeq }"/>" />
	<input type="hidden" name="year" value="<c:out value="${year }"/>" />
</form>