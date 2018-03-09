package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/11/2.
 */
@Entity
@Table(name = "user_popular", schema = "dszdy", catalog = "")
public class UserPopularEntity {
    private int id;
    private Integer recommendId = 0;
    private Integer toAttentionTotal = 0;
    private Integer formAttentionTotal = 0;

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
    @Column(name = "recommend_id", nullable = true)
    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    @Basic
    @Column(name = "to_attention_total", nullable = true)
    public Integer getToAttentionTotal() {
        return toAttentionTotal;
    }

    public void setToAttentionTotal(Integer toAttentionTotal) {
        this.toAttentionTotal = toAttentionTotal;
    }

    @Basic
    @Column(name = "form_attention_total", nullable = true)
    public Integer getFormAttentionTotal() {
        return formAttentionTotal;
    }

    public void setFormAttentionTotal(Integer formAttentionTotal) {
        this.formAttentionTotal = formAttentionTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPopularEntity that = (UserPopularEntity) o;

        if (id != that.id) return false;
        if (recommendId != null ? !recommendId.equals(that.recommendId) : that.recommendId != null) return false;
        if (toAttentionTotal != null ? !toAttentionTotal.equals(that.toAttentionTotal) : that.toAttentionTotal != null)
            return false;
        if (formAttentionTotal != null ? !formAttentionTotal.equals(that.formAttentionTotal) : that.formAttentionTotal != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (recommendId != null ? recommendId.hashCode() : 0);
        result = 31 * result + (toAttentionTotal != null ? toAttentionTotal.hashCode() : 0);
        result = 31 * result + (formAttentionTotal != null ? formAttentionTotal.hashCode() : 0);
        return result;
    }
}
