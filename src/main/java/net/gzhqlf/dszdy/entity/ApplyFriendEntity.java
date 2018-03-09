package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
@Entity
@Table(name = "apply_friend", schema = "dszdy", catalog = "")
public class ApplyFriendEntity {
    private int id;
    private Integer applyId;
    private Integer applyObjectId;
    private Integer createTime;
    private Integer accept = 0; //请求中
    private Integer deleteTag = 0;

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
    @Column(name = "apply_id", nullable = true)
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    @Basic
    @Column(name = "apply_object_id", nullable = true)
    public Integer getApplyObjectId() {
        return applyObjectId;
    }

    public void setApplyObjectId(Integer applyObjectId) {
        this.applyObjectId = applyObjectId;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "accept", nullable = true)
    public Integer getAccept() {
        return accept;
    }

    public void setAccept(Integer accept) {
        this.accept = accept;
    }

    @Basic
    @Column(name = "delete_tag", nullable = true)
    public Integer getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(Integer deleteTag) {
        this.deleteTag = deleteTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplyFriendEntity that = (ApplyFriendEntity) o;

        if (id != that.id) return false;
        if (applyId != null ? !applyId.equals(that.applyId) : that.applyId != null) return false;
        if (applyObjectId != null ? !applyObjectId.equals(that.applyObjectId) : that.applyObjectId != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (accept != null ? !accept.equals(that.accept) : that.accept != null) return false;
        if (deleteTag != null ? !deleteTag.equals(that.deleteTag) : that.deleteTag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (applyId != null ? applyId.hashCode() : 0);
        result = 31 * result + (applyObjectId != null ? applyObjectId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (accept != null ? accept.hashCode() : 0);
        result = 31 * result + (deleteTag != null ? deleteTag.hashCode() : 0);
        return result;
    }
}
