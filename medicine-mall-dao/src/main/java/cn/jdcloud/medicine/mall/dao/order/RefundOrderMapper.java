package cn.jdcloud.medicine.mall.dao.order;

import cn.jdcloud.medicine.mall.domain.order.RefundOrder;
import cn.jdcloud.medicine.mall.domain.order.dto.RefundOrderDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RefundOrderMapper extends BaseMapper<RefundOrder>{

    List<RefundOrderDto> refundOrderPageOnAdmin(@Param("status") byte status,
                                                @Param("search") String search,
                                                @Param("settleStatus") Byte settleStatus,
                                                @Param("refundReason") Byte refundReason,
                                                @Param("refundTimeBegin") String refundTimeBegin,
                                                @Param("refundTimeEnd") String refundTimeEnd,
                                                @Param("orderTimeBegin") String orderTimeBegin,
                                                @Param("orderTimeEnd") String orderTimeEnd,
                                                @Param("offset") Long offset,
                                                @Param("size") Long size
                                          );
    Integer refundOrderPageCountOnAdmin(@Param("status") byte status,
                                          @Param("search") String search,
                                          @Param("settleStatus") Byte settleStatus,
                                          @Param("refundReason") Byte refundReason,
                                          @Param("refundTimeBegin") String refundTimeBegin,
                                          @Param("refundTimeEnd") String refundTimeEnd,
                                          @Param("orderTimeBegin") String orderTimeBegin,
                                          @Param("orderTimeEnd") String orderTimeEnd,
                                          @Param("offset") Long offset,
                                          @Param("size") Long size
                                          );

    Map<String, Integer> statistical(@Param("search") String search,
                                     @Param("settleStatus") Byte settleStatus,
                                     @Param("refundReason") Byte refundReason,
                                     @Param("refundTimeBegin") String refundTimeBegin,
                                     @Param("refundTimeEnd") String refundTimeEnd,
                                     @Param("orderTimeBegin") String orderTimeBegin,
                                     @Param("orderTimeEnd") String orderTimeEnd);
}