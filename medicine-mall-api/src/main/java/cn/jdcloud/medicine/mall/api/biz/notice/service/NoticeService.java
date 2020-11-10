package cn.jdcloud.medicine.mall.api.biz.notice.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.jdcloud.medicine.mall.api.biz.notice.vo.NoticeVo;
import cn.jdcloud.medicine.mall.domain.notice.Notice;

public interface NoticeService  extends IService<Notice>{

	NoticeVo queryLastNotice();
}
