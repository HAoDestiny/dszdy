package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.ChatRecordEntity;
import net.gzhqlf.dszdy.entity.UserInfoEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ChatListPo;
import net.gzhqlf.dszdy.repository.ChatRecordRepository;
import net.gzhqlf.dszdy.vo.ChatMsgVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class ChatRecordService {

    @Resource
    private ChatRecordRepository chatRecordRepository;

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    public void messageRecord(ChatMsgVo chatMsgVo, int status) throws ControllerException {

        try {
            ChatRecordEntity chatRecordEntity = new ChatRecordEntity();
            chatRecordEntity.setChatContent(URLEncoder.encode(chatMsgVo.getContent(), "utf-8"));
            chatRecordEntity.setCreateTime((int) chatMsgVo.getTime());
            chatRecordEntity.setToUid(chatMsgVo.getToUserId());
            chatRecordEntity.setFormUid(chatMsgVo.getSenderId());
            chatRecordEntity.setStatus(status);
            chatRecordRepository.saveAndFlush(chatRecordEntity);
        } catch (UnsupportedEncodingException e) {
            throw new ControllerException("消息服务异常");
        }
    }

    public List<ChatRecordEntity> getMessageRecord(int senderId, int toUserId, int status) {

        if (status == -1) {
            return chatRecordRepository.findByFormUidAndToUid(senderId, toUserId);
        }

        return chatRecordRepository.findByFormUidAndToUidAndStatus(senderId, toUserId, status);
    }

    public List<ChatListPo> getChatList(int userId) {

        List<ChatRecordEntity> chatRecordEntityList = chatRecordRepository.getChatListByUserId(userId);
        List<ChatListPo> listPos = new ArrayList<>();

        for (ChatRecordEntity chatRecordEntity : chatRecordEntityList) {

            ChatListPo chatListPo = new ChatListPo();
            chatListPo.setUserId(userId);

            if (chatRecordEntity.getToUid() == userId) {
                chatListPo.setChatUserId(chatRecordEntity.getFormUid());
            } else {
                chatListPo.setChatUserId(chatRecordEntity.getToUid());
            }

            UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserId(chatListPo.getChatUserId());

            chatListPo.setTrueName(userInfoEntity.getNickname());

            chatListPo.setAvatar(fileService.getFileByFileId(userInfoEntity.getAvatarNormal()));

            chatListPo.setMessage(chatRecordEntity.getChatContent());

            chatListPo.setTime(chatRecordEntity.getCreateTime());

            listPos.add(chatListPo);

        }

        return listPos;
    }

}
