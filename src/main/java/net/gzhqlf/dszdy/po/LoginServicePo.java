package net.gzhqlf.dszdy.po;

import net.gzhqlf.dszdy.entity.UserEntity;

/**
 * Created by DESTINY on 2017/10/24.
 */
public class LoginServicePo {

    private UserEntity userEntity;
    private String uri;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
