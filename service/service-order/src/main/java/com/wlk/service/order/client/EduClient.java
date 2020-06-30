package com.wlk.service.order.client;

import com.wlk.common.utils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-edu")
@Component
public interface EduClient {
    //根据课程id查询课程信息
    @PostMapping("/edu/front/getCourseInfoOrder/{id}")
    CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);

}
