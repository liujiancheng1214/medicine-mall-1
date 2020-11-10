package cn.jdcloud.medicine.mall.api.sys.vo;

import cn.jdcloud.medicine.mall.domain.sys.SysDict;
import lombok.Data;
/**
 * @ClassName SysDictVo
 * @Author wuzhiyong
 * @Date 2020/8/25 10:04
 * @Version 1.0
 **/
@Data
public class SysDictVo {
        /**
         * 自增主键
         */
        private Integer id;
        private String type;

        /**
         * 显示的中文字符
         */
        private String name;

        /**
         * name 对应的 值
         */
        private Integer value;

        /**
         * 排序
         */
        private Integer sort;

        /**
         * 是否多选  0 无规则，1 多选  2 单选
         */
        private Byte isMultiple;


        private Byte isDeleted;

        private String remark;

    public SysDictVo(SysDict sysDict) {
        this.id = sysDict.getId();
        this.type = sysDict.getType();
        this.name = sysDict.getName();
        this.value = sysDict.getValue();
        this.sort = sysDict.getSort();
        this.isMultiple = sysDict.getIsMultiple();
        this.isDeleted = sysDict.getIsDeleted();
        this.remark = sysDict.getRemark();
    }
}
