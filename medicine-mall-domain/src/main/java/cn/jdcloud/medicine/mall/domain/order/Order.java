package cn.jdcloud.medicine.mall.domain.order;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.jdcloud.framework.core.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("t_order")
public class Order extends BaseDomain{
    //0:'待付款';
    public static final byte STATUS_DFK = 0;
    //1:'已付款-活动未结束';
    public static final byte STATUS_YFK_HDWJS = 1;
    //2:'已付款-待处理';
    public static final byte STATUS_YFK_DCL = 2;
    //3:'审核资质';
    public static final byte STATUS_SHZZ = 3;
    //4:'正在开单';
    public static final byte STATUS_ZZKD = 4;
    //5:'分拣中';
    public static final byte STATUS_FJZ = 5;
    //6:'待配送';
    public static final byte STATUS_DPS = 6;
    //7：‘配送中’；
    public static final byte STATUS_PSZ = 7;
    //8:'整单退款-客户申请退款';
    public static final byte STATUS_ZDTK_KHSQTK = 8;
    //9:'整单退款-后台退款';
    public static final byte STATUS_ZDTK_HTTK = 9;
    //10:'整单退款-活动失败退款';
    public static final byte STATUS_ZDTK_HDSBTK = 10;
    //11:'配送完成-正常结束';
    public static final byte STATUS_DDJS_ZCJS = 11;
    //12:'订单结束-失败结束';
    public static final byte STATUS_DDJS_SBJS = 12;

    
    public static String getOrderStatusDesc(byte orderStatus) {
    	switch(orderStatus) {
    	case STATUS_DFK: 
    		return "待付款";
    	case STATUS_YFK_HDWJS: 
    		return "已付款-活动未结束";
    	case STATUS_YFK_DCL: 
    		return "已付款-待处理";
    	case STATUS_SHZZ: 
    		return "审核资质";
    	case STATUS_ZZKD: 
    		return "正在开单";
    	case STATUS_FJZ: 
    		return "分拣中";
    	case STATUS_DPS: 
    		return "待配送";
    	case STATUS_PSZ: 
    		return "配送中";
    	case STATUS_ZDTK_KHSQTK: 
    		return "整单退款-客户申请退款";
    	case STATUS_ZDTK_HTTK: 
    		return "整单退款-后台退款";
    	case STATUS_ZDTK_HDSBTK: 
    		return "整单退款-活动失败退款";
    	case STATUS_DDJS_ZCJS: 
    		return "配送完成-正常结束";
    	case STATUS_DDJS_SBJS: 
    		return "订单结束-失败结束";
    	}
    	return orderStatus+"-未知";
    }
    
    
    
    
    //订单id
    @TableId(value = "order_id",type = IdType.INPUT)
    private String orderId;
    //订单类型 0 普通订单   1 拼团订单
    // 拼团订单的活动Id
    private Integer promotionId;
    
    private Byte orderType;
    //客户id
    private Integer userId;
    //客户名称
    private String userName;
    //商品总数
    private Integer totalNum;
    //应付总金额
    private BigDecimal totalAmount;
    //优惠金额
    private BigDecimal discountAmount;
    //应付金额(优惠后的总金额)
    private BigDecimal paymentAmount;
    
	private BigDecimal transportFee;
    
    //收货地址id 该id不能使用 避免被修改
    private Integer receiveAddressId;
    /////////////////订单地址信息/////////////////////////////
    private String realName;
    private String mobile;
    private Integer provinceId;
    private String provinceName;
    private Integer cityId;
    private String cityName;
    private Integer districtId;
    private String districtName;
    private String address;
    /////////////////订单地址信息/////////////////////////////
    
    //配送方式： 1 自提   2 商家直送  3 快递物流
    private Byte deliveryMethod;
    //快递公司 id
    private Integer expressCompanyId;
    //快递单号
    private String expressNo;
    /** 订单状态: {
     0：待付款
     1：已付款  活动未结束（不显示）
     2：已付款  待处理
     3：审核资质
     4：正在开单
     5：分拣中
     6：待配送
     7：配送中
     8：整单退款   客户申请退款
     9：整单退款   后台退款
     10：整单退款   活动失败退款
     11：订单结束   正常结束
     12：订单结束   失败结束
         配送中，客户点收货  到 订单结束。  结束后可点申请退款。
     }*/
    
    private Date payTime;  //支付时间
    private Date deliveryTime; //发货时间
    private Byte orderStatus;
    /** 结算状态 1 未结算 2 结算中  3已结算*/
    private Byte settleStatus;
    /** 收货状态 2 待确认收货（延迟）3待确认收货（未延迟) */
    private Byte receiptStatus;
    //用户（药店）备注
    private String userRemark;
    //后台（卖家）备注
    private String adminRemark;
    //电子发票（pdf） url
    private String invoiceUrl;
    //业务员  id
    private Integer salesmanId;
    //支付方式  1 微信  2 电汇
    private Byte paymentMethod;
    //留言
    private String message;
    //下载次数
    private Integer downloadTimes;
    //订单类型 1 商家直送  2 委派配送
    private Byte deliveryType;
    //是否邻药汇 订单  1 是 2 否
    private Byte isLyhOrder;
    
    /**
     * 是否删除 0否 1是
     */
    @TableField(exist = false)
    protected Byte isDeleted;

}
