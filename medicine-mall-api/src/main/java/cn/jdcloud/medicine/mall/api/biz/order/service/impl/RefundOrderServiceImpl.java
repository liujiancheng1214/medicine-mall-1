package cn.jdcloud.medicine.mall.api.biz.order.service.impl;

import cn.jdcloud.framework.core.exception.ApiException;
import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.biz.admin.dto.RefundOperateDto;
import cn.jdcloud.medicine.mall.api.biz.admin.dto.RefundPageDto;
import cn.jdcloud.medicine.mall.api.biz.order.code.OrderCode;
import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderDto;
import cn.jdcloud.medicine.mall.api.biz.order.dto.CreateOrderItemDto;
import cn.jdcloud.medicine.mall.api.biz.order.service.RefundOrderInfoService;
import cn.jdcloud.medicine.mall.api.biz.order.service.RefundOrderService;
import cn.jdcloud.medicine.mall.api.biz.order.utils.OrderUtil;
import cn.jdcloud.medicine.mall.api.sys.vo.SysDictVo;
import cn.jdcloud.medicine.mall.client.user.UserSession;
import cn.jdcloud.medicine.mall.dao.order.OrderInfoMapper;
import cn.jdcloud.medicine.mall.dao.order.OrderMapper;
import cn.jdcloud.medicine.mall.dao.order.OrderStatusLogMapper;
import cn.jdcloud.medicine.mall.dao.order.RefundOrderMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemMapper;
import cn.jdcloud.medicine.mall.dao.sys.SysDictMapper;
import cn.jdcloud.medicine.mall.dao.user.UserMapper;
import cn.jdcloud.medicine.mall.domain.order.*;
import cn.jdcloud.medicine.mall.domain.order.dto.RefundOrderDto;
import cn.jdcloud.medicine.mall.domain.product.Item;
import cn.jdcloud.medicine.mall.domain.sys.SysDict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service("RefundOrderService")
public class RefundOrderServiceImpl extends ServiceImpl<RefundOrderMapper, RefundOrder> implements RefundOrderService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    RefundOrderMapper refundOrderMapper ;
    @Resource
    SysDictMapper sysDictMapper ;
    @Resource
    UserMapper userMapper;
    @Resource
    ItemMapper itemMapper;
    @Resource
    RefundOrderInfoService refundOrderInfoService ;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderInfoMapper orderInfoMapper;
    @Resource
    OrderStatusLogMapper orderStatusLogMapper;

    @Override
    public Page listAdmin(Page page, RefundPageDto dto) {
        Integer count = refundOrderMapper.refundOrderPageCountOnAdmin(dto.getStatus(),
                                        StringUtils.isNotBlank(dto.getSearch())?dto.getSearch():null,
                                        dto.getSettleStatus(),
                                        dto.getRefundReason(),
                                        dto.getOrderTimeBegin(),
                                        dto.getOrderTimeEnd(),
                                        dto.getRefundTimeBegin(),
                                        dto.getRefundTimeEnd(),
                                        (page.getCurrent()-1)*page.getSize(),
                                        page.getSize()
                                        );

        if (count==null || count<1){
            page.setRecords(null);
            return page;
        }
        List<RefundOrderDto> list = refundOrderMapper.refundOrderPageOnAdmin(dto.getStatus(),
                StringUtils.isNotBlank(dto.getSearch())?dto.getSearch():null,
                dto.getSettleStatus(),
                dto.getRefundReason(),
                dto.getOrderTimeBegin(),
                dto.getOrderTimeEnd(),
                dto.getRefundTimeBegin(),
                dto.getRefundTimeEnd(),
                (page.getCurrent()-1)*page.getSize(),
                page.getSize()
        );
        page.setTotal(count);
        page.setRecords(list);
        return page;
    }

    @Override
    public void refuseRefundBatch(RefundOperateDto dto) {
        UpdateWrapper updateWrapper = new UpdateWrapper<RefundOrder>();
        updateWrapper.set("status",RefundOrder.STATUS_TKSB);
        updateWrapper.set("remark_admin",dto.getRemarkOrReason());
        updateWrapper.in("id",Arrays.asList(dto.getIds()));
        this.update(updateWrapper);
    }

    @Override
    public void agreeRefundBatch(RefundOperateDto dto) {
        UpdateWrapper updateWrapper = new UpdateWrapper<RefundOrder>();
        updateWrapper.set("status",RefundOrder.STATUS_TKZ);
        updateWrapper.set("remark_admin",dto.getRemarkOrReason());
        updateWrapper.in("id",Arrays.asList(dto.getIds()));
        this.update(updateWrapper);
        //TODO: 调用支付退款功能。。。执行退款
    }

    @Override
    public Page refundReasonPage(Page page,String search) {
        QueryWrapper queryWrapper = new QueryWrapper<SysDict>();
        queryWrapper.eq("type",SysDict.REFUND_REASON);
        queryWrapper.like(StringUtils.isNotBlank(search),"name",search);
        queryWrapper.orderByAsc("sort");
        return sysDictMapper.selectPage(page,queryWrapper);
    }

    @Override
    public void deleteRefundReason(SysDictVo sysDictVo) {
        if (sysDictVo.getId()==null){
            return;
        }
        SysDict sysDict = new SysDict();
        sysDict.setId(sysDictVo.getId());
        sysDict.setIsDeleted(SysDict.NUMBER_BYTE_ONE);
        sysDictMapper.updateById(sysDict);
    }

    @Override
    public void unDeleteRefundReason(SysDictVo sysDictVo) {
        if (sysDictVo.getId()==null){
            return;
        }
        SysDict sysDict = new SysDict();
        sysDict.setId(sysDictVo.getId());
        sysDict.setIsDeleted(SysDict.NUMBER_BYTE_ZERO);
        sysDictMapper.updateById(sysDict);
    }

    @Override
    public void updateRefundReason(SysDictVo sysDictVo) {
        if (sysDictVo.getId()==null){
            return;
        }
        SysDict sysDict = new SysDict();
        sysDict.setId(sysDictVo.getId());
        sysDict.setName(sysDictVo.getName());
        sysDict.setSort(sysDictVo.getSort());
        sysDict.setRemark(sysDictVo.getRemark());
        sysDict.setValue(sysDictVo.getValue());
        sysDictMapper.updateById(sysDict);
    }

    @Override
    public void addRefundReason(SysDictVo sysDictVo) {
        SysDict sysDict = new SysDict();
        sysDict.setType(SysDict.REFUND_REASON);
        sysDict.setIsMultiple(SysDict.NUMBER_BYTE_TWO);
        sysDict.setIsDeleted(SysDict.NUMBER_BYTE_ZERO);
        sysDict.setCreateTime(new Date());
        sysDict.setUpdateTime(new Date());
        sysDict.setName(sysDictVo.getName());
        sysDict.setSort(sysDictVo.getSort());
        sysDict.setRemark(sysDictVo.getRemark());
        sysDict.setValue(sysDictVo.getValue());
        sysDictMapper.insert(sysDict);
    }

    /**
     * 查询各个 状态 数量的统计。用于，退款列表标签页上数量的显示
     * @return
     */
    @Override
    public Map<String, Integer> statistical(RefundPageDto dto) {
        return refundOrderMapper.statistical( StringUtils.isNotBlank(dto.getSearch())?dto.getSearch():null,
                dto.getSettleStatus(),
                dto.getRefundReason(),
                dto.getOrderTimeBegin(),
                dto.getOrderTimeEnd(),
                dto.getRefundTimeBegin(),
                dto.getRefundTimeEnd());
    }

    @Override
    public boolean createOrder(HttpServletRequest request, CreateOrderDto dto) {
        //参数校验
        List<CreateOrderItemDto> list = dto.getList();
        if(CollectionUtils.isEmpty(list)){
            throw new ApiException(OrderCode.ORDER_PRODUCT_EMPTY);
        }
        String orderId = dto.getOrderId();
//        int orderId =0;
        Order order = orderMapper.selectById(orderId);
        Integer userId = order.getUserId();
        String id = OrderUtil.randomOrderId();
//        User user = userMapper.selectById(order.getUserId());
//        if(uesr==null ){
//            logger.error("用户不存在:userId=" + order.getUserId());
//            throw  new ApiException(AdminCode.ADMIN_IS_NOT_EXIST);
//        }

        //生成订单明细
        Date now = new Date();
        int totalNum = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;
        int i=0;
        List<RefundOrderInfo> orderInfos = new ArrayList<>();
        for (CreateOrderItemDto cto : list) {
            Integer itemId = cto.getItemId();
            Item item = itemMapper.selectById(itemId);
            RefundOrderInfo of = new RefundOrderInfo();
            of.setRefundOrderId(id);
            of.setItemId(itemId);
            of.setItemName(item.getItemName());
            of.setItemIcon(item.getImgCover());
            of.setItemNum(cto.getNum());
            OrderInfo orderInfo = orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>().eq("order_id", orderId).eq("item_id", itemId));
            of.setItemPrice(orderInfo.getItemPrice());
            of.setTotalPrice(of.getItemPrice().multiply(new BigDecimal(of.getItemNum())));
            of.setCreateTime(now);
            orderInfos.add(of);
            totalNum += of.getItemNum();
            totalAmount = totalAmount.add(of.getTotalPrice());
            i++;
        }
        //保存 订单明细 orderInfo
        refundOrderInfoService.saveBatch(orderInfos);

        byte s = dto.getAdminOrUser();
        //生成订单 order
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setId(id);
        refundOrder.setOrderId(orderId);
        refundOrder.setUserId(userId);
        refundOrder.setReason(dto.getReason());
        refundOrder.setTotalNum(totalNum);
        refundOrder.setTotalRefundAmount(totalAmount);
        refundOrder.setStatus(s==1?(byte)1:(byte)2);
        refundOrder.setSettleStatus((byte) 1);
        if (s==1){
            refundOrder.setRemarkAdmin(dto.getRemark());
        }else {
            refundOrder.setRemarkUser(dto.getRemark());
        }
        refundOrder.setRefundType(dto.getRefundType());
        refundOrder.setCreateTime(now);
        //保存订单 order
        refundOrderMapper.insert(refundOrder);
            UserSession userSession = (UserSession) request.getSession().getAttribute(UserSession.NAME);
            Integer adminId = userSession.getUserId();
            String adminName = userSession.getUserName();
            if(s == 1) {
                orderStatusLogMapper.insert(new OrderStatusLog(orderId, adminId, adminName, "后台申请退款", now));
            }else if (s == 2) {
                orderStatusLogMapper.insert(new OrderStatusLog(orderId, adminId, adminName, "用户申请退款", now));
            }
        return true;
    }
}
