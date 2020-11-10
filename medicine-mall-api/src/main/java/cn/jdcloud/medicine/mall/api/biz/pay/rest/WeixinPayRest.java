package cn.jdcloud.medicine.mall.api.biz.pay.rest;

import cn.jdcloud.framework.common.enums.PayWay;
import cn.jdcloud.framework.core.annotation.LoginRequired;
import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.common.SysCodeEnums;
import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.framework.utils.XmlUtil;
import cn.jdcloud.framework.utils.http.IpAddressUtil;
import cn.jdcloud.medicine.mall.api.biz.order.service.OrderService;
import cn.jdcloud.medicine.mall.api.biz.pay.constant.WeixinConstant;
import cn.jdcloud.medicine.mall.api.biz.pay.dto.OrderParamsDto;
import cn.jdcloud.medicine.mall.api.biz.pay.service.OrderPayService;
import cn.jdcloud.medicine.mall.api.biz.pay.service.WeixinPayService;
import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.NotifyReq;
import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.NotifyReturn;
import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.UnifiedOrderReq;
import cn.jdcloud.medicine.mall.api.biz.pay.support.weixin.UnifiedOrderReturn;
import cn.jdcloud.medicine.mall.api.biz.pay.vo.weixin.OrderParamsH5Vo;
import cn.jdcloud.medicine.mall.api.biz.pay.vo.weixin.PayResultVo;
import cn.jdcloud.medicine.mall.api.sys.constant.AppConstant;
import cn.jdcloud.medicine.mall.domain.order.OrderPay;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 微信支付
 * @author: yanghuoyun
 * @since: 2017/06/06 15:11
 * @version: v1.0
 */
@RestController
@RequestMapping(value = "/app/pay/weixin")
public class WeixinPayRest extends BaseController {

    public static final NotifyReturn RTN_SUCCESS = new NotifyReturn();
    public static final NotifyReturn RTN_FAIL = new NotifyReturn();

    static {
        RTN_SUCCESS.setReturnCode(WeixinConstant.SUCCESS);
        RTN_SUCCESS.setReturnMsg("OK");
        RTN_FAIL.setReturnCode(WeixinConstant.FAIL);
        RTN_FAIL.setReturnMsg("ERROR");
    }

    @Resource
    private OrderPayService orderPayService;

    @Resource
    private OrderService orderService;

    @Resource
    private WeixinPayService weixinPayService;

    @Resource(name = "gson")
    private Gson gson;


    /**
     * 创建商品订单信息请求接口，
     * 此处是客户端请求后台服务端，获取统一下单订单参数
     * @return 订单参数
     */
    @PostMapping("/orderParams")
    @LoginRequired
    public ApiResult makeOrderInfo(
            @RequestHeader("userId") Integer userId,
            @RequestHeader("userType") Integer userType,
            @RequestBody OrderParamsDto dto,
            HttpServletRequest request) throws ApiException, UnsupportedEncodingException {
        byte payType = dto.getPayType();
        String relatedId = dto.getRelatedId();
        Integer addressId = dto.getAddressId();
        log.info("weixin pay create order pay start relatedId = " + relatedId);
        //更新订单地址
        if(OrderPay.PAY_TYPE_PRODUCT==payType && addressId!=null){
    //        orderService.updateUserAddress(relatedId,addressId);
        }
        //创建支付订单，往t_order_pay 插入一条数据
        OrderPay op = orderPayService.createPrepayOrder(relatedId, dto.getPayType(), null,
                PayWay.weixin, userId, OrderPay.CLIENT_TYPE_JS);
        //获取请求IP地址、调用统一下单api
        String ip = IpAddressUtil.getIpAddr(request);
        UnifiedOrderReq uor = new UnifiedOrderReq(op, ip, OrderPay.CLIENT_TYPE_JS);
        UnifiedOrderReturn uort = weixinPayService.unifiedOrder(uor);
        //判断微信接口返回参数
        if (uort == null) {
            throw new ApiException(SysCodeEnums.SYSTEM_ERROR);
        }
        if (!WeixinConstant.SUCCESS.equals(uort.getReturnCode())) {
            throw new ApiException(-1, uort.getReturnMsg());
        }
        if (!WeixinConstant.SUCCESS.equals(uort.getResultCode())) {
            throw new ApiException(-1, uort.getErrCodeDes());
        }

        OrderParamsH5Vo ov = new OrderParamsH5Vo(uort,op,OrderPay.CLIENT_TYPE_JS);
        System.out.println("----------------------------ov：" + ov);
        return ApiResult.ok(ov);
    }


    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @LoginRequired(false)
    public void weixinNotify(@RequestBody String xml,
                             HttpServletResponse response) throws ApiException {
        log.info("weixin notify xml=" + xml);
        NotifyReq nr = XmlUtil.parseObject(xml, NotifyReq.class);
        NotifyReturn nrt;
        if (nr == null) {
            log.info("weixin notify entity NotifyReq is null");
            nrt = RTN_FAIL;
        } else {
            //微信业务返回成功
            if (WeixinConstant.SUCCESS.equals(nr.getReturnCode()) &&
                    WeixinConstant.SUCCESS.equals(nr.getResultCode())) {
                OrderPay orderPay = orderPayService.getOrderPayById(Integer.valueOf(nr.getOutTradeNo()));
                if (orderPay != null) {
                    //因为这个方法的clientType是
                    if (!nr.checkSign(orderPay.getClientType())) {
                        log.info("weixin notify sign check failed:" + gson.toJson(nr));
                        nrt = RTN_FAIL;
                    } else {
                        OrderPay op = weixinPayService.wrapOrderPay(nr);
                        orderPayService.dealNotifyOrderPay(op);
                        log.info("weixin notify success:" + gson.toJson(op));
                        nrt = RTN_SUCCESS;
                    }
                } else {
                    nrt = RTN_FAIL;
                    log.info("weixin notify fail: ml_order_pay is null client_type is null ");
                }
            } else {
                log.info("weixin notify fail: returnCode=" + nr.getReturnCode() + " returnMsg=" + nr.getReturnMsg() +
                        " resultCode=" + nr.getResultCode() + " errCode=" + nr.getErrCode() + " errCodeDes=" + nr.getErrCodeDes());
                nrt = RTN_FAIL;
            }
        }
        String returnXml = XmlUtil.toXmlString(nrt);
        log.info("weixin notify return=" + returnXml);
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(returnXml);
            log.info("---------------------------------------------验证结果返回 =" + returnXml);
        } catch (IOException e) {
            log.info("---------------------------------------------验证结果返回错误");
            e.printStackTrace();
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
    }

