package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
@Entity
@Table(name = "attention_dynamic", schema = "dszdy", catalog = "")
public class AttentionDynamicEntity {
    private int id;
    private int userId;
    private Integer atentionId;

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
    @Column(name = "atention_id", nullable = true)
    public Integer getAtentionId() {
        return atentionId;
    }

    public void setAtentionId(Integer atentionId) {
        this.atentionId = atentionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttentionDynamicEntity that = (AttentionDynamicEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (atentionId != null ? !atentionId.equals(that.atentionId) : that.atentionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (atentionId != null ? atentionId.hashCode() : 0);
        return result;
    }
}
