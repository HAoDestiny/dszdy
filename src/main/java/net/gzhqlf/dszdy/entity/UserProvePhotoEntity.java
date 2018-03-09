package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/11/2.
 */
@Entity
@Table(name = "user_prove_photo", schema = "dszdy", catalog = "")
public class UserProvePhotoEntity {
    private int id;
    private Integer identity;
    private Integer education;
    private Integer car;
    private Integer house;

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
    @Column(name = "identity", nullable = true)
    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    @Basic
    @Column(name = "education", nullable = true)
    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    @Basic
    @Column(name = "car", nullable = true)
    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    @Basic
    @Column(name = "house", nullable = true)
    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProvePhotoEntity that = (UserProvePhotoEntity) o;

        if (id != that.id) return false;
        if (identity != null ? !identity.equals(that.identity) : that.identity != null) return false;
        if (education != null ? !education.equals(that.education) : that.education != null) return false;
        if (car != null ? !car.equals(that.car) : that.car != null) return false;
        if (house != null ? !house.equals(that.house) : that.house != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (identity != null ? identity.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        return result;
    }
}
