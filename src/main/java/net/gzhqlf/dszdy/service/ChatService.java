package net.gzhqlf.dszdy.service;

import com.alibaba.fastjson.JSON;
import net.gzhqlf.dszdy.ApplicationContextRegister;
import net.gzhqlf.dszdy.entity.ChatRecordEntity;
import net.gzhqlf.dszdy.entity.FriendsEntity;
import net.gzhqlf.dszdy.entity.NotifyEntity;
import net.gzhqlf.dszdy.entity.UserInfoEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ChatRecordListPo;
import net.gzhqlf.dszdy.po.FilePo;
import net.gzhqlf.dszdy.po.SystemMessagePo;
import net.gzhqlf.dszdy.po.WeChatMessagePo;
import net.gzhqlf.dszdy.util.ChatRecordListComparator;
import net.gzhqlf.dszdy.util.ChatSessionManager;
import net.gzhqlf.dszdy.util.TokenUtils;
import net.gzhqlf.dszdy.vo.ChatMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by n0elle on 2017/10/26.
 */

@ServerEndpoint(value = "/api/initChatService")
@Component
public class ChatService {

    private int userId = 0;
    private int toUserId = 0;
    private static int onlineCount = 0;
    private static ChatSessionManager chatSessionManager;
    private Session session;
    private ChatRecordService chatRecordService;
    private NotifyService notifyService;
    private UserService userService;
    private FileService fileService;
    private WeChatService weChatService;

    @Autowired
    public void setChatSessionManager(ChatSessionManager chatSessionManager) {
        ChatService.chatSessionManager = chatSessionManager;
    }

