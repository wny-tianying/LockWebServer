/*当前的文件是管理员界面事件响应的函数的js*/
$(function () {
    updatePersonIcon();
    updateUserContent();
});

function skip_to_phone_person() {
    window.sessionStorage.setItem("lastPage","http://wnyeagle.top/LockWebServer/pages/phone/loginAfter/administor_manager.html");

    window.location="http://wnyeagle.top/LockWebServer/login/loginAfterAction?action=skipToPersonPage";

}

function updatePersonIcon() {
    //更新图标
    $.ajax({
        url: "/LockWebServer/login/getCurrentUserMessage",
        data: {"action": "getCurrentLoginUserName"},
        type: "POST",
        success: function (msg) {
            var userMessage = JSON.parse(msg);
            $(".glyphicon-user").html("&nbsp;" + userMessage.data);
        },
        data_type: "text"
    });
}

/*该函数的作用是用于刷新“管理用户”界面的内容*/
function updateUserContent() {

    $.ajax({
        url: "/LockWebServer/manager/administratorOperate",
        data: {"action": "getUsers"},
        type: "POST",
        success: function (msg) {
            var data = JSON.parse(msg);

            $(".user_manager_block .first_block .show_content").html("");

            for (i = 0; i < data.length; i++) {
                const insertContent = "<div class='single_user_setting'>\n" +
                    "                <div class='content' id='" + data[i].userId +
                    "'>\n" +
                    "                    <span class='user_name'>用户名:" + data[i].userName +
                    "</span><button class='user_setter'  onclick='set_user_message(this)'>设置</button>\n" +
                    "                </div>\n" +
                    "            </div>";
                const curContent = $(".user_manager_block .first_block .show_content").html();
                $(".user_manager_block .first_block .show_content").html(curContent + insertContent);
            }
        },
        data_type: "text"
    });
}

/**
 * 更新锁的管理的界面
 */
function updateLockContent() {
    $.ajax({
        url:"/LockWebServer/manager/administratorOperate",
        data:{"action":"getAllLocks"},
        type:"POST",
        success:function (msg) {
            var data = JSON.parse(msg);
            $(".lock_manager_block .first_block .show_content ").html("");
            for (i=0;i<data.length;i++){
                const curContent= $(".lock_manager_block .first_block .show_content ").html();
                const insertContent = "<div class='single_user_setting'>\n" +
                    "                <div class='content' id='" + data[i].lockId +
                    "'>\n" +
                    "                    <span class='user_name'>锁序号:" + data[i].lockId+
                    "</span><button class='user_setter'  onclick='set_lock_message(this)'>设置</button>\n" +
                    "                </div>\n" +
                    "            </div>";
                $(".lock_manager_block .first_block .show_content ").html(curContent+insertContent);
            }
        },
        data_type:"text"
    });
}




/**
 * 更新历史记录
 */
function updateHistoryContent() {

    $.ajax({
        url:"/LockWebServer/manager/administratorOperate",
        data:{"action":"getSystemHistory"},
        type:"POST",
        success:function (msg) {
            var data = JSON.parse(msg);
            $(".using_history_block .first_block .show_content ").html("");
            for (i=0;i<data.length;i++){
                const curContent= $(".using_history_block .first_block .show_content ").html();
                const show_name_id = "user_name"+i;
                const insertContent = "<div class='single_user_setting'>\n" +
                    "                <div class='content' id='" + data[i].tableId +
                    "'>\n" +
                    "                    <span class='user_name'>锁序号:" + data[i].lockId+
                    "</span><span style='margin-left: 7%;' id="+show_name_id+">使用人:"+"</span><button class='user_setter' onclick='set_history_message(this)'>查看</button>\n" +
                    "                </div>\n" +
                    "            </div>";
                $(".using_history_block .first_block .show_content ").html(curContent+insertContent);
                const cls = "#"+show_name_id;
                var str = 'getUserNameByUserId($('+'"'+cls+'"'+'),'+data[i].userId+')';
                setTimeout(str,1);
            }
        },
        data_type:"text"
    });
}

