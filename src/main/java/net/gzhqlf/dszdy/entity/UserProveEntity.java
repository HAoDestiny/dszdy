package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/11/5.
 */
@Entity
@Table(name = "user_prove", schema = "dszdy", catalog = "")
public class UserProveEntity {
    private int id;
    private Integer identityPro = 0;
    private Integer educationPro = 0;
    private Integer carPro = 0;
    private Integer housePro = 0;
    private Integer peopleStatus = 0;
    private Integer identityStatus = 0;
    private Integer educationStatus = 0;
    private Integer carStatus = 0;
    private Integer houseStatus = 0;
    private String identityRefuse = "";
    private String educationRefuse = "";
    private String carRefuse = "";
    private String houseRefuse = "";

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
    @Column(name = "identity_pro", nullable = true)
    public Integer getIdentityPro() {
        return identityPro;
    }

    public void setIdentityPro(Integer identityPro) {
        this.identityPro = identityPro;
    }

    @Basic
    @Column(name = "education_pro", nullable = true)
    public Integer getEducationPro() {
        return educationPro;
    }

    public void setEducationPro(Integer educationPro) {
        this.educationPro = educationPro;
    }

    @Basic
    @Column(name = "car_pro", nullable = true)
    public Integer getCarPro() {
        return carPro;
    }

    public void setCarPro(Integer carPro) {
        this.carPro = carPro;
    }

    @Basic
    @Column(name = "house_pro", nullable = true)
    public Integer getHousePro() {
        return housePro;
    }

    public void setHousePro(Integer housePro) {
        this.housePro = housePro;
    }

    @Basic
    @Column(name = "people_status", nullable = true)
    public Integer getPeopleStatus() {
        return peopleStatus;
    }

    public void setPeopleStatus(Integer peopleStatus) {
        this.peopleStatus = peopleStatus;
    }

    @Basic
    @Column(name = "identity_status", nullable = true)
    public Integer getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(Integer identityStatus) {
        this.identityStatus = identityStatus;
    }

    @Basic
    @Column(name = "education_status", nullable = true)
    public Integer getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(Integer educationStatus) {
        this.educationStatus = educationStatus;
    }

    @Basic
    @Column(name = "car_status", nullable = true)
    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    @Basic
    @Column(name = "house_status", nullable = true)
    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    @Basic
    @Column(name = "identity_refuse", nullable = true)
    public String getIdentityRefuse() {
        return identityRefuse;
    }


    public void setIdentityRefuse(String identityRefuse) {
        this.identityRefuse = identityRefuse;
    }

    @Basic
    @Column(name = "education_refuse", nullable = true)
    public String getEducationRefuse() {
        return educationRefuse;
    }

    public void setEducationRefuse(String educationRefuse) {
        this.educationRefuse = educationRefuse;
    }
    @Basic
    @Column(name = "car_refuse", nullable = true)
    public String getCarRefuse() {
        return carRefuse;
    }

    public void setCarRefuse(String carRefuse) {
        this.carRefuse = carRefuse;
    }

    @Basic
    @Column(name = "house_refuse", nullable = true)
    public String getHouseRefuse() {
        return houseRefuse;
    }

    public void setHouseRefuse(String houseRefuse) {
        this.houseRefuse = houseRefuse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProveEntity that = (UserProveEntity) o;

        if (id != that.id) return false;
        if (identityPro != null ? !identityPro.equals(that.identityPro) : that.identityPro != null) return false;
        if (educationPro != null ? !educationPro.equals(that.educationPro) : that.educationPro != null) return false;
        if (carPro != null ? !carPro.equals(that.carPro) : that.carPro != null) return false;
        if (housePro != null ? !housePro.equals(that.housePro) : that.housePro != null) return false;
        if (identityStatus != null ? !identityStatus.equals(that.identityStatus) : that.identityStatus != null)
            return false;
        if (educationStatus != null ? !educationStatus.equals(that.educationStatus) : that.educationStatus != null)
            return false;
        if (carStatus != null ? !carStatus.equals(that.carStatus) : that.carStatus != null) return false;
        if (houseStatus != null ? !houseStatus.equals(that.houseStatus) : that.houseStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (identityPro != null ? identityPro.hashCode() : 0);
        result = 31 * result + (educationPro != null ? educationPro.hashCode() : 0);
        result = 31 * result + (carPro != null ? carPro.hashCode() : 0);
        result = 31 * result + (housePro != null ? housePro.hashCode() : 0);
        result = 31 * result + (identityStatus != null ? identityStatus.hashCode() : 0);
        result = 31 * result + (educationStatus != null ? educationStatus.hashCode() : 0);
        result = 31 * result + (carStatus != null ? carStatus.hashCode() : 0);
        result = 31 * result + (houseStatus != null ? houseStatus.hashCode() : 0);
        return result;
    }
}
