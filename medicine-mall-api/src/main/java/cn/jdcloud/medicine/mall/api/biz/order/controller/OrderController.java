package cn.jdcloud.medicine.mall.api.biz.order.controller;

import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.OperateDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.OrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderInfoService;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderService;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderTabCountVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cms/order")
@Api(tags = "订单相关接口")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 查询每个状态的订单数
     *
     * @param dto
     * @return cn.jdcloud.framework.core.vo.ApiResult
     * @author HuZhengYu
     * @date 17:31 2020/8/21
     */
    @ApiOperation(value = "查询每个状态的订单数")
    @GetMapping(value = "/orderCount")
    public ApiResult orderCount(OrderDto dto) {
         OrderTabCountVo vo = orderService.orderCount(dto);
        return ApiResult.ok(vo);
    }

    /**
     * 查询订单分页列表
     *
     * @param page
     * @param dto
     * @return cn.jdcloud.framework.core.vo.ApiResult
     * @author HuZhengYu
     * @date 9:57 2020/8/21
     */
    @ApiOperation(value = "查询订单分页列表")
    @GetMapping(value = "/list")
    public ApiResult orderList(Page page, OrderDto dto) throws Exception {
        page = orderService.orderList(page, dto);
        return ApiResult.ok(page);
    }

    /**
     * 根据订单id查询订单详情
     *
     * @param orderId
     * @return cn.jdcloud.framework.core.vo.ApiResult
     * @author HuZhengYu
     * @date 17:22 2020/8/25
     */
    @ApiOperation(value = "根据订单id查询订单详情")
    @GetMapping(value = "/getInfoByOrderId")
    public ApiResult getInfoByOrderId(String orderId) throws Exception {
        List<OrderInfoVO> infoList = orderInfoService.getInfoByOrderId(orderId);
        return ApiResult.ok(infoList);
    }

    /**
     * 订单导入模板
     *
     * @param response
     * @return void
     * @author HuZhengYu
     * @date 18:55 2020/8/21
     */
    @ApiOperation(value = "订单导入模板")
    @GetMapping(value = "/template")
    public void template(HttpServletResponse response) throws Exception {
        orderService.template(response);
    }

    /**
     * 创建订单
     * @param userId
     * @param createOrderDto
     * @return
     */
    @PostMapping("/createOrder")
    public ApiResult createOrder(@RequestHeader("userId") Integer userId,@RequestBody CreateOrderDto createOrderDto){
        String orderId = orderService.createOrder(userId,createOrderDto);
        return ApiResult.ok(orderId);
    }

    /**
     * 订单详情导出
     *
     * @param dto
     * @param response
     * @return void
     * @author HuZhengYu
     * @date 17:18 2020/8/21
     */
    @ApiOperation(value = "订单详情导出")
    @GetMapping(value = "/export")
    public void export(OrderDto dto, HttpServletResponse response) throws Exception {
        orderService.export(dto, response);
    }

    @ApiOperation(value = "接收订单")
    @PostMapping(value = "/receive")
    public ApiResult receive(HttpServletRequest request,@RequestBody OperateDto dto) throws Exception {
        boolean result = orderService.receive(request,dto);
        return ApiResult.ok(result);
    }

    /**
     * 界面点击 已开户 调此接口 将订单状态修改为正在开单
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "已开户")
    @PostMapping(value = "/starting")
    public ApiResult starting(HttpServletRequest request,@RequestBody OperateDto dto) throws Exception {
        boolean result = orderService.starting(request,dto);
        return ApiResult.ok(result);
    }

    /**
     * 界面点击 开单完成 调此接口 将订单状态修改为分拣中
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "开单完成")
    @PostMapping(value = "/started")
    public ApiResult started(HttpServletRequest request,@RequestBody OperateDto dto) throws Exception {
        boolean result = orderService.started(request,dto);
        return ApiResult.ok(result);
    }

    /**
     * 界面点击 备货完成 调此接口 将订单状态修改为待配送
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "备货完成")
    @PostMapping(value = "/ready")
    public ApiResult ready(HttpServletRequest request, @RequestBody OperateDto dto) throws Exception {
        boolean result = orderService.ready(request,dto);
        return ApiResult.ok(result);
    }

    /**
     * 界面点击 发物流快递 调此接口 将订单状态修改为配送中
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "发物流快递")
    @PostMapping(value = "/sendLogistic")
    public ApiResult sendLogistics(HttpServletRequest request,@RequestBody OperateDto dto) throws Exception {
        boolean result = orderService.sendLogistic(request,dto);
        return ApiResult.ok(result);
    }
    @ApiOperation(value = "申请退款")
    @PostMapping(value = "/addRefund")
    public ApiResult addRefund(HttpServletRequest request,@RequestBody OperateDto dto) throws Exception {
        boolean result = orderService.sendLogistic(request,dto);
        return ApiResult.ok(result);
    }
    /**
     * 界面点击 配送完成 调此接口 将订单状态修改为配送完成
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "配送完成")
    @PostMapping(value = "/delivered")
    public ApiResult delivered(HttpServletRequest request,@RequestBody OperateDto dto) throws Exception {
        boolean result = orderService.delivered(request,dto);
        return ApiResult.ok(result);
    }

    /**
     * 根据订单id查询订单明细
     *
     * @param orderId
     * @return cn.jdcloud.framework.core.vo.ApiResult
     * @throws Exception
     * @author HuZhengYu
     * @date 18:44 2020/8/28
     */
    @ApiOperation(value = "根据订单id查询订单明细")
    @GetMapping(value = "/orderDetail")
    public ApiResult orderDetail(String orderId) {
        List<OrderInfoVO> list = orderInfoService.orderDetail(orderId);
        return ApiResult.ok(list);
    }



}
