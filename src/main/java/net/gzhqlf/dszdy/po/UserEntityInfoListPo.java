package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/8.
 */
public class UserEntityInfoListPo {

    private Object userInfoListPo;
    private int pageSize;
    private int page;
    private int pageTotal;

    public Object getUserInfoListPo() {
        return userInfoListPo;
    }

    public void setUserInfoListPo(Object userInfoListPo) {
        this.userInfoListPo = userInfoListPo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }
}
