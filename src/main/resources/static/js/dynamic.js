var commentDynamicId = 0;
var commentToUserId = 0;
var commentToUserName = "";
var obj;
$(function () {

    $(function () {
        $(".content-page").delegate("a", "click", function (event) {
            event.stopPropagation();
        });
    });

    //回复评论
    $(".content-page").delegate(".comment-item", "click", function () {

        var commentUserId = $(this).attr("user-id");
        var dynamicId = $(this).parent().attr("dynamic-id");

        if ($.cookie("userId") === commentUserId) {
            return;
        }

        commentDynamicId = parseInt(dynamicId);
        commentToUserId = parseInt(commentUserId);

        commentToUserName = $(this).find(".username").text();
        $(".js-title-name").text("回复:" + commentToUserName);
        LogWind();

    });

    $(".content-page").delegate(".send-item", "click", function () {
        var obj = $(this);
        var id = obj.attr("data-id");
        var name = obj.attr("data-name");
        document.location.href = "msg.aspx?ids=" + id + "&hname=" + name;
    });

    //是否认证
    function isIdChecked() {
        // var IDCheck = $("#IDCheck").val();
        // var zonecheckid = $("#zonecheckid").val();
        // if (zonecheckid=="0"||IDCheck == "1") {
        //     return true;
        // }
        // else {
        //     if (isUsedZoneNoID()) {
        //         return true;
        //     } else {
        //         swal({ title: "提示", html: true, text: "<p style='text-align: center;'>您还没有成为\"认证用户\"</p>", showCancelButton: true, closeOnConfirm: false, cancelButtonText: "取消", confirmButtonColor: "#DD6B55", confirmButtonText: "认证" }, function () {
        //             location.href = "/love/wap/checkId.aspx?s=10&check=0";
        //         });
        //         return false;
        //     }
        // }
        return true;
    }

    //是否被封
    function isUsedZone() {
        if ($("#UsedZone").length > 0) {
            var UsedZone = $("#UsedZone").val();
            if (UsedZone == "1") {
                return true;
            }
            else {
                dszdy.UI.Tip("您没有权限操作");
                return false;
            }
        }
    }

    //是否开通特权
    function isUsedZoneNoID() {
        if ($("#UsedZoneNoID").length > 0) {
            var UsedZoneNoID = $("#UsedZoneNoID").val();
            if (UsedZoneNoID == "1") {
                return true;
            }
            else {
                return false;
            }
        }
    }

    //删除这条动态
    $(".content-page").delegate(".js-delzone", "click", function () {
        var obj = $(this);
        var id = obj.parent().attr("data-id");
        dszdy.UI.confirm("是否删除这条动态", "取消", "确定", function () {
            dszdy.UI.loadingReview();
            $.api("del_zone", [id], del_zoneCallBack, "user_zone");
            function del_zoneCallBack(data) {
                dszdy.UI.loadingReview(false);
                if (data[0] == "SUCCESS") {
                    dszdy.UI.Tip("删除成功");
                    obj.parents(".dataItem").parent(".col-xs-12").remove();
                    //window.location.href = "/love/wap/userProfile.aspx";
                }
            }
        });
        //swal({ title: "是否删除这条动态", html: true, text: "", showCancelButton: true, closeOnConfirm: false, cancelButtonText: "取消", confirmButtonColor: "#DD6B55", confirmButtonText: "确认" }, function () {
        //    $.api("del_zone", [id], del_zoneCallBack, "user_zone");
        //    function del_zoneCallBack(data) {
        //        if (data[0] == "SUCCESS") {
        //            dszdy.UI.Tip("删除成功");
        //            obj.parents(".dataItem").parent(".col-xs-12").remove();
        //            //window.location.href = "/love/wap/userProfile.aspx";
        //        }
        //    }
        //});
    });

    //点赞
    $(".content-page").delegate(".js-like", "click", function () {
        var obj = $(this);
        var dynamicId = obj.parent().attr("data-id");
        var param = {"dynamicId": dynamicId};
        dszdy.Business.Dynamic.praise(param, obj);
    });

    //发布评论-弹窗
    $(".content-page").delegate(".js-comment", "click", function () {
        obj = $(this);
        commentToUserId = 0;
        commentDynamicId = parseInt(obj.parent().attr("data-id"));
        $(".js-title-name").text("评论");

        LogWind();
    });

    $(".js-close").on("click", function () {
        closWind();
        $(".emoji_container").remove();
    });

    $(".js-wuserProfile-go").on("click", function () {
        if (isIdChecked() && isUsedZone()) {
            location.href = "postDynamic.html";
        }
    });
    $(".js-quan-go").on("click", function () {
        if (isIdChecked() && isUsedZone()) {
            location.href = "singletons.html";
        }
    });
    $(".js-xzone-go").on("click", function () {
        if (isIdChecked() && isUsedZone()) {
            location.href = "postDynamic.html";
        }
    });

    function LogWind() {

        var srcollh = $(document).scrollTop();

        $("#editor").html("");

        $("#login-model").modal({
            backdrop: "static",
            keyboard: false
        });

        $("#login-model").attr("style", "padding-top: 60px; position: absolute; left: 0px; width: 100%; height: 687px; top: " + srcollh + "px;");
    }

    //送花加好友
    $("#wrapper").delegate(".js-sendflower", "click", function () {
        if (isIdChecked()) {
            var obj = $(this);
            var id = obj.attr("data-id");
            var mfl = $("#MyFl").val();
            swal({
                title: "",
                html: true,
                text: "您当日还剩" + mfl + "朵 <img src='images/rose.png' style='height: 30px' />,送后等待对方是否接受，接受即可私聊",
                showCancelButton: true,
                closeOnConfirm: true,
                cancelButtonText: "取消",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确认"
            }, function () {
                $.api("add_friend", [id], add_friendCallBack, "user_zone");
                function add_friendCallBack(data) {
                    if (data[0] == "SUCCESS") {
                        dszdy.UI.Tip("发送成功");
                    } else {
                        swal(data[0]);
                    }
                }
            });
        }
    });


    //添加黑名单
    $(".js-add-shield").on("click", function () {
        var obj = $(this);
        var id = obj.attr("data-id");
        swal({
            title: "是否屏蔽此人",
            html: true,
            text: "",
            showCancelButton: true,
            closeOnConfirm: false,
            cancelButtonText: "取消",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认"
        }, function () {
            $.api("add_shield", [id], add_shieldCallBack, "user_zone");
            function add_shieldCallBack(data) {
                if (data[0] == "SUCCESS") {
                    dszdy.UI.Tip("屏蔽成功");
                    window.location.href = "/love/wap/xzone.aspx";
                } else {
                    dszdy.UI.Tip("屏蔽失败");
                }
            }
        });
    });
    //移除黑名单
    $(".js-del-shield").on("click", function () {
        var obj = $(this);
        var id = obj.parent().attr("data-id");
        swal({
            title: "是否取消屏蔽",
            html: true,
            text: "",
            showCancelButton: true,
            closeOnConfirm: false,
            cancelButtonText: "取消",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认"
        }, function () {
            $.api("del_shield", [id], del_shieldCallBack, "user_zone");
            function del_shieldCallBack(data) {
                if (data[0] == "SUCCESS") {
                    dszdy.UI.Tip("取消成功");
                    window.location.href = "/love/wap/hide.aspx";
                } else {
                    dszdy.UI.Tip("取消失败");
                }
            }
        });
    });
    //举报他
    $(".js-add-reported").on("click", function () {
        var id = $("#hiuserid").val();
        var type = '';
        $("img:not(.hide)").each(function (e) {
            type += $(this).attr("data-id") + ",";
        });
        if (type == '') {
            dszdy.UI.Tip("请选择举报内容");
            return;
        }
        swal({
            title: "是否举报",
            html: true,
            text: "",
            showCancelButton: true,
            closeOnConfirm: false,
            cancelButtonText: "取消",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认"
        }, function () {
            $.api("add_reported", [id, type], add_reportedCallBack, "user_zone");
            function add_reportedCallBack(data) {
                if (data[0] == "SUCCESS") {
                    dszdy.UI.Tip("举报成功");
                    window.location.href = "/love/wap/xzone.aspx";
                } else {
                    dszdy.UI.Tip(data[0]);
                    swal.close();
                }
            }
        });
    });
});

