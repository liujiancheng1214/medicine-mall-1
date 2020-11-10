package cn.jdcloud.medicine.mall.api.biz.weixin.bo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxAccessToken implements Serializable{

    private static final long serialVersionUID = 358972186854L;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private Integer expiresIn;
}
