package com.wlk.service.cms.service;

import com.wlk.service.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author wlk
 * @since 2020-06-28
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllList();
}
