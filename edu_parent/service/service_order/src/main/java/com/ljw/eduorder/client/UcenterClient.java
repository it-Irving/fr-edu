package com.ljw.eduorder.client;

import com.ljw.commonutils.vo.UcenterMemberVo;

import com.ljw.eduorder.client.impl.UcenterClientFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ucenter客户
 *
 * @author Luo
 */
@FeignClient(name = "service-ucenter", fallback = UcenterClientFileDegradeFeignClient.class) // 用于指定从哪个服务中调用功能 ,名称与被调用的服务名保持一致
@Component
public interface UcenterClient {
    /**
     * 通过会员id获取成员信息
     *
     * @param memberId
     * @return {@link UcenterMemberVo}
     */
    @PostMapping("/educenter/ucenter-member/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String memberId);
}
