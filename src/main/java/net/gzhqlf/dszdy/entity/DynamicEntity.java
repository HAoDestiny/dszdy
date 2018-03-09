package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
@Entity
@Table(name = "dynamic", schema = "dszdy", catalog = "")
public class DynamicEntity {

    private int id;
    private int userId;
    private String content;
    private Integer praiseTotal = 0;
    private Integer commentTotal = 0;
    private String images = "";
    private Integer cityCode;
    private String schoolCode;
    private Integer createTime;
    private int deleteTag;

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
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "content", nullable = false, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "praise_total", nullable = true)
    public Integer getPraiseTotal() {
        return praiseTotal;
    }

    public void setPraiseTotal(Integer praiseTotal) {
        this.praiseTotal = praiseTotal;
    }

    @Basic
    @Column(name = "comment_total", nullable = true)
    public Integer getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(Integer commentTotal) {
        this.commentTotal = commentTotal;
    }

    @Basic
    @Column(name = "images", nullable = true)
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Basic
    @Column(name = "city_code", nullable = true)
    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "school_code", nullable = true)
    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
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

        DynamicEntity that = (DynamicEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (createTime != that.createTime) return false;
        if (deleteTag != that.deleteTag) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (schoolCode != null ? !schoolCode.equals(that.schoolCode) : that.schoolCode != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (praiseTotal != null ? !praiseTotal.equals(that.praiseTotal) : that.praiseTotal != null) return false;
        if (commentTotal != null ? !commentTotal.equals(that.commentTotal) : that.commentTotal != null) return false;
        if (images != null ? !images.equals(that.images) : that.images != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (praiseTotal != null ? praiseTotal.hashCode() : 0);
        result = 31 * result + (commentTotal != null ? commentTotal.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + deleteTag;
        return result;
    }
}
