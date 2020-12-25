package cn.jdcloud.medicine.mall.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.product.service.PresaleService;
import cn.jdcloud.medicine.mall.api.biz.product.vo.PresaleItemVo;
import cn.jdcloud.medicine.mall.api.common.utils.UserContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/presale")
@Api(tags = "预售接口")
public class PresaleRest {
	@Autowired
	private PresaleService  presaleService;
	@Autowired
	private UserContextUtil userContextUtil;

	@ApiOperation(value = "分页查询预售列表")
	@GetMapping(value = "/pagePresale")
	public ApiResult<Page<PresaleItemVo>> pagePresale(int pageNum,int pageSize,String searchValue) {
		Page<PresaleItemVo>  page=presaleService.pagePresale(pageNum, pageSize, searchValue);
		return ApiResult.ok(page);
	}

	
	

	

}
