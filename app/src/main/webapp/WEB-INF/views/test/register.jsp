<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
</head>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#submit').click(function(){ 
		$.ajax({
			type:'POST',   //post 방식으로 전송
			url:'/app/user/register.do',   //데이터를 주고받을 파일 주소
			data:JSON.stringify ({ //변수에 담긴 데이터를 전송해준다 (JSON 방식)
				
				"userId" : "test"
				,"userPwd" : "1234"
				,"userNm" : "관리자"
				,"userNickname" : "관리자"
				,"userType" : "G"
				,"userBirthday" : "2019-01-09"
				,"userCountry" : "KR"
				,"userState" : ""
				,"userAddress" : "청화대"
				,"userTelNo" : "010-0000-0000"
				,"userSex" : "M"
				,"pushToken" : "P.U.S.H.T.O.K.E.N"
				,"teethStatus" : "200|100|100|100|100|100|100|100|100|100|100|100|100|100|100|200|100|100|100|100|100|100|100|100|100|100|100|100|100|100|100|100"
				
			}),
			dataType:'JSON', //데이터 타입 JSON
			contentType : "application/json; charset=UTF-8",
			success : function(data){   //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
				$('#message').html(data);  //현재 화면 위 id="message" 영역 안에 data안에 담긴 html 코드를 넣어준다. 
			}
		});
	});
});
</script>
<body>
	회원가입 >>> <input type="button" id="submit" value="버튼"/>
</body>
</html>