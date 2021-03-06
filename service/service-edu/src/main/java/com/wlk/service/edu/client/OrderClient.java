package com.wlk.service.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-order")
@Component
public interface OrderClient {

    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
