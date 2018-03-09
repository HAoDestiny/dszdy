package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
public class PraiseDynamicVo {

    @NotNull(message = "动态Id")
    private int dynamicId;

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }
}
