package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by DESTINY on 2017/10/22.
 */
public class UserInfoMsgVo {
    @NotNull(message = "性别")
    @NotEmpty(message = "性别")
    private String sex;

    @NotNull(message = "姓名")
    @NotEmpty(message = "姓名")
    private String trueName;

    @NotNull(message = "出生年月")
    @NotEmpty(message = "出生年月")
    private String birth;

    @NotNull(message = "省")
    @NotEmpty(message = "省")
    private String provinceID;

    @NotNull(message = "市")
    @NotEmpty(message = "市")
    private String cityID;

    @NotNull(message = "区")
    @NotEmpty(message = "区")
    private String areaID;

    @NotNull(message = "身高")
    @NotEmpty(message = "身高")
    private String height;

    @NotNull(message = "学历")
    @NotEmpty(message = "学历")
    private String education;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(String provinceID) {
        this.provinceID = provinceID;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
