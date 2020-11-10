package cn.jdcloud.medicine.mall.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.jdcloud.medicine.mall.domain.order.OrderInfo;
import cn.jdcloud.medicine.mall.domain.order.dto.OrderItemListDto;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO;

public interface OrderInfoMapper extends BaseMapper<OrderInfo>{

    /**
     * 根据订单编号获取订单信息集合
     *
     * @param orderId
     * @return java.util.List<cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO>
     * @author HuZhengYu
     * @date 13:33 2020/8/25
     */
    List<OrderInfoVO> getInfoByOrderId(@Param("orderId") String orderId);

    List<OrderItemListDto> orderItemList(@Param("userId") Integer userId,@Param("orderId") String orderId);

    /**
     * 根据订单id查询订单明细
     *
     * @param orderId
     * @return java.util.List<cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO>
     * @author HuZhengYu
     * @date 11:50 2020/8/31
     */
    List<OrderInfoVO> orderDetail(@Param("orderId") String orderId);
}
