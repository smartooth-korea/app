$(document).ready(function(){
    var lastEvent = null;
    var slide  = "#nav > .nav_menu > ul";
    var alink  = "#nav > .nav_menu > a";

    function accordion(){
        if (this == lastEvent) return false;
        lastEvent = this;
        setTimeout(function() {lastEvent = null}, 200);

        if ($(this).attr('class') != 'active') {
            $(slide).slideUp();
            $(this).next(slide).slideDown();
            $(alink).removeClass('active');
            $(this).addClass('active');
        } else if ($(this).next(slide).is(':hidden')) {
            $(slide).slideUp();
            $(this).next(slide).slideDown();
        } else {
            $(this).next(slide).slideUp();
        }
    }
    $(alink).click(accordion).focus(accordion);
    $('#nav > li:last > a').addClass('stay');
});

//app에러 상단 슬라이드
$( function () {
    var mySlider = $( '#app_slide' ).bxSlider( {
        mode: 'horizontal',
        touchEnabled:false,
        speed: 500,
        pager: false,
        moveSlides: 1,
        slideWidth: 180,
        //minSlides: 6,
        maxSlides: 7,
        slideMargin: 10,
        auto: false,
        autoHover: true,
        controls: false,
        autoControls:false
    } );

    //이전 버튼을 클릭하면 이전 슬라이드로 전환
    $( '.prev_btn a' ).on( 'click', function () {
        mySlider.goToPrevSlide();  //이전 슬라이드 배너로 이동
        return false;              //<a>에 링크 차단
    } );

    //다음 버튼을 클릭하면 다음 슬라이드로 전환
    $( '.next_btn a' ).on( 'click', function () {
        mySlider.goToNextSlide();  //다음 슬라이드 배너로 이동
        return false;
    } );

});

//대시보드 슬라이드
$( function () {
    var dashSlider = $( '#dashSlide' ).bxSlider( {
        mode: 'horizontal',
        touchEnabled:false,
        speed: 500,
        pager: false,
        moveSlides: 1,
        slideWidth: 146,
        //minSlides: 6,
        maxSlides: 8,
        slideMargin: 25,
        auto: false,
        autoHover: true,
        controls: false,
        autoControls:false
    } );

    //이전 버튼을 클릭하면 이전 슬라이드로 전환
    $( '.ds_prev_btn' ).on( 'click', function () {
        dashSlider.goToPrevSlide();  //이전 슬라이드 배너로 이동
        return false;              //<a>에 링크 차단
    } );

    //다음 버튼을 클릭하면 다음 슬라이드로 전환
    $( '.ds_next_btn' ).on( 'click', function () {
        dashSlider.goToNextSlide();  //다음 슬라이드 배너로 이동
        return false;
    } );

});

//대시보드 Today SR
/*$( function () {
    var todaySlider = $( '#todaySlide' ).bxSlider( {
        mode: 'vertical',
        touchEnabled:false,
        speed: 500,
        pager: false,
        moveSlides: 1,
        slideWidth: 1450,
        maxSlides:1,
        auto: false,
        autoHover: true,
        controls: false,
        autoControls:false
    } );

    //이전 버튼을 클릭하면 이전 슬라이드로 전환
    $( '.today_up_btn' ).on( 'click', function () {
        todaySlider.goToPrevSlide();  //이전 슬라이드 배너로 이동
        return false;              //<a>에 링크 차단
    } );

    //다음 버튼을 클릭하면 다음 슬라이드로 전환
    $( '.today_down_btn' ).on( 'click', function () {
        todaySlider.goToNextSlide();  //다음 슬라이드 배너로 이동
        return false;
    } );

});*/

//슬라이드 hover 효과
$(document).ready(function ()
{
    $('.app_slide_wrap > li').hover(function(){
        $(this).addClass('app_box_on02');
    }, function() {
        $(this).removeClass('app_box_on02');
    });

});

//탭버튼
$(function() {
    $('.tab_con').hide();
    $('.tab_con:eq(0)').show();

    $('.tabMenu li a').click(function(e){

        e.preventDefault();

        $('.tabMenu li').removeClass('tab_on');
        $(this).parent().addClass('tab_on');

        var activeTab = $(this).attr('href').replace("#","");

        $('.tab_con').hide();
        $("."+activeTab).show();
    });
});

//탭버튼
$(function() {
    $('.error_con').hide();
    $('.error_con:eq(0)').show();

    $('.Tab_btn02 li a').click(function(e){

        e.preventDefault();

        $('.Tab_btn02 li').removeClass('error_btn_on');
        $(this).parent().addClass('error_btn_on');

        var activeTab = $(this).attr('href').replace("#","");

        $('.error_con').hide();
        $("."+activeTab).show();
    });
});


