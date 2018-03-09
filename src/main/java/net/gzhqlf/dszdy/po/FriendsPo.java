package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/4.
 */
public class FriendsPo {

    private FilePo avatar;
    private String trueName;
    private int friendId;
    private int uId;

    public FilePo getAvatar() {
        return avatar;
    }

    public void setAvatar(FilePo avatar) {
        this.avatar = avatar;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

}
