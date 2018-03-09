$(function () {
    //登录校验
    $('#form-login').bootstrapValidator({
        message: '此项为必填项',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: { message: '请填写账号' },
                    regexp: {
                        regexp: /^(17[0-9]|13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i,
                        message: '请输入手机号'
                    }
                }
            },
            userpwd: {
                validators: {
                    notEmpty: { message: '请填写密码' },
                    stringLength: { min: 3, max: 25, message: '密码的长度应在3-25位之间' },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '字母、数字和下划线'
                    },
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        user_login();
    });

    //注册账号
    $('#form-reg').bootstrapValidator({
        message: '此项为必填项',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: { message: '请填写账号' },
                    enabled: false,
                    regexp: {
                        regexp: /^(17[0-9]|13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i,
                        message: '请输入手机号'
                    }
                }
            },
            code: {
                validators: {
                    notEmpty: { message: '请输入短信验证码' }
                }
            },
            Reference: {
                validators: {
                    notEmpty: { message: '请输入推荐人手机号' },
                    regexp: {
                        regexp: /^(17[0-9]|13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i,
                        message: '请输入手机号'
                    },
                    different: {
                        field: 'username',
                        message: '推荐人手机号不能和账号一样'
                    }
                }
            },
            userpwd: {
                validators: {
                    notEmpty: { message: '请填写密码' },
                    stringLength: { min: 3, max: 25, message: '密码的长度应在3-25位之间' },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '字母、数字和下划线'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        register();
    });

    //根据手机号找回密码
    $('#form-findPassword').bootstrapValidator({
        message: '此项为必填项',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: { message: '请填写账号' },
                    enabled: false,
                    regexp: {
                        regexp: /^(17[0-9]|13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i,
                        message: '请输入手机号'
                    }
                }
            },
            findcode: {
                validators: {
                    notEmpty: { message: '请输入短信验证码' }
                }
            },
            userpwd: {
                validators: {
                    notEmpty: { message: '请填写密码' },
                    stringLength: { min: 3, max: 25, message: '密码的长度应在3-25位之间' },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '字母、数字和下划线'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        user_findPassword();
    });

    //注册第一步：必填基本信息
    $('#form-reginfo1').bootstrapValidator({
        message: '此项为必填项',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            TrueName: {
                validators: {
                    notEmpty: { message: '请填写真实姓名' },
                    stringLength: { min: 2, max: 10, message: '姓名的长度应在2-10位之间' }
                }
            },
            Remark: {
                validators: {
                    stringLength: { max: 100, message: '最多输入100个字' },
                }
            },
            UHeight: {
                validators: {
                    notEmpty: { message: '请填数值' },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '数字'
                    },
                    lessThan: {
                        value: 200,
                        inclusive: true,
                        message: '身高不能大于200'
                    },
                    greaterThan: {
                        value: 150,
                        inclusive: true,
                        message: '身高不能小于150'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        submit_editstep1_user();
    });

    //注册基本信息
    $('#form-reginfo2').bootstrapValidator({
        message: '此项为必填项',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            Company: {
                validators: {
                    stringLength: { max: 20, message: '最多输入20个字符' }
                }
            },
            SubArea: {
                validators: {
                    stringLength: { max: 20, message: '最多输入20个字符' }
                }
            },
            Remark: {
                validators: {
                    stringLength: { max: 100, message: '最多输入100个字' },
                }
            },
            UWeight: {
                validators: {
                    notEmpty: { message: '请填写体重' },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '数字'
                    },
                    lessThan: {
                        value: 120,
                        inclusive: true,
                        message: '体重不能大于120'
                    },
                    greaterThan: {
                        value: 35,
                        inclusive: true,
                        message: '体重不能小于35'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        sub_updateOtherProfile();
    });

    //注册基本信息
    $('#form-reginfo').bootstrapValidator({
        message: '此项为必填项',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            TrueName: {
                validators: {
                    notEmpty: { message: '请填写真实姓名' },
                    stringLength: { min: 2, max: 10, message: '姓名的长度应在2-10位之间' }
                }
            },
            Remark: {
                validators: {
                    stringLength: { max: 100, message: '最多输入100个字' },
                }
            },
            UHeight: {
                validators: {
                    notEmpty: { message: '请填数值' },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '数字'
                    },
                    lessThan: {
                        value: 200,
                        inclusive: true,
                        message: '身高不能大于200'
                    },
                    greaterThan: {
                        value: 150,
                        inclusive: true,
                        message: '身高不能小于150'
                    }
                }
            },
            UWeight: {
                validators: {
                    notEmpty: { message: '请填数值' },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '数字'
                    },
                    lessThan: {
                        value: 120,
                        inclusive: true,
                        message: '体重不能大于120'
                    },
                    greaterThan: {
                        value: 35,
                        inclusive: true,
                        message: '体重不能小于35'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        submit_edit_user();
    });

    //注册择偶信息
    $('#form-regobj').bootstrapValidator({
        message: '此项为必填项',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            MetaAge: {
                validators: {
                    callback: {
                        message: '选择大于前一个输入框的值',
                        callback: function (value, validator) {
                            var big = $("select[name='MetaAgeEnd']").val();
                            if (value > big) {
                                $("select[name='MetaAgeEnd']").val(value);
                            }
                            return true;
                        }
                    }
                }
            },
            MetaAgeEnd: {
                validators: {
                    callback: {
                        message: '选择大于前一个输入框的值',
                        callback: function (value, validator) {
                            if (value < parseInt($("select[name='MetaAge']").val()))
                                return false;
                            else {
                                return true;
                            }
                        }
                    }
                }
            },
            MetaUHeight: {
                validators: {
                    callback: {
                        message: '选择大于前一个输入框的值',
                        callback: function (value, validator) {
                            var big = $("select[name='MetaUHeightEnd']").val();
                            if (value > big) {
                                $("select[name='MetaUHeightEnd']").val(value);
                            }
                            return true;
                        }
                    }
                }
            },
            MetaUHeightEnd: {
                validators: {
                    callback: {
                        message: '选择大于前一个输入框的值',
                        callback: function (value, validator) {
                            if (value < parseInt($("select[name='MetaUHeight']").val()))
                                return false;
                            else {
                                return true;
                            }
                        }
                    }
                }
            },
            MetaEducation: {
                validators: {
                    notEmpty: { message: '请选择学历' }
                }
            },
            MetaMaritalStatus: {
                validators: {
                    notEmpty: { message: '请选择状况' }
                }
            },
            MetaIncome: {
               validators: {
                   notEmpty: { message: '请选择收入' },
                   callback: {
                       message: 'Expired',
                       callback: function(value, validator) {
                           value = parseInt(value, 10);
                           var month        = validator.getFieldElements('expMonth').val(),
                               currentMonth = new Date().getMonth() + 1,
                               currentYear  = new Date().getFullYear();
                           if (value < currentYear || value > currentYear + 10) {
                               return false;
                           }
                           if (month == '') {
                               return false;
                           }
                           month = parseInt(month, 10);
                           if (value > currentYear || (value == currentYear && month > currentMonth)) {
                               validator.updateStatus('expMonth', 'VALID');
                               return true;
                           } else {
                               return false;
                           }
                       }
                   }
               }
            }

        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        submit_edit_user_meta();
    });


    $('#login-btn').on("click", function () {
        $("#form-login").bootstrapValidator('validate');
    });
    $('#reg-btn').on("click", function () {
        $("#form-reg").bootstrapValidator('validate');
    });

    //获取短信验证码
    $('.js-getcode').on("click", function () {
        var mobile = $("#username").val();
        if (mobile == "") {
            $("#username").focus();
            dszdy.UI.Tip("请输入手机号");
            return false;
        }
        else if (!/^(17[0-9]|13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i.test(mobile)) {
            dszdy.UI.Tip("手机号码格式错误");
            return false;
        }
        else {
            dszdy.Business.User.getMobileVerifyCode(mobile);
        }
    });

    //忘记密码，获取短信验证码
    $('.js-findPassword-getcode').on("click", function () {
        var mobile = $("#username").val();
        if (mobile == "") {
            $("#username").focus();
            dszdy.UI.Tip("请输入手机号");
            return false;
        }
        else if (!/^(17[0-9]|13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i.test(mobile)) {
            dszdy.UI.Tip("手机号码格式错误");
            return false;
        }
        else {
            dszdy.Business.User.getMobileVerifyCode(mobile);
        }
    });

    $('.js-reginfo').on("click", function () {
        $("#form-reginfo").bootstrapValidator('validate');
    });
    $('.js-reginfo1').on("click", function () {
        $("#form-reginfo1").bootstrapValidator('validate');
    });
    $('.js-reginfo2').on("click", function () {
        $("#form-reginfo2").bootstrapValidator('validate');
    });
    $('.js-regobj').on("click", function () {
        $("#form-regobj").bootstrapValidator('validate');
    });
    $('.js-findPassword').on("click", function () {
        $("#form-findPassword").bootstrapValidator('validate');
    });


    //回车执行
    $('input[name="userpwd"]').keypress(function (event) {
        if (event.keyCode == 13)
            if ($("#login-btn").length > 0) {
                $('#login-btn').click();
            }
            else {
                $("#reg-btn").click();
            }

    });

    //登录
    function user_login() {
        var mobile = $("input[name='username']").val();
        var password = $("input[name='userpwd']").val();
        dszdy.Business.User.login(mobile, password);
    }

    //注册
    function register() {
        var mobile = $("#username").val();
        var password = $("#userpwd").val();
        var code = $("#code").val();
        dszdy.Business.User.register(mobile, password, code);
    }

    //注册第一步：
    function submit_editstep1_user() {
        if ($("input[name='BirthDate']").val() == "") {
            dszdy.UI.Tip("请选择出生日期");
            return;
        }
        dszdy.UI.LoadImg();
        var pcaname = '';
        if (_edit_addr_selection.SelectedProvinceName != null) {
            pcaname += _edit_addr_selection.SelectedProvinceName;
        }
        if (_edit_addr_selection.SelectedCityName != null) {
            pcaname += _edit_addr_selection.SelectedCityName;
        }
        var query_list = [
             $("input[name='TrueName']").val(),
            $("input[name='sex']:checked").val(),
           _edit_addr_selection.SelectedProvinceID,
            _edit_addr_selection.SelectedCityID,
           _edit_addr_selection.SelectedAreaID,
              $("input[name='BirthDate']").val(),
              $("input[name='UHeight']").val(),
              $("select[name='Education']").val(),
              pcaname
        ];
        $.api("webuser_editstep1_user_info", query_list, edit_user_callback, "user_auth");

        function edit_user_callback(return_data) {
            if (return_data[0] != "SUCCESS")
                return dszdy.UI.Tip(return_data[0]);
            var id = $(".js-reginfo").attr("data-id");
            if (id == 1) {
                dszdy.UI.Tip("修改成功");
                window.location.href = "/love/wap/mylove.aspx";
            }
            else {
                window.location.href = "/love/wap/reginfo2.aspx";
            }
        }
    }

    //注册第二步：
    function sub_updateOtherProfile() {

        var weight = parseInt($("input[name='UWeight']").val()); //体重
        var liveProvinceId = parseInt(_edit_addr_selection_work.SelectedProvinceID);
        var liveCityId = parseInt(_edit_addr_selection_work.SelectedCityID);
        var liveAreaId = parseInt(_edit_addr_selection_work.SelectedAreaID);

        var constellation = parseInt($('#StartSign').attr("data-code")); //星座code
        var constellationVal =  $('#StartSign').attr("data-value"); //星座value

        var profession = parseInt($('#Profession').attr("data-code")); //职业code
        var professionVal =  $("#Profession").attr("data-value"); //职业value

        var income = parseInt($('#Income').attr("data-code")); //年收入code
        var incomeVla = $("#Income").attr("data-value"); //年收入value

        var maritalStatus = parseInt($('#MaritalStatus').attr("data-code")); //婚姻code
        var maritalStatusVal =  $("#MaritalStatus").attr("data-value"); //婚姻value

        var workPosition = $("input[name='ZhiWei']").val(); //职位
        var company = $("input[name='Company']").val(); //单位
        var school = $("input[name='SubArea']").val(); //学校
        var hasCar = $("#cbIsCar").prop("checked") ? 1 : 0;
        var hasHome = $("#cbIsHome").prop("checked") ? 1 : 0;
        var familyStruct = $("input[name='FamilyStruct']").val(); //家庭结构
        var characterColor = parseInt($('input:radio[name="LikeColor"]:checked').val()); //性格颜色
        var interest = $("input[name='Interest']").val(); //爱好
        var selfIntroduction = $("textarea[name='Remark']").val(); //介绍

        if (liveProvinceId === 0 || liveCityId === 0 || liveAreaId === 0) {
            dszdy.UI.Tip("请选择城市");
            return;
        }

        if (constellation === -1 || constellationVal === "" || constellationVal === "请选择") {
            dszdy.UI.Tip("请选择星座");
            return;
        }

        if (profession === -1 || professionVal === "" || professionVal === "请选择") {
            dszdy.UI.Tip("请选择职业");
            return;
        }

        if (income === -1 || incomeVla === "" || incomeVla === "请选择") {
            dszdy.UI.Tip("请选择年收入");
            return;
        }

        if (school === "") {
            dszdy.UI.Tip("请选择学校");
            return;
        }

        if (maritalStatus === -1 || maritalStatusVal === "" || maritalStatusVal === "请选择") {
            dszdy.UI.Tip("请选择婚姻状态");
            return;
        }

        var param = {
            "weight": weight,
            "liveProvinceId": liveProvinceId,
            "liveCityId": liveCityId,
            "liveAreaId": liveAreaId,
            "constellation": constellation,
            "profession": profession,
            "income": income,
            "hasCar": hasCar,
            "hasHome": hasHome,
            "maritalStatus": maritalStatus,
            "characterColor": characterColor,
            "workPosition": workPosition,
            "company": company,
            "school": school,
            "familyStruct": familyStruct,
            "interest": interest,
            "selfIntroduction": selfIntroduction
        };

        dszdy.UI.LoadImg();

        dszdy.Business.User.updateOtherProfile(param);
        //$.api("updateOtherProfile", param, edit_user_callback, "user");

        // function edit_user_callback(ret) {
        //     dszdy.UI.Tip(ret.message);
        //     if (ret.status === "SUCCESS") {
        //         window.location.href = "home.html";
        //     }
        // }
    }

    // //注册基本资料函数
    // function submit_edit_user() {
    //     if ($("input[name='BirthDate']").val() == "") {
    //         dszdy.UI.Tip("请选择出生日期");
    //         return;
    //     }
    //     dszdy.UI.LoadImg();
    //     var pcaname = '';
    //     if (_edit_addr_selection.SelectedProvinceName != null) {
    //         pcaname += _edit_addr_selection.SelectedProvinceName;
    //     }
    //     if (_edit_addr_selection.SelectedCityName != null) {
    //         pcaname += _edit_addr_selection.SelectedCityName;
    //     }
    //
    //     var query_list = [
    //         '',
    //          $("input[name='TrueName']").val(),
    //         "2",
    //         $("input[name='sex']:checked").val(),
    //        '',
    //           $("input[name='Mobile']").val(),
    //         '',
    //        _edit_addr_selection.SelectedProvinceID,//addr_select.SelectedProvinceID,
    //         _edit_addr_selection.SelectedCityID, //addr_select.SelectedCityID,
    //        _edit_addr_selection.SelectedAreaID, //addr_select.SelectedAreaID,
    //         $("select[name='Address']").val(), //addr_detail.val(),
    //         "", //postalcode.val(),
    //         "0",//会员类型
    //           $("input[name='BirthDate']").val(),
    //           $("select[name='StartSign']").val(),
    //           $("input[name='UHeight']").val(),
    //           $("input[name='UWeight']").val(),
    //           $("select[name='Education']").val(),
    //           $("input[name='Profession']").val(),
    //           $("select[name='Income']").val(),
    //           $("select[name='MaritalStatus']").val(),
    //           $("input[name='Company']").val(),
    //           $("textarea[name='Remark']").val(),
    //           $("#cbIsCar").prop("checked") ? "1" : "0",
    //           $("#cbIsHome").prop("checked") ? "1" : "0",
    //            _edit_addr_selection_work.SelectedProvinceID,//addr_select.SelectedProvinceID,
    //         _edit_addr_selection_work.SelectedCityID, //addr_select.SelectedCityID,
    //        _edit_addr_selection_work.SelectedAreaID, //addr_select.SelectedAreaID,
    //          $("input[name='FamilyStruct']").val(),
    //          $('input:radio[name="LikeColor"]:checked').val(),
    //          pcaname,
    //          $("input[name='Interest']").val()
    //     ];
    //
    //     $.api("webuser_edit_user_info", query_list, edit_user_callback, "user_auth");
    //
    //     function edit_user_callback(return_data) {
    //         if (return_data[0] != "SUCCESS")
    //             return dszdy.UI.Tip(return_data[0]);
    //         var id = $(".js-reginfo").attr("data-id");
    //         if (id == 1) {
    //             dszdy.UI.Tip("修改成功");
    //             window.location.href = "/love/wap/mylove.aspx";
    //         }
    //         else {
    //             window.location.href = "/love/wap/regobj.aspx";
    //         }
    //     }
    // }

    //注册择偶信息
    function submit_edit_user_meta() {
        if ($("#MetaAge_dummy").val() == "") {
            dszdy.UI.Tip("请选择最小年龄");
            return;
        }
        if ($("#MetaUHeight_dummy").val() == "") {
            dszdy.UI.Tip("请选择身高");
            return;
        }
        if ($("#MetaEducation_dummy").val() == "") {
            dszdy.UI.Tip("请选择学历");
            return;
        }
        dszdy.UI.LoadImg();
        var MetaMaritalStatus = '';
        $("[id*='Status']:checked").each(function (i, item) {
            if (0 == i) {
                MetaMaritalStatus = $(item).val();
            } else {
                MetaMaritalStatus += ("," + $(item).val());
            }
        });
        var splage = $("#MetaAge_dummy").val().split('-');
        var MetaAge = splage[0];
        var MetaAgeEnd = splage[1];
        splage = $("#MetaUHeight_dummy").val().split('-');
        var MetaUHeight = splage[0];
        var MetaUHeightEnd = splage[1];
        var query_list = [
              MetaAge,
             MetaUHeight,
              $("select[name='MetaEducation']").val(),
              MetaMaritalStatus,
              $("input[name='MetaIncome']").val(),
              MetaAgeEnd,
              MetaUHeightEnd

        ];
        $.api("webuser_edit_user_meta_info", query_list, edit_user_callback, "user_auth");

        function edit_user_callback(return_data) {
            if (return_data[0] != "SUCCESS")
                return dszdy.UI.Tip(return_data[0]);
            var id = $(".js-regobj").attr("data-id");
            if (id == 1) {
                dszdy.UI.Tip({ title: "修改成功", showConfirmButton: false });
                window.location.href = "/love/wap/mylove.aspx";
            }
            else {
                window.location.href = "/love/wap/gallery.aspx?UserId=" + return_data[1];
            }
        }
    }

    //忘记密码
    function user_findPassword() {
        dszdy.UI.LoadImg();
        var username = $("#username").val();
        var userpwd = $("#userpwd").val();
        var code = $("#findcode").val();
        $.api("auth_verify_password", [username, userpwd, code], auth_verify_password_callback, "user_auth");
    }

    //忘记密码回调
    function auth_verify_password_callback(return_data) {
        if (return_data[0] != "SUCCESS") {
            $("#userpwd").text("");
            dszdy.UI.Tip(return_data[0]);
            return false;
        }
        //找回登录
        window.location.href = "login.html";
    }
});
