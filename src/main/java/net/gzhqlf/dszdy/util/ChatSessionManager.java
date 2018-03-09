package net.gzhqlf.dszdy.util;

import net.gzhqlf.dszdy.service.ChatService;
import org.springframework.stereotype.Component;

/**
 * Created by n0elle on 2017/10/26.
 */
@Component
public class ChatSessionManager {

    private static CopyOnWriteMap<Object, ChatService> chatServiceCopyOnWriteMap = new CopyOnWriteMap<>();

    public ChatService getChatService(int userId) {
        return chatServiceCopyOnWriteMap.get(userId);
    }

    public void setChatService(int userId, ChatService chatService) {
        chatServiceCopyOnWriteMap.put(userId, chatService);
    }

    public boolean isUserOnline(int userId) {
        return chatServiceCopyOnWriteMap.containsKey(userId);
    }

    public void removeUser(int userId) {
        chatServiceCopyOnWriteMap.remove(userId);
    }

}
