package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/4.
 */
public class ApplyFriendsListPo {

    private Object applyFriendsPoList;
    private int pageSize;
    private int page;
    private int pageTotal;
    private int userId;

    public Object getApplyFriendsPoList() {
        return applyFriendsPoList;
    }

    public void setApplyFriendsPoList(Object applyFriendsPoList) {
        this.applyFriendsPoList = applyFriendsPoList;
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
