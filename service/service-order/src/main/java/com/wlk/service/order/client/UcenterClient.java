package com.wlk.service.order.client;

import com.wlk.common.utils.ordervo.MemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-ucenter")
@Component
public interface UcenterClient {

    //根据用户id获取用户信息
    @PostMapping("/ucenter/member/getUserInfoOrder/{id}")
    public MemberOrder getUserInfoOrder(@PathVariable("id") String id);

}
