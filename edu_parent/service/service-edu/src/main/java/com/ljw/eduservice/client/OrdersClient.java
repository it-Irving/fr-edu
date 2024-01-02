package com.ljw.eduservice.client;

import com.ljw.eduservice.client.impl.OrdersFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 订单
 *
 * @author Luo
 */
@FeignClient(name = "service-order", fallback = OrdersFileDegradeFeignClient.class) // 用于指定从哪个服务中调用功能 ,名称与被调用的服务名保持一致
@Component
public interface OrdersClient {
    /**
     * 判断课程是否支付
     *
     * @param courseId
     * @param memberId
     * @return {@link Boolean}
     */
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);
}