layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    //加载页面数据
    var usersData = '';

    var pageSize = 5;
    var pageTotal = 0;

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/api/admin/getUserList",
        data: JSON.stringify({pageCode: 0, pageSize: pageSize}),
        dataType: "json",
        success: function (ret) {
            if (ret.status !== "SUCCESS") {
                return;
            }
            pageTotal = ret.data.pageTotal;
            usersData = ret.data.userInfoListPo;

            var hasLoaded = true;
            $(".users_content").html(renderDate(usersData));
            $('.users_list thead input[type="checkbox"]').prop("checked", false);
            form.render();

            laypage({
                cont: "page",
                pages: pageTotal,
                jump: function (obj) {
                    if (!hasLoaded) {
                        $.ajax({
                            type: "POST",
                            contentType: "application/json; charset=utf-8",
                            url: "/api/admin/getUserList",
                            data: JSON.stringify({pageCode: obj.curr - 1, pageSize: pageSize}),
                            dataType: "json",
                            success: function (ret) {
                                if (ret.status !== "SUCCESS") {
                                    return;
                                }
                                pageTotal = ret.data.pageTotal;
                                usersData = ret.data.userInfoListPo;

                                $(".users_content").html(renderDate(usersData));
                                $('.users_list thead input[type="checkbox"]').prop("checked", false);
                                form.render();
                            }
                        });
                    }
                    hasLoaded = false;
                }
            });

        }
    });

    //查询
    $(".search_btn").click(function () {
        var userArray = [];
        if ($(".search_input").val() != '') {
            var index = layer.msg('查询中，请稍候', {icon: 16, time: false, shade: 0.8});
            setTimeout(function () {
                $.ajax({
                    url: "../../json/usersList.json",
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        if (window.sessionStorage.getItem("addUser")) {
                            var addUser = window.sessionStorage.getItem("addUser");
                            usersData = JSON.parse(addUser).concat(data);
                        } else {
                            usersData = data;
                        }
                        for (var i = 0; i < usersData.length; i++) {
                            var usersStr = usersData[i];
                            var selectStr = $(".search_input").val();

                            function changeStr(data) {
                                var dataStr = '';
                                var showNum = data.split(eval("/" + selectStr + "/ig")).length - 1;
                                if (showNum > 1) {
                                    for (var j = 0; j < showNum; j++) {
                                        dataStr += data.split(eval("/" + selectStr + "/ig"))[j] + "<i style='color:#03c339;font-weight:bold;'>" + selectStr + "</i>";
                                    }
                                    dataStr += data.split(eval("/" + selectStr + "/ig"))[showNum];
                                    return dataStr;
                                } else {
                                    dataStr = data.split(eval("/" + selectStr + "/ig"))[0] + "<i style='color:#03c339;font-weight:bold;'>" + selectStr + "</i>" + data.split(eval("/" + selectStr + "/ig"))[1];
                                    return dataStr;
                                }
                            }

                            //用户名
                            if (usersStr.userName.indexOf(selectStr) > -1) {
                                usersStr["userName"] = changeStr(usersStr.userName);
                            }
                            //用户邮箱
                            if (usersStr.userEmail.indexOf(selectStr) > -1) {
                                usersStr["userEmail"] = changeStr(usersStr.userEmail);
                            }
                            //性别
                            if (usersStr.userSex.indexOf(selectStr) > -1) {
                                usersStr["userSex"] = changeStr(usersStr.userSex);
                            }
                            //会员等级
                            if (usersStr.userGrade.indexOf(selectStr) > -1) {
                                usersStr["userGrade"] = changeStr(usersStr.userGrade);
                            }
                            if (usersStr.userName.indexOf(selectStr) > -1 || usersStr.userEmail.indexOf(selectStr) > -1 || usersStr.userSex.indexOf(selectStr) > -1 || usersStr.userGrade.indexOf(selectStr) > -1) {
                                userArray.push(usersStr);
                            }
                        }
                        usersData = userArray;
                        usersList(usersData);
                    }
                })

                layer.close(index);
            }, 2000);
        } else {
            layer.msg("请输入需要查询的内容");
        }
    })

    //添加会员
    $(".usersAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加会员",
            type: 2,
            content: "addUser.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        })
        layui.layer.full(index);
    })

    //批量删除
    $(".batchDel").click(function () {
        var $checkbox = $('.users_list tbody input[type="checkbox"][name="checked"]');
        var $checked = $('.users_list tbody input[type="checkbox"][name="checked"]:checked');
        if ($checkbox.is(":checked")) {
            layer.confirm('确定删除选中的信息？', {icon: 3, title: '提示信息'}, function (index) {
                var index = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
                setTimeout(function () {
                    //删除数据
                    for (var j = 0; j < $checked.length; j++) {
                        for (var i = 0; i < usersData.length; i++) {
                            if (usersData[i].newsId == $checked.eq(j).parents("tr").find(".news_del").attr("data-id")) {
                                usersData.splice(i, 1);
                                usersList(usersData);
                            }
                        }
                    }
                    $('.users_list thead input[type="checkbox"]').prop("checked", false);
                    form.render();
                    layer.close(index);
                    layer.msg("删除成功");
                }, 2000);
            })
        } else {
            layer.msg("请选择需要删除的文章");
        }
    })

    //全选
    form.on('checkbox(allChoose)', function (data) {
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        child.each(function (index, item) {
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });

    //通过判断文章是否全部选中来确定全选按钮是否选中
    form.on("checkbox(choose)", function (data) {
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
        if (childChecked.length == child.length) {
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
        } else {
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
        }
        form.render('checkbox');
    })

    //操作
    $("body").on("click", ".users_edit", function () {  //编辑
        // layer.open({
        //     type: 1,
        //     shade: false,
        //     title: false, //不显示标题
        //     content:'<ul class="layer_notice" style="display: block;">\n' +
        //     '<li><a href="http://fly.layui.com/jie/16328/" target="_blank">1. layer 在 Chrome 61 下花屏问题的解决方案</a></li>\n' +
        //     '<li><a href="http://fly.layui.com/jie/2461.html" target="_blank">2. 关注 Layui 微信公众号，与我们更近距离交流</a></li>\n' +
        //     '<li><a href="http://fly.layui.com/jie/220.html" target="_blank">3. Star layer on Github，用她，就给她加个星啵</a></li>\n' +
        //     '</ul>', //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        //     cancel: function(){
        //         layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
        //     }
        // });
    })

    //操作
    $("body").on("click", ".add_flower", function () {  //编辑

    })
    function sendFlower(userId) {
        M.dialog10 = jqueryAlert({
            'title': '赠送鲜花',
            'content': '<p>请输入鲜花数量</p><div class="form-group"><input type="number" id="flower_content" class="form-control"/></div>',
            'width': "200",
            'modal': true,
            'contentTextAlign': 'center',
            'animateType': 'linear',
            'buttons': {
                '关闭': function () {
                    M.dialog10.close();
                },
                '确定': function () {

                    var flowerNum = $('#flower_content').val();
                    if (flowerNum === "") {
                        tips('请输入鲜花数量');
                        return;
                    }

                    $.api("sendFlower", {'type': 1, 'userId': userId, 'flowerNum': flowerNum}, function (ret) {
                        tips(ret.message);
                        if (ret.status === "SUCCESS") {
                            M.dialog10.close();
                        }
                    }, "admin");

                }
            }
        })
    }

    $("body").on("click", ".users_del", function () {  //删除
        var _this = $(this);
        layer.confirm('确定锁定此用户？', {icon: 3, title: '提示信息'}, function (index) {
            //_this.parents("tr").remove();
            for (var i = 0; i < usersData.length; i++) {
                if (usersData[i].usersId == _this.attr("data-id")) {
                    usersData.splice(i, 1);
                    usersList(usersData);
                }
            }
            layer.close(index);
        });
    })

    //渲染数据
    function renderDate(data) {
        var dataHtml = '';
        var sex = function (sexCode) {
            if (sexCode === 0) {
                return "女";
            }

            return "男";
        };

        var status = function (userStatus) {
            if (userStatus === 0) {
                return "正常"
            }
            return "已被封号"
        };

        var exp = function (isExp, vipType, balance) {
            if (isExp === 0 && vipType !== "普通会员") {
                return vipType + ' (次数 ' + balance + ' 到期 ' + formatTimestampLongDet(vipExp) + ')';
            }
            if (isExp === 1 && vipType !== "普通会员") {
                return vipType + ' (次数 ' + balance + ' 已过期 ' + formatTimestampLongDet(vipExp) + ')';
            }
            if (isExp === 1 && vipType === "普通会员") {
                return vipType;
            }
        };

        function nullData(data) {
            if (data === null || data === '' || data === undefined) {
                return "未填写";
            } else {
                return data;
            }
        }

        function formatTimestampLongDet(timestamp) {
            var date = new Date();
            date.setTime(timestamp * 1000);
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            return y + '年' + m + '月' + d + "日 ";
        }

        function formatTimestampLongHex(timestamp) {
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
        }

        if (data.length != 0) {
            for (var i = 0; i < data.length; i++) {

                dataHtml += '<tr>'
                    + '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
                    + '<td>' + nullData(data[i].userInfo.nickname) + '</td>'
                    + '<td>' + sex(data[i].userInfo.sex) + '</td>'
                    + '<td>' + nullData(data[i].userInfo.birth) + '</td>'
                    + '<td>' + data[i].userInfo.height + 'cm</td>'
                    + '<td>' + nullData(data[i].educationValue) + '</td>'
                    + '<td>' + formatTimestampLongHex(data[i].registerTime) + '</td>'
                    + '<td>' + formatTimestampLongHex(data[i].lastLoginTime) + '</td>'
                    + '<td>' + data[i].lastLoginIp + '</td>'
                    + '<td>' + exp(data[i].isExp, data[i].vipType, data[i].balance) + '</td>'
                    + '<td>' + status(data[i].deleteTag) + '</td>'
                    + '<td>'
                    + '<a class="layui-btn layui-btn-mini layui-btn-normal send_flower"><i class="layui-icon"></i> 赠送鲜花</a>'
                    + '<a class="layui-btn layui-btn-mini layui-btn-warm add_activity"><i class="layui-icon">&#xe61f;</i> 加入活动</a>'
                    + '<a class="layui-btn layui-btn-mini users_edit"><i class="iconfont icon-edit"></i> 详细信息</a>'
                    + '<a class="layui-btn layui-btn-mini layui-btn-danger users_del" data-id="' + data[i].usersId + '"><i class="layui-icon">&#xe640;</i> 封号</a>'
                    + '</td>'
                    + '</tr>';
            }
        } else {
            dataHtml = '<tr><td colspan="12">暂无数据</td></tr>';
        }
        return dataHtml;
    }

})