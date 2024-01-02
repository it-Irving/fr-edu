package com.ljw.staservice.client;

import com.ljw.commonutils.R;
import com.ljw.staservice.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ucenter客户
 *
 * @author Luo
 */
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class) // 用于指定从哪个服务中调用功能 ,名称与被调用的服务名保持一致
@Component
public interface UcenterClient {
    /**
     * 统计注册人数（远程调用）
     *
     * @param day 一天
     * @return {@link Integer}
     */
    @GetMapping("/educenter/ucenter-member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
