<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.textButton{
	text-align: center;
	height: 150px;
	width: 500px;
	font-size: 40px;
	
}

#resetButton{
	background-color: #448fe1;
	border-color: #448fe1;
	border-radius: 5px;
	color : #ffffff;
	text-decoration: inherit;
	height: 150px;
	width: 500px;
	font-size: 40px;
	
}
</style>
</head>

<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	// input 태그 값 추출
	var strPwd1 = $('#userPwd_first').val();
	var strPwd2 = $('#userPwd_second').val();
	
	function pwdChk(){
		
		var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;
		var userId = $('#userId').val();
		var strPwd1 = $('#userPwd_first').val();
		var strPwd2 = $('#userPwd_second').val();
		if(strPwd1 != strPwd2){
			alert("The passwords entered do not match.");
			return false;
		}else{
			document.getElementById('frm').submit();
			alert("Your password has been changed.");
			
			var userAgent = navigator.userAgent;
			var visitedAt = (new Date()).getTime(); // 방문 시간
// 			if (userAgent.match(/iPhone|iPad|iPod/)) {
			if (true) {
				setTimeout(
						function() {
							if ((new Date()).getTime() - visitedAt < 2000) {
// 								location.href = "https://apps.apple.com/app/id1548711244"; // 마켓주소
							}
						}, 500);
				setTimeout(function() { 
// 					location.href = "{itms-apps://itunes.apple.com/app/1597422687}"; // 커스텀 스킴주소
// 					window.open("itms-apps://itunes.apple.com/app/1548711244");
// 					location.href = "{itms-apps://itunes.apple.com/app/1548711244}"; // 커스텀 스킴주소 :: 테스트
				}, 0);
			}
		}
	}
</script>
<body>
	<form id="frm" action="/app/user/updateUserPwd.do" method="post">
	<input type="hidden" id="userId" name="userId" value="${userId}">
	<div align="center" style="position: relative; top: 250px;">
		<div>
			<input type="password" id="userPwd_first" class="textButton" name="userPwd"/>
			<div style="height:20px;"></div>
			<input type="password" id="userPwd_second" class="textButton" name="userPwd"/>
		</div>
		<div>
			<div style="height:20px;"></div>
			<input type="button" id="resetButton" value="Reset Your Password" onclick="pwdChk()">
		</div>
	</div>
	</form>
	
</body>
</html>