package net.gzhqlf.dszdy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.gzhqlf.dszdy.entity.JsTicketEntity;
import net.gzhqlf.dszdy.entity.WeChatAccessTokenEntity;
import net.gzhqlf.dszdy.entity.WeChatTokenEntity;
import net.gzhqlf.dszdy.entity.WeChatUserInfoEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.WeChatMessagePo;
import net.gzhqlf.dszdy.po.WeChatUserBasePo;
import net.gzhqlf.dszdy.repository.WeChatAccessTokenRepository;
import net.gzhqlf.dszdy.repository.WeChatTokenRepository;
import net.gzhqlf.dszdy.util.HttpsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * Created by Destiny_hao on 2017/11/16.
 */

@Service
public class WeChatService {

    @Value("${dszdy.wechat.appId}")
    private String APP_ID;

    @Value("${dszdy.wechat.appSecret}")
    private String APP_SECRET;

    @Value("${dszdy.project.domain}")
    private String DOMAIN;

    @Value("${dszdy.wechat.admin.openid}")
    private String ADMIN_OPENID;

    @Resource
    private WeChatTokenRepository weChatTokenRepository;

    @Resource
    private WeChatAccessTokenRepository weChatAccessTokenRepository;

    private static final String SCOPE_USERINFO = "snsapi_userinfo";

    private static final String SCOPE_BASEINFO = "snsapi_base";

    private static final String STATE_WECHAT_REGISTER = "WECHAT_REGISTER";

    private static final String STATE_WECHAT_LOGIN = "WECHAT_LOGIN";

    private static final String ACCESS_TOKEN =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    private static final String JS_TICKET =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";


    private static final String CUSTOM_SEND =
            "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

    //授权
    private static final String AUTHORIZE_URL =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    //二维码
    private static final String AUTHORIZATION_URL =
            "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    private static final String ACCESSTOKE_OPENID_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private static final String REFRESH_TOKEN_URL =
            "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

    private static final String USER_INFO_URL =
            "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    private static final String CALLBACK_URL = "http://%s/api/weChat/getWeChatUserInfo";

    private static final String CALLBACK_URL_OPENID = "http://%s/api/weChat/getOpenId";

