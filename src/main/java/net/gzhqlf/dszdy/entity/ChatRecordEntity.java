package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
@Entity
@Table(name = "chat_record", schema = "dszdy", catalog = "")
public class ChatRecordEntity {
    private int id;
    private Integer formUid;
    private Integer toUid;
    private String chatContent;
    private Integer createTime;
    private Integer status;

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
    @Column(name = "form_uid", nullable = true)
    public Integer getFormUid() {
        return formUid;
    }

    public void setFormUid(Integer formUid) {
        this.formUid = formUid;
    }

    @Basic
    @Column(name = "to_uid", nullable = true)
    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    @Basic
    @Column(name = "chat_content", nullable = true, length = 255)
    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
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
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRecordEntity that = (ChatRecordEntity) o;
        if (id != that.id) return false;
        if (formUid != null ? !formUid.equals(that.formUid) : that.formUid != null) return false;
        if (toUid != null ? !toUid.equals(that.toUid) : that.toUid != null) return false;
        if (chatContent != null ? !chatContent.equals(that.chatContent) : that.chatContent != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (formUid != null ? formUid.hashCode() : 0);
        result = 31 * result + (toUid != null ? toUid.hashCode() : 0);
        result = 31 * result + (chatContent != null ? chatContent.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
