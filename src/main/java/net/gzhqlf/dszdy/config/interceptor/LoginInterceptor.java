package net.gzhqlf.dszdy.config.interceptor;

import net.gzhqlf.dszdy.entity.UserEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.util.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by n0elle on 2017/10/25.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //System.out.println(request.getRequestURI());

        //0 页面请求 1 api请求
        int requestType = 0;
        //0 其他动作 1 登录动作 2 注册动作 3 寻找密码
        int actionType = 0;

        if (request.getRequestURL().indexOf("/api/") > 1) {
            requestType = 1;
        }

        if (request.getRequestURL().indexOf("login") > 1) {
            actionType = 1;
        }
        if (request.getRequestURL().indexOf("register") > 1) {
            actionType = 2;
        }
        if (request.getRequestURL().indexOf("findPassword") > 1) {
            actionType = 3;
        }

        if (request.getRequestURL().indexOf("admin") > 1) {

            UserEntity userEntity = (UserEntity) request.getSession().getAttribute("Admin");
            if (userEntity == null) {

                if (requestType == 1 && actionType == 1) {
                    return true;
                }

                if (requestType == 1 && actionType == 0) {
                    throw new ControllerException("尚未登录");
                }

                response.sendRedirect("/admin/");
                return false;
            }

            return true;
        }

        UserEntity userEntity = (UserEntity) request.getSession().getAttribute("User");

        if (userEntity == null) {
            if (requestType == 0 && actionType != 0) {
                userEntity = new UserEntity();
                userEntity.setId(0);
                request.getSession().setAttribute("User", userEntity);
                return true;
            }
            if (requestType == 1 && actionType == 0) {
                throw new ControllerException("尚未登录");
            }
            if (requestType == 0) {
                //跳转登录
                response.sendRedirect("/m/login.html");
                return false;
            }
        }

        Cookie cookies[] = request.getCookies();
        //session id
        String __dsuuid = request.getSession().getId();
        String __dstoken = "";

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if ("__dstoken".equalsIgnoreCase(cookies[i].getName())) {

                    __dstoken = URLDecoder.decode(URLDecoder.decode(cookies[i].getValue(),"UTF-8"),"UTF-8").replace(' ', '+');
                    break;
                }
            }

            //跳转登录
            if (__dsuuid.trim().equals("")) {
                if (requestType == 1) {
                    throw new ControllerException("尚未登录");
                }
                response.sendRedirect("/m/login.html");
                return false;
            }

            if (requestType == 1) {

                TokenUtils tokenUtils = new TokenUtils(__dsuuid, userEntity != null ? userEntity.getId() : 0);

                //添加dstoken
                if (__dstoken.trim().equals("")) {

                    __dstoken = tokenUtils.getTokenCode();

                    try {
                        __dstoken = URLEncoder.encode(__dstoken, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new ControllerException("未知错误");
                    }

                    Cookie dstoken = new Cookie("__dstoken", __dstoken);
                    dstoken.setMaxAge(3600 * 24 * 15);
                    dstoken.setPath("/");
                    response.addCookie(dstoken);

                    return true;
                }

                if (!tokenUtils.validToken(__dstoken)) {

                    request.getSession().removeAttribute("User");
                    request.getSession().invalidate();

                    Cookie dstoken = new Cookie("__dstoken", "");
                    dstoken.setMaxAge(0);
                    dstoken.setPath("/");
                    response.addCookie(dstoken);

                    throw new ControllerException("令牌失效");
                }

            }

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

}