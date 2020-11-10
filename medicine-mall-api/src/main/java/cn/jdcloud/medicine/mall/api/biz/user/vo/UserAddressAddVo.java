package cn.jdcloud.medicine.mall.api.biz.user.vo;

import lombok.Data;

@Data
public class UserAddressAddVo {
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
    //是否默认地址:1表示是默认收获地址，0表示不是默认收获地址
    private Byte isDefault;
}
