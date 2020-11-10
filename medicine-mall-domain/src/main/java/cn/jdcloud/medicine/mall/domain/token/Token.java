package cn.jdcloud.medicine.mall.domain.token;



import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class Token implements Serializable {
//
    public final static long ACC_TOKEN_EXP = 6 * 3600; //6小时 单位秒

    public final static long REF_TOKEN_EXP = 2400 * 3600; //100天 单位秒

    private static final long serialVersionUID = -5888707740021483901L;

    private String accessToken;  // 访问TOKEN
    private String refreshToken; // 刷新TOKEN
    private long expiresIn;      //access token 有效期 的时间点 单位秒
    private long createTime;     // 建立时间
    private int userId;         // 用户ID

    public Token() {

    }
    public Token(int  uid) {
        this.userId = uid;
        this.accessToken = UUID.randomUUID().toString().replaceAll("-", "");
        this.refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
        this.createTime = System.currentTimeMillis();
        this.expiresIn = createTime + ACC_TOKEN_EXP;
    }

}
