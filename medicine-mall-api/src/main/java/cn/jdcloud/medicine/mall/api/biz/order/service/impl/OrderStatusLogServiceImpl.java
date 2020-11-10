package cn.jdcloud.medicine.mall.api.biz.order.service.impl;

import cn.jdcloud.medicine.mall.api.biz.order.service.OrderStatusLogService;
import cn.jdcloud.medicine.mall.dao.order.OrderStatusLogMapper;
import cn.jdcloud.medicine.mall.domain.order.OrderStatusLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author HuZhengYu
 * @description OrderStatusLogServiceImpl
 * @date 9:16 2020/8/26
 */
@Service
public class OrderStatusLogServiceImpl extends ServiceImpl<OrderStatusLogMapper, OrderStatusLog> implements OrderStatusLogService {

}
