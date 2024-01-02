package com.ljw.eduorder.service;

import com.ljw.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author luo
 * @since 2023-01-20
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     *
     * @param courseId           课程id
     * @param memberIdByJwtToken 成员由jwt id令牌
     * @return {@link String}
     */
    String createOrder(String courseId, String memberIdByJwtToken);
}