//app 에러 팝업
function wrapWindowByMask(){
    //화면의 높이와 너비를 구한다.
    var maskHeight = $(document).height();
    var maskWidth = $(window).width();

    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
    $('#mask').css({'width':maskWidth,'height':maskHeight});

    //애니메이션 효과 - 일단 1초동안 까맣게 됐다가 80% 불투명도로 간다.
    $('#mask').fadeIn(0);
    $('#mask').show("slow",0);

    //윈도우 같은 거 띄운다.
    $('.window').show();
}

$(document).ready(function(){
    //검은 막 띄우기
    $('#app_popup').click(function(e){
        e.preventDefault();
        wrapWindowByMask();
    });

    $('#activity_popup').click(function(e){
        e.preventDefault();
        wrapWindowByMask();
    });

    $('#errorPopup').click(function(e){
        e.preventDefault();
        wrapWindowByMask();
    });
    $('#source_popup_btn').click(function(e){
        e.preventDefault();
        wrapWindowByMask();
    });

    $('#test_list_btn').click(function(e){
        e.preventDefault();
        wrapWindowByMask();
    });

    $('#pwPopup').click(function(e){
        e.preventDefault();
        wrapWindowByMask();
    });

});

$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
        return this;
    }

    showPopup = function () {
        $("#app_all_popup").show();
        $("#app_all_popup").center();
    }
    $('#popup_close').click(function(){
        $('#app_all_popup').hide();
    })
    $('#popup_close').click(function (e) {
        e.preventDefault();
        $('#mask, .window').hide();
    });


});

$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
        return this;
    }

    showPopup02 = function () {
        $("#act_popup").show();
        $("#act_popup").center();
    }
    $('#act_close').click(function(){
        $('#act_popup').hide();
    })
    $('#act_close').click(function (e) {
        e.preventDefault();
        $('#mask, .window').hide();
    });

});

$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
        this.css("right", Math.max(0, (($(window).width() - $(this).outerWidth()) / 0) + $(window).scrollLeft()) + "px");
        return this;
    }

    alramPopup = function () {
        $("#alram_popup").show();
        $("#alram_popup").center();
    }
    $('#alram_close').click(function(){
        $('#alram_popup').hide();
    })


});

$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 1) + $(window).scrollTop()) + "px");
        this.css("right", Math.max(0, (($(window).width() - $(this).outerWidth()) / 1) + $(window).scrollLeft()) + "px");
        return this;
    }

    errorPopup = function () {
        $("#error_con03Popup").show();
        $("#error_con03Popup").center();
    }
    $('#errorPopup_close').click(function(){
        $('#error_con03Popup').hide();
    })
    $('#errorPopup_close').click(function (e) {
        e.preventDefault();
        $('#mask, .window').hide();
    });


});
//소스비교 팝업창
$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 1) + $(window).scrollTop()) + "px");
        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 1) + $(window).scrollLeft()) + "px");
        return this;
    }

    sourcePopup = function () {
        $("#source_popup").show();
        $("#source_popup").center();
    }
    $('#source_close').click(function(){
        $('#source_popup').hide();
    })
    $('#source_close').click(function (e) {
        e.preventDefault();
        $('#mask, .window').hide();
    });

});

//시나리오관리 팝업창
/*$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 1) + $(window).scrollTop()) + "px");
        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 1) + $(window).scrollLeft()) + "px");
        return this;
    }

    test_listPopup = function () {
        $("#test_list_popup").show();
        $("#test_list_popup").center();
    }
    $('#test_list_close').click(function(){
        $('#test_list_popup').hide();
    })
    $('#test_list_close').click(function (e) {
        e.preventDefault();
        $('#mask, .window').hide();
    });

});*/
// 시나리오 팝업창
$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 1) + $(window).scrollTop()) + "px");
        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 1) + $(window).scrollLeft()) + "px");
        return this;
    }

    checkType_servicePopup = function () {
        $('#checkType-service-popup').show();
        $('#checkType-service-popup').center();
    }
    $('#checkType_service_close').click(function(){
        fn_doCleanPopupData();
        $('#checkType-service-popup').hide();
    })
    $('#checkType_service_close').click(function(e){
        e.preventDefault();
        $('#mask, .window').hide();
    })

    checkType_websitePopup = function () {
        $('#checkType-website-popup').show();
        $('#checkType-website-popup').center();
    }
    $('#checkType_website_close').click(function(){
        fn_doCleanPopupData();
        $('#checkType-website-popup').hide();
    })
    $('#checkType_website_close').click(function(e){
        e.preventDefault();
        $('#mask, .window').hide();
    })

});

