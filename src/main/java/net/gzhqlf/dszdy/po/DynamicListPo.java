package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
public class DynamicListPo {
    private Object dynamic;
    private int pageSize;
    private int page;
    private int pageTotal;
    private int userId;

    public Object getDynamic() {
        return dynamic;
    }

    public void setDynamic(Object dynamic) {
        this.dynamic = dynamic;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
