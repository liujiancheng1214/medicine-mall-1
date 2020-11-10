function Tools() {
}

Tools.chTS2GMTStr = function (timestamp) {
    var date = new Date(timestamp)
    var Str = prev0(date.getFullYear()) + '-' +
        (prev0(date.getMonth() + 1)) + '-' +
        prev0(date.getDate()) + ' ' +
        prev0(date.getHours()) + ':' +
        prev0(date.getMinutes());

    function prev0(num) {
        return (num < 10 ? '0' + num : num);
    }

    return Str
};


Tools.chTS2DateStr = function (timestamp) {
    var date = new Date();
    if (timestamp != null) {
        date = new Date(timestamp);
    }
    var Str = prev0(date.getFullYear()) + '-' +
        (prev0(date.getMonth() + 1)) + '-' +
        prev0(date.getDate());

    function prev0(num) {
        return (num < 10 ? '0' + num : num);
    }

    return Str
};

Tools.chTS2DateStr2 = function (timestamp) {
    var date = new Date();
    if (timestamp != null) {
        date = new Date(timestamp);
    }
    var Str = prev0(date.getFullYear()) + '年' +
        (prev0(date.getMonth() + 1)) + '月' +
        prev0(date.getDate()) + '日';

    function prev0(num) {
        return (num < 10 ? '0' + num : num);
    }

    return Str
};


//字符串转格林威治
Tools.string2Date = function(dateStr, separator)
{
    if (!separator) {
        separator = "-";
    }
    var dateArr = dateStr.split(separator);
    var year = parseInt(dateArr[0]);
    var month;
    //处理月份为04这样的情况
    if (dateArr[1].indexOf("0") == 0) {
        month = parseInt(dateArr[1].substring(1));
    } else {
        month = parseInt(dateArr[1]);
    }
    var day = parseInt(dateArr[2]);
    var date = new Date(year, month - 1, day);
    return date;
};

//格林威治转字符串
Tools.chGMT2Str = function(gmtDate, type)
{
    if (gmtDate == '') return '';
    var date = new Date(gmtDate);
    var Str = "";
    switch (type) {
        case "year":
            Str = prev0(date.getFullYear());
            break;
        default:
            Str = prev0(date.getFullYear()) + '-' +
                (prev0(date.getMonth() + 1)) + '-' +
                prev0(date.getDate()) + ' ' +
                prev0(date.getHours()) + ':' +
                prev0(date.getMinutes()) + ':' +
                prev0(date.getSeconds());
    }

    function prev0(num) {
        return (num < 10 ? '0' + num : num);
    }

    return Str
};