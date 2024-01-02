package com.ljw.staservice.client.impl;

import com.ljw.commonutils.R;
import com.ljw.staservice.client.UcenterClient;
import org.springframework.stereotype.Component;

/**
 * ucenter客户impl
 *
 * @author Luo
 */
@Component
public class UcenterClientImpl implements UcenterClient{

    /**
     * 统计注册人数（远程调用）
     *
     * @param day 一天
     * @return {@link Integer}
     */
    @Override
    public R countRegister(String day) {
        return null;
    }
}
