package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/7.
 */
public class AdminLoginVo {

    @NotNull(message = "手机号")
    @NotEmpty(message = "手机号")
    private String mobile;

    @NotNull(message = "密码")
    @NotEmpty(message = "密码")
    private String password;

    private String ip;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
