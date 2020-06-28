package com.wlk.service.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.service.cms.entity.CrmBanner;
import com.wlk.service.cms.mapper.CrmBannerMapper;
import com.wlk.service.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author wlk
 * @since 2020-06-28
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Resource
    private CrmBannerMapper bannerMapper;

    @Cacheable(value = "banner", key = "'selectAllList'")
    @Override
    public List<CrmBanner> selectAllList() {
        //根据id降序排列 显示排列之后前两条数据
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");

        return bannerMapper.selectList(null);
    }
}
