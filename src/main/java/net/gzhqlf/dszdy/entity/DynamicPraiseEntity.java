package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/10/27.
 */
@Entity
@Table(name = "dynamic_praise", schema = "dszdy", catalog = "")
public class DynamicPraiseEntity {
    private int id = 0;
    private int dynamicId;
    private int praisePersonId;

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
    @Column(name = "praise_person_id", nullable = false)
    public int getPraisePersonId() {
        return praisePersonId;
    }

    public void setPraisePersonId(int praisePersonId) {
        this.praisePersonId = praisePersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DynamicPraiseEntity that = (DynamicPraiseEntity) o;

        if (id != that.id) return false;
        if (dynamicId != that.dynamicId) return false;
        if (praisePersonId != that.praisePersonId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + dynamicId;
        result = 31 * result + praisePersonId;
        return result;
    }
}
