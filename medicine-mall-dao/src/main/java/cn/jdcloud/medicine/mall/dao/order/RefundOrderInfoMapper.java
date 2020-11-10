package cn.jdcloud.medicine.mall.dao.order;

import cn.jdcloud.medicine.mall.domain.order.RefundOrderInfo;
import cn.jdcloud.medicine.mall.domain.order.dto.RefundOrderInfoDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface RefundOrderInfoMapper extends BaseMapper<RefundOrderInfo>{

    RefundOrderInfoDto selectInfoDtoByRefundOrderId(@Param("refundOrderId") String id);

}