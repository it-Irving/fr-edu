package com.ljw.eduservice.client.impl;

import com.ljw.eduservice.client.OrdersClient;
import org.springframework.stereotype.Component;

/**
 * 降低假装客户订单文件
 *
 * @author Luo
 */
@Component
public class OrdersFileDegradeFeignClient implements OrdersClient {
    /**
     * 判断课程是否支付
     *
     * @param courseId
     * @param memberId
     * @return {@link Boolean}
     */
    @Override
    public Boolean isBuyCourse(String courseId, String memberId) {
        System.out.println("远程调用orders失败。。。。");
        return false;
    }
}
