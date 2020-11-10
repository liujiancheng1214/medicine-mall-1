package cn.jdcloud.medicine.mall.domain.order.vo;

import lombok.Data;

/**
 * @author HuZhengYu
 * @description 每个状态的订单数
 * @date 17:32 2020/8/21
 */
@Data
public class OrderTabCountVo {

    /** 未付款*/
    private Integer tab1;
    /** 已付款*/
    private Integer tab2;
    /** 资质审核*/
    private Integer tab3;
    /** 正在开单*/
    private Integer tab4;
    /** 分拣中*/
    private Integer tab5;
    /** 待配送*/
    private Integer tab6;
    /** 配送中*/
    private Integer tab7;
    /** 配送完成*/
    private Integer tab8;
    /** 整单退款*/
    private Integer tab9;

}
