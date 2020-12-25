package cn.jdcloud.medicine.mall.api.biz.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.OperateDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.OrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.OrderListDto;
import cn.jdcloud.medicine.mall.api.biz.order.vo.OrderListVo;
import cn.jdcloud.medicine.mall.domain.order.Order;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO;
import cn.jdcloud.medicine.mall.domain.order.vo.OrderTabCountVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public interface OrderService extends IService<Order> {


	/**
	 * 支付后进行更新操作
	 * @param orderId
	 */
	void afterPayUpdate(String orderId);
	
	
	String updateOrderStatus(Integer userId,String orderId,byte orderStatus);

	List<OrderListVo> pageOrderList(int pageNum,int pageSize,Integer userId,String orderId,Byte orderState);

	List<Order> orderList(OrderListDto orderListDto);

    String createOrder(Integer userId, CreateOrderDto createOrderDto);
    /**
     * 查询订单分页列表
     *
     * @param page
     * @param dto
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @throws  Exception
     * @author HuZhengYu
     * @date 9:58 2020/8/21
     */
    Page orderList(Page page, OrderDto dto) throws Exception;

    /**
     * 订单详情导出
     *
     * @param dto
     * @param response
     * @return void
     * @throws  Exception
     * @author HuZhengYu
     * @date 17:18 2020/8/21
     */
    void export(OrderDto dto, HttpServletResponse response) throws Exception;

    /**
     * 查询每个状态的订单数
     *
     * @param dto
     * @return cn.jdcloud.medicine.mall.domain.order.vo.OrderTabCountVo
     * @author HuZhengYu
     * @date 17:36 2020/8/21
     */
    OrderTabCountVo orderCount(OrderDto dto);

    /**
     * 订单导入模板
     *
     * @param response
     * @return void
     * @throws  Exception
     * @author HuZhengYu
     * @date 18:55 2020/8/21
     */
    void template(HttpServletResponse response) throws Exception;

    /**
     * 根据订单id查询订单详情
     *
     * @param orderId
     * @return java.util.List<cn.jdcloud.medicine.mall.domain.order.vo.OrderInfoVO>
     * @throws  Exception
     * @author HuZhengYu
     * @date 17:22 2020/8/25
     */
    List<OrderInfoVO> getInfoByOrderId(String orderId) throws Exception;

    boolean receive(HttpServletRequest request, OperateDto dto);

    /**
     * 界面点击 已开户 调此接口 将订单状态改正在开单
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    boolean starting(HttpServletRequest request, OperateDto dto);

    boolean started(HttpServletRequest request, OperateDto dto);

    boolean ready(HttpServletRequest request, OperateDto dto);

    boolean delivered(HttpServletRequest request, OperateDto dto);

    boolean sendLogistic(HttpServletRequest request, OperateDto dto);
}
