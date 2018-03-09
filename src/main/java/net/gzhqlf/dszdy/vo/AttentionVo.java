package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/2.
 */
public class AttentionVo {

    @NotNull(message = "关注对象")
    private int toAttentionId;
    @NotNull(message = "类型")
    private int type;

    public int getToAttentionId() {
        return toAttentionId;
    }

    public void setToAttentionId(int toAttentionId) {
        this.toAttentionId = toAttentionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
