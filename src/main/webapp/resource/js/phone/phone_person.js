$(function () {
    initPage();
    $(".modify_action").click(function () {
        $(".modify_background").css("display","block")
    });

    $(".ui-icon-cancel").click(function (msg) {
        $(".modify_background").css("display","none");

    });

    $("input[type='text']").blur(function () {
        if ($(this).val()==""){
            $(this).val("不能为空");
            $(this).css("color","red");
        }
    });

    $("input[type='text']").focus(function () {
        if ($(this).val()=="不能为空"){
            $(this).val("");
            $(this).css("color","black");
        }
    });

    $(".confirm_modify").click(function () {
        confirmModify();
    });

    $(".exit_action").click(function () {
        exit_Action();
    });

    $(".back_button").click(function () {
        skip_back();
    });
});

function confirmModify() {
    var rst =true;
    if ($("input[name='userName']").val()==""||$("input[name='userName']").val()=="不能为空"){
        rst=false;
    }
    if ($("input[name='userEmail']").val()==""||$("input[name='userName']").val()=="不能为空"){
        rst=false;
    }
    if ($("input[name='userAge']").val()==""||$("input[name='userName']").val()=="不能为空"){
        rst=false;
    }
    if (!rst){
        alert("输入的数据不能为空");
    }else{
        $.ajax({
            url: "/LockWebServer/login/loginAfterAction",
            data:{"action":"updateUserMessage",
                "name":$("input[name='userName']").val(),
                "email":$("input[name='userEmail']").val(),
                "age":$("input[name='userAge']").val()
            },
            type: "POST",
            success:function (msg) {
                var data = JSON.parse(msg);
                if (data){
                    alert("修改成功");
                    initPage();
                    $(".modify_background").css("display","none");
                }else{
                    alert("出问题了");
                }
            },
            data_type: "text"
        });
    }
}

function initPage() {
    $.ajax({
        url:"/LockWebServer/login/loginAfterAction",
        data:{"action":"getUserMessage"},
        type:"POST",
        success:function (msg) {
            var userData = JSON.parse(msg);
            var value = $(".showUserMessage").children(".userMessage");
            $(value[0]).children(".value").html(userData.userName);
            $(value[1]).children(".value").html(userData.email);
            $(value[2]).children(".value").html(userData.age);
            $(value[3]).children(".value").html(userData.authority);

            $("input[name='userName']").val(userData.userName);
            $("input[name='userEmail']").val(userData.email);
            $("input[name='userAge']").val(userData.age);
        },
        data_type:"text"
    });
}

function exit_Action() {
    $.ajax({
        url:"/LockWebServer/login/loginAfterAction",
        data:{"action":"exitToLogin"},
        type:"GET",
        success:function (msg) {
            window.location="/LockWebServer";
        },
        data_type:""
    });
}

function skip_back() {
    let item = window.sessionStorage.getItem("lastPage");
    window.location=item;
}