package cn.jdcloud.medicine.mall.dao.order;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;
import org.apache.ibatis.annotations.Param;

public interface OrderPayMapper extends BaseMapper<OrderPay> {

    /**
     * 新增订单支付
     * @param orderPay
     * @return
     */
    int saveOrderPay(OrderPay orderPay);

    /**
     * 修改订单支付
     * @param orderPay
     * @return
     */
    int updateOrderPay(OrderPay orderPay);

    /**
     * 更新订单支付结果
     * @param orderPay
     * @return
     */
    int updateNotifyOrderPay(OrderPay orderPay);

    /**
     * 删除订单支付
     * @param orderPayId 订单支付id
     * @return
     */
    int deleteOrderPay(Integer orderPayId);

    /**
     * 根据主键id查订单支付信息
     * @param id 主键id
     * @return 订单支付对象
     */
    OrderPay getOrderPayById(Integer id);

    OrderPay getByRelatedId(@Param("relatedId") String relatedId, @Param("payType") Byte payType);

    /**根据orderId查询已经支付成功了的订单信息
     * @param relatedId   订单ID
     * @return
     */
    OrderPay getSuccessOrderPay(@Param("relatedId") String relatedId, @Param("payType") Byte payType);
}