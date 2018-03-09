package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/3.
 */
public class FansOrAttentionPo {

    private FilePo avatar;
    private String fansName;
    private int fansId;
    private int fansSex;
    private String age;
    private int time;
    private String attentionStatus;

    public FilePo getAvatar() {
        return avatar;
    }

    public void setAvatar(FilePo avatar) {
        this.avatar = avatar;
    }

    public String getFansName() {
        return fansName;
    }

    public void setFansName(String fansName) {
        this.fansName = fansName;
    }

    public int getFansId() {
        return fansId;
    }

    public void setFansId(int fansId) {
        this.fansId = fansId;
    }

    public int getFansSex() {
        return fansSex;
    }

    public void setFansSex(int fansSex) {
        this.fansSex = fansSex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getAttentionStatus() {
        return attentionStatus;
    }

    public void setAttentionStatus(String attentionStatus) {
        this.attentionStatus = attentionStatus;
    }

}
