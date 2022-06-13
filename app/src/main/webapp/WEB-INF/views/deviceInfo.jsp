<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	//[요청 json 데이터 선언] ::: 치아 측정
// 	var jsonData = { 
// 		"USER_ID" : "jungjuhyun12@gmail.com"
// 	};
	
	
	
	$('#submit').click(function(){ 
		$.ajax({
			type:'POST',   //post 방식으로 전송
// 			url:'/app/user/signUp/emailAuth.do',   //데이터를 주고받을 파일 주소
			url:'/app/user/insertDeviceInfo.do',   //데이터를 주고받을 파일 주소
			data:JSON.stringify ({
				"USER_ID" : "jungjuhyun12@gmail.com"
				,"USER_NO" : "KR-I-0002"
				,"DEVICE_NM" : "Smartooth-device"
				,"DEVICE_CODE" : "MLK13KH/A"
				,"SERIAL_NO" : "T5H72W4XGV88"
				,"MAC_ADDRESS" : "6E400001-B5A3-F393-E0A9-E50E24DCCA9E"
			}),   //위의 변수에 담긴 데이터를 전송해준다.
			dataType:'JSON',   //json 파일 형식으로 값을 담아온다.
			contentType : "application/json; charset=UTF-8",
			success : function(data){   //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
				$('#message').html(data);  //현재 화면 위 id="message" 영역 안에 data안에 담긴 html 코드를 넣어준다. 
			}
		});
	});
});
</script>
<body>
		<input type="button" id="submit" value="버튼"/>
</body>
</html>