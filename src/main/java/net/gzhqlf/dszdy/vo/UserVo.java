package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by DESTINY on 2017/10/23.
 */
public class UserVo {

    @NotNull(message = "手机号")
    @NotEmpty(message = "手机号")
    private String mobile;

    @NotNull(message = "密码")
    @NotEmpty(message = "密码")
    private String password;

    private String IP;

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

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
