!(function(){

    function getUserNotify() {
        $.api("getUserNotify", {}, function(ret){
            if(ret.status == "SUCCESS") {
                var isHome = window.location.href.indexOf("home.html") > 1 ? true : false;

                if (ret.data.notifyCounts == 0) {
                    $(".badgered-fixed").addClass("hide");
                    if (isHome)
                        $(".badgered badgenumred").addClass("hide");
                    return;
                }
                $(".badgered-fixed").removeClass("hide");
                if (ret.message != null) {
                    dszdy.UI.Tip(ret.message);
                }
                if (!isHome) {
                    return;
                }

                var dynamicNotifyCounts = 0;
                var privateChatNotifyCounts = 0;
                var friendRequestNotifyCounts = 0;

                $.each(ret.data.notify, function (notifyIndex, notifyObj) {
                    if (notifyObj.notifyType == 1) {
                        dynamicNotifyCounts += 1;
                    }
                    if (notifyObj.notifyType == 2) {
                        privateChatNotifyCounts += 1;
                    }
                    if (notifyObj.notifyType == 3) {
                        friendRequestNotifyCounts += 1;
                    }
                });

                if (dynamicNotifyCounts != 0) {
                    $(".js-post").removeClass("hide").text(dynamicNotifyCounts);
                }
                if (privateChatNotifyCounts != 0) {
                    $(".js-siliao").removeClass("hide").text(privateChatNotifyCounts);
                }
                if (friendRequestNotifyCounts != 0) {
                    $(".js-addfd").removeClass("hide").text(friendRequestNotifyCounts);
                }
            }
        }, "notify");
    }

    getUserNotify();

    setInterval(function(){
        getUserNotify();
    }, 5000);

})();