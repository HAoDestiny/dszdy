package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/10/28.
 */
public class CommentDynamicVo {

    @NotNull(message = "动态Id")
    private int dynamicId;

    @NotNull(message = "评论内容")
    @NotEmpty(message = "评论内容")
    private String content;

    private int commentToUserId;

    private long time = System.currentTimeMillis()/1000;

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentToUserId() {
        return commentToUserId;
    }

    public void setCommentToUserId(int commentToUserId) {
        this.commentToUserId = commentToUserId;
    }

    public long getTime() {
        return time;
    }
}
