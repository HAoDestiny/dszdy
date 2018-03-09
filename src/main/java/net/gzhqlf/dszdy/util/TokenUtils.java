package net.gzhqlf.dszdy.util;

/**
 * Created by n0elle on 2017/10/25.
 */

public class TokenUtils {

    private static final int expiredDays = 15;
    private static final String key = "HY92QUOIwMDGbj8CC4mW7BABCUwCD48r";

    private String UUID;
    private int userId;
    private long expiredTime;

    /**
     * 初始化验证令牌
     * @param UUID
     * @param userId
     */
    public TokenUtils(String UUID, int userId) {
        this.UUID = UUID.toLowerCase();
        this.userId = userId;
        this.expiredTime = System.currentTimeMillis()/1000 + 60*60*24* expiredDays;
    }

    /**
     * 验证token
     * @param token
     * @return boolean
     */
    public boolean validToken(String token) {

        String decode = AESEncrypt.AESDecode(key, token);

        if (null == decode)
            return false;

        String tokenParameter[] = decode.split("_");

        if (tokenParameter.length != 4) {
            return false;
        }

        if (!"dstoken".equals(tokenParameter[0])) {
            return false;
        }

        String tokenUUID = tokenParameter[1];
        long tokenExpiredTime = Long.parseLong(tokenParameter[2]);
        int tokenUserId = Integer.parseInt(tokenParameter[3]);

        if (tokenUUID.trim().toLowerCase().equals(this.UUID)) {
            if (tokenUserId == this.userId) {
                if (System.currentTimeMillis()/1000 < tokenExpiredTime) {
                    this.UUID = tokenUUID;
                    this.expiredTime = tokenExpiredTime;
                    this.userId = tokenUserId;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 初始化解密令牌 不验证uid
     * @param token
     */
    public TokenUtils(String token) {
        String decode = AESEncrypt.AESDecode(key, token);
        String tokenParameter[] = decode.split("_");
        if (tokenParameter.length == 4) {
            this.UUID = tokenParameter[1];
            this.expiredTime = Long.parseLong(tokenParameter[2]);
            this.userId = Integer.parseInt(tokenParameter[3]);
        }
    }

    /**
     * 验证uuid 过期时间
     * @return String
     */
    public boolean valid(String UUID) {
        if (UUID.trim().toLowerCase().equals(this.UUID)) {
                if (System.currentTimeMillis()/1000 < this.expiredTime) {
                    return true;
                }
            }
        return false;
    }

    /**
     * 获取token
     * @return String
     */
    public String getTokenCode() {

        String tokenCode = "dstoken_" + this.UUID + "_" + this.expiredTime + "_" + this.userId;

        return AESEncrypt.AESEncode(key, tokenCode);
    }


    public String getUUID() {
        return UUID;
    }

    public int getUserId() {
        return userId;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

}