/**
 * 该函数的作用是修改用户的信息
 */
function set_user_message(obj) {
    $(".user_manager_block").css("display","none");
    $(".user_tanchuan").css("display","block");
    //获取当前的用户信息，并更新到选项框当中
    const user_id = $(obj).parents(".content").prop("id");
    $.ajax({
        url:"/LockWebServer/manager/administratorOperate",
        data:{"action":"getSingleUserMsg",
            "data":user_id
        },
        type:"POST",
        success:function (msg) {
            const data = JSON.parse(msg);
            $(".user_personal_msg").html("");
            //将当前的用户
            const ht = "<div class=\"storeUserId\" value='"+data.userId+"' hidden='hidden'></div>"
            const insert_content = "用户名: "+data.userName+"<br>年龄:&nbsp;"+data.age+"<br>权限:"
            +(data.authority=="manager"?"管理员":"普通用户")+"<br>邮箱:&nbsp;"+data.email;
            $(".user_personal_msg").html(insert_content+ht);
        },
        data_type:"text"
    });
}

/**
 * 修改锁的相关信息
 */
function set_lock_message(obj) {
    $(".lock_tanchuan").css("display","block");
    $(".lock_manager_block").css("display","none");
    //获取当前的锁的id
    const lock_id = $(obj).parents(".content").prop("id");
    //向后台获取当前锁的信息
    $.ajax({
        url:"/LockWebServer/manager/administratorOperate",
        data:{"action":"getManagerLockMsg",
            "lockId":lock_id
        },
        type:"POST",
        success:function (msg) {
            var data = JSON.parse(msg);
            var insert_content;
            if (data.ableToUse){//可以使用的状态
                if (data.isOrdered){//已经有人预约
                    insert_content ="锁的序号: "+data.lockId+"<br>是否可用: 可用<br>预约状况: 已经预约<br>预约的人:"+data.orderPerson;
                }else{
                    insert_content ="锁的序号: "+data.lockId+"<br>是否可用: 可用<br>预约状况: 无人预约<br>"
                }
            }else {
                insert_content="锁的序号: "+data.lockId+"<br>是否可用: 不可用";
            }
            $(".lock_detailed_msg").html(insert_content+"<div class='store_lock_id' hidden='hidden' value='"+data.lockId+"'></div>")
        },
        data_type:"text"
    });
}

/**
 * 历史显示模块详细显示信息
 */
function set_history_message(obj) {
    $(".history_tanchuan").css("display","block");
    $(".using_history_block").css("display","none");
    const tableId = $(obj).parents(".content").prop("id");

    $.ajax({
        url:"/LockWebServer/manager/administratorOperate",
        data:{"action":"getSingleSystemHistory",
            "tableId":tableId
        },
        type:"POST",
        success:function (msg) {
            const data = JSON.parse(msg);

            const insertContent = "锁的序号:"+data.lockId+"<br><span style='position: absolute;left: -6px;' class='user_name'>使用人: "+"</span><br>预约时间: "+data.orderTime
            +"<br>开始使用时间: "+data.useStartTime+"<br>结束使用时间: "+data.useEndTime;

            $(".history_detailed_msg").html(insertContent+"<div class='store_table_id' value='"+data.tableId+"'></div>");
            getUserNameByUserId($('.history_tanchuan .history_show_content .history_detailed_msg .user_name'),data.userId);

        },
        data_type:"text"
    });

}


function getUserNameByUserId(obj,userId) {
    $.ajax({
        url:"/LockWebServer/login/loginAfterAction",
        data:{"action":"getUserById",
            "userId":userId
        },
        type:"POST",
        success:function (msg) {
            let data = JSON.parse(msg);
            $(obj).html($(obj).html()+data.userName);
        },
        data_type:"text"
    });

}


/**
 * 
 */
