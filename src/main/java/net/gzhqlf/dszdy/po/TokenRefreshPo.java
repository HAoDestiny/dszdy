package net.gzhqlf.dszdy.po;

/**
 * Created by n0elle on 2017/10/25.
 */
public class TokenRefreshPo {
    private boolean isNeedRefreshTokenCode;

    public TokenRefreshPo(boolean refresh){
        this.isNeedRefreshTokenCode = refresh;
    }

    public boolean isNeedRefreshTokenCode() {
        return isNeedRefreshTokenCode;
    }

}
