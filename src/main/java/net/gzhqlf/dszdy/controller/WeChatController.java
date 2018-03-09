package net.gzhqlf.dszdy.controller;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import net.gzhqlf.dszdy.entity.*;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.LoginServicePo;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.po.WeChatUserBasePo;
import net.gzhqlf.dszdy.service.admin.AddressService;
import net.gzhqlf.dszdy.service.GoodsService;
import net.gzhqlf.dszdy.service.UserService;
import net.gzhqlf.dszdy.service.WeChatService;
import net.gzhqlf.dszdy.util.Tool;
import net.gzhqlf.dszdy.vo.AuthorizeTypeVo;
import net.gzhqlf.dszdy.vo.OrderInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Destiny_hao on 2017/11/16.
 */

@RestController
@RequestMapping(value = "/api/weChat")
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    @Resource
    private UserService userService;

    @Resource
    private AddressService addressService;

    @Resource
    private GoodsService goodsService;

    @Autowired
    private BestPayServiceImpl bestPayService;


    @RequestMapping(value = "/getAuthorize")
    public ResultPo getAuthorize(@RequestBody AuthorizeTypeVo authorizeTypeVo) throws UnsupportedEncodingException, ControllerException {

        ResultPo resultPo = new ResultPo();
        String url = weChatService.getAuthorizeUrl(authorizeTypeVo.getType());

        System.out.println(url);

        resultPo.setStatus("SUCCESS");
        resultPo.setData(url);

        return resultPo;
    }


    @RequestMapping(value = "/getOpenId")
    public ResultPo getOpenId(String code, HttpServletResponse httpServletResponse) throws IOException, ControllerException {

        if (null == code) {
            String url = weChatService.getAuthorizeUrl();
            httpServletResponse.sendRedirect(url);
            return null;
        }

        WeChatUserBasePo weChatUserBasePo = weChatService.getAccessTokenOrOpenId(code);

        if (null == weChatUserBasePo || 0 == weChatUserBasePo.getExpires_in()) {
            throw new ControllerException("授权失败");
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("获取成功");
        resultPo.setData(weChatUserBasePo);

        return resultPo;
    }


    @RequestMapping(value = "/getWeChatUserInfo")
    public void getWeChatUserInfo(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                                  HttpSession httpSession, HttpServletResponse httpServletResponse)
            throws ControllerException, IOException {

//        System.out.println("code：" + code + "state：" + state);

        if (code != null && state != null) {

            if (state.equals("WECHAT_LOGIN")) {

                WeChatUserBasePo weChatUserBasePo = weChatService.getAccessTokenOrOpenId(code);
                WeChatTokenEntity wechatTokenEntity = weChatService.getWeChatTokenEntityByOpenId(weChatUserBasePo.getOpenid());

                if (null == wechatTokenEntity) {

                    httpServletResponse.sendRedirect("/m/register.html");
                    return;
                }

                httpSession.setAttribute("WeChatUserBasePo", weChatUserBasePo);

                httpServletResponse.sendRedirect("wechatLoginCallback");
                return;
            }

            if (state.equals("WECHAT_REGISTER")) {

                WeChatUserBasePo weChatUserBasePo = weChatService.getAccessTokenOrOpenId(code);
                WeChatTokenEntity wechatTokenEntity = weChatService.getWeChatTokenEntityByOpenId(weChatUserBasePo.getOpenid());

                if (null != wechatTokenEntity) {

                    httpServletResponse.sendRedirect("/m/login.html");
                    return;
                }

                WeChatUserInfoEntity weChatUserInfoEntity = weChatService.getWeChatUserInfo(
                        weChatUserBasePo.getAccess_token(),
                        weChatUserBasePo.getOpenid()
                );

                httpSession.setAttribute("WeChatUserBasePo", weChatUserBasePo);
                httpSession.setAttribute("WeChatUserInfoEntity", weChatUserInfoEntity);

                httpServletResponse.sendRedirect("wechatRegisterCallback");
                return;

            }
        }
    }

    @RequestMapping(value = "/wechatRegisterCallback")
    public void wechatRegisterCallback(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest,
                                       HttpSession httpSession) throws IOException, ControllerException {

        WeChatUserBasePo weChatUserBasePo = (WeChatUserBasePo) httpSession.getAttribute("WeChatUserBasePo");

        WeChatUserInfoEntity weChatUserInfoEntity = (WeChatUserInfoEntity) httpSession.getAttribute("WeChatUserInfoEntity");

        ResultPo resultPo = userService.weChatRegister(Tool.getIpAddress(httpServletRequest), weChatUserBasePo, weChatUserInfoEntity);

        httpSession.setAttribute("User", resultPo.getData());

        Cookie cookie_token = new Cookie("__dstoken","");
        cookie_token.setMaxAge(0);
        cookie_token.setPath("/");
        httpServletResponse.addCookie(cookie_token);

        if (weChatUserInfoEntity.getCountry().equals("中国")) {

            if (!"".equals(weChatUserInfoEntity.getProvince())) {
                int province_code = addressService.getAddressByName(weChatUserInfoEntity.getProvince(), 1);

                if (province_code != -110) {
                    weChatUserInfoEntity.setProvince(String.valueOf(province_code));
                }
            }

            if (!"".equals(weChatUserInfoEntity.getCity())) {
                int city_code = addressService.getAddressByName(weChatUserInfoEntity.getCity(), 2);

                if (city_code != -110) {
                    weChatUserInfoEntity.setCity(String.valueOf(city_code));
                }
            }

        } else {
            weChatUserInfoEntity.setProvince("110000");
            weChatUserInfoEntity.setCity("110100");
        }

        Cookie cookie = new Cookie("wechat_user_info", weChatUserInfoEntity.getSex()+"|"+weChatUserInfoEntity.getProvince()+"|"+weChatUserInfoEntity.getCity());
        cookie.setMaxAge(300);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        httpServletResponse.sendRedirect("/m/profileBase.html");
    }

    @RequestMapping(value = "/wechatLoginCallback")
    public void wechatLoginCallback(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                      HttpSession httpSession) throws ControllerException, IOException {

        WeChatUserBasePo weChatUserBasePo = (WeChatUserBasePo) httpSession.getAttribute("WeChatUserBasePo");

        ResultPo resultPo = userService.weChatLogin(weChatUserBasePo, Tool.getIpAddress(httpServletRequest));

        LoginServicePo loginServicePo = (LoginServicePo) resultPo.getData();

        if ("SUCCESS".equals(resultPo.getStatus())) {
            httpSession.setAttribute("User", loginServicePo.getUserEntity());

            Cookie cookie = new Cookie("__dstoken", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

            httpServletResponse.sendRedirect("/m/home.html");
            return;
        }

        if (resultPo.getStatus().equals("ACTION")) {
            httpSession.setAttribute("User", loginServicePo.getUserEntity());

            Cookie cookie = new Cookie("__dstoken", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

            httpServletResponse.sendRedirect("/m/" + loginServicePo.getUri());
           return;
        }

        httpServletResponse.sendRedirect("/m/index.html");
    }

    @RequestMapping(value = "/createOrder")
    public ResultPo createOrder(@RequestBody OrderInfoVo orderInfoVo, HttpSession httpSession) throws ControllerException {

        ResultPo resultPo = new ResultPo();

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (orderInfoVo.getGoodsId() == 0) {
            throw new ControllerException("商品id错误");
        } else {

            GoodsEntity goodsEntity = goodsService.getGoodsEntityByGoodsId(orderInfoVo.getGoodsId());

            if (goodsEntity == null) {
                throw new ControllerException("商品不存在");
            }

            if (orderInfoVo.getGoodsTotal() == 0) {
                throw new ControllerException("商品数量错误");
            }

            String orderFee = String.valueOf(goodsEntity.getGoodsFee());
            String orderIdStr = System.currentTimeMillis() + Tool.getRandomCodeString(7);

            OrdersEntity ordersEntity = new OrdersEntity();
            ordersEntity.setOrderId(orderIdStr);
            ordersEntity.setOrderName(orderInfoVo.getOrderName());
            ordersEntity.setOrderFee(orderFee);
            ordersEntity.setOrderUserId(userEntity.getId());
            ordersEntity.setOrderPayStatus(0);

            ordersEntity.setGoodsId(goodsEntity.getId());
            ordersEntity.setGoodsTotal(orderInfoVo.getGoodsTotal());
            ordersEntity.setCreateTime((int) (System.currentTimeMillis()/1000));

            ordersEntity = goodsService.createOrder(ordersEntity);

            if (!orderIdStr.equals(String.valueOf(ordersEntity.getOrderId()))) {
                throw new ControllerException("创建订单失败");
            }

            orderInfoVo.setOrderId(orderIdStr);
            orderInfoVo.setPayUrl("//" + weChatService.getDOMAIN() + "/m/wechatPay.html");
            resultPo.setData(orderInfoVo);
            resultPo.setStatus("SUCCESS");
        }

        return resultPo;
    }

    @RequestMapping(value = "/wechatPayNotify")
    public ModelAndView weChatPayNotify(@RequestBody String notifyData, ModelAndView modelAndView) {

        modelAndView.setViewName("wechatPayNotifyBack");
        PayResponse response = bestPayService.asyncNotify(notifyData);
        String orderId = response.getOrderId();
        double orderAmount = response.getOrderAmount();

        OrdersEntity ordersEntity = goodsService.getOrdersEntityByOrderId(orderId);

        if (ordersEntity == null) {
            return modelAndView;
        }

        if (ordersEntity.getOrderPayStatus() == 1) {
            return modelAndView;
        }

        System.out.println(orderAmount);
        System.out.println(Double.valueOf(ordersEntity.getOrderFee()));

        if (orderAmount < Double.valueOf(ordersEntity.getOrderFee())) {
            return modelAndView;
        }

        ordersEntity.setOrderPayStatus(1);
        ordersEntity = goodsService.saveOrder(ordersEntity);

        if (ordersEntity.getOrderPayStatus() == 1) {

            GoodsEntity goodsEntity = goodsService.getGoodsEntityByGoodsId(ordersEntity.getGoodsId());

            if (goodsEntity.getGoodsType() == 1) {

                userService.updateFlowerTotal(ordersEntity.getOrderUserId(), ordersEntity.getGoodsTotal());

            }

            if (goodsEntity.getGoodsType() == 2) {

                userService.updateUserVip(
                        ordersEntity.getOrderUserId(),
                        (int)(System.currentTimeMillis()/1000) + 86400 * goodsEntity.getGoodsDays(),
                        goodsEntity.getGoodsNum(),
                        goodsEntity.getGoodsDesc()
                        );

            }

        }

        return modelAndView;
    }

    public Map<String, String> getWeChatPay(String orderId, int userId) throws ControllerException {

        //微信新版支付
//        WeChatAccessTokenEntity weChatAccessTokenEntity = weChatService.getWeChatJsTicket();
//        Map<String, String> map = null;
//
//        if (weChatAccessTokenEntity != null) {
//
//            //js ticket
//            map = WeChatSign.sign(weChatAccessTokenEntity.getAccessToken(),
//                    "http://" + weChatService.getDOMAIN() + "/m/wechatPay.html?orderId=" + orderId);
//
//            map.put("appId", weChatService.getAPP_ID());
//        }

        Map<String, String> map = new HashMap<>();

        PayRequest request = new PayRequest();

        WeChatTokenEntity weChatTokenEntity = weChatService.getWeChatTokenEntityByUserId(userId);

        if (weChatTokenEntity == null) {
            throw new ControllerException("用户未授权公众号");
        }

        OrdersEntity ordersEntity = goodsService.getOrdersEntityByOrderId(orderId);

        if (ordersEntity == null) {
            throw new ControllerException("订单不存在");
        }

        if (ordersEntity.getOrderPayStatus() == 1) {
            throw new ControllerException("订单已完成");
        }

        if (ordersEntity.getOrderUserId() != userId) {
            throw new ControllerException("用户id错误");
        }

        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        request.setOrderId(orderId);
        request.setOrderAmount(Double.valueOf(ordersEntity.getOrderFee()));//
        request.setOrderName(ordersEntity.getOrderName());
        request.setOpenid(weChatTokenEntity.getOpenId());

        PayResponse payResponse = bestPayService.pay(request);

        map.put("appId", weChatService.getAPP_ID());
        map.put("timeStamp", payResponse.getTimeStamp());
        map.put("nonceStr", payResponse.getNonceStr());
        map.put("package", payResponse.getPackAge());
        map.put("signType", payResponse.getSignType());
        map.put("paySign", payResponse.getPaySign());

//        map.put("appId", weChatService.getAPP_ID());
//        map.put("timeStamp", (int)(System.currentTimeMillis()/1000)+"");
//        map.put("nonceStr", Tool.getRandomString(16));
//        map.put("package", "prepay_id=wx" + Tool.getRandomCodeString(32));
//        map.put("signType", "MD5");

//        try
//        {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//
//            crypt.reset();
//
//            crypt.update(map.get("nonceStr").getBytes("UTF-8"));
//            map.put("paySign", byteToHex(crypt.digest()));
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }


        return map;
    }

}