//关闭弹框
function closWind() {
    $('#login-model').modal('hide')
}

//评论
function SendContent() {

    var contentBefor = $("#editor").html();
    if ($("#editor").text().length > 600) {
        dszdy.UI.Tip("字数不能超过600个");
        return false;
    }

    var content = replaceEmoji(utf16toEntities(contentBefor));

    var param = {"content": content, "dynamicId": parseInt(commentDynamicId), "commentToUserId":commentToUserId};

    $.api("comment", param, function (data) {
        commentDynamicCallBack(data, contentBefor, commentToUserName);
    }, "dynamic");

    function commentDynamicCallBack(ret, content, commentToUserName) {
        dszdy.UI.Tip(ret.message);
        if (ret.status === "SUCCESS") {

            if (ret.data.commentToUserId === 0) {
                var s = '<div class="comment-item" user-id="' +  ret.data.userId + '">' +
                    '<div class="avatar">' +
                    '<b class="pic" style="background-image: url(' + ret.data.avatar.fileHost + "/100/100/" + ret.data.avatar.fileName + ret.data.avatar.fileType + ')"></b>' +
                    '</div>' +
                    '<div class="mainer">' +
                    '<div class="comment-info" role="text">' +
                    '<p class="title"><a class="username">' + ret.data.username + '</a></p>' +
                    '</div>' +
                    '<div class="comment-text">' + content + '</div>' +
                    '</div>' +
                    '</div>';
            } else {
                s = '<div class="comment-item"  user-id="' + ret.data.userId + '">' +
                        '<div class="avatar">' +
                        '<b class="pic" style="background-image: url(' + ret.data.avatar.fileHost + "/100/100/" + ret.data.avatar.fileName + ret.data.avatar.fileType + ')"></b>' +
                        '</div>' +
                        '<div class="mainer">   ' +
                        '<div class="comment-info">   ' +
                        '<p class="title">' +
                        '<a class="username" href="javascript:onClickJump('+ userId +')">' + ret.data.username + '</a>回复:' +
                        '<a href="javascript:onClickJump('+ ret.data.commentToUserId +')">'+ commentToUserName +'</a>' +
                        '</p>  ' +
                        '</div>   ' +
                        '<div class="comment-text"> ' + content +
                        '</div>   ' +
                        '</div>' +
                        '</div>';
            }

            var nums = parseInt($("div[data-id='" + commentDynamicId + "']").find(".js-comment").text().replace("评论", ""));
            if (nums === 0 || $(".js-comment-list" + commentDynamicId + " .comment-item:last").length === 0) {
                $(".js-comment-list" + commentDynamicId).after(s);
            } else {
                $(".js-comment-list" + commentDynamicId + " .comment-item:last").after(s);
            }
            $("div[data-id='" + commentDynamicId + "']").find(".js-comment").text("评论" + (++nums));
            // }
            closWind();
            return false;
        }

    }
}

function utf16toEntities(str) {
    var patt = /[\ud800-\udbff][\udc00-\udfff]/g; // 检测utf16字符正则
    str = str.replace(patt, function (char) {
        var H, L, code;
        if (char.length === 2) {
            H = char.charCodeAt(0); // 取出高位
            L = char.charCodeAt(1); // 取出低位
            code = (H - 0xD800) * 0x400 + 0x10000 + L - 0xDC00; // 转换算法
            return "&#" + code + ";";
        } else {
            return char;
        }
    });
    return str;
}

function replaceEmoji(str) {
    var patt = /<img class="emoji_icon" src="static\/emoji\/img\/(.*?)\/(.*?).gif">/g;
    return str.replace(patt, "[" + "$1" + ":" + "$2" + "]");
}
