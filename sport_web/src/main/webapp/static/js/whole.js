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

function openIpSearch() {
    $.ajax({
        url: encodeURI(baseUrl + "/user/ipSearch.do?t=" + Math.random()),
        type: "PUT",
        async: true,//这里表示同步
        dataType: 'json',
        headers: {
            Accept: "application/json; charset=utf-8",
            Authorization: token
        },
        data: {
            "open":!ipSearch
        },
        cache: false,
        success: function (result) {
            alert(result.data);
            isOpen();
        },
        error: function (result) {
            console.log(result);
        }
    });
}
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
    return $.base64.btoa(password + "swust_sport");
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

!(function($) {

    var b64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
        a256 = '',
        r64 = [256],
        r256 = [256],
        i = 0;

    var UTF8 = {

        /**
         * Encode multi-byte Unicode string into utf-8 multiple single-byte characters
         * (BMP / basic multilingual plane only)
         *
         * Chars in range U+0080 - U+07FF are encoded in 2 chars, U+0800 - U+FFFF in 3 chars
         *
         * @param {String} strUni Unicode string to be encoded as UTF-8
         * @returns {String} encoded string
         */
        encode: function(strUni) {
            // use regular expressions & String.replace callback function for better efficiency
            // than procedural approaches
            var strUtf = strUni.replace(/[\u0080-\u07ff]/g, // U+0080 - U+07FF => 2 bytes 110yyyyy, 10zzzzzz
                function(c) {
                    var cc = c.charCodeAt(0);
                    return String.fromCharCode(0xc0 | cc >> 6, 0x80 | cc & 0x3f);
                })
                .replace(/[\u0800-\uffff]/g, // U+0800 - U+FFFF => 3 bytes 1110xxxx, 10yyyyyy, 10zzzzzz
                    function(c) {
                        var cc = c.charCodeAt(0);
                        return String.fromCharCode(0xe0 | cc >> 12, 0x80 | cc >> 6 & 0x3F, 0x80 | cc & 0x3f);
                    });
            return strUtf;
        },

        /**
         * Decode utf-8 encoded string back into multi-byte Unicode characters
         *
         * @param {String} strUtf UTF-8 string to be decoded back to Unicode
         * @returns {String} decoded string
         */
        decode: function(strUtf) {
            // note: decode 3-byte chars first as decoded 2-byte strings could appear to be 3-byte char!
            var strUni = strUtf.replace(/[\u00e0-\u00ef][\u0080-\u00bf][\u0080-\u00bf]/g, // 3-byte chars
                function(c) { // (note parentheses for precence)
                    var cc = ((c.charCodeAt(0) & 0x0f) << 12) | ((c.charCodeAt(1) & 0x3f) << 6) | (c.charCodeAt(2) & 0x3f);
                    return String.fromCharCode(cc);
                })
                .replace(/[\u00c0-\u00df][\u0080-\u00bf]/g, // 2-byte chars
                    function(c) { // (note parentheses for precence)
                        var cc = (c.charCodeAt(0) & 0x1f) << 6 | c.charCodeAt(1) & 0x3f;
                        return String.fromCharCode(cc);
                    });
            return strUni;
        }
    };

    while(i < 256) {
        var c = String.fromCharCode(i);
        a256 += c;
        r256[i] = i;
        r64[i] = b64.indexOf(c);
        ++i;
    }

    function code(s, discard, alpha, beta, w1, w2) {
        s = String(s);
        var buffer = 0,
            i = 0,
            length = s.length,
            result = '',
            bitsInBuffer = 0;

        while(i < length) {
            var c = s.charCodeAt(i);
            c = c < 256 ? alpha[c] : -1;

            buffer = (buffer << w1) + c;
            bitsInBuffer += w1;

            while(bitsInBuffer >= w2) {
                bitsInBuffer -= w2;
                var tmp = buffer >> bitsInBuffer;
                result += beta.charAt(tmp);
                buffer ^= tmp << bitsInBuffer;
            }
            ++i;
        }
        if(!discard && bitsInBuffer > 0) result += beta.charAt(buffer << (w2 - bitsInBuffer));
        return result;
    }

    var Plugin = $.base64 = function(dir, input, encode) {
        return input ? Plugin[dir](input, encode) : dir ? null : this;
    };

    Plugin.btoa = Plugin.encode = function(plain, utf8encode) {
        plain = Plugin.raw === false || Plugin.utf8encode || utf8encode ? UTF8.encode(plain) : plain;
        plain = code(plain, false, r256, b64, 8, 6);
        return plain + '===='.slice((plain.length % 4) || 4);
    };

    Plugin.atob = Plugin.decode = function(coded, utf8decode) {
        coded = coded.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        coded = String(coded).split('=');
        var i = coded.length;
        do {--i;
            coded[i] = code(coded[i], true, r64, a256, 6, 8);
        } while (i > 0);
        coded = coded.join('');
        return Plugin.raw === false || Plugin.utf8decode || utf8decode ? UTF8.decode(coded) : coded;
    };
}(jQuery));
