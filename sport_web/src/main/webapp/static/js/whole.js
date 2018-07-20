var user_info = {
    userId: '',
    usName: '',
    usAge: '',
    usNickname: '',
    usSign: '',
    usClass: '',
    usMajor: '',
    usInstitution: '',
    usRoomid: '',
    usStatus: '',
    usRole: '',
    init: function (user) {
        this.userId = user.id;
        this.usName = user.usName;
        this.usAge = user.usAge;
        this.usNickname = user.usNickname;
        this.usSign = user.usSign;
        this.usClass = user.usClass;
        this.usMajor = user.usMajor;
        this.usInstitution = user.usInstitution;
        this.usRoomid = user.usRoomid;
        this.usStatus = user.usStatus;
        this.usRole = user.usRole;
    },
    destroy:function () {
        this.userId = '';
        this.usName = '';
        this.usAge = '';
        this.usNickname = '';
        this.usSign = '';
        this.usClass = '';
        this.usMajor = '';
        this.usInstitution = '';
        this.usRoomid = '';
        this.usStatus = '';
        this.usRole = '';
    }
};
var token;

function checkOnline() {
    if (localStorage.user == null || localStorage.token == null) {
        toQuite();
        return false;
    }
    else {
        var user = JSON.parse(localStorage.user);
        user_info.init(user);
        token = localStorage.token;
        $("#admin").html("欢迎管理员：" + user_info.usName + " !");
    }
    return true;
}
function toQuite() {
    localStorage.clear();
    window.location.href = "login.html";
}

function ToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = date.getDate() + ' ';
    var h = date.getHours() + ':';
    var m = date.getMinutes() + ':';
    var s = date.getSeconds();
    return Y + M + D + h + m + s;
}

function encrypt(password) {
    return password + "asd";
}
/**
 * 分页按钮
 */
(function ($) {
    var ms = {
        init: function (totalsubpageTmep, args) {
            return (function () {
                ms.fillHtml(totalsubpageTmep, args);
                ms.bindEvent(totalsubpageTmep, args);
            })();
        },
        //填充html
        fillHtml: function (totalsubpageTmep, args) {
            return (function () {
                totalsubpageTmep = "";
                // 页码大于等于4的时候，添加第一个页码元素
                if (args.currPage != 1 && args.currPage >= 4 && args.totalPage != 4) {
                    totalsubpageTmep += "<li class=\"page-item\"><a class=\"page-link\" href=\"#\">" + 1 + "</a></li>";
                }
                /* 当前页码>4, 并且<=总页码，总页码>5，添加“···”*/
                if (args.currPage - 2 > 2 && args.currPage <= args.totalPage && args.totalPage > 5) {
                    totalsubpageTmep += "<li class=\"page-item\"><a class=\"page-\" href=\"#\">....</a></li>";
                }
                /* 当前页码的前两页 */
                var start = args.currPage - 2;
                /* 当前页码的后两页 */
                var end = args.currPage + 2;

                if ((start > 1 && args.currPage < 4) || args.currPage == 1) {
                    end++;
                }
                if (args.currPage > args.totalPage - 4 && args.currPage >= args.totalPage) {
                    start--;
                }
                for (; start <= end; start++) {
                    if (start <= args.totalPage && start >= 1) {
                        if (start != nowPage)
                            totalsubpageTmep += "<li class=\"page-item\"><a class=\"page-link\" href=\"#\">" + start + "</a></li>";
                        else
                            totalsubpageTmep += "<li class=\"page-item\"><a style='background-color: #F4F4F4' class=\"page-link\" href=\"#\">" + start + "</a></li>";
                    }
                }
                if (args.currPage + 2 < args.totalPage - 1 && args.currPage >= 1 && args.totalPage > 5) {
                    totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page-' data-go='' >....</a></li>";
                }

                if (args.currPage != args.totalPage && args.currPage < args.totalPage - 2 && args.totalPage != 4) {
                    totalsubpageTmep += "<li class=\"page-item\"><a class=\"page-link\" href=\"#\">" + args.totalPage + "</a></li>";
                }
                $(".pagination").html(totalsubpageTmep);
            })();
        },
        //绑定事件
        bindEvent: function (totalsubpageTmep, args) {
            return (function () {
                totalsubpageTmep.on("click", "a.page-link", function (event) {
                    var current = parseInt($(this).text());
                    ms.fillHtml(totalsubpageTmep, {
                        "currPage": current,
                        "totalPage": args.totalPage,
                        "turndown": args.turndown
                    });
                    if (typeof(args.backFn) == "function") {
                        args.backFn(current);
                    }
                });
            })();
        }
    };
    $.fn.createPage = function (options) {
        ms.init(this, options);
    }
})(jQuery);