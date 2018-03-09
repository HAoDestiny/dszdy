package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.entity.UserEntity;
import net.gzhqlf.dszdy.po.ChatListPo;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.service.ChatRecordService;
import net.gzhqlf.dszdy.service.NotifyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by n0elle on 2017/10/26.
 */

@RestController
@RequestMapping(value = {"/api/chat"}, method = RequestMethod.POST)
public class ChatController {

    @Resource
    private ChatRecordService chatRecordService;

    @Resource
    private NotifyService notifyService;

    @RequestMapping(value = {"/getChatList"})
    public ResultPo getChatList(HttpSession httpSession) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        notifyService.updateNotifyStatus(2, userEntity.getId(), 2);

        List<ChatListPo> listPos = chatRecordService.getChatList(userEntity.getId());

        ResultPo resultPo = new ResultPo();

        resultPo.setStatus("SUCCESS");
        resultPo.setData(listPos);

        return resultPo;

    }

}
