package net.gzhqlf.dszdy.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/9.
 */
public class UserCollectionVo {

    @NotNull(message = "喜欢对象")
    private int collectionObjectId;

    @NotNull(message = "活动id")
    private int activityId;

    @NotNull(message = "操作类型")
    private int operationType;

    public int getCollectionObjectId() {
        return collectionObjectId;
    }

    public void setCollectionObjectId(int collectionObjectId) {
        this.collectionObjectId = collectionObjectId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }
}
