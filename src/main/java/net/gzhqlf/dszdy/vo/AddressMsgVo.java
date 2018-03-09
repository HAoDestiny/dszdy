package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by DESTINY on 2017/10/19.
 */
public class AddressMsgVo {

    @NotNull(message = "省")
    @NotEmpty(message = "省")
    private String province_id;

    @NotNull(message = "市")
    @NotEmpty(message = "市")
    private String city_id;

    @NotNull(message = "区")
    @NotEmpty(message = "区")
    private String area_id;

    @NotNull(message = "类型")
    @NotEmpty(message = "类型")
    private String type;

    private String father_id;

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFather_id() {
        return father_id;
    }

    public void setFather_id(String father_id) {
        this.father_id = father_id;
    }
}
