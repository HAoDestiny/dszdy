package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DynamicMsgVo {

    @NotNull(message = "内容")
    @NotEmpty(message = "内容")
    private String content;

    private String images;

    private long time = System.currentTimeMillis()/1000;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public long getTime() {
        return time;
    }
}
