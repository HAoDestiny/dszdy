package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.entity.UserEntity;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.service.NotifyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/notify", method = RequestMethod.POST)
public class NotifyController {

    @Resource
    private NotifyService notifyService;

    @RequestMapping(value = "/getUserNotify")
    public ResultPo getUserNotify(HttpSession httpSession) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        return notifyService.getNotify(userEntity.getId());
    }
}
