package net.gzhqlf.dszdy.po;

/**
 * Created by n0elle on 2017/10/26.
 */
public class SystemMessagePo {

    private String status;
    private Object data;
    private String message;
    private long time = System.currentTimeMillis();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }
}
