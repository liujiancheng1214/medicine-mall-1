package cn.jdcloud.medicine.mall.api.biz.admin.dto;

import lombok.Data;
/**
 * @ClassName RefuseRefundDto
 * @Author wuzhiyong
 * @Date 2020/8/25 8:39
 * @Version 1.0
 **/
@Data
public class RefundOperateDto {
    /** 退款单 ids */
    private Integer[] ids;
    /** 备注 或 原因 */
    private String remarkOrReason;
}
