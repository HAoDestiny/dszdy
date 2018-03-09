package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/10/31.
 */
@Entity
@Table(name = "user_mate", schema = "dszdy", catalog = "")
public class UserMateEntity {
    private int id;
    private String ageRange = "";
    private Integer education = 0;
    private Integer marriageStatus = 0;
    private String refuseZodiac = "";
    private String heightRange = "";

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
    @Column(name = "age_range", nullable = true, length = 10)
    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
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
    @Column(name = "marriage_status", nullable = true)
    public Integer getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    @Basic
    @Column(name = "refuse_zodiac", nullable = true, length = 4)
    public String getRefuseZodiac() {
        return refuseZodiac;
    }

    public void setRefuseZodiac(String refuseZodiac) {
        this.refuseZodiac = refuseZodiac;
    }

    @Basic
    @Column(name = "height_range", nullable = true, length = 10)
    public String getHeightRange() {
        return heightRange;
    }

    public void setHeightRange(String heightRange) {
        this.heightRange = heightRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMateEntity that = (UserMateEntity) o;

        if (id != that.id) return false;
        if (ageRange != null ? !ageRange.equals(that.ageRange) : that.ageRange != null) return false;
        if (education != null ? !education.equals(that.education) : that.education != null) return false;
        if (marriageStatus != null ? !marriageStatus.equals(that.marriageStatus) : that.marriageStatus != null)
            return false;
        if (refuseZodiac != null ? !refuseZodiac.equals(that.refuseZodiac) : that.refuseZodiac != null) return false;
        if (heightRange != null ? !heightRange.equals(that.heightRange) : that.heightRange != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ageRange != null ? ageRange.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (marriageStatus != null ? marriageStatus.hashCode() : 0);
        result = 31 * result + (refuseZodiac != null ? refuseZodiac.hashCode() : 0);
        result = 31 * result + (heightRange != null ? heightRange.hashCode() : 0);
        return result;
    }
}
