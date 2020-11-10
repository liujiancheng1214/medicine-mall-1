package cn.jdcloud.medicine.mall.api.biz.admin.vo;

import lombok.Data;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @ClassName itemCategoryVo
 * @Author wuzhiyong
 * @Date 2020/8/13 16:08
 * @Version 1.0
 **/
@Data
@ApiModel("子类别对象")
public class ItemSubCategoryVo {
	@ApiModelProperty(value="类别名称")
    private String categoryName;
	@ApiModelProperty(value="类别编码")
    private String categoryNo;
    //child 表示 子节点。 0表示没有子节点。 3 表示有三个子节点
	@ApiModelProperty(value="0表示没有子节点。 3 表示有三个子节点")
    private int child;
    private int id;
    @ApiModelProperty(value="类别图片")
    String categoryImg;
    @ApiModelProperty(value="父级编码")
    private int parentId;
    @ApiModelProperty(value="序号")
    private int sort;
    private int isDeleted;

    public ItemSubCategoryVo(Map<String,Object> m) {
        this.categoryName = m.get("category_name").toString();
        this.categoryNo = m.get("category_no").toString();
        this.child = Integer.parseInt(m.get("child")==null?"0":m.get("child").toString());
        this.id = Integer.parseInt(m.get("id").toString());
        this.categoryImg = m.get("category_img")==null?"":m.get("category_img").toString();
        this.parentId = Integer.parseInt(m.get("parent_id").toString());
        this.sort = Integer.parseInt(m.get("sort").toString());
        this.isDeleted = Integer.parseInt(m.get("is_deleted").toString());
    }
}
