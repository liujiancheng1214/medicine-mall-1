package cn.jdcloud.medicine.mall.api.biz.user.vo;

import cn.jdcloud.medicine.mall.domain.user.UserAddress;
import lombok.Data;

@Data
public class UserAddressVo {

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
    
    private Byte isDefault;
    //是否删除:0代表未删除，1代表已删除

	
	public UserAddressVo(){
	}
	public UserAddressVo(UserAddress ua){
	}

}
