package cn.jdcloud.medicine.mall.api.biz.weixin.bo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class Ticket implements Serializable{

    private static final long serialVersionUID = -855135544353135L;
    /**
     * {
     "errcode":0,
     "errmsg":"ok",
     "ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
     "expires_in":7200
     }
     */
    @SerializedName("errcode")
    private Integer errCode;
    @SerializedName("errmsg")
    private String errMsg;
    @SerializedName("ticket")
    private String ticket;
    @SerializedName("expires_in")
    private Integer expiresIn;

}
