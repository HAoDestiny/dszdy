package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/6.
 */
public class AdminProveListVo {

    @NotNull(message = "列表类型")
    private int type;

    @NotNull(message = "当前页")
    private int pageCode;

    @NotNull(message = "显示条数")
    private int pageSize;

    private String nickname;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
