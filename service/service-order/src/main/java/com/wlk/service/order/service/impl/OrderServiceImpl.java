package com.wlk.service.order.service.impl;

import com.wlk.common.utils.OrderNoUtil;
import com.wlk.common.utils.ordervo.CourseWebVoOrder;
import com.wlk.common.utils.ordervo.MemberOrder;
import com.wlk.service.order.client.EduClient;
import com.wlk.service.order.client.UcenterClient;
import com.wlk.service.order.entity.Order;
import com.wlk.service.order.mapper.OrderMapper;
import com.wlk.service.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author wlk
 * @since 2020-06-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private EduClient eduClient;
    @Resource
    private UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        MemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberIdByJwtToken);

        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