    public ChatService() {
        ApplicationContext act = ApplicationContextRegister.getApplicationContext();
        this.chatRecordService = act.getBean(ChatRecordService.class);
        this.notifyService = act.getBean(NotifyService.class);
        this.userService = act.getBean(UserService.class);
        this.fileService = act.getBean(FileService.class);
        this.weChatService = act.getBean(WeChatService.class);
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {

        addOnlineCount();

        SystemMessagePo systemMessagePo = new SystemMessagePo();
        Map<String, List<String>> conParameter = session.getRequestParameterMap();
        String __dstoken = "";
        String __dsuuid = "";

        try {
            __dstoken = conParameter.get("dstoken").get(0);
            __dsuuid = conParameter.get("uuid").get(0);
            this.toUserId = Integer.parseInt(conParameter.get("touid").get(0));
        } catch (NullPointerException npe) {
            systemMessagePo.setStatus("CHAT_SYSTEM_ERROR_NULL");
            systemMessagePo.setMessage("参数不完整");
            try {
                sendMessage(JSON.toJSONString(systemMessagePo));
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            __dstoken = URLDecoder.decode(__dstoken, "UTF-8").replace(' ', '+');

            TokenUtils tokenUtils = new TokenUtils(__dstoken);

            if (!tokenUtils.valid(__dsuuid)) {
                systemMessagePo.setStatus("CHAT_SYSTEM_ERROR_TOKEN");
                systemMessagePo.setMessage("令牌过期");
                try {
                    sendMessage(JSON.toJSONString(systemMessagePo));
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.userId = tokenUtils.getUserId();
            this.session = session;

            FriendsEntity friendsEntity = userService.getFriendsEntityByUserIdAndFriendsIdAndDeleteTag(this.userId, this.toUserId, 0);

            if (friendsEntity == null) {
                systemMessagePo.setStatus("CHAT_USER_IS_NOT_FRIEND");
                systemMessagePo.setMessage("对方不是你的好友");
                try {
                    sendMessage(JSON.toJSONString(systemMessagePo));
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            chatSessionManager.setChatService(this.userId, this);

            System.out.println("id[" + this.userId + "] toid[" + this.toUserId + "] 连接加入！当前在线人数为" + getOnlineCount());

            List<ChatRecordEntity> chatRecordEntityList = chatRecordService.getMessageRecord(this.userId, this.toUserId, -1);
            chatRecordEntityList.addAll(chatRecordService.getMessageRecord(this.toUserId, this.userId, -1));

            ChatRecordListPo chatRecordListPo = new ChatRecordListPo();

            UserInfoEntity chatUserInfoEntity = userService.getUserInfoEntityByUserId(this.toUserId);
            chatRecordListPo.setChatUsername(chatUserInfoEntity.getNickname());

            chatRecordListPo.setChatUserAvatar(
                    fileService.getFileByFileId(chatUserInfoEntity.getAvatarNormal())
            );

            chatRecordListPo.setUserAvatar(
                    fileService.getFileByFileId(userService.getUserInfoEntityByUserId(this.userId).getAvatarNormal())
            );

            if (!chatRecordEntityList.isEmpty()) {
                ChatRecordListComparator chatRecordListComparator = new ChatRecordListComparator();
                chatRecordEntityList.sort(chatRecordListComparator);
                chatRecordListPo.setChatRecord(chatRecordEntityList);
            }

            systemMessagePo.setData(chatRecordListPo);
            systemMessagePo.setStatus("CHAT_USER_ENTER_SUCCESS");
            sendMessage(JSON.toJSONString(systemMessagePo));

            systemMessagePo.setData(null);

            if (!chatSessionManager.isUserOnline(this.toUserId)) {
                systemMessagePo.setStatus("CHAT_USER_NOT_ONLINE");
                sendMessage(JSON.toJSONString(systemMessagePo));
            } else {
                systemMessagePo.setStatus("CHAT_USER_IS_ONLINE");
                systemMessagePo.setMessage("好友已上线");
                chatSessionManager.getChatService(this.toUserId).sendMessage(JSON.toJSONString(systemMessagePo));
            }

        } catch (UnsupportedEncodingException e) {
            systemMessagePo.setStatus("CHAT_SYSTEM_ERROR_TOKEN");
            systemMessagePo.setMessage("令牌错误");
            try {
                sendMessage(JSON.toJSONString(systemMessagePo));
                session.close();
            } catch (IOException ex) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            systemMessagePo.setStatus("CHAT_SYSTEM_ERROR_CONNECT");
            systemMessagePo.setMessage("通讯错误");
            try {
                sendMessage(JSON.toJSONString(systemMessagePo));
                session.close();
            } catch (IOException exc) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        subOnlineCount();
        chatSessionManager.removeUser(this.userId);

        SystemMessagePo systemMessagePo = new SystemMessagePo();

        systemMessagePo.setStatus("CHAT_USER_IS_OFFLINE");
        systemMessagePo.setMessage("好友已离线");

        if (chatSessionManager.getChatService(this.toUserId) == null) {
            return;
        }

        try {
            chatSessionManager.getChatService(this.toUserId).sendMessage(JSON.toJSONString(systemMessagePo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws ControllerException {

        ChatMsgVo chatMsgVo = JSON.parseObject(message, ChatMsgVo.class);

        SystemMessagePo systemMessagePo = new SystemMessagePo();

        if (chatMsgVo.getSenderId() == this.userId) {
            if (!chatSessionManager.isUserOnline(chatMsgVo.getToUserId())) {

                UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserId(chatMsgVo.getSenderId());

                FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

                WeChatMessagePo weChatMessagePo = new WeChatMessagePo();
                weChatMessagePo.setDescription(userInfoEntity.getNickname() + " 给您发送了私信！");
                weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
                weChatMessagePo.setTitle("您有一条新的私信");
                weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/home.html");

                weChatService.sendWeChatMessage(chatMsgVo.getToUserId(), weChatMessagePo);

                systemMessagePo.setStatus("CHAT_USER_NOT_ONLINE");
                systemMessagePo.setMessage("好友不在线, 已通过微信提醒对方");


                String notifyContent = userInfoEntity.getNickname() + " 给您发送了私信！";

                NotifyEntity notifyEntity = new NotifyEntity();
                notifyEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                notifyEntity.setNotifyContent(notifyContent);
                notifyEntity.setNotifyChatUserId(this.userId);
                notifyEntity.setNotifyStatus(0);
                notifyEntity.setNotifyToUserId(chatMsgVo.getToUserId());
                notifyEntity.setNotifyType(2);

                notifyService.setNotify(notifyEntity);

                try {
                    sendMessage(JSON.toJSONString(systemMessagePo));
                    chatRecordService.messageRecord(chatMsgVo, 1);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            systemMessagePo.setStatus("CHAT_MESSAGE_INCOME");
            systemMessagePo.setMessage(JSON.toJSONString(chatMsgVo));
            try {
                chatSessionManager.getChatService(chatMsgVo.getToUserId()).sendMessage(JSON.toJSONString(systemMessagePo));
                chatRecordService.messageRecord(chatMsgVo, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("来自客户端的消息:" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ChatService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ChatService.onlineCount--;
    }

}
