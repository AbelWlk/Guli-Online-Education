package com.wlk.service.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.common.utils.JwtUtils;
import com.wlk.common.utils.R;
import com.wlk.service.order.entity.Order;
import com.wlk.service.order.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-29
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    //根据订单id查询信息
    @GetMapping("/getOrderInfo/{orderId}")
    private R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order one = orderService.getOne(wrapper);
        return R.ok().data("item", one);
    }

    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isCourse(@PathVariable String courseId, @PathVariable String memberId) {

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        return count > 0;
    }
}

