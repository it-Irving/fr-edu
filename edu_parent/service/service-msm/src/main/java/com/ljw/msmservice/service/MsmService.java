package com.ljw.msmservice.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 短信服务
 *
 * @author Luo
 */

public interface MsmService {
    /**
     * 调用阿里云发送短信方法
     *
     * @param codeMap 短信验证码map
     * @param phone   电话
     * @return boolean
     */
    boolean sendMsm(Map<String, Object> codeMap, String phone);
}
