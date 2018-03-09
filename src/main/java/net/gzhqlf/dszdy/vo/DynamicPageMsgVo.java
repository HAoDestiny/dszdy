package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
public class DynamicPageMsgVo {

    private int userId = 0;

    @NotNull(message = "当前页")
    private int pageCode;

    @NotNull(message = "显示条数")
    private int pageSize;

    @NotNull(message = "动态类型")
    private int tag;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPageCode() {
        return pageCode;
    }

    public void setPageCode(int pageCode) {
        this.pageCode = pageCode;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
