package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by DESTINY on 2017/10/20.
 */
public class RegisterVo {

    @NotNull(message = "手机号")
    @NotEmpty(message = "手机号")
    private String mobile;

    @NotNull(message = "密码")
    @NotEmpty(message = "密码")
    private String password;

    @NotNull(message = "验证码")
    @NotEmpty(message = "验证码")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
