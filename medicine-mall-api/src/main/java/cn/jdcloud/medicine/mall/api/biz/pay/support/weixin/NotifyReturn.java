package cn.jdcloud.medicine.mall.api.biz.pay.support.weixin;

import cn.jdcloud.framework.core.adapter.CDataAdapter;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by yanghuoyun on 2017/6/6.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class NotifyReturn {
    //返回状态码 SUCCESS/FAIL
    @XmlElement(name="return_code")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String returnCode;

    //返回信息
    @XmlElement(name="return_msg")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String returnMsg;

}
