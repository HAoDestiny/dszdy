package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by n0elle on 2017/10/26.
 */
public class ChatMsgVo {

    @NotNull(message = "消息")
    @NotEmpty(message = "消息")
    private String content;
    @NotNull(message = "接收人")
    @NotEmpty(message = "接收人")
    private int toUserId;
    private long time = System.currentTimeMillis()/1000;
    @NotNull(message = "发送者")
    @NotEmpty(message = "发送者")
    private int senderId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public long getTime() {
        return time;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
