$(function () {
    initPersonMessage();
    updateHistoryList();
    $(".operate_lock").click(function () {
        window.location="http://wnyeagle.top/LockWebServer/pages/phone/loginAfter/phone_manager.html"
    });
});

function initPersonMessage() {
    //更新图标
    $.ajax({
        url:"/LockWebServer/login/getCurrentUserMessage",
        data:{"action":"getCurrentLoginUserName"},
        type:"POST",
        success:function (msg){
            var userMessage = JSON.parse(msg);
            $(".glyphicon-user").html("&nbsp;"+userMessage.data);
        },
        data_type:"text"
    });
}

function updateHistoryList() {
    $.ajax({
        url: "/LockWebServer/login/getAndOperateLockMessage",
        data:{"action":"getUserLockHistory"},
        type: "POST",
        success:function (msg) {
            var data = JSON.parse(msg);
            $(".showHistoryList").html("");
            for (i=0;i<data.length;i++){

                var insertHtml = "<div class=\"singleHistoryRecord\">\n" +
                    "            <div style=\"width: 86%;height: 100%\">\n" +
                    "                <span style=\"margin-left: 5px;margin-right: 5px\">锁号:"+data[i].lockId+
                    "&nbsp;</span><span>预约时间:"+timeStampToDate(data[i].orderTime)+"</span><br><span style=\"margin-left: 5px;\">使用时间:"
                +timeStampToDate(data[i].useStartTime)+"</span>\n" +
                    "            </div>\n" +
                    "            <div style='position:absolute;width: 8%;right: 0;bottom: 20%'>\n" +
                    "                <input type='checkbox' style='display: none' name='chooseLock' value='"+data[i].lockId+"'>\n" +
                    "            </div>";
                $(".showHistoryList").html($(".showHistoryList").html( )+insertHtml);
            }
        },
        data_type: "text"
    });
}

/**
 * 时间转换的函数
 * @param time
 * @returns {string}
 */
function timeStampToDate(time){
    var timeData = new Date(time);
    return timeData.getFullYear()+"-"+timeData.getMonth()+"-"+timeData.getDay()+" "
        +timeData.getHours()+":"+timeData.getMinutes()+":"+timeData.getMilliseconds();
}

function decreaseOrder(obj) {
    if ($(".delete_button").attr("disabled")=="disabled"){
        $("input[name='chooseLock']").css("display","block");
        $(".delete_button").removeAttr("disabled");
        $(".delete_button").css("opacity",1)
    }else{
        $("input[name='chooseLock']").css("display","none");
        $(".delete_button").attr("disabled","disabled");
        $(".delete_button").css("opacity",0)
    }

}

function deleteOrder() {
    var choseLock = document.getElementsByName("chooseLock");
    var sendData = new Array();
    for (i=0;i<choseLock.length;i++){
        if (choseLock[i].checked){
            sendData.push(choseLock[i].value)
        }
    }
    $.ajax({
        url:"/LockWebServer/login/getAndOperateLockMessage",
        data:{"action":"deleteHistory",
            "locks":JSON.stringify(sendData)
        },
        type:"POST",
        success:function (msg) {
            var data = JSON.parse(msg);
            if (data){
                updateHistoryList();
            }
        },
        data_type:"text"
    });
}

function clearAll() {
    $.ajax({
        url:"/LockWebServer/login/getAndOperateLockMessage",
        data:{"action":"deleteAll"},
        type:"POST",
        success:function (msg) {
            var data = JSON.parse(msg);
            if (data){
                updateHistoryList();
            }
        },
        data_type:"text"
    });
}