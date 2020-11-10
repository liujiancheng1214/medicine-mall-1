package cn.jdcloud.medicine.mall.api.biz.order.service.impl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.DateUtils;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.order.code.OrderCode;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderInfoService;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderItemListVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.order.OrderInfoMapper;
import cn.jdcloud.medicine.mall.domain.order.OrderInfo;
import cn.jdcloud.medicine.mall.domain.order.dto.OrderItemListDto;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("OrderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Override
	public List<OrderItemListVo> orderItemList(Integer userId, String orderId) {
		List<OrderItemListDto> list=orderInfoMapper.orderItemList(userId, orderId);
		List<OrderItemListVo> voList=new ArrayList<>();
		for(OrderItemListDto dto:list) {
			OrderItemListVo  orderItemListVo=new OrderItemListVo();
			BeanUtil.copyProperties(dto, orderItemListVo);
			orderItemListVo.setEffectiveDate(DateUtils.strToTimestamp(dto.getEffectiveDate()));
			voList.add(orderItemListVo);
		}
		return voList;
	}

    @Override
    public List<OrderInfoVO> getInfoByOrderId(String orderId){
        if (StringUtils.isBlank(orderId)) {
            throw new ApiException(OrderCode.ORDER_PARAM_ERROR);
        }
        return orderInfoMapper.getInfoByOrderId(orderId);
    }

    @Override
    public List<OrderInfoVO> orderDetail(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            throw new ApiException(OrderCode.ORDER_PARAM_ERROR);
        }
        return orderInfoMapper.orderDetail(orderId);
    }

}
