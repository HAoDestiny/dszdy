package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
@Entity
@Table(name = "city_activity", schema = "dszdy", catalog = "")
public class CityActivityEntity {
    private int id;
    private String activityTitle;
    private String activityTag;
    private String activityUrl;
    private int activityPic;
    private int createTime = (int) (System.currentTimeMillis()/1000);
    private int activityStatus = 0;
    private int deleteTag = 0;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "activity_title", nullable = false, length = 180)
    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    @Basic
    @Column(name = "activity_tag", nullable = true, length = 32)
    public String getActivityTag() {
        return activityTag;
    }

    public void setActivityTag(String activityTag) {
        this.activityTag = activityTag;
    }

    @Basic
    @Column(name = "activity_url", nullable = false, length = 255)
    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    @Basic
    @Column(name = "activity_pic", nullable = false, length = 255)
    public int getActivityPic() {
        return activityPic;
    }

    public void setActivityPic(int activityPic) {
        this.activityPic = activityPic;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "activity_status", nullable = false)
    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }

    @Basic
    @Column(name = "delete_tag", nullable = false)
    public int getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(int deleteTag) {
        this.deleteTag = deleteTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityActivityEntity that = (CityActivityEntity) o;

        if (id != that.id) return false;
        if (createTime != that.createTime) return false;
        if (activityStatus != that.activityStatus) return false;
        if (deleteTag != that.deleteTag) return false;
        if (activityTitle != null ? !activityTitle.equals(that.activityTitle) : that.activityTitle != null)
            return false;
        if (activityTag != null ? !activityTag.equals(that.activityTag) : that.activityTag != null) return false;
        if (activityUrl != null ? !activityUrl.equals(that.activityUrl) : that.activityUrl != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (activityTitle != null ? activityTitle.hashCode() : 0);
        result = 31 * result + (activityTag != null ? activityTag.hashCode() : 0);
        result = 31 * result + (activityUrl != null ? activityUrl.hashCode() : 0);
        result = 31 * result + createTime;
        result = 31 * result + activityStatus;
        result = 31 * result + deleteTag;
        return result;
    }
}
