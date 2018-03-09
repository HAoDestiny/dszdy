package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/9.
 */
public class ActivityPersonListPo {

    private Object activityPersonPo;
    private int pageSize;
    private int page;
    private int pageTotal;
    private int activityId;
    private int userListType;

    public Object getActivityPersonPo() {
        return activityPersonPo;
    }

    public void setActivityPersonPo(Object activityPersonPo) {
        this.activityPersonPo = activityPersonPo;
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

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getUserListType() {
        return userListType;
    }

    public void setUserListType(int userListType) {
        this.userListType = userListType;
    }
}
