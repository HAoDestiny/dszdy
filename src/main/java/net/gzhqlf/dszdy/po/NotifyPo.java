package net.gzhqlf.dszdy.po;

public class NotifyPo {

    private int notifyCounts;
    private Object notify;
    private int notifyUserId;
    private long notifyTime = System.currentTimeMillis()/1000;

    public int getNotifyCounts() {
        return notifyCounts;
    }

    public void setNotifyCounts(int notifyCounts) {
        this.notifyCounts = notifyCounts;
    }

    public Object getNotify() {
        return notify;
    }

    public void setNotify(Object notify) {
        this.notify = notify;
    }

    public int getNotifyUserId() {
        return notifyUserId;
    }

    public void setNotifyUserId(int notifyUserId) {
        this.notifyUserId = notifyUserId;
    }

    public long getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(long notifyTime) {
        this.notifyTime = notifyTime;
    }
}
