var maxPage = 1;
var nowPage = 1;
$(document).ready(function () {
    if (checkOnline()) {
        usersQuery(0);
        refreshClick();
    }
});

function refreshClick() {
    $('#myModal').off("click").on('show.bs.modal', function (event) {
        var btnThis = $(event.relatedTarget); //触发事件的按钮
        var Id = btnThis.data('id');   //解析出data-id的内容
        var role = btnThis.data('role');
        $("#nowId").html(Id);
        $("#usertype").val(role);
        $("#password").val("");
    });

    $(".change_status").off("click").click(function () {
        var btnThis = $(this);
        var userId = btnThis.data('id');
        var status = btnThis.data('status');
        var data = {
            "id": userId,
            "usStatus": status * -1,
        }
        $.ajax({
            url: encodeURI(baseUrl + "/user/update.do?t=" + Math.random()),
            type: "PUT",
            async: true,//这里表示同步
            dataType: 'json',
            headers: {
                Accept: "application/json; charset=utf-8",
                Authorization: token
            },
            data: data,
            cache: false,
            success: function (result) {
                if (result.data = 1) {
                    alert("修改成功.");
                    usersQuery(1);
                }
                else {
                    alert("修改失败.");
                }
            },
            error: function (result) {
                console.log(result);
            }
        });
    });
}

/**
 * 更新个人信息
 */
function changeUser() {
    var role = $("#usertype").val();
    var id = $("#nowId").text();
    var password = $("#password").val();
    console.log(role + "--" + id + "--" + password);
    var data = {
        "id": id,
        "usRole": role
    };
    if (password != null && password.trim() != '') {
        data["usPassword"] = encrypt(password);
    }
    $.ajax({
        url: encodeURI(baseUrl + "/user/update.do?t=" + Math.random()),
        type: "PUT",
        async: true,//这里表示同步
        dataType: 'json',
        headers: {
            Accept: "application/json; charset=utf-8",
            Authorization: token
        },
        data: data,
        cache: false,
        success: function (result) {
            if (result.data = 1) {
                alert("修改成功.");
                usersQuery(1);
            }
            else {
                alert("修改失败.");
            }
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 条件查询
 */
function usersQuery(withPage) {
    usersCount();
    var userId = $("#userId").val();
    var username = $("#username").val();
    if (withPage == 0)
        nowPage = 1;
    var start = nowPage;
    if (start == null)
        start = 1;
    start = (start - 1) * 20;

    var data = {
        "usName": username,
        "id": userId,
        "start": start,
        "rows": 20
    };
    if (username == null || username.trim() == '') {
        data = {
            "id": userId,
            "start": start,
            "rows": 20
        };
    }
    $.ajax({
        url: encodeURI(baseUrl + "/user/users.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        headers: {
            Accept: "application/json; charset=utf-8",
            Authorization: token
        },
        data: data,
        cache: false,
        success: function (result) {
            drawUser(result.data);
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 添加用户
 * @param data
 */
function drawUser(data) {
    var users = $("#users");
    users.html('');
    if (data == null) {
        users.html('暂无数据.');
        return;
    }
    for (var i = 0; i < data.length; i++) {
        var user = data[i];
        users.append(record(user));
    }
    refreshClick();
}

/**
 * 返回一条记录
 * @param user
 * @returns {string}
 */
function record(user) {
    var role = user.usRole == 1 ? "admin" : "普通用户";
    var status = user.usStatus == 1 ? "正常" : "锁定中";
    var oper = user.usStatus == 1 ? "封停" : "解封";
    return "<tr>\n" +
        "                    <td>" + user.id + "</td>\n" +
        "                    <td>" + user.usName + "</td>\n" +
        "                    <td>" + role + "</td>\n" +
        "                    <td>" + user.usAge + "</td>\n" +
        "                    <td>" + user.usNickname + "</td>\n" +
        "                    <td>" + user.usInstitution + "</td>\n" +
        "                    <td>" + user.usMajor + "</td>\n" +
        "                    <td>" + user.usClass + "</td>\n" +
        "                    <td>" + status + "</td>\n" +
        "                    <td> <button data-id=" + user.id + " data-status=" + user.usStatus + " class=\"btn btn-primary change_status\">" + oper + "</button></td>\n" +
        "                    <td> <button data-id=" + user.id + " data-role=" + user.usRole + " type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#myModal\">修改</button></td>\n" +
        "                </tr>";
}

/**
 * 条件查询个数
 */
function usersCount() {
    var userId = $("#userId").val();
    var username = $("#username").val();
    var data = {
        "usName": username,
        "id": userId
    };
    if (username == null || username.trim() == '') {
        data = {
            "id": userId
        };
    }
    $.ajax({
        url: encodeURI(baseUrl + "/user/userCount.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        headers: {
            Accept: "application/json; charset=utf-8",
            Authorization: token
        },
        data: data,
        cache: false,
        success: function (result) {
            maxPage = Math.floor(result.data / 20) + 1;
            $(".pagination").createPage({
                totalPage: maxPage,
                currPage: nowPage,
                backFn: function (p) {
                    if (nowPage == p)
                        return;
                    nowPage = p;
                    usersQuery(1);
                }
            });
        },
        error: function (result) {
            console.log(result);
        }
    });
}