<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style type="text/css">

#left-nav {
 	width: 330px;
	height: calc(100% - 40px);
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

.side-bar .menu .item{
	position: relative;
	cursor: pointer;
	
}

.side-bar .menu .item a{
	color : #FFFFFF;
	background: #4D4D4D;
	font-size: 22px;
	text-decoration: none;
	display:block;
	padding: 5px 30px;
	line-height: 74px;
	
	
}

.side-bar .menu .item a:hover{
	color: #000000;
	background: #F2F2F2;
	transition: 0.3s ease;
}

.side-bar .menu .item i{
	margin-right: 18px;
}

.side-bar .menu .item a .dropdown{
	position: absolute;
	right: 0;
	margin: 25px;
	transition: 0.3s ease;
}


.side-bar .menu .item .sub-menu{
	display: none;
}

.side-bar .menu .item .sub-menu a{
	padding-left: 70px;
	color: #000000;
	background-color: #FFFFFF;
    line-height: 40px;
    font-size: 18px;
}
/*회원관리 - 개인(파란색으로 변하게하는 부분)*/
.side-bar .menu .item .sub-menu a:hover{
	padding-left: 70px;
	color: #FFFFFF;
	background-color: #60A3F7;
}

#sub-item-btn-individual-hover{
	padding-left: 70px;
	color: #FFFFFF;
	background-color: #60A3F7;
}

#sub-item-btn-group-hover{
	padding-left: 70px;
	color: #FFFFFF;
	background-color: #60A3F7;
}

#sub-item-btn-organization-hover{
	padding-left: 70px;
	color: #FFFFFF;
	background-color: #60A3F7;
}

.rotate{
	transform : rotate(90deg);
}

.side-bar .menu .item .sub-menu .sub-menu-group{
	display: none;
}

.side-bar .menu .item .sub-menu .sub-menu-group a{
	padding-left: 80px;
	background: #C4C4C4;
}

.side-bar .menu .item .sub-menu .sub-menu-group a:hover{
	padding-left: 80px;
	color:#000000;
	background: #FFFFFF;
}

</style>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

var btnClick = 0;
$(document).ready(function(){
	
	// 로딩 시 대시 보드가 활성화 되도록 설정
	//var title = $("#title").val();
	$('.sub-btn').click(function(){
		$(this).next('.sub-menu').slideToggle();
		$(this).find('.dropdown').toggleClass('rotate');
	});
	
// 	$('.sub-item-btn').click(function(){
// 		$(this).next('.sub-menu-group').slideToggle();
// 		$(this).find('.dropdown').toggleClass('rotate');
// 	});

	var div2 =  document.getElementsByClassName("sub-menu");

	function handleClick(event) {
		// console.log(this);
		// 콘솔창을 보면 둘다 동일한 값이 나온다

		console.log(event.target.classList);

		if (event.target.classList[1] === "clicked") {
			event.target.classList.remove("clicked");
		} else {
			
			for (var i = 0; i < div2.length; i++) {
				div2[i].classList.remove("clicked");
			}
	        event.target.classList.add("clicked");
		}
    }

	function init() {
		for (var i = 0; i < div2.length; i++) {
			div2[i].addEventListener("click", handleClick);
		}
	}

    init();
	
});
	
</script>
<script src="/js/common.js"></script>
<input type="hidden" id="title" value="${title}" />
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
				<div style="font-size: 12px;">&nbsp;서버개발자</div>
				<div style="font-size: 12px;">&nbsp;2022/05/02</div>
				<div class="commonHeight5"></div>
				<div style="font-size: 12px; margin-top: 3px;">
					<input type="button" value="로그아웃" onclick="location.href='/logout'">
				</div>
			</div>
		</div>
		<div class="left-li">
			<div class="sub-left-div-hr">
				<hr style="border: solid 1px #3F3F3F; width: 85%">
			</div>
		</div>
		<div class="side-bar"> 
            <div class="menu">
                <div class="item"></div>
                <div class="item"><a href="#" ><i class="fa-solid fa-border-all"></i>대시보드</a></div>
                <div class="item">
                	<a class="sub-btn"><i class="fa-regular fa-user"></i>사용자 관리<i class="fas fa-angle-right dropdown"></i></a>
					<div class="sub-menu">
						<a href="#" class="sub-item-btn">개인</a>
						<a href="#" class="sub-item-btn">그룹</a>
						<div class="sub-menu-group">
							<a href="#" class="sub-item-group">A유치원</a>
							<a href="#" class="sub-item-group">B유치원</a>
							<a href="#" class="sub-item-group">C유치원</a>
						</div>
						<a href="#" class="sub-item-btn">단체</a>
					</div>               	
               	</div>
                <div class="item"><a href="#"><i class="fa-solid fa-chart-line"></i>통계 정보</a></div>
            </div>
        </div>     
	</div>
</div>