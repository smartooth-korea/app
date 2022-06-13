<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style type="text/css">

#left-nav {
 	width: 330px;
/* 	height: calc(100% - 40px); */
	height: 100%;
	position: fixed;
	top: 100px;
	left: 0;
	z-index: 100;
	background-color: #4D4D4D; 
}

/**나의 정보**/
.sub-left-nav-div-myinfo {
	width: 40%;
	height: 100px;
	border: 0px solid gray;
	float: left;
}

.left-side-bar .left-menu .div-menu-depth1{
	position: relative;
	cursor: pointer;
	
}

.left-side-bar .left-menu .div-menu-depth1 a{
	color : #FFFFFF;
	background: #4D4D4D;
	font-size: 22px;
	text-decoration: none;
	display:block;
	padding: 5px 30px;
	line-height: 74px;
	
	
}

.left-side-bar .left-menu .div-menu-depth1 a:hover{
	color: #000000;
	background: #F2F2F2;
	transition: 0.3s ease;
}

.left-side-bar .left-menu .div-menu-depth1 i{
	margin-right: 20px;
}

.left-side-bar .left-menu .div-menu-depth1 a .dropdownDepth1{
	position: absolute;
	right: 0;
	margin: 25px;
	transition: 0.5s ease;
}

.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 a .dropdownDepth2{
	position: absolute;
	right: 0;
	margin-top: 10px;
 	transition: 0.5s ease;
}


.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2{
	display: none;
}

.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 .div-menu-depth3{
	display: none;
}

/*회원관리 - 개인(파란색으로 변하게하는 부분)*/
/* .left-side-bar .menu .div-menu-depth1 .div-menu-depth2 a:hover{ */
/* 	padding-left: 70px; */
/* 	color: #FFFFFF; */
/* 	background-color: #60A3F7; */
/* } */

/* .left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 a.div-menu-depth2-btn{ */
.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 a{
	padding-left: 70px;
	color: #000000;
	background-color: #FFFFFF;
	line-height: 40px;
    font-size: 20px;
}

.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 a.clicked{
	padding-left: 70px;
	color: #000000;
	background-color: #60A3F7;
    line-height: 40px;
    font-size: 20px;
}

.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 a:hover{
	padding-left: 70px;
	color: #000000;
	background-color: #60A3F7;
    line-height: 40px;
    font-size: 20px;
}


/* .left-side-bar .left-menu .div-menu-depth1 .div-menu-depth3 a{ */
/* 	padding-left: 70px; */
/* 	color: #000000; */
/* 	background-color: #FFFFFF; */
/* 	line-height: 40px; */
/*     font-size: 20px; */
/* } */

/* .left-side-bar .left-menu .div-menu-depth1 .div-menu-depth3 a.clicked{ */
/* 	padding-left: 70px; */
/* 	color: #000000; */
/* 	background-color: #60A3F7; */
/*     line-height: 40px; */
/*     font-size: 20px; */
/* } */

/* .left-side-bar .left-menu .div-menu-depth1 .div-menu-depth3 a:hover{ */
/* 	padding-left: 70px; */
/* 	color: #000000; */
/* 	background-color: #60A3F7; */
/*     line-height: 40px; */
/*     font-size: 20px; */
/* } */

/* .left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 a.div-menu-depth2-btn:hover{ */
/* 	color: #FFFFFF; */
/* 	background-color: #60A3F7; */
/* } */


.rotate{
	transform : rotate(90deg);
}

.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 .div-menu-depth3{
	display: none;
}

.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 .div-menu-depth3 a{
	padding-left: 90px;
	color:#000000;
	background: #C4C4C4;
	font-size:18px;
}

.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 .div-menu-depth3 a.clicked{
	padding-left: 90px;
	color:#000000;
	background: #FFFFFF;
	font-size:18px;
}

.left-side-bar .left-menu .div-menu-depth1 .div-menu-depth2 .div-menu-depth3 a:hover{
	padding-left: 90px;
	color:#000000;
	background: #FFFFFF;
	font-size:18px;
}

</style>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">


