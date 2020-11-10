package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import lombok.Data;

import java.util.Map;
/**
 * @ClassName itemCategoryVo
 * @Author wuzhiyong
 * @Date 2020/8/13 16:08
 * @Version 1.0
 **/
@Data
public class ItemSubBrandVo {
    private String ibrandName;
    private int child;
    private int id;
    String brandImg;
    private int parentId;
    private int sort;
    private int isDeleted;

    public ItemSubBrandVo(Map<String,Object> m) {
        this.ibrandName = m.get("ibrand_name").toString();
        this.child = Integer.parseInt(m.get("child")==null?"0":m.get("child").toString());
        this.id = Integer.parseInt(m.get("id").toString());
        this.brandImg = m.get("brand_img")==null?"":m.get("brand_img").toString();
        this.parentId = Integer.parseInt(m.get("parent_id").toString());
        this.sort = Integer.parseInt(m.get("sort").toString());
        this.isDeleted = Integer.parseInt(m.get("is_deleted").toString());
    }
}
