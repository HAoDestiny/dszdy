package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.entity.UserEntity;
import net.gzhqlf.dszdy.entity.UserInfoEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by DESTINY on 2017/10/18.
 */

@Controller
@RequestMapping(value = "/m", method = RequestMethod.GET)
public class MobileController {

    @Resource
    private WeChatController weChatController;

    @Resource
    private UserService userService;

    @RequestMapping(value = {"/"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/{forward}")
    public String reward(@PathVariable String forward) {
        return forward;
    }

    @RequestMapping(value = "/wechatPay")
    public String weChatPay(@RequestParam String orderId, Model model, HttpSession httpSession) throws ControllerException {

        if (orderId == null || "".equals(orderId)) {
            throw new ControllerException("订单id错误");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        Map<String, String> payMap = weChatController.getWeChatPay(orderId, userEntity.getId());

        model.addAttribute("payMap", payMap);

        return "wechatPay";
    }

    @RequestMapping(value = "/home")
    public String home(HttpSession httpSession) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity == null) {
            return "redirect:/m/login.html";
        }

        UserEntity user = userService.getUserEntityByUserId(userEntity.getId());

        if (user == null) {
            return "redirect:/m/register.html";
        }

        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserId(user.getId());

        if (userInfoEntity == null) {
            return "redirect:/m/register.html";
        }

        if (null == userInfoEntity.getNickname() || "".equals(userInfoEntity.getNickname())) {

            return "redirect:/m/profileBase.html";
        }

        if (null == userInfoEntity.getAvatarNormal() || userInfoEntity.getAvatarNormal() == 0) {

            return "redirect:/m/uploadPic.html";
        }

        return "home";
    }

    @RequestMapping(value = "/login")
    public String login(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("__dstoken", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        return "login";
    }

//    @RequestMapping(value = "/profileBase")
//    public String profileBase(HttpSession httpSession) {
//
//        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
//
//        if (userEntity != null && userEntity.getUserInfoId() != 0) {
//            return "redirect:/m/home.html";
//        }
//
//        return "profileBase";
//    }
//
//    @RequestMapping(value = "/uploadPic")
//    public String uploadPic(HttpSession httpSession) throws ControllerException {
//
//        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
//
//        if (userEntity != null) {
//            UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());
//
//            if (userInfoEntity == null) {
//                return "redirect:/m/profileBase.html";
//            }
//
//            if (userInfoEntity.getAvatarNormal() != 0) {
//                return "redirect:/m/home.html";
//            }
//        }
//
//        return "uploadPic";
//    }

}
