package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/8.
 */
public class UserEntityInfoPo {

    private Object userInfo;
    private int userId;
    private String mobile;
    private String educationValue;
    private String originProvinceValue;
    private String originCityValue;
    private int registerTime;
    private int lastLoginTime;
    private String lastLoginIp;
    private int balance;
    private String vipType;
    private int vipExp;
    private int isExp = 1;
    private int deleteTag;

    public Object getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEducationValue() {
        return educationValue;
    }

    public void setEducationValue(String educationValue) {
        this.educationValue = educationValue;
    }

    public String getOriginProvinceValue() {
        return originProvinceValue;
    }

    public void setOriginProvinceValue(String originProvinceValue) {
        this.originProvinceValue = originProvinceValue;
    }

    public String getOriginCityValue() {
        return originCityValue;
    }

    public void setOriginCityValue(String originCityValue) {
        this.originCityValue = originCityValue;
    }

    public int getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(int registerTime) {
        this.registerTime = registerTime;
    }

    public int getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(int lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public int getVipExp() {
        return vipExp;
    }

    public void setVipExp(int vipExp) {
        this.vipExp = vipExp;
    }

    public int getIsExp() {
        return isExp;
    }

    public void setIsExp(int isExp) {
        this.isExp = isExp;
    }

    public int getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(int deleteTag) {
        this.deleteTag = deleteTag;
    }
}
