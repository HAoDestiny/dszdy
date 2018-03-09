package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
@Entity
@Table(name = "dynamic_comment", schema = "dszdy", catalog = "")
public class DynamicCommentEntity {
    private int id;
    private int dynamicId;
    private int commentToUserId;
    private int commentPersonId;
    private String commentContent;
    private long createCommentTime;
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
    @Column(name = "dynamic_id", nullable = false)
    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    @Basic
    @Column(name = "comment_to_user_id", nullable = false)
    public int getCommentToUserId() {
        return commentToUserId;
    }

    public void setCommentToUserId(int commentToUserId) {
        this.commentToUserId = commentToUserId;
    }

    @Basic
    @Column(name = "comment_person_id", nullable = false)
    public int getCommentPersonId() {
        return commentPersonId;
    }

    public void setCommentPersonId(int commentPersonId) {
        this.commentPersonId = commentPersonId;
    }

    @Basic
    @Column(name = "comment_content", nullable = false, length = 255)
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Basic
    @Column(name = "create_comment_time", nullable = false)
    public long getCreateCommentTime() {
        return createCommentTime;
    }

    public void setCreateCommentTime(long createCommentTime) {
        this.createCommentTime = createCommentTime;
    }

    @Basic
    @Column(name = "delete_tag")
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

        DynamicCommentEntity that = (DynamicCommentEntity) o;

        if (id != that.id) return false;
        if (dynamicId != that.dynamicId) return false;
        if (commentToUserId != that.commentToUserId) return false;
        if (commentPersonId != that.commentPersonId) return false;
        if (createCommentTime != that.createCommentTime) return false;
        if (deleteTag != that.deleteTag) return false;
        if (commentContent != null ? !commentContent.equals(that.commentContent) : that.commentContent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + dynamicId;
        result = 31 * result + commentToUserId;
        result = 31 * result + commentPersonId;
        result = 31 * result + (commentContent != null ? commentContent.hashCode() : 0);
        result = 31 * result + deleteTag;
        return result;
    }
}
