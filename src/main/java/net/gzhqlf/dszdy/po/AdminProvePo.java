package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/6.
 */
public class AdminProvePo {

    private String trueName;
    private int userId;
    private String mobile;
    private int fileId;
    private FilePo provePic;

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public FilePo getProvePic() {
        return provePic;
    }

    public void setProvePic(FilePo provePic) {
        this.provePic = provePic;
    }
}
