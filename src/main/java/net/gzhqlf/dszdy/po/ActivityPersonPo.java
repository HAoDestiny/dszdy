package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/9.
 */
public class ActivityPersonPo {

    private int userId;
    private FilePo avatar;
    private String nickname;
    private int sex;
    private int activity;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public FilePo getAvatar() {
        return avatar;
    }

    public void setAvatar(FilePo avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }
}
