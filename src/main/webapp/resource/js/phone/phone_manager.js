/* phone_manager.jsp 引用的js文件 */

//该全局变量用于存储在增加
var increaseLockId = new Array();


//页面加载时执行
$(function (){
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

    updateContent();

    //为取消按钮注册点击的监听事件
    $(".singleLockContent button").click(function (){exit()});

    $(".order_singleLockContent button").click(function (){order_exit()});

    //确认按钮的监听器
    $(".confirm").click(function () {confirmOperate();});

    $(".look_history").click(function () {
        window.location="http://wnyeagle.top/LockWebServer/pages/phone/loginAfter/phone_lockHistory.html"
    });

});

function skip_to_phone_person() {
    window.sessionStorage.setItem("lastPage","http://wnyeagle.top/LockWebServer/pages/phone/loginAfter/phone_manager.html");
    window.location="http://wnyeagle.top/LockWebServer/login/loginAfterAction?action=skipToPersonPage";

}

//更新锁的显示的数据
function updateContent() {

    $.ajax({
        url:"/LockWebServer/login/getCurrentUserMessage",
        data:{"action":"getCurrentLockMessage"},
        type:"POST",
        success:function (msg) {
            var effectData = JSON.parse(msg);
            if (effectData.length<=0){
                $(".showOrderContent").html("当前没有预约或者使用的锁");
            }else{
                $(".showOrderContent").html("");
                for(var i=0;i<effectData.length;i++){

                    var lockStatus = effectData[i].lockStatus;

                    var showGift;
                    if (lockStatus == "正在使用"){

                        showGift = "<div style=\"width: 8px;height:8px;border-radius: 50%;background: #0bd38a;position: relative;left: 56%\"></div>";
                    }else{
                        showGift = "<div style=\"width: 8px;height:8px;border-radius: 50%;background: #708090;position: relative;left: 56%\"></div>"
                    }
                    $(".showOrderContent").html( $(".showOrderContent").html()+ "<div class=\"first_content\">\n" +
                        "            <span class=\"lockId\" style=\"position: relative;top: 30%;left: 7%\">锁号: "
                        +effectData[i].lockId+
                        "</span><span style=\"position: relative;top: 30%;left: 15%\">"
                        +effectData[i].lockStatus+
                        "</span>\n" + showGift+
                        "            <button class=\"edit\" onclick=\"set(this)\" style=\"position: relative;left: 76%;bottom:18px;background: white\">设置</button></div>"
                    );

                }
            }
        },
        data_type:"text"
    });
}

//设置按钮的响应函数
function set(obj){
    var lockStatus;
    document.getElementById('tanchuan').style.display='block';
    //获取当前的id
    var getStr = $(obj).parents(".first_content").children(".lockId").html();
    var lock_id = getStr.substr(4);
    //向服务器发送请求
    $.ajax({
        url:"/LockWebServer/login/getAndOperateLockMessage",
        data:{
            "action":"getCurrentLockStatus",
            "lockId":lock_id
        },
        type:"POST",
        success:function (rst) {//解析数据，并显示在弹窗上面
            var data = JSON.parse(rst);
            lockStatus = data.lockStatus;

            if ($.isEmptyObject(data)){//数据响应异常
                $(".needToFill").html("数据异常");
                $(".needToFill").style.color = "red";

            }else {//数据响应正常
                $(".singleLockMessage #lockId").html(data.lockId);
                $(".singleLockMessage #orderTime").html(data.orderTime);
                if (!$.isEmptyObject(data.useTime)){
                    $(".singleLockMessage #useTime").html(data.useTime);
                }else {
                    $(".singleLockMessage #useTime").html("  ");
                }
                $(".singleLockMessage #lockStatus").html(data.lockStatus);
                //根据锁的状态设置’开始使用‘和‘结束使用’两个按钮的可使用属性
                if (lockStatus=="正在使用"){
                    $(".start").attr("disabled",true);
                    $(".end").attr("disabled",false
                    );
                }else if (lockStatus=="已经预约"){
                    $(".end").attr("disabled",true);
                    $(".start").attr("disabled",false
                    );
                }
            }
        },
        data_type:"text"
    });

}

//设置弹出框的退出按钮
function exit() {
    document.getElementById('tanchuan').style.display='none';
}

//预约增加弹出框的退出按钮
function order_exit() {
    document.getElementById('order_tanchuan').style.display='none';
}

//删除预约的按钮的响应函数
function decreaseOrder(obj) {
    if ($(obj).attr("hasClick")=="false"){
        //将删除按钮激活
        $(".delete_button").css('opacity', 1);
        $(".delete_button").attr("disabled",false);
        //将showOrderContent元素的内容中添加单选按钮
        var eles = $(obj).parents(".order_header").parents(".use_lock").children(".showOrderContent").children();
        for (var i=0;i<eles.length;i++){
            //获取当前同一行的锁号
            var das = $(eles[i]).children("span").html();
            var current_lock_id = das.substr(4);
            //获取当前同一行的锁的状态
            var lock_status = $(eles[i]).children("span").last().html();
            if(lock_status=="已经预约"){
                var content = $(eles[i]).html();
                $(eles[i]).html(content+"<input type='checkbox' name='check_lock_ids' style='position: relative" +
                    ";float: right;bottom: 40%;right: 3%' value='"+current_lock_id+"'>");
            }
        }
        $(obj).attr("hasClick","true");
    }else{
        //将删除按钮激活
        $(".delete_button").css('opacity',0);
        $(".delete_button").attr("disabled","disabled");
        //将showOrderContent元素的内容中添加单选按钮
        var eles = $(obj).parents(".order_header").parents(".use_lock").children(".showOrderContent").children();
        for (var i=0;i<eles.length;i++){
            //获取当前同一行的锁号
            var das = $(eles[i]).children("span").html();
            var current_lock_id = das.substr(4);
            //获取当前同一行的锁的状态
            var lock_status = $(eles[i]).children("span").last().html();
            if(lock_status=="已经预约"){
                var content = $(eles[i]).html();
                $(eles[i]).children("input[type=checkbox]").remove();
            }
        }
        $(obj).attr("hasClick","false");
    }


    /*//此时要将当前的减少按钮置为失效
    if ($(".decrease_count").attr("disabled")==null){
        $(".decrease_count").attr("disabled",true);
    }else {
        $(".decrease_count").attr("disabled",false);
    }*/

}



