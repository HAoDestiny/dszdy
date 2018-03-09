package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/10/29.
 */
public class UserInfoOtherVo {

    @NotNull(message = "体重")
    private int weight;

    @NotNull(message = "省")
    private int liveProvinceId;

    @NotNull(message = "市")
    private int liveCityId;

    @NotNull(message = "区")
    private int liveAreaId;

    @NotNull(message = "星座")
    private int constellation;

    @NotNull(message = "职业")
    private int profession;

    @NotNull(message = "年收入")
    private int income;

    @NotNull(message = "婚姻状况")
    private int maritalStatus = 0;

    private int hasCar = 0;
    private int hasHome = 0;

    private int characterColor = 0;

    private String workPosition = "";
    private String company = "";

    @NotNull(message = "学校")
    @NotEmpty(message = "学校")
    private String school = "";
    private String familyStruct = "";
    private String interest = "";
    private String selfIntroduction = "";

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLiveProvinceId() {
        return liveProvinceId;
    }

    public void setLiveProvinceId(int liveProvinceId) {
        this.liveProvinceId = liveProvinceId;
    }

    public int getLiveCityId() {
        return liveCityId;
    }

    public void setLiveCityId(int liveCityId) {
        this.liveCityId = liveCityId;
    }

    public int getLiveAreaId() {
        return liveAreaId;
    }

    public void setLiveAreaId(int liveAreaId) {
        this.liveAreaId = liveAreaId;
    }

    public int getConstellation() {
        return constellation;
    }

    public void setConstellation(int constellation) {
        this.constellation = constellation;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getHasCar() {
        return hasCar;
    }

    public void setHasCar(int hasCar) {
        this.hasCar = hasCar;
    }

    public int getHasHome() {
        return hasHome;
    }

    public void setHasHome(int hasHome) {
        this.hasHome = hasHome;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getCharacterColor() {
        return characterColor;
    }

    public void setCharacterColor(int characterColor) {
        this.characterColor = characterColor;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFamilyStruct() {
        return familyStruct;
    }

    public void setFamilyStruct(String familyStruct) {
        this.familyStruct = familyStruct;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }
}
