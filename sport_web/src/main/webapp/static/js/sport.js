$(document).ready(function () {
    if (checkOnline()) {
        queryAll();
        refreshClick();
    }
});

function refreshClick() {
    $('#myModal').on('show.bs.modal', function (event) {
        var btnThis = $(event.relatedTarget); //触发事件的按钮
        var id = btnThis.data('id');   //解析出data-id的内容
        var name = btnThis.data('name');
        $("#nowId").html(id);
        $("#name").val(name);
    });
    $(".delete_this").click(function () {
        var btnThis = $(this);
        var id = btnThis.data("id");
        var data = {};
        data["id"] = id;
        data["_method"] = "DELETE";
        $.ajax({
            url: encodeURI(baseUrl + "/sports/delete.do?t=" + Math.random()),
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
                if (result.data == 1)
                    alert("删除成功");
                queryAll();
            },
            error: function (result) {
                console.log(result);
            }
        });
    })
}


function insertSport() {
    var name = $("#newName").val();
    var files = $("#newImg").prop('files');
    var reader = new FileReader();//新建一个FileReader
    try {
        reader.readAsDataURL(files[0]);//读取文件
        reader.onload = function () {
            var file = this.result;
            var index = file.indexOf("base64,");
            file = file.substring(index + 7);
            var data = {};
            data["spName"] = name;
            data["file"] = file;
            insertOne(data);
        }
    } catch (e) {
        var data = {};
        data["spName"] = name;
        insertOne(data);
    }
}

/**
 * 插入一个
 * @param data
 */
function insertOne(data) {
    $.ajax({
        url: encodeURI(baseUrl + "/sports/insert.do?t=" + Math.random()),
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
            console.log(result.data);
            queryAll();
            alert("添加成功.");
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 更新模块信息
 */
function updateSport() {
    var id = $("#nowId").text();
    var name = $("#name").val();
    var files = $("#img").prop('files');
    var reader = new FileReader();//新建一个FileReader
    try {
        reader.readAsDataURL(files[0]);//读取文件
        reader.onload = function () {
            var file = this.result;
            var index = file.indexOf("base64,");
            file = file.substring(index + 7);
            var data = {};
            data["id"] = id;
            data["spName"] = name;
            data["file"] = file;
            updateOne(data);
        }
    } catch (e) {
        var data = {};
        data["id"] = id;
        data["spName"] = name;
        updateOne(data);
    }
}

/**
 * 更新
 * @param data
 */
function updateOne(data) {
    $.ajax({
        url: encodeURI(baseUrl + "/sports/update.do?t=" + Math.random()),
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
            if (result.data == 1)
                alert("修改成功.");
            queryAll();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 查询所有的模块
 */
function queryAll() {
    var sportId = $("#sportId").val();
    var sportName = $("#sportName").val();
    var data = {};
    if (sportId != null)
        data["id"] = sportId;
    if (sportName != null && sportName.trim() != '')
        data["spName"] = sportName;
    $.ajax({
        url: encodeURI(baseUrl + "/sports/sports.do?t=" + Math.random()),
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
            drawModels(result.data);
            refreshClick();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

/**
 * 绘制模块
 * @param data
 */
function drawModels(data) {
    var sports = $("#sports");
    sports.html('');
    if (data == null || data.length == 0) {
        sports.html('暂无数据.');
        return;
    }
    for (var i = 0; i < data.length; i++) {
        sports.append(record(data[i]));
    }
}

/**
 * 返回一个模块内容
 * @param sport
 * @returns {string}
 */
function record(sport) {
    return "<tr>\n" +
        "                    <td>" + sport.id + "</td>\n" +
        "                    <td><img src=\"" + baseUrl + sport.spImg + "\"/> </td>\n" +
        "                    <td>" + sport.spName + "</td>\n" +
        "                    <td>" + ToTime(sport.spCreatdate) + "</td>\n" +
        "                    <td> <button data-id=" + sport.id + " class=\"btn btn-primary delete_this\">删除</button></td>\n" +
        "                    <td> <button data-id=" + sport.id + " data-name=" + sport.spName + " type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#myModal\">修改</button></td>\n" +
        "                </tr>";
}