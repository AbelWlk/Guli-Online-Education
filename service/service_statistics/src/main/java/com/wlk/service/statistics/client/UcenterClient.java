package com.wlk.service.statistics.client;

import com.wlk.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UcenterClient {

    @GetMapping(value = "/ucenter/member/countregister/{day}")
    public R registerCount(@PathVariable("day") String day);
}
