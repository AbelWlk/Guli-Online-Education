package com.wlk.service.cms.controller;

import com.wlk.common.utils.R;
import com.wlk.service.cms.entity.CrmBanner;
import com.wlk.service.cms.service.CrmBannerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cms/banner/front")
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerService;

    @GetMapping("/getAllBanner")
    public R index() {
        List<CrmBanner> list = bannerService.selectAllList();
        return R.ok().data("bannerList", list);
    }

}
