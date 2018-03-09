package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/7.
 */
public class AdminCityActivityVo {

    @NotNull(message = "活动标题")
    @NotEmpty(message = "活动标题")
    private String activityTitle;

    @NotNull(message = "活动简介")
    @NotEmpty(message = "活动简介")
    private String activityTag;

    @NotNull(message = "活动链接")
    @NotEmpty(message = "活动链接")
    private String activityUrl;

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityTag() {
        return activityTag;
    }

    public void setActivityTag(String activityTag) {
        this.activityTag = activityTag;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }
}
