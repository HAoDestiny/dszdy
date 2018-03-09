package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/11/9.
 */
@Entity
@Table(name = "activity_person", schema = "dszdy", catalog = "")
public class ActivityPersonEntity implements Comparable<ActivityPersonEntity> {
    private int id;
    private Integer userId;
    private Integer activityId;
    private Integer userSex = 0;
    private Integer joinTime = (int) (System.currentTimeMillis()/1000);

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "activity_id", nullable = true)
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @Basic
    @Column(name = "user_sex", nullable = true)
    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    @Basic
    @Column(name = "join_time", nullable = true)
    public Integer getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Integer joinTime) {
        this.joinTime = joinTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityPersonEntity that = (ActivityPersonEntity) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (activityId != null ? !activityId.equals(that.activityId) : that.activityId != null) return false;
        if (joinTime != null ? !joinTime.equals(that.joinTime) : that.joinTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (activityId != null ? activityId.hashCode() : 0);
        result = 31 * result + (joinTime != null ? joinTime.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(ActivityPersonEntity o) {
        if (this.activityId > o.getActivityId()) {
            return 1;
        } else {
            return -1;
        }
    }
}
