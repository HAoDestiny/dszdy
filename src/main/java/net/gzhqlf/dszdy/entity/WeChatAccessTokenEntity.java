package net.gzhqlf.dszdy.entity;

import javax.persistence.*;

/**
 * Created by DESTINY on 2017/10/24.
 */
@Entity
@Table(name = "wechat_access_token", schema = "dszdy", catalog = "")
public class WeChatAccessTokenEntity {
    private int id;
    private String accessToken;
    private Integer expiresIn;
    private Integer refreshTime;

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
    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Basic
    @Column(name = "expires_in")
    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Basic
    @Column(name = "refresh_time", nullable = true)
    public Integer getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Integer refreshTime) {
        this.refreshTime = refreshTime;
    }
}