    public String getDOMAIN() {
        return DOMAIN;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public String getAuthorizeUrl () throws UnsupportedEncodingException, ControllerException {
        return getAuthorizeUrl(2, CALLBACK_URL_OPENID);
    }

    public String getAuthorizeUrl(int type) throws UnsupportedEncodingException, ControllerException {

        return getAuthorizeUrl(type, CALLBACK_URL);
    }

    private String getAuthorizeUrl(int type, String callback) throws UnsupportedEncodingException, ControllerException {

        String snsapi, state;

        if (type == 1) {
            snsapi = SCOPE_USERINFO;
            state = STATE_WECHAT_REGISTER;
        } else if (type == 2) {
            snsapi = SCOPE_BASEINFO;
            state = STATE_WECHAT_LOGIN;
        } else {
            throw new ControllerException("参数错误");
        }

        return String.format(AUTHORIZE_URL, APP_ID, String.format(callback, DOMAIN), snsapi, state);
    }

    public WeChatUserBasePo getAccessTokenOrOpenId(String code) throws ControllerException {

        String url = String.format(ACCESSTOKE_OPENID_URL, APP_ID, APP_SECRET, code);

        String response = HttpsUtil.httpsRequestToString(url, "GET", null);

        return JSON.parseObject(response, WeChatUserBasePo.class);
    }

    public WeChatUserInfoEntity getWeChatUserInfo(String access_token, String open_id) {

        String url = String.format(USER_INFO_URL, access_token, open_id);

        String response = HttpsUtil.httpsRequestToString(url, "GET", null);

        return JSON.parseObject(response, WeChatUserInfoEntity.class);

    }

    public WeChatTokenEntity getWeChatTokenEntityByOpenId(String openId) {
        return weChatTokenRepository.findByOpenId(openId);
    }

    public WeChatTokenEntity getWeChatTokenEntityByUserId(int userId) {
        return weChatTokenRepository.findByUserId(userId);
    }

    private WeChatAccessTokenEntity getWeChatAccessToken() {

        WeChatAccessTokenEntity weChatAccessTokenEntity = weChatAccessTokenRepository.findOne(1);


        if (weChatAccessTokenEntity == null ||
                (weChatAccessTokenEntity.getRefreshTime() + weChatAccessTokenEntity.getExpiresIn()) < (System.currentTimeMillis() / 1000)) {

            String url = String.format(ACCESS_TOKEN, APP_ID, APP_SECRET);
            String response = HttpsUtil.httpsRequestToString(url, "GET", null);

            weChatAccessTokenEntity = JSON.parseObject(response, WeChatAccessTokenEntity.class);

            if (weChatAccessTokenEntity.getAccessToken() == null) {
                return null;
            }

            weChatAccessTokenEntity.setId(1);
            weChatAccessTokenEntity.setRefreshTime((int) (System.currentTimeMillis() / 1000));

            weChatAccessTokenRepository.saveAndFlush(weChatAccessTokenEntity);
            return weChatAccessTokenEntity;
        }

        return weChatAccessTokenEntity;
    }

    public WeChatAccessTokenEntity getWeChatJsTicket() {

        JsTicketEntity jsTicketEntity;

        WeChatAccessTokenEntity weChatJsTicket = weChatAccessTokenRepository.findOne(2);

        if (weChatJsTicket == null ||
                (weChatJsTicket.getRefreshTime() + weChatJsTicket.getExpiresIn()) < (System.currentTimeMillis() / 1000)) {

            WeChatAccessTokenEntity weChatAccessTokenEntity = getWeChatAccessToken();

            if (weChatAccessTokenEntity == null) {
                return null;
            }

            String url = String.format(JS_TICKET, weChatAccessTokenEntity.getAccessToken());
            String response = HttpsUtil.httpsRequestToString(url, "GET", null);

            jsTicketEntity = JSON.parseObject(response, JsTicketEntity.class);

            if (!("ok").equals(jsTicketEntity.getErrmsg())) {
                return null;
            }

            weChatJsTicket.setId(2);
            weChatJsTicket.setRefreshTime((int) (System.currentTimeMillis() / 1000));
            weChatJsTicket.setAccessToken(jsTicketEntity.getTicket());
            weChatJsTicket.setExpiresIn(jsTicketEntity.getExpires_in());

            weChatAccessTokenRepository.saveAndFlush(weChatJsTicket);

            return weChatJsTicket;
        }

        return weChatJsTicket;
    }

    @Async
    public void sendWeChatMessage(int toUserId, WeChatMessagePo weChatMessagePo) {

        WeChatAccessTokenEntity weChatAccessTokenEntity = getWeChatAccessToken();

        if (weChatAccessTokenEntity == null) {
            return;
        }

        boolean flag = false;

        String openid = ADMIN_OPENID;

        if (toUserId != 0) {

            WeChatTokenEntity weChatTokenEntity = weChatTokenRepository.findByUserId(toUserId);

            if (weChatTokenEntity == null) {
                return;
            }
            openid = weChatTokenEntity.getOpenId();
        }

        String url = String.format(CUSTOM_SEND, weChatAccessTokenEntity.getAccessToken());

        String jsonMsg = makeNewsCustomMessage(openid, weChatMessagePo);

        JSONObject jsonResult = JSON.parseObject(HttpsUtil.httpsRequestToString(url, "POST", jsonMsg));

        if (jsonResult != null) {

            int errorCode = jsonResult.getIntValue("errcode");

//            String errorMessage = jsonResult.getString("errmsg");

            if (errorCode == 0) {

                flag = true;
            }

            else {

                flag = false;
            }
        }

    }

    public static String makeNewsCustomMessage(String openId, WeChatMessagePo weChatMessagePo) {

        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":[%s]}}";

        jsonMsg = String.format(jsonMsg, openId, JSON.toJSONString(weChatMessagePo).replaceAll("\"", "\\\""));

        return jsonMsg;
    }
}
