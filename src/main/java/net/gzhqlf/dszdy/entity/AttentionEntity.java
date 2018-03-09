package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/11/1.
 */
@Entity
@Table(name = "attention", schema = "dszdy", catalog = "")
public class AttentionEntity {
    private int id;
    private int createTime = (int) (System.currentTimeMillis()/1000);
    private int formAttentionId;
    private int toAttentionId;
    private int attentionStatus;

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
    @Column(name = "create_time", nullable = false)
    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "form_attention_id", nullable = false)
    public int getFormAttentionId() {
        return formAttentionId;
    }

    public void setFormAttentionId(int formAttentionId) {
        this.formAttentionId = formAttentionId;
    }

    @Basic
    @Column(name = "to_attention_id", nullable = false)
    public int getToAttentionId() {
        return toAttentionId;
    }

    public void setToAttentionId(int toAttentionId) {
        this.toAttentionId = toAttentionId;
    }

    @Basic
    @Column(name = "attention_status", nullable = false)
    public int getAttentionStatus() {
        return attentionStatus;
    }

    public void setAttentionStatus(int attentionStatus) {
        this.attentionStatus = attentionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttentionEntity that = (AttentionEntity) o;

        if (id != that.id) return false;
        if (createTime != that.createTime) return false;
        if (formAttentionId != that.formAttentionId) return false;
        if (toAttentionId != that.toAttentionId) return false;
        if (attentionStatus != that.attentionStatus) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + createTime;
        result = 31 * result + formAttentionId;
        result = 31 * result + toAttentionId;
        result = 31 * result + attentionStatus;
        return result;
    }
}
