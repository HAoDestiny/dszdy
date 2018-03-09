package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/9.
 */
public class JoinActivityVo {

    @NotNull(message = "用户Id")
    private int userId;

    @NotNull(message = "活动Id")
    private int activityId;

    private int addVip = 0;
    private int goodsId = 0;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getAddVip() {
        return addVip;
    }

    public void setAddVip(int addVip) {
        this.addVip = addVip;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
