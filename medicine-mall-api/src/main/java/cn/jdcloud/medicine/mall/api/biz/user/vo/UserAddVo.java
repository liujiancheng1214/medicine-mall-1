package cn.jdcloud.medicine.mall.api.biz.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户注册对象")
public class UserAddVo {
	@ApiModelProperty(value = "公司类型 1：诊所/卫生院/医院 2：药店/商业公司/连锁总部")
	private Byte companyType; // 公司类型 1：诊所/卫生院/医院 2：药店/商业公司/连锁总部
	@ApiModelProperty(value = "证件地址")
	private String companyAddress; // 证件地址
	// 采购员身份扫描件
	@ApiModelProperty(value = "采购员身份扫描件")
	private String cgysfsmj;
	// 采购委托扫描件
	@ApiModelProperty(value = "采购委托扫描件")
	private String cgwtssmj;
	// 医疗机构执业许可证
	@ApiModelProperty(value = "医疗机构执业许可证")
	private String yljgzyxkz;
	// 营业执照
	@ApiModelProperty(value = "营业执照")
	private String yyzz;
	// GSP
	@ApiModelProperty(value = "GSP")
	private String gsp;
	// 药品经营许可证
	@ApiModelProperty(value = "药品经营许可证")
	private String ypjyxkz;
	@ApiModelProperty(value = "联系人名字")
	private String contactName; // 联系人名字
	@ApiModelProperty(value = "联系人手机")
	private String contactPhone; // 联系人手机

	@ApiModelProperty(value = "注册手机号")
	private String mobile; // 注册手机号
	@ApiModelProperty(value = "验证码")
	private String code;
	@ApiModelProperty(value = "密码")
	private String password; // 密码
	@ApiModelProperty(value = "店铺名")
	private String companyName; // 客户名/公司名
	@ApiModelProperty(value = "省ID")
	private Integer provinceId; // 省id
	@ApiModelProperty(value = "市ID")
	private Integer cityId; // 市id
	@ApiModelProperty(value = "县ID")
	private Integer districtId; // 县id
	@ApiModelProperty(value = "店铺地址")
	private String contactAddress; // 联系人地址
	@ApiModelProperty(value = "营业执照")
	private String businessLicense; // 营业执照
	@ApiModelProperty(value = "经验许可证")
	private String licence; // 许可证
}
