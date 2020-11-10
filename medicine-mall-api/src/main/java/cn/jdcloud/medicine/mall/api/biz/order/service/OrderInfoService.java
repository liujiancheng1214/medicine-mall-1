package cn.jdcloud.medicine.mall.api.biz.order.service;

import java.util.List;

import cn.jdcloud.medicine.mall.domain.order.OrderInfo;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderItemListVo;
import cn.jdcloud.medicine.mall.domain.order.OrderInfo;

import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {

	List<OrderItemListVo> orderItemList(Integer userId,String orderId);
    /**
     * 根据订单id查询订单详情
     *
     * @param orderId
     * @return java.util.List<cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO>
     * @throws  Exception
     * @author HuZhengYu
     * @date 17:22 2020/8/25
     */
    List<OrderInfoVO> getInfoByOrderId(String orderId) throws Exception;

    /**
     * 根据订单id查询订单明细
     *
     * @param orderId
     * @return java.util.List<cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO>
     * @author HuZhengYu
     * @date 18:47 2020/8/28
     */
    List<OrderInfoVO> orderDetail(String orderId);

}
