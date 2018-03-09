package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/19.
 */
public class AdminSearchOrderVo {

    @NotNull(message = "搜索类型")
    private int type;

    @NotNull(message = "当前页")
    private int pageCode;

    @NotNull(message = "显示条数")
    private int pageSize;

    @NotEmpty(message = "搜索内容")
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
