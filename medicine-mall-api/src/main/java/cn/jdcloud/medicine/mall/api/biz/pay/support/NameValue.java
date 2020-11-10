package cn.jdcloud.medicine.mall.api.biz.pay.support;

import lombok.Data;

/**
 * Created by yanghuoyun on 2017/6/8.
 */
@Data
public class NameValue {
    private String name;
    private String value;

    public NameValue(){}

    public NameValue(String name,String value){
        this.name = name;
        this.value = value;
    }
}