$(document).ready(function(){
	
	// 로딩 시 대시 보드가 활성화 되도록 설정

	
	
	
	$('.div-menu-depth1-btn').click(function(){
		$(this).next('.div-menu-depth2').slideToggle();
		$(this).find('.dropdownDepth1').toggleClass('rotate');
	});
	
	$('.div-menu-depth2-btn').click(function(){
		$(this).next('.div-menu-depth3').slideToggle();
		$(this).find('.dropdownDepth2').toggleClass('rotate');
	});

	
	
	
	var div2 =  document.getElementsByClassName("div-menu-depth2-btn");

	function handleClick1(event) {
		if (event.target.classList[1] === "clicked") {
			event.target.classList.remove("clicked");
		} else {
			for (var i = 0; i < div2.length; i++) {
				div2[i].classList.remove("clicked");
			}
	        event.target.classList.add("clicked");
		}
    }
	function init1() {
		
		for (var i = 0; i < div2.length; i++) {
			div2[i].addEventListener("click", handleClick1);
		}
	}
	
	init1();
	
	var div3 =  document.getElementsByClassName("div-menu-depth3-btn");

	function handleClick2(event) {
		if (event.target.classList[1] === "clicked") {
			event.target.classList.remove("clicked");
		} else {
			for (var i = 0; i < div3.length; i++) {
				div3[i].classList.remove("clicked");
			}
	        event.target.classList.add("clicked");
		}
    }
	function init2() {
		for (var i = 0; i < div3.length; i++) {
			div3[i].addEventListener("click", handleClick2);
		}
	}
	
	init2();
	
	
});
	
</script>
<script src="/js/common.js"></script>
<div id="left-nav">
	<div class="left-ul" style="height: 100%;">
		<div class="left-li">
			<div class="sub-left-nav-div-myinfo">
				<!-- mypage로 이동 -->
				<img class="logo" alt="" src="/imgs/layout/menu/icon/tn.png"
					onclick="#"
					style="display: inline-block; position: fixed; left: 30px; top: 115px;">
			</div>
			<div style="color: #FFFFFF; margin-top: 8px; height: 92px;"">
				<div style="font-size: 22px;">정주현</div>
				<div style="font-size: 12px;">서버개발자</div>
				<div style="font-size: 12px;">2022/05/02</div>
				<div style="font-size: 12px;">
					<input type="button" value="로그아웃" onclick="location.href='/logout'">
				</div>
			</div>
		</div>
		<div class="left-li">
			<div class="sub-left-div-hr">
				<hr style="border: solid 1px #3F3F3F; width: 85%">
			</div>
		</div>
		<div class="left-side-bar"> 
            <div class="left-menu">
                <div class="div-menu-depth1">
                	<a href="/main.do" ><i class="fa-solid fa-border-all"></i>대시보드</a>
               	</div>
                <div class="div-menu-depth1">
                	<a class="div-menu-depth1-btn"><i class="fa-regular fa-user"></i>사용자 관리<i class="fas fa-angle-right dropdown dropdownDepth1"></i></a>
					<div class="div-menu-depth2">
						<a href="/user.do" class="div-menu-depth2-btn">개인</a>
						<a href="#" class="div-menu-depth2-btn">그룹<i class="fas fa-angle-right dropdown dropdownDepth2"></i></a>
						<div class="div-menu-depth3">
							<a href="/group.do?gubn=A" class="div-menu-depth3-btn">A유치원</a>
							<a href="/group.do?gubn=B" class="div-menu-depth3-btn">B유치원</a>
							<a href="/group.do?gubn=C" class="div-menu-depth3-btn">C유치원</a>
						</div>
						<a href="#" class="div-menu-depth2-btn">단체</a>
					</div>
               	</div>
                <div class="div-menu-depth1">
                	<a class="div-menu-depth1-btn"><i class="fa-solid fa-chart-line"></i>통계 정보<i id="arrow" class="fas fa-angle-right dropdown dropdownDepth1"></i></a>
                	<div class="div-menu-depth2">
						<a href="/" class="div-menu-depth2-btn">개인</a>
						<a href="#" class="div-menu-depth2-btn">그룹<i class="fas fa-angle-right dropdown dropdownDepth2"></i></a>
						<div class="div-menu-depth3">
							<a href="/?gubn=A" class="div-menu-depth3-btn">A유치원</a>
							<a href="/?gubn=B" class="div-menu-depth3-btn">B유치원</a>
							<a href="/?gubn=C" class="div-menu-depth3-btn">C유치원</a>
						</div>
						<a href="#" class="div-menu-depth2-btn">단체</a>
					</div>
               	</div>
            </div>
        </div>     
	</div>
</div>

