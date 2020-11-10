package cn.jdcloud.medicine.mall.api.biz.order.service;

import cn.jdcloud.medicine.mall.api.biz.admin.dto.RefundOperateDto;
import cn.jdcloud.medicine.mall.api.biz.admin.dto.RefundPageDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderDto;
import cn.jdcloud.medicine.mall.api.sys.vo.SysDictVo;
import cn.jdcloud.medicine.mall.domain.order.RefundOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RefundOrderService extends IService<RefundOrder> {
    /**
     * 系统后台 查询退款单列表
     * @param page
     * @param dto
     * @return
     */
    Page listAdmin(Page page, RefundPageDto dto);

    void refuseRefundBatch(RefundOperateDto dto);

    void agreeRefundBatch(RefundOperateDto dto);

    Page refundReasonPage(Page page,String search);

    void deleteRefundReason(SysDictVo sysDictVo);

    void unDeleteRefundReason(SysDictVo sysDictVo);

    void updateRefundReason(SysDictVo sysDictVo);

    void addRefundReason(SysDictVo sysDictVo);

    Map<String, Integer> statistical(RefundPageDto dto);

    /**
     * 创建退款单
     */
    boolean createOrder(HttpServletRequest request, CreateOrderDto dto);
}
