package cn.jdcloud.medicine.mall.api.biz.product.service;

import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemBatchVo;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface ItemBatchService extends IService<ItemBatch> {

    Map<String, List<ItemBatchVo>> getGroupMapByNos(List<String> nos);

    void insertBatch(List<ItemBatch> itemBatchList);
    
    ItemBatch queryItemBatchBySkuAndItemNo(String sku,String itemNo);
    
    List<ItemBatch> listItemBatchByItemNo(String itemNo);
}
