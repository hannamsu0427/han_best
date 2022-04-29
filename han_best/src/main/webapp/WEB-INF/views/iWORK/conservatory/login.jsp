<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<%@ include file="/iWORK/conservatory/include/meta.jsp"%>
<title>글로벌문화예술교육원</title>
<%@ include file="/iWORK/conservatory/include/link.jsp"%>
<%@ include file="/iWORK/conservatory/include/script.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$('form').bind("keypress", function(e) {
		if (e.keyCode == 13) {
			$("#fm").submit();
			return false;
		}
	});
	$('input[name=user_id]').focus();
	$("input[name=user_id]").keydown(function(e) {
		if (e.keyCode == 13) {
			if ($("input#user_id").val().trim() == "") {
				alert("아이디를 입력해 주세요.");
				$("input#user_id").focus();
				return false;
			}
			$('input[name=pwd]').focus();
		}
	});
	$("input[name=pwd]").keydown(function(e) {
		if (e.keyCode == 13) {
			if ($("input#user_id").val().trim() == "") {
				alert("아이디를 입력해 주세요.");
				$("input#user_id").focus();
				return false;
			}
			if ($("input#pwd").val().trim() == "") {
				alert("비밀번호를 입력해 주세요.");
				$("input#pwd").focus();
				return false;
			}
			$("#fm").submit();
			return false;
		}
	});
	
	$("#loginBtn").click(function(e) {
		if ($("input#user_id").val().trim() == "") {
			alert("아이디를 입력해 주세요.");
			$("input#user_id").focus();
			return false;
		}
		if ($("input#pwd").val().trim() == "") {
			alert("비밀번호를 입력해 주세요.");
			$("input#pwd").focus();
			return false;
		}
		$("#fm").submit();
		return false;
	});
	
});
</script>
</head>
<body>
	<%@ include file="/iWORK/conservatory/include/u_skip.jsp"%>
	<%@ include file="/iWORK/conservatory/include/header.jsp"%>
	<!-- //header -->
	<div id="container">
		<div class="sub_container">
			<section>
				<article>
					<div class="page_header visual07">
						<div class="page_header_inner">
							<ul class="location">
								<li>Home</li>
								<li class="here">로그인</li>
							</ul>
							<h2 class="gnb_depth1_tit">로그인</h2>
						</div>
					</div>
				</article>
				<article class="page_conts">
					<div class="page_conts_tit">
						<h3 class="">로그인</h3>
					</div>
					<div class="login">
						<div class="login_wrap">
							<h1><span>Login</span></h1>
							<div class="login_form">
								<form action="/logInProc.do" method="post" autocomplete="off" id="fm">
								<input type="hidden" name="url" id="url" value="${url }">
								<fieldset>
									<legend>Login Form</legend>
									<ul>
										<li>
											<div class="id">
												<label for="user_id" class="blind">ID</label><input type="text" name="user_id" id="user_id" placeholder="ID" class="txt_field">
											</div>
										</li>
										<li>
											<div class="pw">
												<label for="pwd" class="blind">Password</label> <input type="password" name="pwd" id="pwd" placeholder="PASSWORD" maxlength="30" autocomplete="off" class="txt_field">
											</div>
										</li>
									</ul>
									<div class="bt_login_wrap">
										<button type="button" name="loginBtn" id="loginBtn" class="bt_login">로그인</button>
									</div>
								</fieldset>
								</form>
							</div>
						</div>
					</div>
					<!--// login -->
				</article>
				<!-- // page_conts -->
			</section>
		</div>
		<!-- //sub_container -->
		<aside>
			<%@ include file="/iWORK/conservatory/include/floatMenu.jsp"%>
		</aside>
	</div>
	<!-- // container -->
	<%@ include file="/iWORK/conservatory/include/footer.jsp"%>
</body>
</html>