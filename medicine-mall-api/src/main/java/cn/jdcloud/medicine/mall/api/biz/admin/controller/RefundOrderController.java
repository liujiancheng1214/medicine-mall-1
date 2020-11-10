package cn.jdcloud.medicine.mall.api.biz.admin.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.admin.dto.RefundPageDto;
import cn.jdcloud.medicine.mall.api.biz.admin.dto.RefundOperateDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderService;
import cn.jdcloud.medicine.mall.api.biz.order.service.RefundOrderService;
import cn.jdcloud.medicine.mall.api.sys.vo.SysDictVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("adminRefundOrderController")
@RequestMapping("/cms/refund")
@Api(tags = "退款单相关接口")
public class RefundOrderController {

    @Resource
    RefundOrderService refundOrderService;
    @Resource
    OrderService orderService;

    @ApiOperation(value = "退款单列表查询")
    @GetMapping("/page")
    public ApiResult page(Page page, RefundPageDto dto){
        page = refundOrderService.listAdmin(page,dto);
        return ApiResult.ok(page);
    }

    /**
     * 创建退款单
     * @param request
     * @param dto
     * @return
     */
    @Transactional
    @PostMapping("/createOrder")
    public ApiResult createOrder(HttpServletRequest request, @RequestBody CreateOrderDto dto) throws Exception {
        dto.setAdminOrUser((byte) 1);
        boolean b = refundOrderService.createOrder(request, dto);
        return ApiResult.ok(b);
    }

    @ApiOperation(value = "退款单列表各状态统计")
    @GetMapping("/statistical")
    public ApiResult statistical(RefundPageDto dto){
        Map<String,Integer> m = refundOrderService.statistical(dto);
        return ApiResult.ok(m);
    }

    @ApiOperation(value = "拒绝退款")
    @PostMapping("/refuseRefund")
    public ApiResult refuseRefundBatch(@RequestBody RefundOperateDto dto){
        refundOrderService.refuseRefundBatch(dto);
        return ApiResult.ok();
    }

    @ApiOperation(value = "同意退款")
    @PostMapping("/agreeRefundBatch")
    public ApiResult agreeRefundBatch(@RequestBody RefundOperateDto dto){
        refundOrderService.agreeRefundBatch(dto);
        return ApiResult.ok();
    }

    @ApiOperation(value = "拒绝退款原因列表查询")
    @GetMapping("/refundReasonPage")
    public ApiResult refundReasonPage(Page page,String search){
        page = refundOrderService.refundReasonPage(page,search);
        return ApiResult.ok(page);
    }

    /**
     * 禁用某项 退款原因
     * @param sysDictVo
     * @return
     */
    @ApiOperation(value = "删除（禁用）某项退款原因")
    @PostMapping("/deleteRefundReason")
    public ApiResult deleteRefundReason(@RequestBody SysDictVo sysDictVo){
        refundOrderService.deleteRefundReason(sysDictVo);
        return ApiResult.ok();
    }

    /**
     * 启用某项 退款原因
     * @param sysDictVo
     * @return
     */
    @ApiOperation(value = "删除（启用）某项退款原因")
    @PostMapping("/unDeleteRefundReason")
    public ApiResult unDeleteRefundReason(@RequestBody SysDictVo sysDictVo){
        refundOrderService.unDeleteRefundReason(sysDictVo);
        return ApiResult.ok();
    }

    /**
     * 编辑某项 退款原因
     * @param sysDictVo
     * @return
     */
    @ApiOperation(value = "更新某项退款原因")
    @PostMapping("/updateRefundReason")
    public ApiResult updateRefundReason(@RequestBody SysDictVo sysDictVo){
        refundOrderService.updateRefundReason(sysDictVo);
        return ApiResult.ok();
    }

    /**
     * 新增某项 退款原因
     * @param sysDictVo
     * @return
     */
    @ApiOperation(value = "新增某项退款原因")
    @PostMapping("/addRefundReason")
    public ApiResult addRefundReason(@RequestBody SysDictVo sysDictVo){
        refundOrderService.addRefundReason(sysDictVo);
        return ApiResult.ok();
    }
}
