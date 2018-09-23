$(document).ready(function () {
    refreshVerify();
});


function login() {
    var username = $("#usr").val();
    var pass = $("#pass").val();
    var verify = $("#verify").val();
    if (username.trim() == ''
        || pass.trim() == ''
        || verify.trim() == '') {
        alert("输入不能为空哦.");
        return;
    }
    $.ajax({
        url: encodeURI(baseUrl + "/user/login.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        data: {
            "usName": username,
            "usPassword": encrypt(pass),
            "code": verify
        },
        xhrFields: {
            withCredentials: true
        },
        cache: false,
        success: function (result) {
            var status = result.data.status;
            if (status == 4) {
                alert("验证码错误，请重新输入.");
            }
            else if (status == 3) {
                alert("密码错误！");
            }
            else if (status == 2) {
                alert("用户不存在的哦！");
            }
            else if (status == 5) {
                alert("账户已锁定，请联系管理员！");
            }
            else if (status == 1) {
                var user = result.data;
                if (user.usRole != 1) {
                    alert("您不是管理员，无法登录管理系统!");
                }
                else {
                    localStorage.user = JSON.stringify(result.data);
                    localStorage.token = result.token;
                    window.location.href = "main.html";
                }
            }
        },
        error: function (result) {
            console.log(result);
        }
    });
}

function refreshVerify() {
    $.ajax({
        url: encodeURI(baseUrl + "/verify/getVerify.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        cache: false,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#verifyImg").attr("src", "data:image/gif;base64," + result.data);
        },
        error: function (result) {
            console.log(result);
        }
    });
}