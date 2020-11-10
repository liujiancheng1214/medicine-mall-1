package cn.jdcloud.medicine.mall.api.biz.order.vo;
import lombok.Data;

@Data
public class OrderAddressVo {
	private String orderId;
	 private String realName;
    private String mobile;
    private Integer provinceId;
    private String provinceName;
    private Integer cityId;
    private String cityName;
    private Integer districtId;
    private String districtName;
    private String address;
    
    
}
