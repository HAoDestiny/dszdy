package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
public class CommentListPo {

    private int userId;
    private Object avatar;
    private String username;
    private String content;
    private long time;
    private int dynamicId;
    private int commentToUserId;
    private String commentToUserName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public int getCommentToUserId() {
        return commentToUserId;
    }

    public void setCommentToUserId(int commentToUserId) {
        this.commentToUserId = commentToUserId;
    }

    public String getCommentToUserName() {
        return commentToUserName;
    }

    public void setCommentToUserName(String commentToUserName) {
        this.commentToUserName = commentToUserName;
    }
}
