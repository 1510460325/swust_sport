$(document).ready(function () {
    if (checkOnline()) {
        queryAllComplains();
    }
});

/**
 * 删除反馈
 */
function refreshClick() {
    $(".delete_complain").off("click").click(function () {
        var btnThis = $(this);
        var id = btnThis.data('id');
        var data = {
            "id": id
        };
        data["_method"] = "DELETE";
        $.ajax({
            url: encodeURI(baseUrl + "/complain/delete.do?t=" + Math.random()),
            type: "POST",
            async: true,//这里表示同步
            dataType: 'json',
            headers: {
                Accept: "application/json; charset=utf-8",
                Authorization: token
            },
            data: data,
            cache: false,
            success: function (result) {
                queryAllComplains();
            },
            error: function (result) {
                console.log(result);
            }
        });
    });
}

function queryAllComplains() {
    $.ajax({
        url: encodeURI(baseUrl + "/complain/complains.do?t=" + Math.random()),
        type: "GET",
        async: true,//这里表示同步
        dataType: 'json',
        headers: {
            Accept: "application/json; charset=utf-8",
            Authorization: token
        },
        cache: false,
        success: function (result) {
            drawComplains(result.data);
            refreshClick();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

function drawComplains(data) {
    var complains = $("#complains");
    complains.html('');
    if (data == null || data.length == 0) {
        complains.html('暂无数据');
        return;
    }
    for (var i = 0; i < data.length; i++) {
        complains.append(record(data[i]));
    }
}

function record(data) {
    return "<tr>\n" +
        "                <td>" + data.id + "</td>\n" +
        "                <td>" + data.coUserid+"</td>\n" +
        "                <td>"+ data.usName + "</td>\n" +
        "                <td>"+ data.coContent +"</td>\n" +
        "                <td>"+ToTime(data.coCreatdate)+"</td>\n" +
        "                <td><button data-id="+ data.id +" class=\"btn btn-primary delete_complain\">收到!</button></td>\n" +
        "            </tr>"
}