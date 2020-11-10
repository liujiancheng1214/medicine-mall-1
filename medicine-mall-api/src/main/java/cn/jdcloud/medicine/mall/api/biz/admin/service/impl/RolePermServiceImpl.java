package cn.jdcloud.medicine.mall.api.biz.admin.service.impl;


import cn.jdcloud.medicine.mall.api.biz.admin.service.RolePermService;
import cn.jdcloud.medicine.mall.dao.admin.RolePermMapper;
import cn.jdcloud.medicine.mall.domain.admin.RolePermission;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class RolePermServiceImpl extends ServiceImpl<RolePermMapper, RolePermission> implements RolePermService {

}
