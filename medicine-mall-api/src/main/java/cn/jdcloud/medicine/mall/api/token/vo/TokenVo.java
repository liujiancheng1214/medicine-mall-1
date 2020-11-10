package cn.jdcloud.medicine.mall.api.token.vo;

/**
 * tokenVO
 * author: yanghuoyun
 * date: 2016/3/31
 */
public class TokenVo {

    private String accessToken; // 访问TOKEN
    private String refreshToken; // 刷新TOKEN
    private long expiresIn;      //access token 有效期 的时间点 单位秒
    private long createTime; // 建立时间

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
