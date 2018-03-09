package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/8.
 */
public class AdminRegisterVo {

    @NotNull(message = "账号")
    @NotEmpty(message = "账号")
    private String account;

    @NotNull(message = "密码")
    @NotEmpty(message = "密码")
    private String password;

    @NotNull(message = "管理级别")
    private int type;

    private String ip;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
