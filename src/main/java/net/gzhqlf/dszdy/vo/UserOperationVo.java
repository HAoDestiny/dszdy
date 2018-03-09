package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/8.
 */
public class UserOperationVo {

    @NotNull(message = "用户ID")
    private int userId;

    @NotNull(message = "操作类型")
    private int operationType;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }
}
