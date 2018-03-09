package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by DESTINY on 2017/10/24.
 */
@Entity
@Table(name = "user_info", schema = "dszdy", catalog = "")
public class UserInfoEntity {
    private int id;
    private String nickname;
    private Integer sex = 0;
    private Integer avatarNormal = 0;
    private Integer avatarProB = 0;
    private Integer avatarProC = 0;
    private Integer avatarProD = 0;
    private Integer originProvinceId = 0;
    private Integer originCityId = 0;
    private String birth;
    private Integer height  = 0;
    private Integer education = 0;

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
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "sex")
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "avatar_normal")
    public Integer getAvatarNormal() {
        return avatarNormal;
    }

    public void setAvatarNormal(Integer avatarNormal) {
        this.avatarNormal = avatarNormal;
    }

    @Basic
    @Column(name = "avatar_pro_b")
    public Integer getAvatarProB() {
        return avatarProB;
    }

    public void setAvatarProB(Integer avatarProB) {
        this.avatarProB = avatarProB;
    }

    @Basic
    @Column(name = "avatar_pro_c")
    public Integer getAvatarProC() {
        return avatarProC;
    }

    public void setAvatarProC(Integer avatarProC) {
        this.avatarProC = avatarProC;
    }

    @Basic
    @Column(name = "avatar_pro_d")
    public Integer getAvatarProD() {
        return avatarProD;
    }

    public void setAvatarProD(Integer avatarProD) {
        this.avatarProD = avatarProD;
    }

    @Basic
    @Column(name = "origin_province_id")
    public Integer getOriginProvinceId() {
        return originProvinceId;
    }

    public void setOriginProvinceId(Integer originProvinceId) {
        this.originProvinceId = originProvinceId;
    }

    @Basic
    @Column(name = "origin_city_id")
    public Integer getOriginCityId() {
        return originCityId;
    }

    public void setOriginCityId(Integer originCityId) {
        this.originCityId = originCityId;
    }

    @Basic
    @Column(name = "birth")
    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Basic
    @Column(name = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Basic
    @Column(name = "education")
    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfoEntity that = (UserInfoEntity) o;

        if (id != that.id) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (avatarNormal != null ? !avatarNormal.equals(that.avatarNormal) : that.avatarNormal != null) return false;
        if (avatarProB != null ? !avatarProB.equals(that.avatarProB) : that.avatarProB != null) return false;
        if (avatarProC != null ? !avatarProC.equals(that.avatarProC) : that.avatarProC != null) return false;
        if (avatarProD != null ? !avatarProD.equals(that.avatarProD) : that.avatarProD != null) return false;
        if (originProvinceId != null ? !originProvinceId.equals(that.originProvinceId) : that.originProvinceId != null)
            return false;
        if (originCityId != null ? !originCityId.equals(that.originCityId) : that.originCityId != null) return false;
        if (birth != null ? !birth.equals(that.birth) : that.birth != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;
        if (education != null ? !education.equals(that.education) : that.education != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (avatarNormal != null ? avatarNormal.hashCode() : 0);
        result = 31 * result + (avatarProB != null ? avatarProB.hashCode() : 0);
        result = 31 * result + (avatarProC != null ? avatarProC.hashCode() : 0);
        result = 31 * result + (avatarProD != null ? avatarProD.hashCode() : 0);
        result = 31 * result + (originProvinceId != null ? originProvinceId.hashCode() : 0);
        result = 31 * result + (originCityId != null ? originCityId.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);

        return result;
    }
}
