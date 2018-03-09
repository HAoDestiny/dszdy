package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/1.
 */
public class ApplyFriendsVo {

    @NotNull(message = "请求对象id")
    private int applyObjectId;

    @NotNull(message = "请求类型")
    private int type;

    private int accept = 0;
    private long createTime = (System.currentTimeMillis()/1000);

    public int getApplyObjectId() {
        return applyObjectId;
    }

    public void setApplyObjectId(int applyObjectId) {
        this.applyObjectId = applyObjectId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public long getCreateTime() {
        return createTime;
    }
}
