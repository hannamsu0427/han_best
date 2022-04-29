<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<form name="curPageForm" id="curPageForm">
	<input type="hidden" name="aboutSeq" value="<c:out value="${MAJOR_ABOUT_SEQ }"/>" />
	<input type="hidden" name="curPage" value="<c:out value="${curPage }"/>" />
	<input type="hidden" name="pageNum" value="<c:out value="${pageNum }"/>" />
</form>