// 휴일정보 팝업창
$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 1) + $(window).scrollTop()) + "px");
        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 1) + $(window).scrollLeft()) + "px");
        return this;
    }

    holidayPopup = function () {
        $("#holiday_popup").show();
        $("#holiday_popup").center();
    }
    $('#holiday_popup_close').click(function(){
        fn_doCleanPopupData();
        $('#holiday_popup').hide();
    })
    $('#holiday_popup_close').click(function (e) {
        e.preventDefault();
        $('#mask, .window').hide();
    });

});

//비밀번호변경 팝업창
$(function() {
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 1) + $(window).scrollTop()) + "px");
        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 1) + $(window).scrollLeft()) + "px");
        return this;
    }

    pwPopup = function () {
        $("#group_pw_popup").show();
        $("#group_pw_popup").center();
    }
    $('#group_pw_popup_close').click(function(){
        $('#group_pw_popup').hide();
    })
    $('#group_pw_popup_close').click(function (e) {
        e.preventDefault();
        $('#mask, .window').hide();
    });

});

//처리자배정
$(function() {

    $('#handlePopup01').click(function(){
        $("#handle_popup01").show();
    })
    $('#handle_popup_close').click(function(){
        $('#handle_popup01').hide();
    })

    $('#handlePopup02').click(function(){
        $("#handle_popup02").show();
    })
    $('#handle_popup_close02').click(function(){
        $('#handle_popup02').hide();
    })

    $('#handlePopup03').click(function(){
        $("#handle_popup03").show();
    })
    $('#handle_popup_close03').click(function(){
        $('#handle_popup03').hide();
    })

    $('#handlePopup04').click(function(){
        $("#handle_popup04").show();
    })
    $('#handle_popup_close04').click(function(){
        $('#handle_popup04').hide();
    })

    $('#handlePopup05').click(function(){
        $("#handle_popup05").show();
    })
    $('#handle_popup_close05').click(function(){
        $('#handle_popup05').hide();
    })

    $('#handlePopup06').click(function(){
        $("#handle_popup06").show();
    })
    $('#handle_popup_close06').click(function(){
        $('#handle_popup06').hide();
    })

    $('#handlePopup07').click(function(){
        $("#handle_popup07").show();
    })
    $('#handle_popup_close07').click(function(){
        $('#handle_popup07').hide();
    })
    $('#day_Change_btn').click(function(){
        $("#day_change").show();
    })
    $('#day_popup_close').click(function(){
        $('#day_change').hide();
    })

});


//서비스체크

$(function() {
    $(".sc_down_btn a").on("click", function() {
        $(this).closest(".serivce_table").find(".service_txt").slideToggle();
    });
});

$(function() {
    $('.sc_down_btn a').click( function() {
        if( $(this).html() == '' ) {
            $(this).html('');
        }
        else {
            $(this).html('');
        }
    });
});

//시나리오명 경고등 표시
$(function () {
    setInterval(function(){
        $(".scen_alram img").fadeToggle();
    }, 500);
})

//대시보드 경고등 표시
$(function () {
    setInterval(function(){
        $(".de_serive_no_icon img").fadeToggle();
    }, 700);
})


//시나리오관리 상세
$(function () {
    $("#day_box_btn").on("click", function() {
        $('.day_box').show();
    });

    $(".day_box .table_button a").on("click", function() {
        $('.day_box').hide();
    });
})

//대시보드 쇼히든
$(function () {
    $('.list_type_btn').click(function () {
        $('.dashboard_site').show();
        $('.dashboard_content').hide();
        $('.list_type_btn').hide();
        $('.site_type_btn').show();
    });
    $('.site_type_btn').click(function () {
        $('.dashboard_content').show();
        $('.dashboard_site').hide();
        $('.site_type_btn').hide();
        $('.list_type_btn').show();
    })
});

//차트 팝업
$(function() {
    $(".d_chart_text > li > a").on("click", function() {
        $(this).closest(".d_chart_text").find(".d_chart_popup_wrap").fadeToggle(500);

        return false;
    });
    $(".d_chart_text > li > .d_chart_popup_wrap > h5 > a").on("click", function() {
        $(this).closest(".d_chart_text").find(".d_chart_popup_wrap").fadeOut(500);

        return false;
    })
});

$(document).click(function(e){ //문서 body를 클릭했을때
    if(e.target.className =="d_chart_popup_wrap"){return false}
    $(".d_chart_popup_wrap").stop().fadeOut(500);
});


