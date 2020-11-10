package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @ClassName ItemDetailVo
 * @Author wuzhiyong
 * @Date 2020/8/13 14:23
 * @Version 1.0
 **/
@Data
public class ItemBrandDetailVo {
    /**
     * id
     */
    private Integer id;

    /**
     * 父级id
     */
    private Integer parentId;
    /**
     * 父品牌名称
     */
    private String parentName;

    /**
     * 品牌名称
     */
    private String ibrandName;
    /**
     * 品牌图片
     */
    private String brandImg;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updator;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 是否删除 0 未删除  1 删除
     */
    protected Byte isDeleted;

    protected List<ItemBrandDetailVo> subItem;

    public ItemBrandDetailVo(Map<String,Object> map) {
        this.id = Integer.parseInt(map.get("id").toString());
        this.parentId = Integer.parseInt(map.get("parent_id").toString());
        this.parentName = map.get("parent_name").toString();
        this.ibrandName = map.get("ibrand_name").toString();
        this.brandImg = map.get("brand_img")==null?"":map.get("brand_img").toString();
        this.sort = Integer.parseInt(map.get("sort").toString());
        this.creator = Integer.parseInt(map.get("creator").toString());
        this.updator = Integer.parseInt(map.get("updator").toString());;
        this.isDeleted = Byte.parseByte(map.get("is_deleted").toString());
    }
}
