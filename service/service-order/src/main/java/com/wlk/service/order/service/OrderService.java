package com.wlk.service.order.service;

import com.wlk.service.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author wlk
 * @since 2020-06-29
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberIdByJwtToken);
}