    /**
     * @param orderPayId
     * @param payCode    0 成功;-1 普通错误类型;-2 用户点击取消并返回;-3 发送失败;-4 授权失败;-5 微信不支持
     * @return
     * @throws ApiException
     */
    @GetMapping("/queryPayStatus")
    @LoginRequired
    public ApiResult queryPayStatus(@RequestParam(value = "orderPayId", required = true) Integer orderPayId,
                              @RequestParam(value = "payCode", required = false,defaultValue = "0") Byte payCode)
            throws ApiException {
        log.info("weixin queryPay orderPayId=" + orderPayId + " payCode=" + payCode);
        OrderPay op = orderPayService.getOrderPayById(orderPayId);
        if (op == null) {
            return ApiResult.ok();
        }
        //支付结果未收到通知，调用接口查询结果
        if (OrderPay.ORDER_PAY_ZFZ == op.getPayStatus().byteValue()) {
            OrderPay queryResult = weixinPayService.queryPayResult(orderPayId, OrderPay.CLIENT_TYPE_JS);
            //处理支付结果
            if (payCode == -2) { //用户取消支付
                queryResult.setPayCode(WeixinConstant.PAY_CANCEL);
            }
            orderPayService.dealNotifyOrderPay(queryResult);
            //重新查询处理后的支付结果
            op = orderPayService.getOrderPayById(orderPayId);
        }
        log.info("weixin pay success select result = " + op);
        return ApiResult.ok(new PayResultVo(op));
    }

    /**
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/queryOrderPay", method = RequestMethod.POST)
    public ApiResult queryOrderPay(@RequestParam("payType") Byte payType,
                                   @RequestParam("orderId") String orderId) throws ApiException {
        log.info("weixin queryOrderPay orderId=" + orderId + " payType=" + payType);
        OrderPay op = orderPayService.getByRelatedId(orderId, payType);
        if (op == null) {
            return ApiResult.ok();
        }
        //支付结果未收到通知，调用接口查询结果
        if (OrderPay.ORDER_PAY_ZFZ == op.getPayStatus().byteValue()) {
            OrderPay queryResult = weixinPayService.queryPayResult(op.getId(), AppConstant.WEB);
            //处理支付结果
//            if (payCode == -2) { //用户取消支付
//                queryResult.setPayCode(WeixinConstant.PAY_CANCEL);
//            }
            orderPayService.dealNotifyOrderPay(queryResult);
            //重新查询处理后的支付结果
            op = orderPayService.getOrderPayById(op.getId());
        }
        log.info("weixin pay success select result = " + op);
        return ApiResult.ok(new PayResultVo(op));
    }
}