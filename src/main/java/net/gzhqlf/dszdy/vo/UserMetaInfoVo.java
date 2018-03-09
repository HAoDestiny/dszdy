package net.gzhqlf.dszdy.vo;

/**
 * Created by Destiny_hao on 2017/10/31.
 */
public class UserMetaInfoVo {

    private String ageRang = "";
    private String heightRang = "";
    private int education = 0;
    private int marriageStatus = 0;
    private String refuseZodiac = "";

    public String getAgeRang() {
        return ageRang;
    }

    public void setAgeRang(String ageRang) {
        this.ageRang = ageRang;
    }

    public String getHeightRang() {
        return heightRang;
    }

    public void setHeightRang(String heightRang) {
        this.heightRang = heightRang;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(int marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getRefuseZodiac() {
        return refuseZodiac;
    }

    public void setRefuseZodiac(String refuseZodiac) {
        this.refuseZodiac = refuseZodiac;
    }
}
