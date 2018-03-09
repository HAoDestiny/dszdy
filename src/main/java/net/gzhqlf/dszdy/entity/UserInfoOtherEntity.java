package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by DESTINY on 2017/10/24.
 */
@Entity
@Table(name = "user_info_other", schema = "dszdy", catalog = "")
public class UserInfoOtherEntity {
    private int id;
    private Integer weight = 0;
    private String workUnit = "";
    private String workPosition = "";
    private Integer liveProvinceId = 0;
    private Integer liveCityId = 0;
    private Integer liveAreaId = 0;
    private Integer constellation = 0;
    private Integer career = 0;
    private Integer hasCar = 0;
    private Integer hasHouse = 0;
    private Integer yearIncome = 0;
    private String school = "";
    private Integer marriageStatus = 0;
    private String familyStructure = "";
    private Integer characterColor = 0;
    private String interest = "";
    private String selfIntroduction = "";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "weight")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "work_unit")
    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    @Basic
    @Column(name = "work_position")
    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    @Basic
    @Column(name = "live_province_id")
    public Integer getLiveProvinceId() {
        return liveProvinceId;
    }

    public void setLiveProvinceId(Integer liveProvinceId) {
        this.liveProvinceId = liveProvinceId;
    }

    @Basic
    @Column(name = "live_city_id")
    public Integer getLiveCityId() {
        return liveCityId;
    }

    public void setLiveCityId(Integer liveCityId) {
        this.liveCityId = liveCityId;
    }

    @Basic
    @Column(name = "live_area_id")
    public Integer getLiveAreaId() {
        return liveAreaId;
    }

    public void setLiveAreaId(Integer liveAreaId) {
        this.liveAreaId = liveAreaId;
    }

    @Basic
    @Column(name = "constellation")
    public Integer getConstellation() {
        return constellation;
    }

    public void setConstellation(Integer constellation) {
        this.constellation = constellation;
    }

    @Basic
    @Column(name = "career")
    public Integer getCareer() {
        return career;
    }

    public void setCareer(Integer career) {
        this.career = career;
    }

    @Basic
    @Column(name = "has_car")
    public Integer getHasCar() {
        return hasCar;
    }

    public void setHasCar(Integer hasCar) {
        this.hasCar = hasCar;
    }

    @Basic
    @Column(name = "has_house")
    public Integer getHasHouse() {
        return hasHouse;
    }

    public void setHasHouse(Integer hasHouse) {
        this.hasHouse = hasHouse;
    }

    @Basic
    @Column(name = "year_income")
    public Integer getYearIncome() {
        return yearIncome;
    }

    public void setYearIncome(Integer yearIncome) {
        this.yearIncome = yearIncome;
    }

    @Basic
    @Column(name = "school")
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Basic
    @Column(name = "marriage_status")
    public Integer getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    @Basic
    @Column(name = "family_structure")
    public String getFamilyStructure() {
        return familyStructure;
    }

    public void setFamilyStructure(String familyStructure) {
        this.familyStructure = familyStructure;
    }

    @Basic
    @Column(name = "character_color")
    public Integer getCharacterColor() {
        return characterColor;
    }

    public void setCharacterColor(Integer characterColor) {
        this.characterColor = characterColor;
    }

    @Basic
    @Column(name = "interest")
    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    @Basic
    @Column(name = "self_introduction")
    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfoOtherEntity that = (UserInfoOtherEntity) o;

        if (id != that.id) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (workUnit != null ? !workUnit.equals(that.workUnit) : that.workUnit != null) return false;
        if (workPosition != null ? !workPosition.equals(that.workPosition) : that.workPosition != null) return false;
        if (liveProvinceId != null ? !liveProvinceId.equals(that.liveProvinceId) : that.liveProvinceId != null)
            return false;
        if (liveCityId != null ? !liveCityId.equals(that.liveCityId) : that.liveCityId != null) return false;
        if (liveAreaId != null ? !liveAreaId.equals(that.liveAreaId) : that.liveAreaId != null) return false;
        if (constellation != null ? !constellation.equals(that.constellation) : that.constellation != null)
            return false;
        if (career != null ? !career.equals(that.career) : that.career != null) return false;
        if (hasCar != null ? !hasCar.equals(that.hasCar) : that.hasCar != null) return false;
        if (hasHouse != null ? !hasHouse.equals(that.hasHouse) : that.hasHouse != null) return false;
        if (yearIncome != null ? !yearIncome.equals(that.yearIncome) : that.yearIncome != null) return false;
        if (school != null ? !school.equals(that.school) : that.school != null) return false;
        if (marriageStatus != null ? !marriageStatus.equals(that.marriageStatus) : that.marriageStatus != null)
            return false;
        if (familyStructure != null ? !familyStructure.equals(that.familyStructure) : that.familyStructure != null)
            return false;
        if (characterColor != null ? !characterColor.equals(that.characterColor) : that.characterColor != null)
            return false;
        if (interest != null ? !interest.equals(that.interest) : that.interest != null) return false;
        if (selfIntroduction != null ? !selfIntroduction.equals(that.selfIntroduction) : that.selfIntroduction != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (workUnit != null ? workUnit.hashCode() : 0);
        result = 31 * result + (workPosition != null ? workPosition.hashCode() : 0);
        result = 31 * result + (liveProvinceId != null ? liveProvinceId.hashCode() : 0);
        result = 31 * result + (liveCityId != null ? liveCityId.hashCode() : 0);
        result = 31 * result + (liveAreaId != null ? liveAreaId.hashCode() : 0);
        result = 31 * result + (constellation != null ? constellation.hashCode() : 0);
        result = 31 * result + (career != null ? career.hashCode() : 0);
        result = 31 * result + (hasCar != null ? hasCar.hashCode() : 0);
        result = 31 * result + (hasHouse != null ? hasHouse.hashCode() : 0);
        result = 31 * result + (yearIncome != null ? yearIncome.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (marriageStatus != null ? marriageStatus.hashCode() : 0);
        result = 31 * result + (familyStructure != null ? familyStructure.hashCode() : 0);
        result = 31 * result + (characterColor != null ? characterColor.hashCode() : 0);
        result = 31 * result + (interest != null ? interest.hashCode() : 0);
        result = 31 * result + (selfIntroduction != null ? selfIntroduction.hashCode() : 0);
        return result;
    }
}
