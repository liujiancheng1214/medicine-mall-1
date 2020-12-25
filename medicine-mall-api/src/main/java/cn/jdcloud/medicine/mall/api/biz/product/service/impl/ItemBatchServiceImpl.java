package cn.jdcloud.medicine.mall.api.biz.product.service.impl;

import cn.jdcloud.medicine.mall.api.biz.product.service.ItemBatchService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.ItemBatchVo;
import cn.jdcloud.medicine.mall.dao.product.ItemBatchMapper;
import cn.jdcloud.medicine.mall.dao.product.ItemMapper;
import cn.jdcloud.medicine.mall.domain.product.ItemBatch;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemBatchServiceImpl extends ServiceImpl<ItemBatchMapper, ItemBatch> implements ItemBatchService {
    @Resource
    private ItemMapper itemMapper;
    @Resource
    private ItemBatchMapper itemBatchMapper;

    @Override
    public Map<String, List<ItemBatchVo>> getGroupMapByNos(List<String> nos) {
        List<ItemBatch> itemBatches = this.list(new QueryWrapper<ItemBatch>().in("item_no", nos).eq("is_del", 0));
        Map<String, List<ItemBatchVo>> listMap = itemBatches.stream()
                .map(batch -> new ItemBatchVo(batch))
                .collect(Collectors.groupingBy(ItemBatchVo::getItemNo));
        return listMap;
    }

    @Override
    public void insertBatch(List<ItemBatch> itemBatchList) {
        itemBatchMapper.insertBatch(itemBatchList);
    }

	@Override
	public ItemBatch queryItemBatchBySkuAndItemNo(String sku,String itemNo) {
		return itemBatchMapper.selectOne(new QueryWrapper<ItemBatch>().eq("sku", sku).eq("item_no", itemNo).eq("is_del", 0));
	}

	@Override
	public List<ItemBatch> listItemBatchByItemNo(String itemNo) {
		// TODO Auto-generated method stub
		return itemBatchMapper.selectList(new QueryWrapper<ItemBatch>().eq("item_no", itemNo).eq("is_del", 0));
	}
}
