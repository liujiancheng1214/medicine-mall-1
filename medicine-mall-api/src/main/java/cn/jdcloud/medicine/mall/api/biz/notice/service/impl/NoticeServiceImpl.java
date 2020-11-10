package cn.jdcloud.medicine.mall.api.biz.notice.service.impl;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jdcloud.medicine.mall.api.biz.notice.service.NoticeService;
import cn.jdcloud.medicine.mall.api.biz.notice.vo.NoticeVo;
import cn.jdcloud.medicine.mall.api.common.utils.BeanUtil;
import cn.jdcloud.medicine.mall.dao.notice.NoticeMapper;
import cn.jdcloud.medicine.mall.domain.notice.Notice;


@Service
public class NoticeServiceImpl   extends ServiceImpl<NoticeMapper, Notice>  implements NoticeService {


	@Override
	public NoticeVo queryLastNotice() {
		QueryWrapper<Notice> queryWrapper=new QueryWrapper<Notice>();
		queryWrapper.orderByDesc("create_time");
		Page<Notice> page=new Page<Notice>();
		page.setCurrent(1);
		page.setSize(1);
		page =this.page(page, queryWrapper);
		List<Notice> list=page.getRecords();
		if(list!=null&&list.size()>0) {
			NoticeVo noticeVo=new NoticeVo();
			BeanUtil.copyProperties(list.get(0), noticeVo);
			return noticeVo;
		}
		return null;
	}

}
