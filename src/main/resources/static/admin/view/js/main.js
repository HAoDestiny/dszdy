layui.config({
	base : "js/"
}).use(['form','element','layer','jquery'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		element = layui.element(),
		$ = layui.jquery;

	$(".panel a").on("click",function(){
		window.parent.addTab($(this));
	})

    //systemCounts
    $.get("/api/admin/system/getSystemTotal",
        function(data){

            if (data.message === "尚未登录") {
                window.top.location.href = "/admin/";
            }

            $(".newMessage span").text(data.proveCounts);
            $(".newUser span").text(data.newUserCounts);
            $(".userAll span").text(data.userCounts);
            $(".imgAll span").text(data.picCounts);
            $(".orderAll span").text(data.orderCounts);
            $(".allNews span").text(data.activityCounts);
        }
    );

	//动态获取文章总数和待审核文章数量,最新文章
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/api/admin/getCityActivityList",
        data: JSON.stringify({pageCode:0, pageSize: 5}),
        dataType: "json",
        success: function(ret){
            var data = ret.data.data;

            var hotNewsHtml = '';
            for(var i = 0; i < data.length; i++){
                hotNewsHtml += '<tr>'
                    +'<td align="left">'+data[i].activityTitle+'</td>'
                    +'<td style="width: 20%">'+ formatTimestampLongDet(data[i].createTime) +'</td>'
                    +'</tr>';
            }
            $(".hot_news").html(hotNewsHtml);
        }
    });

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/api/admin/goods/getOrderList",
        data: JSON.stringify({pageCode:0, pageSize: 10}),
        dataType: "json",
        success: function(ret){
        	if (ret.status !== "SUCCESS") {
        		return;
			}
            var data = ret.data.orders;

            var hotOrdersHtml = '';
            for(var i = 0; i < data.length; i++){
                hotOrdersHtml += '<tr>'
                    +'<td align="left">'+data[i].order.orderName+'</td>'
                    +'<td style="width: 30%">下单人('+ data[i].userName +')</td>'
                    +'<td align="right">'+ formatTimestampLongDet(data[i].order.createTime) +'</td>'
                    +'</tr>';
            }
            $(".hot_orders").html(hotOrdersHtml);
        }
    });

    function formatTimestampLongDet(timestamp) {
        var date = new Date();
        date.setTime(timestamp * 1000);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var i = date.getMinutes();
        h = h < 10 ? ('0' + h) : h;
        i = i < 10 ? ('0' + i) : i;
        return y + '-' + m + '-' + d + ' ' + h + ':' + i;
    }

	//数字格式化
	$(".panel span").each(function(){
		$(this).html($(this).text()>9999 ? ($(this).text()/10000).toFixed(2) + "<em>万</em>" : $(this).text());	
	})

	//系统基本参数
	if(window.sessionStorage.getItem("systemParameter")){
		var systemParameter = JSON.parse(window.sessionStorage.getItem("systemParameter"));
		fillParameter(systemParameter);
	}else{
		$.ajax({
			url : "/api/admin/system/getSystemInfo",
			type : "get",
			dataType : "json",
			success : function(data){
				fillParameter(data);
			}
		})
	}

	//填充数据方法
 	function fillParameter(data){
 		//判断字段数据是否存在
 		function nullData(data){
 			if(data == '' || data == "undefined"){
 				return "未定义";
 			}else{
 				return data;
 			}
 		}
 		$(".version").text(nullData(data.version));      //当前版本
		$(".author").text(nullData(data.author));        //开发作者
		$(".homePage").text(nullData(data.homePage));    //网站首页
		$(".server").text(nullData(data.server));        //服务器环境
		$(".dataBase").text(nullData(data.dataBase));    //数据库版本
		$(".maxUpload").text(nullData(data.maxUpload));    //最大上传限制
		$(".userRights").text(nullData(data.userRights));//当前用户权限

        if (data.userRights !== "超级管理员") {
            $("#headNav").hide();
        }
 	}

});
