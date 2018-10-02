var nowUserId;
var nowRoomId;
$(document).ready(function () {
    if (checkOnline()) {
        queryRooms();
        queryUserMessage();
    }
});
function refreshClick() {
    $(".look_content").off("click").click(function () {
        var btnThis = $(this);
        nowRoomId = btnThis.data('id');
        nowUserId = null;
        $("#roomName").html(nowRoomId);
        queryContentByCondition();
    });
    $(".lookcontent_withuser").off("click").click(function () {
        var btnThis = $(this);
        nowRoomId = btnThis.data('id');
        var userId = btnThis.data('user');
        if (userId == -1)
            nowUserId = null;
        else
            nowUserId = userId;
        $("#roomName").html(nowRoomId);
        queryContentByCondition();
    });
    $(".change_status").off("click").click(function () {
        var btnThis = $(this);
        var userId = btnThis.data('id');
        var status = btnThis.data('status');
        var data = {
            "id": userId,
            "usStatus": status * -1
        };
        $.ajax({
            url: encodeURI(baseUrl + "/user/update.do?t=" + Math.random()),
            type: "PUT",
            async: true,//这里表示同步
            dataType: 'json',
            headers: {
                Accept: "application/json; charset=utf-8",
                Authorization: token
            },
            contentType : "application/json",
            data: JSON.stringify(data),
            cache: false,
            success: function (result) {
                if (result.data = 1) {
                    alert("修改成功.");
                    queryContentByCondition();
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
 * 条件查询聊天记录
 * @param data
 */
function queryContentByCondition() {
    var data={
        "usRoomid":nowRoomId,
        "usUserid":nowUserId
    };
    $.ajax({
        url: encodeURI(baseUrl + "/message/messages.do?t=" + Math.random()),
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
            drawContents(result.data);

        },
        error: function (result) {
            console.log(result);
        }
    });
}

function drawContents(data) {
    var contents = $("#contents");
    contents.html('');
    if (data == null || data.length == 0) {
        contents.html('暂无数据');
        return;
    }
    for (var i = 0; i < data.length; i++) {
        contents.append(contentsRecord(data[i]));
    }
    refreshClick();
}

function contentsRecord(data) {
    var status;
    if (data.usStatus == 1)
        status = "封停他";
    else
        status = "解封他";
    return "<tr>\n" +
        "                <td>" + data.usName + "</td>\n" +
        "                <td>"+ ToTime(data.usSendtime) +
        "                <br/>" +data.usMessage + "</td>\n" +
        "                <td>\n" +
        "                    <button data-id=" + data.usUserid + " data-status=" +data.usStatus + " class=\"btn btn-primary change_status\">"+status+"</button>\n" +
        "                </td>\n" +
        "            </tr>";
}
/**
 * 查找所有的房间
 */
function queryRooms() {
    var roomId = $("#roomId").val();
    var data={
      "id":roomId
    };
    $.ajax({
        url: encodeURI(baseUrl + "/room/rooms.do?t=" + Math.random()),
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
            drawRooms(result.data);
            refreshClick();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

function drawRooms(data) {
    var rooms = $("#rooms");
    rooms.html('');
    if (data == null || data.length == 0) {
        rooms.html('暂无数据');
        return;
    }
    for (var i = 0; i < data.length; i++) {
        rooms.append(roomRecord(data[i]));
    }
}
function roomRecord(room) {
    var status;
    switch (room.roStatus) {
        case 1:status = '准备中';break;
        case 2:status = '运动中';break;
        case 3:status = '已结束';break;
    }
    return "<tr>\n" +
        "                        <td>"+room.id+"</td>\n" +
        "                        <td>"+room.roSportname+"</td>\n" +
        "                        <td>"+room.roOwnerid+"</td>\n" +
        "                        <td>"+room.roNum +"/"+ room.roOrinum +"</td>\n" +
        "                        <td>"+status+"</td>\n" +
        "                        <td>\n" +
        "                            <button data-id=" + room.id +" class=\"btn btn-primary look_content\">查看</button>\n" +
        "                        </td>\n" +
        "                    </tr>";
}


function queryUserMessage() {
    var username = $("#username").val();
    var roomId = $("#Id").val();

    var data={
        "roomId":roomId
    };
    if (username != null && username.trim() != '')
        data["username"] = username;
    $.ajax({
        url: encodeURI(baseUrl + "/message/userMessages.do?t=" + Math.random()),
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
            drawUserMess(result.data);
            refreshClick();
        },
        error: function (result) {
            console.log(result);
        }
    });
}
function drawUserMess(data) {
    var users = $("#users");
    users.html('');
    if (data == null || data.length == 0) {
        users.html('暂无数据');
        return;
    }
    for (var i = 0; i < data.length;i++) {
        users.append(userMessRecord(data[i]));
    }
}
function userMessRecord(data) {
    return "<tr>\n" +
        "                        <td>"+data.roomId+"</td>\n" +
        "                        <td>"+data.usName+"</td>\n" +
        "                        <td>"+data.total+"</td>\n" +
        "                        <td>"+ToTime(data.sendTime)+"</td>\n" +
        "                        <td>\n" +
        "                            <button data-user=" + data.userId + " data-id="+ data.roomId +" class=\"btn btn-primary lookcontent_withuser\">查看</button>\n" +
        "                        </td>\n" +
        "                    </tr>";
}