package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.entity.*;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.DynamicListPo;
import net.gzhqlf.dszdy.po.FilePo;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.po.WeChatMessagePo;
import net.gzhqlf.dszdy.service.*;
import net.gzhqlf.dszdy.vo.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/dynamic", method = RequestMethod.POST)
public class DynamicController {

    @Resource
    private DynamicService dynamicService;

    @Resource
    private NotifyService notifyService;

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    @Resource
    private WeChatService weChatService;

    @RequestMapping(value = "/post")
    public ResultPo post(@RequestBody @Valid DynamicMsgVo dynamicMsgVo, HttpSession httpSession,
                         BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity.getIsVerify() == 0) {
            throw new ControllerException("未进行身份认证，请先提交资料审核");
        }

        return dynamicService.post(userEntity, dynamicMsgVo);
    }

    @RequestMapping(value = "/show")
    public ResultPo show(@RequestBody @Valid DynamicPageMsgVo dynamicPageMsgVo, HttpSession httpSession,
                         BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity;

        if (dynamicPageMsgVo.getUserId() != 0) {
            userEntity = new UserEntity();
            userEntity.setId(dynamicPageMsgVo.getUserId());
        }
        else {
            userEntity = (UserEntity) httpSession.getAttribute("User");
        }

        ResultPo resultPo = dynamicService.findAllDynamic(userEntity, dynamicPageMsgVo.getPageCode(), dynamicPageMsgVo.getPageSize(), dynamicPageMsgVo.getTag());
        DynamicListPo dynamicListPo = (DynamicListPo) resultPo.getData();

        dynamicListPo.setUserId(userEntity.getId());
        resultPo.setData(dynamicListPo);

        return resultPo;
    }

    @RequestMapping(value = "/me")
    public ResultPo me(@RequestBody @Valid DynamicPageMsgVo dynamicPageMsgVo, HttpSession httpSession,
                       BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        List<NotifyEntity> notifyEntityList = notifyService.getNotifies(userEntity.getId(), 3, 1);

        notifyService.updateNotifyStatus(1, userEntity.getId(), 2);

        List<Integer> longs = new ArrayList<>();
        for (NotifyEntity notifyEntity : notifyEntityList) {
            longs.add(notifyEntity.getNotifyDynamicId());
        }

        ResultPo resultPo = dynamicService.findAllDynamic(
                userEntity,
                dynamicPageMsgVo.getPageCode(),
                dynamicPageMsgVo.getPageSize(),
                4,
                longs
                );

        DynamicListPo dynamicListPo = (DynamicListPo) resultPo.getData();

        dynamicListPo.setUserId(userEntity.getId());
        resultPo.setData(dynamicListPo);

        return resultPo;
    }

    @RequestMapping(value = "/follow")
    public ResultPo follow(@RequestBody @Valid DynamicPageMsgVo dynamicPageMsgVo, HttpSession httpSession,
                       BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        List<AttentionEntity> attentionEntities = userService.getAttentionEntitiesByUserId(userEntity.getId());

        List<Integer> longs = new ArrayList<>();
        for (AttentionEntity attentionEntity : attentionEntities) {
            longs.add(attentionEntity.getToAttentionId());
        }

        ResultPo resultPo = dynamicService.findAllDynamic(
                userEntity,
                dynamicPageMsgVo.getPageCode(),
                dynamicPageMsgVo.getPageSize(),
                5,
                longs
        );

        DynamicListPo dynamicListPo = (DynamicListPo) resultPo.getData();

        dynamicListPo.setUserId(userEntity.getId());
        resultPo.setData(dynamicListPo);

        return resultPo;
    }

    @RequestMapping(value = "/praise")
    public ResultPo praise(@RequestBody @Valid PraiseDynamicVo praiseDynamicVo, HttpSession httpSession,
                           BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity.getIsVerify() == 0) {
            throw new ControllerException("未进行身份认证，请先提交资料审核");
        }

        ResultPo resultPo = dynamicService.praise(praiseDynamicVo.getDynamicId(), userEntity.getId());
        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());
        DynamicEntity dynamicEntity = dynamicService.findDynamicEntityByDynamicId(praiseDynamicVo.getDynamicId());

        String notifyContent = userInfoEntity.getNickname() + " 给你点了一个赞！";

        FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

        WeChatMessagePo weChatMessagePo = new WeChatMessagePo();
        weChatMessagePo.setDescription(userInfoEntity.getNickname() + " 给你点了一个赞！");
        weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
        weChatMessagePo.setTitle("您的动态被点赞啦！");
        weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/home.html");

        weChatService.sendWeChatMessage(dynamicEntity.getUserId(), weChatMessagePo);

        NotifyEntity notifyEntity = new NotifyEntity();
        notifyEntity.setCreateTime((int) (System.currentTimeMillis()/1000));
        notifyEntity.setNotifyContent(notifyContent);
        notifyEntity.setNotifyDynamicId(praiseDynamicVo.getDynamicId());
        notifyEntity.setNotifyStatus(0);
        notifyEntity.setNotifyToUserId(dynamicEntity.getUserId());
        notifyEntity.setNotifyType(1);

        notifyService.setNotify(notifyEntity);

        return resultPo;
    }

    @RequestMapping(value = "/comment")
    public ResultPo comment(@RequestBody @Valid CommentDynamicVo commentDynamicVo, HttpSession httpSession,
                            BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity.getIsVerify() == 0) {
            throw new ControllerException("未进行身份认证，请先提交资料审核");
        }

        ResultPo resultPo = dynamicService.comment(commentDynamicVo, userEntity.getId());
        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());

        DynamicEntity dynamicEntity = dynamicService.findDynamicEntityByDynamicId(commentDynamicVo.getDynamicId());

        if (userEntity.getId() == dynamicEntity.getUserId() && commentDynamicVo.getCommentToUserId() == 0) {
            return resultPo;
        }

        String notifyContent;

        if (commentDynamicVo.getCommentToUserId() == 0) {

            commentDynamicVo.setCommentToUserId(dynamicEntity.getUserId());

            notifyContent = userInfoEntity.getNickname() + " 评论了你的动态！";

            FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

            WeChatMessagePo weChatMessagePo = new WeChatMessagePo();
            weChatMessagePo.setDescription(userInfoEntity.getNickname() + " 评论了你的动态！");
            weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
            weChatMessagePo.setTitle("您的动态被评论啦！");
            weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/home.html");

            weChatService.sendWeChatMessage(commentDynamicVo.getCommentToUserId(), weChatMessagePo);

        } else {

            notifyContent = userInfoEntity.getNickname() + " 回复了你的评论！";

            FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

            WeChatMessagePo weChatMessagePo = new WeChatMessagePo();

            weChatMessagePo.setTitle("您的评论被回复啦！");
            weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/home.html");

            weChatService.sendWeChatMessage(commentDynamicVo.getCommentToUserId(), weChatMessagePo);

        }

        NotifyEntity notifyEntity = new NotifyEntity();
        notifyEntity.setCreateTime((int) (System.currentTimeMillis()/1000));
        notifyEntity.setNotifyContent(notifyContent);
        notifyEntity.setNotifyDynamicId(commentDynamicVo.getDynamicId());
        notifyEntity.setNotifyStatus(0);
        notifyEntity.setNotifyToUserId(commentDynamicVo.getCommentToUserId());
        notifyEntity.setNotifyType(1);

        notifyService.setNotify(notifyEntity);

        return resultPo;

    }
}