function ensure_modify() {
    const user_id =  $(".storeUserId").attr("value");
    const authority = $("#authority").find("option:selected").val();
    //发到后台进行修改
    $.ajax({
        url:"/LockWebServer/manager/administratorOperate",
        data:{"action":"modifyAuthority",
            "userId":user_id,
            "authority":authority
        },
        type:"POST",
        success:function (msg) {
            var rst = JSON.parse(msg);
            if (rst){
                alert("修改成功!");
                $(".user_manager_block").css("display","block");
                $(".user_tanchuan").css("display","none");
            }else {
                alert("修改失败!");
            }
        },
        data_type:"text"
    });
}

//确认当前的锁的修改
function ensure_modify_lock(){
    let able_to_use = $("#lockUseFul").find("option:selected").val();
    let lock_id  = $(".store_lock_id").attr("value");
    $.ajax({
        url:"/LockWebServer/manager/administratorOperate",
        data:{"action":"modifyLockAbleToUse",
            "ableToUse":able_to_use,
            "lockId":lock_id
        },
        type:"POST",
        success:function (msg) {
            var data = JSON.parse(msg);
            if (data){
                alert("修改成功!");
                $(".lock_tanchuan").css("display","none");
                $(".lock_manager_block").css("display","block");
            }else{
                alert("修改失败!");
            }
        },
        data_type:"text"
    });
}

//历史弹窗的删除按钮
function del_current_dir() {
    let table_id = $(".store_table_id").attr("value");
    $.ajax({
        url:"/LockWebServer/manager/administratorOperate",
        data:{"action":"delSystemHistory"},
        type:"POST",
        success:function (msg) {
            var data = JSON.parse(msg);
            if (data){
                alert("删除成功!");
                $(".history_tanchuan").css("display","none");
                $(".using_history_block").css("display","block");
            }else {
                alert("删除失败!");
            }
        },
        data_type:"text"
    });
}

function exit_user_setter() {
    $(".user_manager_block").css("display","block");
    $(".user_tanchuan").css("display","none");
}

/**
 * 退出锁的设置界面
 */
function exit_lock_setter() {
    $(".lock_tanchuan").css("display","none");
    $(".lock_manager_block").css("display","block");
}

/**
 * 退出历史模块的弹窗
 */
function exit_history_setter() {
    $(".history_tanchuan").css("display","none");
    $(".using_history_block").css("display","block");
}

// ”—“ 按钮的事件函数 作用：在每个选项后面添加
function decreaseOrder(obj) {
    var eles = $(obj).parents(".header").parents(".first_block").children(".show_content").children();
    var del_btn = $(obj).parents(".header").children(".delete_button");
    if ($(del_btn).attr("disabled") == "disabled") {
        //将删除按钮激活
        $(del_btn).css('opacity', 1);
        $(del_btn).attr("disabled", false);
        //将全选按钮激活
        $(obj).parents(".header").children(".increase_count").css("display", "block");
        //
        for (i = 0; i < eles.length; i++) {
            var Id = $(eles[i]).children().attr("id");
            let html = $(eles[i]).children().html();
            //为其添加复选按钮，名为choose_id
            $(eles[i]).children().html(html + "<input class='choose_id' type='checkbox' name='choose_id' value='" + Id + "'>");
        }
    } else {
        //将删除按钮激活
        $(del_btn).css('opacity', 0);
        $(del_btn).attr("disabled", true);
        //将全选按钮激活
        $(obj).parents(".header").children(".increase_count").css("display", "none");
        for (i = 0; i < eles.length; i++) {
            var Id = $(eles[i]).children().attr("id");
            let html = $(eles[i]).children().children("input").remove();
        }
    }

}

