package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

@Entity
@Table(name = "notify", schema = "dszdy", catalog = "")
public class NotifyEntity {
    private int id;
    private Integer notifyToUserId;
    private Integer notifyType;
    private String notifyContent;
    private Integer notifyDynamicId = 0;
    private Integer notifyChatUserId = 0;
    private Integer notifyStatus;
    private Integer createTime;

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
    @Column(name = "notify_to_user_id")
    public Integer getNotifyToUserId() {
        return notifyToUserId;
    }

    public void setNotifyToUserId(Integer notifyToUserId) {
        this.notifyToUserId = notifyToUserId;
    }

    @Basic
    @Column(name = "notify_type")
    public Integer getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }

    @Basic
    @Column(name = "notify_content")
    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    @Basic
    @Column(name = "notify_dynamic_id")
    public Integer getNotifyDynamicId() {
        return notifyDynamicId;
    }

    public void setNotifyDynamicId(Integer notifyDynamicId) {
        this.notifyDynamicId = notifyDynamicId;
    }

    @Basic
    @Column(name = "notify_chat_user_id")
    public Integer getNotifyChatUserId() {
        return notifyChatUserId;
    }

    public void setNotifyChatUserId(Integer notifyChatUserId) {
        this.notifyChatUserId = notifyChatUserId;
    }

    @Basic
    @Column(name = "notify_status")
    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    @Basic
    @Column(name = "create_time")
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}

