package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

public class UserIdVo {
    @NotNull(message = "用户id")
    private int userId = 0;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
