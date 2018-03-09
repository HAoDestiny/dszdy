!(function () {

    //针对原型的方法添加应用支持
    String.prototype.trim = function () {
        var str = this;
        str = str.replace(/^\s\s*/, "");
        var ws = /\s/;
        var i = str.length;
        while (ws.test(str.charAt(--i)));
        return str.slice(0, i + 1);
    }

    //字符串占位符格式化功能
    String.prototype.format = function () {
        if (arguments.length == 0) return this;
        for (var s = this, i = 0; i < arguments.length; i++)
            s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
        return s;
    };

    //获取api令牌
    var dstoken = $.cookie("__dstoken");

    //设定基本命名空间
    var dszdy = window.dszdy || {};
    GET = $.urlGet(); //获取URL的Get参数
    //设定基本框架
    dszdy = {
        _INSTALL: function () {
            window.dszdy = dszdy;
            window.dstoken = dstoken;
        },
        Base: {}, 	//基础层,所有的基础函数库,如数据验证、转换等
        DAO: {}, 	//数据访问层,取数据,一般为Ajax的套接口
        Business: {}, //业务逻辑层,处理数据,作为数据层和显示层的接口
        UI: {}, 	//前端显示层,用来重构和回流DOM,前端的特效显示处理
        Page: {}		//用户页面层,用来做一些页面上特有的功能
    };

    //dszdy.DAO
    //数据访问函数库
    dszdy.DAO = {
        ShoppingCart: {
            // GetProduct: function (callback) {
            //     debugger
            //     // $.api("get_trolley_product", null, callback, "user_trolley");
            //     alert(callback);
            // },
            // SetProduct: function (pid, size, count, parentid, parentsize, saletype, promoid, callback) {
            //     if (typeof parentid == "function") {
            //         callback = parentid;
            //         parentid = null;
            //     }
            //
            //     $.api("set_trolley_product", [pid, size, count, parentid || null, parentsize || null, saletype || 0, promoid || 0], callback, "user_trolley");
            // },
            // RemoveProduct: function (pid, size, parentid, parentsize, saletype, promoid, callback) {
            //     if (typeof parentid == "function") {
            //         callback = parentid;
            //         parentid = null;
            //     }
            //
            //     $.api("remove_trolley_product", [pid, size, parentid || null, parentsize || null, saletype || 0, promoid || 0], callback, "user_trolley");
            // },
            // ClearProduct: function (callback) {
            //     $.api("clear_trolley_product", null, callback, "user_trolley");
            // }
        },
        User: {
            login: function (mobile, password, callback) {
                $.api("login" , {"mobile":mobile, "password":password}, callback, "user");
            },
            getUserInfo: function (callback) {
                $.api("getUserInfo", {}, callback, "user");
            },
            getUserInfoOther: function (callback) {
                $.api("getUserInfoOther", {}, callback, "user");
            },
            register: function (mobile, password, code, callback) {
                $.api("register" , {"mobile":mobile, "password":password, "code":code}, callback, "user");
            },
            logout: function (callback) {
                $.api("logout", {}, callback, "user");
            },
            editProfile: function (json, callback) {
                $.api("editProfile", json, callback, "user");
            },
            updateOtherProfile: function (json, callback) {
                $.api("updateOtherProfile", json, callback, "user");
            },
            getUserMateInfoProfile: function (callback) {
                $.api("getMateInfoProfile", {}, callback, "user");
            },
            getMobileVerifyCode: function (mobile, callback) {
                $.api("getMobileVerifyCode" , {"mobile":mobile}, callback, "user");
            },
            getAvatarList: function (avatarIsExistCallback, callback) {
                $.api("getAvatarList", {}, function (ret) {
                    callback(ret, avatarIsExistCallback);
                }, "user");
            },
            removeAvatar: function (id, obj, callback) {
                $.api("removeAvatar?picIndex=" + id, {}, function (ret) {
                    callback(ret, obj);
                }, "user");
            }
        },
        Token: {
            getTokenCode: function (callback) {
                $.api("getTokenCode", {}, callback, "token");
            },
            getChatTokenCode: function (callback) {
                $.api("getChatTokenCode", {}, callback, "token");
            }
        },
        Configuration: {
            getEducationList: function (callback) {
                $.api("getEducationList", {}, callback, "configuration");
            },
            getConstellationList: function (callback) {
                $.api("getConstellationList", {}, callback, "configuration");
            },
            getProfessionList: function (callback) {
                $.api("getProfessionList", {}, callback, "configuration");
            },
            getIncomeList: function (callback) {
                $.api("getIncomeList", {}, callback, "configuration");
            },
            getMaritalStatusList: function (callback) {
                $.api("getMaritalStatusList", {}, callback, "configuration");
            },
            getMetaEducationList: function (callback) {
                $.api("getEducationList", {}, callback, "configuration");
            },
            getMetaMaritalStatusList: function (callback) {
                $.api("getMaritalStatusList", {}, callback, "configuration");
            }
        },
        Address: {
            getAddressListBath: function (param, callback) {
                $.api("getAddressListBath", param, callback, "address");
            }
        },
        Dynamic: {
            post: function (param, callback) {
                $.api("post", param, callback, "dynamic");
            },
            praise: function (param, obj, callback) {
                $.api("praise", param, function (ret) {
                    callback(obj, ret);
                }, "dynamic");
            }

        }
    }
    //业务逻辑函数库
    dszdy.Business = {
        User: (function () {
            function loginCallback(ret) {
                if (ret.status == "SUCCESS") {
                    window.location.href = "singletons.html";
                }
                if (ret.status == "ACTION") {
                    dszdy.UI.Tip(ret.message);
                    setTimeout(function () {
                        window.location.href = ret.data
                    }, 1500);
                }
                else {
                    dszdy.UI.Tip(ret.message);
                }
            }
            function getUserInfoCallback(ret) {
                if (ret.status === "SUCCESS") {
                    var data = ret.data;
                    var avatar = data.avatar.fileHost + "/200/200/" + data.avatar.fileName + data.avatar.fileType;
                    $("#UserPic").attr('src', avatar);
                    $("#trueName").html(data.trueName);
                    $("#userInfo").html(
                        "<p style=\"width: 121px;\">家乡：" + data.province + data.city +
                        "</p><p>学历：" + data.education + "</p><p>出生：" + data.birth + "</p><p>身高：" + data.height + "</p>"
                    );
                    $("#vipType").html("&nbsp;&nbsp;" + data.vipType + "&nbsp;");
                    $('#flowerTotals').text(data.flowerTotal);

                    if (data.proveStatus === 1) {

                        if (data.verify.car === 1) {
                            $("#verify-car").addClass("verify-status");
                        }
                        if (data.verify.education === 1) {
                            $("#verify-education").addClass("verify-status");
                        }
                        if (data.verify.house === 1) {
                            $("#verify-house").addClass("verify-status");
                        }
                        if (data.verify.identity === 1) {
                            $("#verify-identity").addClass("verify-status");
                        }
                        if (data.verify.people === 1) {

                            $("#verify-people").addClass("verify-status");
                        }
                    }
                }
            }
            function getUserInfoOtherCallback(ret) {
                if (ret.status === "SUCCESS") {

                    var userInfo = ret.data.userInfoOther;
                    if (userInfo.weight != 0) {
                        $("input[name='UWeight']").val(userInfo.weight);
                    }

                    //星座
                    //$("select[name='StartSign']").val(userInfo.constellation);
                    if (ret.data.constellation != null) {
                        $('#StartSign').attr("data-code", userInfo.constellation);
                        $('#StartSign').attr("data-value", ret.data.constellation);
                        $("#StartSign_dummy").val(ret.data.constellation);
                    } else {
                        $('#StartSign').attr("data-code", "-1");
                        $('#StartSign').attr("data-value", "");
                    }

                    //职业
                    //$("select[name='Profession']").val(userInfo.career);
                    if (ret.data.profession != null) {
                        $('#Profession').attr("data-code", userInfo.career);
                        $('#Profession').attr("data-value", ret.data.profession);
                        $("#Profession_dummy").val(ret.data.profession);
                    } else {
                        $('#Profession').attr("data-code", "-1");
                        $('#Profession').attr("data-value", "");
                    }

                    //年收入
                    //$("select[name='Income']").val(userInfo.yearIncome);
                    if (ret.data.income != null) {
                        $('#Income').attr("data-code", userInfo.yearIncome);
                        $('#Income').attr("data-value", ret.data.income);
                        $("#Income_dummy").val(ret.data.income);
                    } else {
                        $('#Income').attr("data-code", "-1");
                        $('#Income').attr("data-value", "");
                    }

                    //婚姻状况
                    //$("select[name='MaritalStatus']").val(userInfo.marriageStatus);
                    if (ret.data.maritalStatus != null) {
                        $('#MaritalStatus').attr("data-code", userInfo.marriageStatus);
                        $('#MaritalStatus').attr("data-value", ret.data.maritalStatus);
                        $("#MaritalStatus_dummy").val(ret.data.maritalStatus);
                    } else {
                        $('#MaritalStatus').attr("data-code", "-1");
                        $('#MaritalStatus').attr("data-value", "");
                    }

                    $("input[name='ZhiWei']").val(userInfo.workPosition);
                    $("input[name='Company']").val(userInfo.workUnit);
                    $("input[name='SubArea']").val(userInfo.school);
                    $("textarea[name='Remark']").val(userInfo.selfIntroduction);
                    $("input[name='FamilyStruct']").val(userInfo.familyStructure);

                    var interest = userInfo.interest;
                    if (interest != null && interest != "") {
                        $("input[name='Interest']").val(interest);
                        var splitstr = interest.split(/[,，]/);
                        for (var j = 0; j < splitstr.length; j++) {
                            $(".js-interest-list a[data-id='" + splitstr[j] + "']").addClass("act");
                        }
                    }

                    var cbIsCar = $('input:checkbox[id="cbIsCar"]');
                    var cbIsHome = $('input:checkbox[id="cbIsHome"]');
                    userInfo.hasCar === 1 ? cbIsCar.prop("checked", true) : cbIsCar.prop("checked", false);
                    userInfo.hasHouse === 1 ? cbIsHome.prop("checked", true) : cbIsHome.prop("checked", false);

                    $($('input:radio[name="LikeColor"]')[parseInt(userInfo.characterColor)]).prop("checked", true);

                    getAddressOnProfile(userInfo.liveProvinceId, userInfo.liveCityId, userInfo.liveAreaId);
                }
                else {
                    getAddressOnProfile("440000", "440300", "440304");//初始化地址控件

                }
            }
            function registerCallback(ret) {
                if (ret.message != "") {
                    setTimeout(dszdy.UI.Tip(ret.message), 1000);
                }
                if (ret.status == "SUCCESS") {
                    window.location.href = "profileBase.html";
                }
                else {
                    $("#userpwd").text("");
                }
            }
            function logoutCallback(ret) {
                dszdy.UI.Tip(ret.message);
                if (ret.status == "ACTION") {
                    $.cookie("__dstoken", "", {
                        path : '/',
                        expires : 0
                    });
                    $.cookie("userId", "");
                    setTimeout(
                        window.location.href = ret.data
                        , 1000);
                }
            }
            function editProfileCallback(ret) {
                if (ret.status == "SUCCESS")
                    window.location.href = "uploadPic.html";
                else {
                    dszdy.UI.Tip(ret.message);
                }
            }
            function updateOtherProfileCallback(ret) {
                dszdy.UI.Tip(ret.message);
                if (ret.status === "SUCCESS") {
                    window.location.href = "home.html";
                }
            }
            function getUserMateInfoProfileCallback(ret) {
                if (ret.status === "SUCCESS" && ret.data.mateInfo.ageRange != null && ret.data.mateInfo.heightRange != null) {
                    //$(".js-regobj").text("确认");
                    //$(".js-regobj").attr("data-id", "1");

                    var mateInfoMessage = ret.data;

                    var metaAgeRang = mateInfoMessage.mateInfo.ageRange.split('-');
                    var heightRang = mateInfoMessage.mateInfo.heightRange.split('-');

                    var metaAge = metaAgeRang[0];
                    var metaAgeEnd = metaAgeRang[1];

                    var metaUHeight = heightRang[0];
                    var metaUHeightEnd = heightRang[1];

                    var metaEducationCode = mateInfoMessage.mateInfo.education;
                    var metaEducationValue = "";  //学历
                    var marriageStatusCode = mateInfoMessage.mateInfo.marriageStatus;
                    var marriageStatusValue = mateInfoMessage.maritalStatusValue; //婚姻状态
                    var refuseZodiac = mateInfoMessage.mateInfo.refuseZodiac; //生肖

                    mateInfoMessage.educationValue === "海归"?metaEducationValue = mateInfoMessage.educationValue:metaEducationValue = mateInfoMessage.educationValue + "以上";

                    if (marriageStatusValue === "单身") {
                        marriageStatusValue = "未婚";
                    }

                    $("select[name='MetaAge']").val(metaAge);
                    $("select[name='MetaAgeEnd']").val(metaAgeEnd);
                    $("select[name='MetaUHeight']").val(metaUHeight);
                    $("select[name='MetaUHeightEnd']").val(metaUHeightEnd);
                    $("input[name='Zodiac']").val(refuseZodiac);

                    $('#MetaEducation').attr("data-code", metaEducationCode);
                    $('#MetaEducation').attr("data-value", metaEducationValue);
                    $('#MetaMaritalStatus').attr("data-code", marriageStatusCode);
                    $('#MetaMaritalStatus').attr("data-value", marriageStatusValue);

                    $("#MetaEducation_dummy").val(metaEducationValue);
                    $("#MetaMaritalStatus_dummy").val(marriageStatusValue);

                    initVal(metaAge, metaAgeEnd, metaUHeight, metaUHeightEnd);
                } else {
                    initVal("1985", "1985", "160", "160");
                }
            }
            function getMobileVerifyCodeCallback(ret) {
                dszdy.UI.Tip(ret.message);
                if (ret.status == "SUCCESS") {
                    var curCount = 120;
                    $('.js-getcode').attr("disabled", "true");
                    $('.js-getcode').text("剩下" + curCount + "秒");
                    var InterValObj = window.setInterval(SetRemainTime, 1000);
                    function SetRemainTime() {
                        if (curCount == 0) {
                            window.clearInterval(InterValObj);//停止计时器
                            $('.js-getcode').removeAttr("disabled");//启用按钮
                            $('.js-getcode').text("重新获取");
                        }
                        else {
                            curCount--;
                            $('.js-getcode').text("剩下" + curCount + "秒");
                        }
                    }
                }
            }
            function getAvatarListCallback(ret, avatarIsExistCallback) {
                if(ret.status === "SUCCESS") {
                    var obj = ret.data;
                    for (var avatarType in obj) {
                        var buttonTag;
                        var avatar;
                        if (obj[avatarType] == null)
                            continue;
                        if (avatarType === "normal") {
                            avatarIsExistCallback(true);
                            buttonTag = "1";
                        }
                        if (avatarType === "proB")
                            buttonTag = "2";
                        if (avatarType === "proC")
                            buttonTag = "3";
                        if (avatarType === "proD")
                            buttonTag = "4";
                        avatar = obj[avatarType].fileHost + "/200/200/" + obj[avatarType].fileName + obj[avatarType].fileType;
                        $("#button_upload" + buttonTag)
                            .attr("style", "background-image: url(" + avatar + ");")
                            .prev().removeClass("hide");
                    }
                }

            }
            function removeAvatarCallback(ret, obj) {
                dszdy.UI.Tip(ret.message);
                if (ret.status == "SUCCESS") {
                    obj.parent().addClass("hide");
                    obj.parent().next().attr("style", "background-image: url(static/img/empty.png);");
                }
            }
            return {
                login: function (mobile, password) {
                    dszdy.DAO.User.login(mobile, password, loginCallback);
                },
                getUserInfo: function () {
                    dszdy.DAO.User.getUserInfo(getUserInfoCallback);
                },
                getUserInfoOther: function () {
                    dszdy.DAO.User.getUserInfoOther(getUserInfoOtherCallback);
                },
                register: function (mobile, password, code) {
                    dszdy.DAO.User.register(mobile, password, code, registerCallback);
                },
                logout: function () {
                    dszdy.DAO.User.logout(logoutCallback);
                },
                editProfile: function (json) {
                    dszdy.DAO.User.editProfile(json, editProfileCallback);
                },
                updateOtherProfile: function (json) {
                    dszdy.DAO.User.updateOtherProfile(json, updateOtherProfileCallback);
                },
                getUserMateInfoProfile: function () {
                    dszdy.DAO.User.getUserMateInfoProfile(getUserMateInfoProfileCallback);
                },
                getMobileVerifyCode: function (mobile) {
                    dszdy.DAO.User.getMobileVerifyCode(mobile, getMobileVerifyCodeCallback);
                },
                getAvatarList: function (avatarIsExistCallback) {
                    dszdy.DAO.User.getAvatarList(avatarIsExistCallback, getAvatarListCallback);
                },
                removeAvatar: function (id, obj) {
                    dszdy.DAO.User.removeAvatar(id, obj, removeAvatarCallback);
                }
            }
        })(),
        Token: (function () {
            function getTokenCodeCallback(ret) {
                if (ret.status === "SUCCESS") {
                    var expire= new Date();
                    expire.setTime(expire.getTime() + (15*24*60*60*100));
                    $.cookie("__dstoken", ret.data, {
                        path : '/',
                        expires : expire
                    });
                }
            }
            function getChatTokenCodeCallback(ret) {
                if (ret.status === "SUCCESS") {
                    var expire= new Date();
                    expire.setTime(expire.getTime() + (7*24*60*60*100));
                    $.cookie("__chatuid", ret.data, {
                        path : '/',
                        expires : expire
                    });
                }
            }
            return {
                getTokenCode: function () {
                    dszdy.DAO.Token.getTokenCode(getTokenCodeCallback);
                },
                getChatTokenCode: function (callback) {
                    dszdy.DAO.Token.getChatTokenCode(callback);
                }
            }
        })(),
        Configuration: (function () {
            function getEducationListCallback(ret) {
                if (ret.status === "SUCCESS") {
                    var html = "";
                    $.each(ret.data.value, function (index, value) {
                        html += "<option value=\"" + value.code + "\">" + value.value + "</option>";
                    });
                    $('#Education').append(html);
                }
            }
            function configurationCallback(ret) {
                if (ret.status != "SUCCESS") {
                    return;
                }
                var html = "";
                $.each(ret.data.value, function (index, value) {
                    html += "<option value=\"" + value.code + "\">" + value.value + "</option>";
                });
                if (ret.data.type === 1) {
                    $('#StartSign').append(html);
                }
                if (ret.data.type === 2) {
                    $('#Profession').append(html);
                }
                if (ret.data.type === 3) {
                    $('#Income').append(html);
                }
                if (ret.data.type === 4) {
                    $('#MaritalStatus').append(html);
                }
            }
            function getMetaEducationListCallback(ret) {
                if (ret.status === "SUCCESS") {
                    var html = "";
                    $.each(ret.data.value, function (index, value) {
                        if (index === ret.data.value.length - 1) {
                            html += "<option value=\"" + value.code + "\">" + value.value + "</option>";
                        } else {
                            html += "<option value=\"" + value.code + "\">" + value.value + "以上" + "</option>";
                        }
                    });
                    $('#MetaEducation').append(html);
                }
            }

            function getMetaMaritalStatusListCallback(ret) {
                if (ret.status === "SUCCESS") {
                    var html = "";
                    $.each(ret.data.value, function (index, value) {
                        if (index === 0) {
                            html += "<option value=\"" + value.code + "\">" + "未婚" + "</option>";
                        } else {
                            html += "<option value=\"" + value.code + "\">" + value.value + "</option>";
                        }
                    });
                    $('#MetaMaritalStatus').append(html);
                }
            }
            return {
                getEducationList: function () {
                    dszdy.DAO.Configuration.getEducationList(getEducationListCallback);
                },
                getConstellationList: function () {
                    dszdy.DAO.Configuration.getConstellationList(configurationCallback);
                },
                getProfessionList: function () {
                    dszdy.DAO.Configuration.getProfessionList(configurationCallback);
                },
                getIncomeList: function () {
                    dszdy.DAO.Configuration.getIncomeList(configurationCallback);
                },
                getMaritalStatusList: function () {
                    dszdy.DAO.Configuration.getMaritalStatusList(configurationCallback);
                },
                getMetaEducationList: function () {
                    dszdy.DAO.Configuration.getMetaEducationList(getMetaEducationListCallback)
                },
                getMetaMaritalStatusList: function () {
                    dszdy.DAO.Configuration.getMetaMaritalStatusList(getMetaMaritalStatusListCallback);
                }
            }
        })(),
        Address: (function () {
            return {
                getAddressListBath: function (param, callback) {
                    dszdy.DAO.Address.getAddressListBath(param, callback);
                }
            }
        })(),
        Dynamic: (function() {
            function postDynamicCallback(ret) {
                dszdy.UI.loadingReview(false);
                if (ret.status === "SUCCESS") {
                    dszdy.UI.Tip("发布成功");
                    setTimeout(function () { document.location.href = "singletons.html"; }, 1000);
                } else {
                    dszdy.UI.Tip(ret.message);
                }
            }
            function praiseDynamicCallback(obj, ret) {
                dszdy.UI.Tip(ret.message);
                if (ret.status === "SUCCESS") {
                    var val = $("span", obj);
                    var praise = val.text();
                    praise = parseInt(praise) + 1;
                    val.text(praise);
                }
            }
            return {
                post: function post(param) {
                    dszdy.DAO.Dynamic.post(param, postDynamicCallback);
                },
                praise: function praise(param, obj) {
                    dszdy.DAO.Dynamic.praise(param, obj, praiseDynamicCallback);
                }
            }
        })()
    };

    dszdy.UI = {
        LazyLoad: {
            Run: function (_place) {
                var place = "../images/empty.png";
                if (typeof _place != "undefined" || _place != null) {
                    place = _place;
                }
                //延迟加载图片
                if ($('.lazy').length > 0) {
                    $("img.lazy").lazyload({ placeholder: place, effect: "fadeIn" });
                }
            }
        },
        Tip: function (txt, fun) {
            $('.tusi').remove();
            var div = $('<div style="background-image: url(static/images/tip.png);max-width: 85%;min-height: 66px;min-width: 270px;position: absolute;left: -1000px;top: -1000px;text-align: center;border-radius:10px;"><span style="color: #ffffff;line-height: 66px;font-size: 23px;">' + txt + '</span></div>');
            $('body').append(div);
            div.css('zIndex', 9999999);
            div.css('left', parseInt(($(window).width() - div.width()) / 2));
            var top = parseInt($(window).scrollTop() + ($(window).height() - div.height()) / 2);
            div.css('top', top);
            setTimeout(function () {
                div.remove();
                if (fun) {
                    fun();
                }
            }, 2000);
        },
        LoadImg: function () {
            swal({ title: "", showConfirmButton: false, imageUrl: "static/images/ajax_loading.gif" });
            $(".sa-custom").height("40");
        },
        loading: function (txt) {
            if (txt === false) {
                if ($(".qp_lodediv").length > 0)
                    $('.qp_lodediv').remove();
            } else {
                if ($(".qp_lodediv").length > 0)
                    $('.qp_lodediv').remove();
                var div = $('<div class="qp_lodediv" style="background: url(static/images/loadb.png);width: 269px;height: 107px;position: absolute;left: -1000px;top: -1000px;text-align: center;"><span style="color: #ffffff;line-height: 107px;font-size: 23px; white-space: nowrap;">&nbsp;&nbsp;&nbsp;<img src="static/images/ajax_loading.gif" style="vertical-align: middle;"/>&nbsp;&nbsp;' + txt + '</span></div>');
                $('body').append(div);
                div.css('zIndex', 9999999);
                div.css('left', parseInt(($(window).width() - div.width()) / 2));
                var top = parseInt($(window).scrollTop() + ($(window).height() - div.height()) / 2);
                div.css('top', top);
            }
        },
        loadingReview: function (txt) {
            if (txt === false) {
                $(".fixedloading").removeClass("loadingcss3");
                $(".fixedloading").fadeOut(300);
                $(".fixedloadingbg").fadeOut(300);
            } else {
                if ($("#loadingcss3").length == 0) {
                    var div = $(' <div id="loadingcss3" class="fixedloading"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></div><div class="fixedloadingbg"></div>');
                    $('body').append(div);
                }
                $(".fixedloading").removeClass("loadingcss3");
                $(".fixedloading").fadeOut(300);
                $(".fixedloadingbg").fadeOut(300);

                $(".fixedloading").addClass("loadingcss3");
                $(".fixedloading").fadeIn(300);
                $(".fixedloadingbg").fadeIn(300);
            }
        },
        modalBox: function (boxTit, boxCon, boxWidth, Isiframe, id) {
            var boxHtml = '<div class="modal fade" id="login" tabindex="-1" role="dialog" aria-labelledby="activityModalLabel">';
            boxHtml += '<div class="modal-dialog" style="width:' + boxWidth + 'px;"><div class="modal-content"><div class="modal-header">';
            boxHtml += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
            boxHtml += '<h4 class="modal-title" id="activityModalLabel">' + boxTit + '</h4></div><div class="modal-body">';
            if (Isiframe) {
                boxHtml += '<iframe width="100%" id="IframeMain" src="' + boxCon + '" scrolling="no" frameborder="0"></iframe>';
            } else {
                boxHtml += boxCon;
            }
            boxHtml += '</div></div></div></div>';
            $("#login").length > 0 && $("#login").remove(),
                $("body").append(boxHtml),
                $("#login").modal({ backdrop: 'static', keyboard: false });
            $('#login').on('hide.bs.modal', function () {
                if (typeof (int) != "undefined" && int != undefined) {
                    //CloseTime();
                }
            });
        },
        alert: function (a, t) {
            function d(a) {
                setTimeout(function () {
                    a.fadeOut(function () {
                        a.parent().fadeOut(function () {
                            $(this).remove();
                        });
                    })
                }, 2000)
            }

            var Icons = {
                success: 'glyphicon glyphicon-ok-sign',
                error: 'glyphicon glyphicon-remove-sign',
                warning: 'glyphicon glyphicon-info-sign',
                question: 'glyphicon glyphicon-question-sign'
            };
            var icon = '';
            if (t == "success") {
                icon = Icons.success;
            } else if (t == "error") {
                icon = Icons.error;
            } else if (t == "warning") {
                icon = Icons.warning;
            } else if (t == "question") {
                icon = Icons.question;
            }
            if ($("#alert_bg").length) {
                $("#alert_bg").remove();
            }
            var e = window.top.document, f = e.createElement("div"), s = '<i class="' + icon + '"></i><span>' + a + '</span>';
            f.setAttribute("id", "alert_bg"), e.body.appendChild(f);
            var g = e.createElement("div");
            g.setAttribute("id", "alert_content"), f.appendChild(g), $(g).html(s).fadeIn(function () {
                d($(this));
            });
        },
        confirm: function (a, b, c, d, e) {
            if ($("#confirm_bg").length) {
                $("#confirm_bg").remove();
            }
            if ($("#alert_bg").length) {
                $("#alert_bg").remove();
            }
            var f = document, g = f.createElement("div");
            g.setAttribute("id", "confirm_bg"), f.body.appendChild(g);
            var h = f.createElement("div");
            h.setAttribute("id", "confirm_content"), g.appendChild(h);
            var i = $("#confirm_content"), j = "";
            j = j + "<div id='confirm_text'>" + a + "</div>", j += "<div id='confirm_btnW'>", c ? (j = j + "<div id='confirm_btnA'>" + b + "</div>", j = j + "<div id='confirm_btnB'>" + c + "</div>") : j = j + "<div id='confirm_btnA' style='width:100%;border-right:none'>" + b + "</div>", j += "</div>", i.html(j).fadeIn(), $("#confirm_btnA").bind("click", function () {
                e && e(), i.fadeOut(), $("#confirm_bg").fadeOut(function () {
                    $(this).remove()
                })
            }), c && $("#confirm_btnB").bind("click", function () {
                d && d(), i.fadeOut(), $("#confirm_bg").fadeOut(function () {
                    $(this).remove()
                })
            })
        },
        modalLogin: function (locationhref, datatype) {
            var boxHtml = '<div class="login-modal"  id="form-login"><div class="form-horizontal"><div class="form-group"><div class="col-sm-12"><div class="input-group"><div class="input-group-addon"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div><input type="text" id="UserName" name="UserName" class="form-control" placeholder="邮箱/手机"></div></div></div><div class="form-group"><div class="col-sm-12"><div class="input-group"><div class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></div><input type="password" id="UserPwd" name="UserPwd" class="form-control" placeholder="密码"></div></div></div><div class="form-group group-btm"><div class="col-sm-12"><button type="button" class="btn btn-primary btn-block js-modal-login" id="login-btn" data-type="' + datatype + '">登 录</button><div class="clearfix box-btm"><span class="pull-right">没有帐号?<a href="/en/reg.aspx">立即注册</a></span><div class="checkbox-wrap"><label><a href="/en/findPassword.aspx">忘记密码？</a></label></div></div></div></div></div></div>';
            boxHtml += '<div class="l-l panel-body"><ul class="third-login"><li><a href="/love/3rd_pages/tsina/SinaWeiboLoginTransfer.aspx?source=pc" class="l-wx"><i class="sp">　</i><span> 微博登录</span></a></li><li><a href="/love/3rd_pages/qq/QQLoginTransfer.aspx?source=pc" class="l-qq"><i class="sp">　</i><span> QQ登录</span></a></li></ul></div>';
            dszdy.UI.modalBox("用户登录", boxHtml, "475");
            EmailMatch($("input[name='UserName']")).setEmailList(emailList).run(function () { $("input[name='UserPwd']").focus(); });
            $('input[name="UserPwd"]').keydown(function (event) {
                if (event.keyCode == 13)
                    if ($("#login-btn").length > 0) {
                        $('#login-btn').click();
                    }
                    else {
                        $("#reg-btn").click();
                    }

            });
            $(document).on("click", ".js-modal-login", function () {
                //登录
                function user_login() {
                    var UserName = $("#form-login input[name='UserName']").val();
                    var UserPwd = $("#form-login input[name='UserPwd']").val();
                    if (!UserName) {
                        dszdy.UI.alert("请填写登录账号", "warning");
                        return false;
                    }
                    if (!UserPwd) {
                        dszdy.UI.alert("请填写登录密码", "warning");
                        return false;
                    }
                    dszdy.UI.loading_bg("登录中");
                    $.api("auth_set_session", [UserName, UserPwd], user_login_callback, "user_auth");
                    //登录回调
                    function user_login_callback(return_data) {
                        dszdy.UI.loading_bg(false);
                        //登录成功后记录cookie
                        if (return_data[0] == "SUCCESS") {
                            var org = GET["org"];

                            if (org != undefined && org != "") {
                                window.location.href = org;
                            }
                            else if (typeof (locationhref) == "undefined") {
                                location.reload();
                            }
                            else if (locationhref != '') {
                                window.location.href = locationhref;

                            } else if (locationhref == '') {
                                location.reload();
                            }
                        }
                        else {
                            dszdy.UI.alert(return_data[0], "error");
                        }
                    }
                }
                user_login();
            });
        },
        formatDate: function (date) {
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m < 10 ? '0' + m : m;
            var d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            return y + '-' + m + '-' + d;
        },
        getTime: function () {
            var date = new Date();
            var h = date.getHours();
            var m = date.getMinutes();
            m = m < 10 ? '0' + m : m;
            h = h < 10 ? ('0' + h) : h;
            return h + ':' + m;
        },
        formatTimestamp: function (timestamp) {
            var date = new Date();
            date.setTime(timestamp * 1000);
            var h = date.getHours();
            var m = date.getMinutes();
            m = m < 10 ? '0' + m : m;
            h = h < 10 ? ('0' + h) : h;
            return h + ':' + m;
        },
        formatTimestampLong: function (timestamp) {
            var date = new Date();
            date.setTime(timestamp * 1000);
            var m = date.getMonth() + 1;
            var h = date.getHours();
            var i = date.getMinutes();
            var d = date.getDate();
            h = h < 10 ? ('0' + h) : h;
            i = i < 10 ? ('0' + i) : i;
            return m + '月' + d + "日 " + h + ':' + i;
        },
        formatTimestampLongDet: function (timestamp) {
            var date = new Date();
            date.setTime(timestamp * 1000);
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            var h = date.getHours();
            var i = date.getMinutes();
            h = h < 10 ? ('0' + h) : h;
            i = i < 10 ? ('0' + i) : i;
            return y + '年' + m + '月' + d + "日 " + h + ':' + i;
        },
        formatDateLong: function () {
            var in_date = new Date();

            var day_str = in_date.getDate();
            day_str = day_str < 10 ? "0" + day_str : day_str;

            var month_str = in_date.getMonth() + 1;
            month_str = month_str < 10 ? ("0" + month_str) : month_str;


            var year_str = in_date.getFullYear().toString().substr(2, 2);

            var hour_str = in_date.getHours();
            hour_str = hour_str < 10 ? "0" + hour_str : hour_str;

            var minute_str = in_date.getMinutes();
            minute_str = minute_str < 10 ? "0" + minute_str : minute_str;

            var second_str = in_date.getSeconds();
            second_str = second_str < 10 ? "0" + second_str : second_str;

            return in_date.getFullYear() + "-" + month_str + "-" + day_str + " " + hour_str + ":" + minute_str + ":" + second_str;
        },
        formatTimestampLongHex: function (timestamp) {
            var in_date = new Date();
            in_date.setTime(timestamp * 1000);

            var day_str = in_date.getDate();
            day_str = day_str < 10 ? "0" + day_str : day_str;

            var month_str = in_date.getMonth() + 1;
            month_str = month_str < 10 ? ("0" + month_str) : month_str;


            var year_str = in_date.getFullYear().toString().substr(2, 2);

            var hour_str = in_date.getHours();
            hour_str = hour_str < 10 ? "0" + hour_str : hour_str;

            var minute_str = in_date.getMinutes();
            minute_str = minute_str < 10 ? "0" + minute_str : minute_str;

            var second_str = in_date.getSeconds();
            second_str = second_str < 10 ? "0" + second_str : second_str;

            return in_date.getFullYear() + "-" + month_str + "-" + day_str + " " + hour_str + ":" + minute_str + ":" + second_str;
        },
        SetClipboard: {
            copy: function (maintext) {
                debugger
                if (window.clipboardData) {
                    alert("复制成功！");
                    return (window.clipboardData.setData("Text", maintext));
                }
                else if (window.netscape) {
                    try {
                        netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                    }
                    catch (e) {
                        alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将 'signed.applets.codebase_principal_support'设置为'true'");
                    };
                    var clip = Components.classes['@mozilla.org/widget/clipboard;1'].
                    createInstance(Components.interfaces.nsIClipboard);
                    if (!clip) return;
                    var trans = Components.classes['@mozilla.org/widget/transferable;1'].
                    createInstance(Components.interfaces.nsITransferable);
                    if (!trans) return;
                    trans.addDataFlavor('text/unicode');
                    var str = new Object();
                    var len = new Object();
                    var str = Components.classes["@mozilla.org/supports-string;1"].
                    createInstance(Components.interfaces.nsISupportsString);
                    var copytext = maintext;
                    str.data = copytext;
                    trans.setTransferData("text/unicode", str, copytext.length * 2);
                    var clipid = Components.interfaces.nsIClipboard;
                    if (!clip) return false;
                    clip.setData(trans, null, clipid.kGlobalClipboard);
                }
                alert("复制成功！");
                return false;
            },
            bindEvent: function (btnobj, iptobj) {
                if (btnobj.length == 0 || iptobj.length == 0) {
                    return false;
                }
                btnobj.bind("click", function () {
                    var content = iptobj.val();
                    dszdy.UI.SetClipboard.copy(content);
                });
            }
        },
        isWeiXin: function () {
            var ua = window.navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == 'micromessenger') {
                return true;
            } else {
                return false;
            }
        }
    }

    dszdy.Page.HeaderFooter = {
        SearchBar: (function () {

            function _SearchKeyword(keyword) {
                if (typeof keyword != "string") {
                    return false;
                }
                keyword = keyword.trim();
                if (keyword == "" || keyword == "请您输入想要搜索的关键词") {
                    return false;
                }
                keyword = keyword.replace("'", " ");
                keyword = keyword.replace('"', ' ');
                keyword = keyword.replace("script", " ");
                keyword = keyword.replace("?", " ");
                keyword = keyword.replace("\?", " ");
                keyword = keyword.replace("\/", " ");
                keyword = keyword.replace("\\", " ");
                keyword = keyword.replace("<", "&lt;");
                keyword = keyword.replace(">", "&gt;");
                keyword = encodeURIComponent(keyword);
                window.location.href = "list.html?kw=" + keyword + _SetTimeStamp();
            }
            function _SetTimeStamp() {
                var t = new Date();
                var year = t.getUTCFullYear() + "";
                var month = t.getUTCMonth() + 1 < 10 ? "0" + (t.getUTCMonth() + 1) : (t.getUTCMonth() + 1) + "";
                var day = t.getUTCDate() < 10 ? "0" + t.getUTCDate() : t.getUTCDate() + "";
                var hour = t.getUTCHours() < 10 ? "0" + t.getUTCHours() : t.getUTCHours() + "";
                var minute = t.getUTCMinutes() < 10 ? "0" + t.getUTCMinutes() : t.getUTCMinutes() + "";
                var ts = "&ts=" + year + month + day + hour + minute;
                return ts;
            }
            return function (inputobject, buttonobject) {
                //键盘回车事件
                inputobject.keypress(function (e) {
                    e = e || event;
                    if (e.keyCode == 13) {
                        _SearchKeyword($(this).val());
                        return false;
                    }
                });
                //按钮点击事件
                buttonobject.bind("click", function () {
                    _SearchKeyword(inputobject.val());
                });

                return {
                    SearchKeyword: _SearchKeyword
                };
            }
        })()
    }

    dszdy._INSTALL();

})();

$(function () {
    // //全选按钮事件绑定
    // $("#ckb-prod-all").on("click", function () {
    //     if ($("#ckb-prod-all").prop("checked")) {
    //         $("input[id^='ckb-prod']").prop("checked", true);
    //     }
    //     else {
    //         $("input[id^='ckb-prod']").prop("checked", false);
    //     }
    // });
    // $(".js-btn-search").on("click", function () {
    //     SearchKeyword();
    // });
    // //搜索
    // function SearchKeyword() {
    //     var _searchEng = dszdy.Page.HeaderFooter.SearchBar($(".js-btn-search"), $(".form-control"));
    //     var keyword = $(".form-control").val();
    //     _searchEng.SearchKeyword(keyword);
    // };
    $(".lv-icon >span").on("click", function () {
        history.go(-1);
    });

    $.cookie("cookie_enable", "1");
    var cookieEnable = window.navigator.cookieEnabled || $.cookie("cookie_enable") == "1";
    if (!cookieEnable) {
        sweetAlert("请打开微信浏览器Cookie访问权限!");
    }
    if (dstoken == undefined && cookieEnable) {
        dszdy.Business.Token.getTokenCode();
    }
});