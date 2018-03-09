package net.gzhqlf.dszdy.po;

import net.gzhqlf.dszdy.entity.ApplyFriendEntity;

/**
 * Created by Destiny_hao on 2017/11/1.
 */
public class ApplyFriendsPo {

    private FilePo avatar;
    private String trueName;
    private int userId;
    private ApplyFriendEntity applyFriendEntity;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ApplyFriendEntity getApplyFriendEntity() {
        return applyFriendEntity;
    }

    public void setApplyFriendEntity(ApplyFriendEntity applyFriendEntity) {
        this.applyFriendEntity = applyFriendEntity;
    }
}
