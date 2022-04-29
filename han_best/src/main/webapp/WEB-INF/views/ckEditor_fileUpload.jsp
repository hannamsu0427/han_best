<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<script type="text/javascript" src="/common/js/jQuery/jquery-1.9.0.js"></script>
<script type="text/javascript">
	$(function() {
		if ('${sizeExcess}' != "") {
			alert('${sizeExcess}\n창을 닫고 다시 시도하여 주십시오.');
		} else if ('${error}' != "") {
			alert('${error}\n창을 닫고 다시 시도하여 주십시오.');
		} else {
			window.parent.CKEDITOR.tools.callFunction('${CKEditorFuncNum}', '${filePath}', '');
		}
	});
</script>
</head>
</html>