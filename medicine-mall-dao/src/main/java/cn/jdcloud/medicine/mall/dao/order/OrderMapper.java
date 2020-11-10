package cn.jdcloud.medicine.mall.dao.order;


import cn.jdcloud.medicine.mall.domain.order.Order;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderTabCountVo;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface OrderMapper extends BaseMapper<Order>{

    /**
     * 查询订单分页列表
     *
     * @param page
     * @param map
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.jdcloud.medicine.mall.domain.order.vo.OrderVo>
     * @author HuZhengYu
     * @date 16:04 2020/8/28
     */
    Page<OrderVo> selectOrderPage(Page<OrderVo> page, @Param("map") Map map);

    /**
     * 查询订单各个状态的数量
     *
     * @param map
     * @return OrderTabCountVo
     * @author HuZhengYu
     * @date 15:45 2020/8/24
     */
    OrderTabCountVo selectTabCount(Map<String, Object> map);


}
