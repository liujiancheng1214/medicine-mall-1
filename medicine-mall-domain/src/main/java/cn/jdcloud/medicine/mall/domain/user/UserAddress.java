package cn.jdcloud.medicine.mall.domain.user;


import com.baomidou.mybatisplus.annotation.TableName;

import cn.jdcloud.framework.core.common.BaseDomain;
import lombok.Data;

@Data
//@EqualsAndHashCode(callSuper=false)
@TableName("t_user_address")
public class UserAddress extends BaseDomain {
    //自增id
    private Integer id;
    //用户主键
    private Integer userId;
    //收货人姓名
    private String realName;
    //收货人手机号码
    private String mobile;
    //地址省id
    private Integer provinceId;
    //地址省名称
    private String provinceName;
    //地址市id
    private Integer cityId;
    //地址市名字
    private String cityName;

    private Integer districtId;

    private String districtName;
    //详细地址
    private String address;
    //是否删除:0代表未删除，1代表已删除

    private Byte isDeleted;
    //是否默认地址:1表示是默认收获地址，0表示不是默认收获地址
    private Byte isDefault;

}
