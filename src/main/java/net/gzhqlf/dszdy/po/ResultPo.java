package net.gzhqlf.dszdy.po;

/**
 * Created by DESTINY on 2017/10/18.
 */
public class ResultPo {

    private String status;
    private Object data;
    private long timestamp = System.currentTimeMillis();
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