function deleteOrder(data,obj) {
    var eles = $(obj).parents(".header").parents(".first_block").children(".show_content").children();
    if (data == "del_user") {//用户管理模块的删除
        // var eles = $("input[name='choose_id']");
        const user_ids = new Array();//用于存储当前的要删除的用户
        for (i = 0; i < eles.length; i++) {
            var ck = $(eles[i]).children(".content").children("input[name='choose_id']");
            if (ck.prop("checked")) {
                user_ids.push(ck.attr("value"));
            }
        }
        if (user_ids.length<=0){
            return;
        }
        $.ajax({
            url: "/LockWebServer/manager/administratorOperate",
            data: {
                "action": "delUsers",
                "user_ids": JSON.stringify(user_ids)
            },
            type: "POST",
            success: function (msg) {
                const rst = JSON.parse(msg);
                if (rst) {
                    updateUserContent();
                } else {
                    alert("当前有用户正在使用锁，请勿删除")
                }
            },
            data_type: "text"
        })

    } else if (data == "del_lock") {//删除锁的模块
        const lock_ids = new Array();//存储需要删除的锁
        for (i = 0; i < eles.length;i++) {
            const ck = $(eles[i]).children(".content").children("input[name='choose_id']");
            if (ck.prop("checked")) {
                lock_ids.push(ck.attr("value"));
            }
        }
        $.ajax({
            url:"/LockWebServer/manager/administratorOperate",
            data:{"action":"delLocks",
                "data":JSON.stringify(lock_ids)
            },
            type:"POST",
            success:function (msg) {
                var data = JSON.parse(msg);
                if (data){
                    alert("删除成功！");
                    updateLockContent();
                }else {
                    alert("删除失败！");
                }

            }
        });
    }
    else if (data=="del_history"){//删除历史模块
        const  history_ids = new Array();
        for (i=0;i<eles.length;i++){
            const ck = $(eles[i]).children(".content").children("input[name='choose_id']");
            if (ck.prop("checked")) {
                history_ids.push(ck.attr("value"));
            }
        }
        $.ajax({
            url:"/LockWebServer/manager/administratorOperate",
            data:{"action":"delSystemManyHistory",
                "tableIds":JSON.stringify(history_ids)
            },
            type:"POST",
            success:function (msg) {
                var data = JSON.parse(msg);
                if (data){
                    updateHistoryContent();
                }else {
                    alert("删除失败!");
                }
            },
            data_type:"text"
        });
    }
}

//全选按钮的作用
function choose_all(obj) {
    const content = $(obj).parents(".header").parents(".first_block").children(".show_content");
    const eles = $(content).children();
    var rst = $(content).attr("isAllChecked");
    if (rst==0) {

        for (i = 0; i < eles.length; i++) {
            $(eles[i]).children(".content").children("input").prop("checked", false);
        }
        content.attr("isAllChecked","1");
    } else {
        //将全部选中
        for (i = 0; i < eles.length; i++) {
            $(eles[i]).children(".content").children("input").prop("checked",true);
        }
        $(content).attr("isAllChecked","0");
    }
}

//跳转到锁的管理界面
function skip_lock_manager() {
    $(".lock_manager_block").css("display", "block");
    $(".using_history_block").css("display", "none");
    $(".user_manager_block").css("display", "none");
    $(".lock_manager_block .header .delete_button").css("opacity","0");
    $(".lock_manager_block .header .delete_button").attr("disabled","disabled");
    $(".lock_manager_block .header .increase_count").css("display","none");
    updateLockContent();
}

//跳转到用户的管理界面
function skip_user_manager() {
    $(".lock_manager_block").css("display", "none");
    $(".using_history_block").css("display", "none");
    $(".user_manager_block").css("display", "block");
    $(".user_manager_block .header .delete_button").css("opacity","0");
    $(".user_manager_block .header .delete_button").attr("disabled","disabled");
    $(".user_manager_block .header .increase_count").css("display","none");
}

//跳转到历史的管理
function skip_history_manager() {
    $(".lock_manager_block").css("display", "none");
    $(".using_history_block").css("display", "block");
    $(".user_manager_block").css("display", "none");
    updateHistoryContent();
}