package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by DESTINY on 2017/10/22.
 */
public class MobileMsgVo {

    @NotNull(message = "手机号")
    @NotEmpty(message = "手机号")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
