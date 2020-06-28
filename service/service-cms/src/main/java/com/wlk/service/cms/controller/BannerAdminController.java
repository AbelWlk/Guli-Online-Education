package com.wlk.service.cms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.common.utils.R;
import com.wlk.service.cms.entity.CrmBanner;
import com.wlk.service.cms.service.CrmBannerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-28
 */
@CrossOrigin
@RestController
@RequestMapping("/cms/banner/admin")
public class BannerAdminController {

    @Resource
    private CrmBannerService bannerService;

    //分页查询
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable Long page, @PathVariable Long limit) {

        Page<CrmBanner> pageBanner = new Page<>(page, limit);

        bannerService.page(pageBanner, null);

        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }


    @GetMapping("/getBanner/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("banner", banner);
    }


    @PostMapping("/addBanner")
    public R save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @PutMapping("/updateBanner")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }


    @DeleteMapping("/removeBanner/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}

