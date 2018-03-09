package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.entity.UserEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.util.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by n0elle on 2017/10/25.
 */

@RestController
@RequestMapping(value = "/api/token", method = RequestMethod.POST)
public class TokenController {

    @Value("${dszdy.project.domain}")
    private String DOMAIN;

    @RequestMapping(value = "/getTokenCode")
    public ResultPo getTokenCode(HttpSession httpSession, HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity == null) {
            //未正常打开登录页面换取临时session
            throw new ControllerException("尚未登录");
        }

        String __dsuuid = httpSession.getId();
        String __dstoken = "";


        if (__dsuuid.trim().equals("")) {
            //未正常打开登录页面换取临时session
            throw new ControllerException("尚未登录");
        }

        TokenUtils tokenUtils = new TokenUtils(__dsuuid, userEntity.getId());
        __dstoken = tokenUtils.getTokenCode();

        try {
            __dstoken = URLEncoder.encode(__dstoken, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ControllerException("未知错误");
        }

        Cookie cookie = new Cookie("__dstoken", __dstoken);
        cookie.setMaxAge(3600 * 24 * 15);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(__dstoken);

        return resultPo;
    }

    @RequestMapping(value = "/getChatTokenCode")
    public ResultPo getChatTokenCode(HttpSession httpSession,
                                 HttpServletResponse httpServletResponse) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity == null) {
            //未正常打开登录页面换取临时session
            throw new ControllerException("尚未登录");
        }

        String __dsuuid = httpSession.getId();

        if (__dsuuid.trim().equals("")) {
            //未正常打开登录页面换取临时session
            throw new ControllerException("尚未登录");
        }

        Cookie cookie = new Cookie("__chatuid", __dsuuid);
        cookie.setMaxAge(3600 * 24 * 7);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(DOMAIN);

        return resultPo;
    }
}
