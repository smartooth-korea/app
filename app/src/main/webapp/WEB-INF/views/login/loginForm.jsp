<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="㈜스마트코리아" />
<meta name="description" content="Smartooth" />
<title>Smartooth 관리자 페이지 :: 로그인</title>
<link rel="shortcut icon" href="#"><!-- favicon ico 에러 -->
<link href="/css/common/sub.css" rel="stylesheet" type="text/css" />
<link href="/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<form name="frm" method="post" action="/login.do">
		<div class="commonHeight120"></div>
		<div align="center">
			<img src="/imgs/login/login_logo.png" alt="㈜스마투스코리아 로고" style="width: 380px" />
		</div>
		<div class="commonHeight60"></div>
		<div align="center">
			<div class="input-background">
				<div class="commonPaddingTop15">
					<input type="text" id="userId" class="loginInput" name="userId" value="" placeholder="아이디" />
					<input type="password" id="userPwd" class="loginInput" name="userPwd" value="" placeholder="비밀번호" />
				</div>
			</div>
		</div>
		<div class="commonHeight15"></div>
		<div align="center">
			<input type="checkbox" id="login_chk"/><font color="#FFFFFF">아이디 저장하기</font>
		</div>
		<div class="commonHeight15"></div>
		<div align="center">
<!-- 			<input type="button" id="login_btn" value="로그인" onclick="handleCookie(); opSubmit(); return false;" /> -->
			<input type="button" id="login_btn" value="로그인" onclick="handleCookie(); submit();" />
		</div>
	</form>
	
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="/js/password.js"></script>

<script type="text/javascript">

	$(document).ready(function() {
		var smartoothId = GetCookie("smartoothId");
		if (smartoothId != "" && smartoothId != "undefined"
				&& smartoothId != null) {
			$("#login_chk").prop("checked", true);
			$("#userId").val(smartoothId);
		} else {
			$("#login_chk").prop("checked", false);
		}
		;
		$("#userId").focus();
	});

	function opSubmit() { // 해당 암호화를 사용해야할지 여부 확인
		if ($("#userId").val() == "") {
			alert("아이디를 입력해주세요.");
			$("#userId").focus();
			return false;
		}
		if ($("#userPwd").val() == "") {
			alert("비밀번호를 입력해주세요");
			$("#userPwd").focus();
			return false;
		}

		if ($("#userId").val() != "") {
			var encId = opEncrypt($("#userId").val());
			$("#encUserId").val(encId);
		}
		if ($("#userPwd").val() != "") {
			var encPwd = opEncrypt($("#userPwd").val());
			$("#encUserPwd").val(encPwd);
		}

		$("#userId").prop("disabled", true);
		$("#userPwd").prop("disabled", true);
		$("#frm").submit();
	}

	function handleCookie() {
		if ($("#login_chk").is(":checked")) {
			
			var userId = $("#userId").val();
			SetCookie("smartoothId", userId, 365);
		} else {
			SetCookie("smartoothId", "", -1);
		}
	}

	function getCookieVal(offset) {
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr == -1)
			endstr = document.cookie.length;
		return unescape(document.cookie.substring(offset, endstr));
	};

	function GetCookie(name) {
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg)
				return getCookieVal(j);
			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0)
				break;
		}
		return null;
	};

	function SetCookie(name, value, expires) {
		var argv = SetCookie.arguments;
		var argc = SetCookie.arguments.length;
		var expires = new Date(Date.now() + (expires * 24 * 60 * 60 * 1000));
		var path = "/";
		var domain = $(location).attr("host");
		document.cookie = name
				+ "="
				+ escape(value)
				+ ((expires == null) ? "" : ("; expires=" + expires
						.toGMTString()))
				+ ((path == null) ? "" : ("; path=" + path))
				+ ((domain == null) ? "" : ("; domain=" + domain));
	};

	$(".loginInput").keydown(function(key) {
		if (key.keyCode == 13) {
			opSubmit();
		}
	});
</script>
</body>
</html>


