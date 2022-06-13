<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
	#top-nav-top{
		background-image: url('/imgs/layout/header/background.png');
		width: 100%;
		height: 100px;
		position: relative;
	}
	
	#top-nav-bottom{
		position: fixed;
		top: 75px;
		left: 65px;
		color: #000000;
	}
	

	#top-nav-top img{
		width: 165px;
		position: fixed;
		top: 18px;
		left: 30px;
	}
	
	.searchInput{
		background-image: url('https://cdn0.iconfinder.com/data/icons/multimedia-solid-30px/30/search-512.png');
	    background-position: 13px center;
	    background-size: contain;
	    background-repeat: no-repeat;
	    
	    position: absolute;
	    top: 27px;
	    right: 10px;
	    width: 150px;
	    height: 27px;
	    border: 1px solid #ccc;
	    text-indent: 30px;
	    color: #3D3D3D;
	}
	
	.search{
	    position: absolute;
	    top: 27px;
	    right: 10px;
	    width: 130px;
	    height: 25px;
	    border: 1px solid #ccc;
	    text-indent: 35px;
	    color: #3D3D3D;
	}
	
	span.searc_icon{
	    z-index: 100;
	    position: fixed;
	    right: 115px;
	    top: 27px;
	}
	
	span.searc_icon:hover{
		cursor: pointer;
	}
</style>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#search").keydown(function(key) {
			if (key.keyCode == 13) {
				onSearch();
			}
		});
	});
	
</script>
<div id="top-nav-top" class="top-nav-top">
	<img alt="" src="/imgs/layout/menu/logo/smartooth.png" onclick="location.href='#'">
</div>
<div>
	<div id="top-nav-bottom" style="float: left;">
		${headerCategoriesName}
	</div>
	<!-- common.js 에 기능 구현 -->
	<span class="searc_icon">
		<i class="fa fa-search" onclick="onSearch();"></i>
	</span> 
	<input type="search" id="search"  class="search" value="" placeholder="검색"/>
</div>