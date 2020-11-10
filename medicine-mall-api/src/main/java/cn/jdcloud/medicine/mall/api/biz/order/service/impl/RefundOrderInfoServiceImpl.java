package cn.jdcloud.medicine.mall.api.biz.order.service.impl;

import cn.jdcloud.medicine.mall.api.biz.order.service.OrderInfoService;
import cn.jdcloud.medicine.mall.api.biz.order.service.RefundOrderInfoService;
import cn.jdcloud.medicine.mall.dao.order.OrderInfoMapper;
import cn.jdcloud.medicine.mall.dao.order.RefundOrderInfoMapper;
import cn.jdcloud.medicine.mall.domain.order.OrderInfo;
import cn.jdcloud.medicine.mall.domain.order.RefundOrderInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("RefundOrderInfoService")
public class RefundOrderInfoServiceImpl extends ServiceImpl<RefundOrderInfoMapper, RefundOrderInfo> implements RefundOrderInfoService {
}
