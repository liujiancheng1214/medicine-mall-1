package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.notice.service.NoticeService;
import cn.jdcloud.medicine.mall.api.biz.notice.vo.NoticeVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController
//@RequestMapping("/notice")
//@Api(tags = "公告接口")
public class NoticeRest {


	@Autowired
	private NoticeService noticeService;


	@ApiOperation(value = "查询首页公告")
	@GetMapping(value = "/queryItemDetail")
	public ApiResult<NoticeVo> queryItemDetail(String itemNo){
		NoticeVo notice=noticeService.queryLastNotice();
		return ApiResult.ok(notice);
	}

}
