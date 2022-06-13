var DateUtil = {
    getY: function() {
        var date = new Date();
        return  "" + date.getFullYear();
    },
    getYM: function() {
        var date = new Date();
        var month = (date.getMonth() + 1);
        if(Number(month) < 10){ month = "0" + month; }
        return  "" + date.getFullYear() +
                "" + month;
    },
    getYMD: function() {
        var date = new Date();
        var month = (date.getMonth() + 1);
        var day = date.getDate();
        if(Number(month) < 10){ month = "0" + month; }
        if(Number(day) < 10){ day = "0" + day; }
        return  "" + date.getFullYear() +
                "" + month +
                "" + day;
    },
    getYMDH: function() {
        var date = new Date();
        var month = (date.getMonth() + 1);
        var day = date.getDate();
        var hrs = date.getHours();
        if(Number(month) < 10){ month = "0" + month; }
        if(Number(day) < 10){ day = "0" + day; }
        if(Number(hrs) < 10){ hrs = "0" + hrs; }
        return  "" + date.getFullYear() +
                "" + month +
                "" + day +
                "" + hrs;
    },
    getYMDHM: function() {
        var date = new Date();
        var month = (date.getMonth() + 1);
        var day = date.getDate();
        var hrs = date.getHours();
        var min = date.getMinutes();
        if(Number(month) < 10){ month = "0" + month; }
        if(Number(day) < 10){ day = "0" + day; }
        if(Number(hrs) < 10){ hrs = "0" + hrs; }
        if(Number(min) < 10){ min = "0" + min; }
        return  "" + date.getFullYear() +
                "" + month +
                "" + day +
                "" + hrs +
                "" + min;
    },
    getYMDHM: function() {
        var date = new Date();
        var month = (date.getMonth() + 1);
        var day = date.getDate();
        var hrs = date.getHours();
        var min = date.getMinutes();
        if(Number(month) < 10){ month = "0" + month; }
        if(Number(day) < 10){ day = "0" + day; }
        if(Number(hrs) < 10){ hrs = "0" + hrs; }
        if(Number(min) < 10){ min = "0" + min; }
        return  "" + date.getFullYear() +
                "" + month +
                "" + day +
                "" + hrs +
                "" + min;
    },
    getYMDHMS: function() {
        var date = new Date();
        var month = (date.getMonth() + 1);
        var day = date.getDate();
        var hrs = date.getHours();
        var min = date.getMinutes();
        var sec = date.getSeconds();
        if(Number(month) < 10){ month = "0" + month; }
        if(Number(day) < 10){ day = "0" + day; }
        if(Number(hrs) < 10){ hrs = "0" + hrs; }
        if(Number(min) < 10){ min = "0" + min; }
        if(Number(sec) < 10){ sec = "0" + sec; }
        return  "" + date.getFullYear() +
                "" + month +
                "" + day +
                "" + hrs +
                "" + min +
                "" + sec;
    },
    /**
     * 현재 년월일시분초밀리초 을 리턴합니다.
     * @returns
     */
    getYMDHMS_S: function() {
        var date = new Date();
        var month = (date.getMonth() + 1);
        var day = date.getDate();
        var hrs = date.getHours();
        var min = date.getMinutes();
        var sec = date.getSeconds();
        if(Number(month) < 10){ month = "0" + month; }
        if(Number(day) < 10){ day = "0" + day; }
        if(Number(hrs) < 10){ hrs = "0" + hrs; }
        if(Number(min) < 10){ min = "0" + min; }
        if(Number(sec) < 10){ sec = "0" + sec; }
        return  "" + date.getFullYear() +
                "" + month +
                "" + day +
                "" + hrs +
                "" + min +
                "" + sec +
                "" + date.getMilliseconds();
    },
    /**
     * 월, 일의 포멧을 변경합니다.
     * 1~9 -> 01~09
     * @param _Date
     * @returns {String}
     */
    formatYMD: function(_Date) {
        var month = (_Date.getMonth() + 1);
        var day = _Date.getDate();
        if(Number(month) < 10){ month = "0" + month; }
        if(Number(day) < 10){ day = "0" + day; }
        return  "" + _Date.getFullYear() +
                "" + month +
                "" + day;
    },
    /**
     * 주어진 날짜의 차이 일을 구합니다.
     * @param sday - 시작일
     * @param eday - 종료일
     * @returns {number}
     */
    formatDiffDays: function(sday, eday) {
        sday = sday.replace(/-/gi, "");
        eday = eday.replace(/-/gi, "");

        var date1 = new Date(sday.substr(0,4), sday.substr(4,2)-1, sday.substr(6,2));
        var date2 = new Date(eday.substr(0,4), eday.substr(4,2)-1, eday.substr(6,2));

        var interval = date2 - date1;
        var day = 1000*60*60*24;
        var month = day*30;
        var year = month*12;

        return parseInt(interval/day)+1;
    },
    getBeforeDate: function(dayPrefix, format) {
        var transDate = new Date();
        var processTime = new Date().getTime() + (parseInt(dayPrefix) * 24 * 60 * 60 * 1000);
        transDate.setTime(processTime);
        if(format){
            return new Date().formatYMD(transDate);
        }else{
            return transDate;
        }
    }
};