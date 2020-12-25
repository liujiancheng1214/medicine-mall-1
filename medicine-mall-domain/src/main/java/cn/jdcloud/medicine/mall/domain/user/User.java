package cn.jdcloud.medicine.mall.domain.user;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User {

    public final static byte GROWER = 1;
    public final static byte SELLER = 2;
    public final static byte CUSTOMER = 3;
    public final static byte ADMIN = 4;

    private Integer id;
    private String headImg;  //用户头像
    private String helpCode; //客户编码
    private String mobile; //注册手机号
    private String openid; //微信公众号id
    private String password; //密码
    private Byte type; //客户类型
    private Byte companyType; //公司类型 1：诊所/卫生院/医院 2：药店/商业公司/连锁总部
    private String companyName; //客户名/公司名/证件名称
    private String companyAddress; //证件地址
    // 采购员身份扫描件
    private String cgysfsmj;
    // 采购委托扫描件
    private String cgwtssmj;
    //医疗机构执业许可证
    private String yljgzyxkz;
    // 营业执照
    private  String yyzz;
    // GSP
    private String gsp;
    // 药品经营许可证
    private String ypjyxkz;
    
    
    private Integer provinceId; //省id
    private Integer cityId; //市id
    private Integer districtId; //县id
    private String contactAddress; //联系人地址
    private String contactName; //联系人名字
    private String contactPhone; //联系人手机
    
    private Integer integral;  //总积分


    private String taxNo;  //税号
    private String invoiceType; //发票类型
    private Integer userLevelId; //用户等级id
    private Byte status; //用户状态 0可用 1不可用
    private Byte accountStatus; //开户状态 0已开户 1未开户
    private Byte receiptStatus; //收证状态 0已符合要求 1未符合要求
    private String salt; //密码加盐
    private Date updateTime;
    private Date createTime;
    private String nickName;
    private String businessLicense ; // 营业执照
	private String licence; //许可证

}