//删除选中的预约锁的操作
function deleteOrder() {
    var sendLockIdArray = new Array();
    var eles =  document.getElementsByName("check_lock_ids");
    for (var i = 0;i<eles.length;i++){
        if(eles[i].checked) {sendLockIdArray[i] = eles[i].value};
    }
    var sendLockId = JSON.stringify(sendLockIdArray);
    //发送给数据库删除相应的预约锁
    $.ajax({
        url:"/LockWebServer/login/getAndOperateLockMessage",
        data:{"action":"deleteHasOrder",
            "deleteLockIds":sendLockId
        },
        type:"POST",
        success:function (msg) {
            var rst = JSON.parse(msg);
            if (rst){
                alert("删除成功");
                //重新刷新预约的锁的内容
                updateContent();
            }else {
                alert("删除失败");
            }
        },
        data_type:"text"
    })

    //将删除按钮隐藏和失效
    $(".delete_button").css('opacity', 0);
    $(".delete_button").attr("disabled",true);
    $(".decrease_count").attr("disabled",false?true:false);

}


//增加预约锁的操作
function increaseOrder() {
    //先将全局的数组清空
    increaseLockId.splice(0,increaseLockId.length);
    //将预约模块显示出来
    document.getElementById("order_tanchuan").style.display="block";
    //在order_needToFill模块当中添加还没有预约的锁
        //从后台获取还未预约和使用的锁
    $.ajax({
        url:"/LockWebServer/login/getAndOperateLockMessage",
        data:{"action":"getAbleToOrder"},
        type:"POST",
        success:function (msg) {
            var lockIds = JSON.parse(msg);
            $(".order_needToFill").html("");
            for (i=0;i<lockIds.length;i++){
                var butStr = "<button onmousedown='clickToOrder(this)' style='width: 15%;margin-left: 10%;margin-top:10%;text-align:left;'>"+lockIds[i]+"</button>";
                $(".order_needToFill").html($(".order_needToFill").html()+butStr);
            }
        },
        data_type:"text"
    });

}

//每一个锁的序号的按钮（increaseOrder函数里面动态增加button）响应事件
function clickToOrder(obj) {
    //
    if ($(obj).css("background-color")=="rgb(239, 239, 239)"){
        $(obj).css("background","green");
        increaseLockId.push($(obj).html());
    }else{
        $(obj).css("background","rgb(239, 239, 239)");
        increaseLockId.splice($.inArray($(obj).html(),increaseLockId),1);
    }
}

//确认按钮的监听事件，该函数会为当前的用户预约已经点击的锁
function confirmOperate() {
    var sendData = JSON.stringify(increaseLockId);
    $.ajax({
        url:"/LockWebServer/login/getAndOperateLockMessage",
        data:{
            "action":"excuteIncreseOrderLock",
            "orderLockId":sendData
        },
        type:"POST",
        success:function (msg) {
            var hasOrdered = JSON.parse(msg);
            if (hasOrdered.size>0){
                alert("预约失败===》》锁号："+hasOrdered+"已经被预约");
                //重新跳出更新后的可预约的锁
                increaseOrder();
            }else {
                //预约成功后，更新已经预约的锁
                updateContent();
            }
        },
        data_type:"text"
    })

}


//开始使用的按钮的函数
function startToUse(obj) {
    //获取当前的的锁的id
    var lockObj = $(obj).parents(".operateInSet").parents(".lockDetailsMessage").children(".needToFill").children(".singleLockMessage").first().children(".singleLockMessageValue");
    var lockId =lockObj.html();
    //发送给后台
    $.ajax({
        url:"/LockWebServer/login/getAndOperateLockMessage",
        data:{"action":"startToUse",
            "lockId":lockId
        },
        type:"POST",
        success:function (rst) {
            var cor = JSON.parse(rst);
            if (cor){
                updateContent();
            }else {
                alert("操作失败");
            }
        },
        data_type:"text"
    });
}

//结束使用的按钮的函数
function endToUse(obj) {
    //获取当前的的锁的id
    var lockObj = $(obj).parents(".operateInSet").parents(".lockDetailsMessage").children(".needToFill").children(".singleLockMessage").first().children(".singleLockMessageValue");
    var lockId =lockObj.html();
    //发送给后台
    $.ajax({
        url:"/LockWebServer/login/getAndOperateLockMessage",
        data:{
            "action":"endToUse",
            "lockId":lockId
        },
        type:"POST",
        success:function (msg) {
            var delRst = JSON.parse(msg);
            if (delRst)updateContent();
        },
        data_type:"text"
    });
}
