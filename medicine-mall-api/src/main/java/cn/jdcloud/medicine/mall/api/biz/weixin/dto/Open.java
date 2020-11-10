package cn.jdcloud.medicine.mall.api.biz.weixin.dto;

import lombok.Data;

@Data
public class Open {


    /**
    {"access_token":"21_MDcGdKRJL_lFjOsm5UfH0-9SE6FalCOjlLCdTdUM7wUNRE9zQxvOBuUGhVvLcbVVXL6DH9tBrCGyrDLKPAX3kQ",
     "expires_in":7200,
     "refresh_token":"21_Rgs26X1Y0Q19PG_52EJMerZKM5gkzw2NA1HVd098FbRP3yxyfP885JLvJn23-07aG-qlpxPNw495WCU0D3H7bw",
     "openid":"o3TxK6OqRjfAvNNd5EUw81ldjONw",
     "scope":"snsapi_userinfo",
     "unionid":"oV39S5pzzTg-Lw9m3jgABvv3lBpA"}
     */
    private String openid;
    private String access_token;
    private String unionid;//微信公众平台唯一账号

}
