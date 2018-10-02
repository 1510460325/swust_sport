$(document).ready(function () {
    if (checkOnline()) {
        queryAll();
        refreshClick();
    }
});

function refreshClick() {
    $('#myModal').off("click").on('show.bs.modal', function (event) {
        var btnThis = $(event.relatedTarget); //触发事件的按钮
        var id = btnThis.data('id');   //解析出data-id的内容
        var name = btnThis.data('name');
        $("#nowId").html(id);
        $("#name").val(name);
    });
    $(".delete_this").off("click").click(function () {
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
    var data = {};
    data["spName"] = name;
    uploadFile1(data,2);
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
    var data = {};
    data["id"] = id;
    data["spName"] = name;
    uploadFile1(data,1);
}

function uploadFile1(data, type) {
    var input = type == 1 ? "#Img1" : "#newImg1";
    var files = $(input).prop('files');
    var reader = new FileReader();//新建一个FileReader
    try {
        reader.readAsDataURL(files[0]);//读取文件
        reader.onload = function () {
            var file = this.result;
            var index = file.indexOf("base64,");
            file = file.substring(index + 7);
            data["file1"] = file;
            uploadFile2(data, type);
        }
    } catch (e) {
        uploadFile2(data, type);
    }
}

function uploadFile2(data, type) {
    var input = type == 1 ? "#Img2" : "#newImg2";
    var files = $(input).prop('files');
    var reader = new FileReader();//新建一个FileReader
    try {
        reader.readAsDataURL(files[0]);//读取文件
        reader.onload = function () {
            var file = this.result;
            var index = file.indexOf("base64,");
            file = file.substring(index + 7);
            data["file2"] = file;
            if (type == 1)
                updateOne(data);
            else
                insertOne(data);
        }
    } catch (e) {
        if (type == 1)
            updateOne(data);
        else
            insertOne(data);
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
        contentType : "application/json",
        data: JSON.stringify(data),
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
        "                    <td><img src=\"" + imgUrl + sport.spImg + "\" width='400px' height='300px'/> </td>\n" +
        "                    <td><img src=\"" + imgUrl + sport.spRoimg + "\" width='127px' height='127px'/> </td>\n" +
        "                    <td>" + sport.spName + "</td>\n" +
        "                    <td>" + ToTime(sport.spCreatdate) + "</td>\n" +
        "                    <td> <button data-id=" + sport.id + " class=\"btn btn-primary delete_this\">删除</button></td>\n" +
        "                    <td> <button data-id=" + sport.id + " data-name=" + sport.spName + " type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#myModal\">修改</button></td>\n" +
        "                </tr>";
}