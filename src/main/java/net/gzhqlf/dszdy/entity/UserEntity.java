package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by DESTINY on 2017/10/24.
 */
@Entity
@Table(name = "user", schema = "dszdy", catalog = "")
public class UserEntity {
    private int id;
    private String mobile;
    private String password;
    private String registerIp;
    private int registerTime;
    private String lastLoginIp;
    private int lastLoginTime;
    private int userLock = 0;
    private int userType = 0;
    private int userInfoId = 0;
    private int userInfoOtherId = 0;
    private int userMateId = 0;
    private int userPopularId = 0;
    private int userProveId = 0;
    private int userProvePhotoId = 0;
    private int isVerify = 0;
    private Integer flowerTotal = 0;
    private Integer balance = 0;
    private String vipType = "";
    private Integer vipExp = 0;
    private int deleteTag = 0;

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
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "register_ip")
    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    @Basic
    @Column(name = "register_time")
    public int getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(int registerTime) {
        this.registerTime = registerTime;
    }

    @Basic
    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Basic
    @Column(name = "last_login_time")
    public int getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(int lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "user_lock")
    public int getUserLock() {
        return userLock;
    }

    public void setUserLock(int userLock) {
        this.userLock = userLock;
    }

    @Basic
    @Column(name = "user_type")
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "user_info_id")
    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Basic
    @Column(name = "user_info_other_id")
    public int getUserInfoOtherId() {
        return userInfoOtherId;
    }

    public void setUserInfoOtherId(int userInfoOtherId) {
        this.userInfoOtherId = userInfoOtherId;
    }

    @Basic
    @Column(name = "user_mate_id")
    public int getUserMateId() {
        return userMateId;
    }

    public void setUserMateId(int userMateId) {
        this.userMateId = userMateId;
    }

    @Basic
    @Column(name = "user_popular_id")
    public int getUserPopularId() {
        return userPopularId;
    }

    public void setUserPopularId(int userPopularId) {
        this.userPopularId = userPopularId;
    }

    @Basic
    @Column(name = "user_prove_id")
    public int getUserProveId() {
        return userProveId;
    }

    public void setUserProveId(int userProveId) {
        this.userProveId = userProveId;
    }

    @Basic
    @Column(name = "user_prove_photo_id")
    public int getUserProvePhotoId() {
        return userProvePhotoId;
    }

    public void setUserProvePhotoId(int userProvePhotoId) {
        this.userProvePhotoId = userProvePhotoId;
    }

    @Basic
    @Column(name = "is_verify")
    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

    @Basic
    @Column(name = "flower_total")
    public Integer getFlowerTotal() {
        return flowerTotal;
    }

    public void setFlowerTotal(Integer flowerTotal) {
        this.flowerTotal = flowerTotal;
    }

    @Basic
    @Column(name = "balance")
    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "vip_type")
    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    @Basic
    @Column(name = "vip_exp")
    public Integer getVipExp() {
        return vipExp;
    }

    public void setVipExp(Integer vipExp) {
        this.vipExp = vipExp;
    }

    @Basic
    @Column(name = "delete_tag")
    public int getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(int deleteTag) {
        this.deleteTag = deleteTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (registerTime != that.registerTime) return false;
        if (lastLoginTime != that.lastLoginTime) return false;
        if (userLock != that.userLock) return false;
        if (userType != that.userType) return false;
        if (userInfoId != that.userInfoId) return false;
        if (userInfoOtherId != that.userInfoOtherId) return false;
        if (userMateId != that.userMateId) return false;
        if (userPopularId != that.userPopularId) return false;
        if (userProveId != that.userProveId) return false;
        if (userProvePhotoId != that.userProvePhotoId) return false;
        if (isVerify != that.isVerify) return false;
        if (deleteTag != that.deleteTag) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (registerIp != null ? !registerIp.equals(that.registerIp) : that.registerIp != null) return false;
        if (lastLoginIp != null ? !lastLoginIp.equals(that.lastLoginIp) : that.lastLoginIp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (registerIp != null ? registerIp.hashCode() : 0);
        result = 31 * result + registerTime;
        result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
        result = 31 * result + lastLoginTime;
        result = 31 * result + userLock;
        result = 31 * result + userType;
        result = 31 * result + userInfoId;
        result = 31 * result + userInfoOtherId;
        result = 31 * result + userMateId;
        result = 31 * result + userPopularId;
        result = 31 * result + userProveId;
        result = 31 * result + userProvePhotoId;
        result = 31 * result + isVerify;
        result = 31 * result + deleteTag;
        return result;
    }
}
