package com.ljw.eduorder.client.impl;

import com.ljw.commonutils.vo.UcenterMemberVo;
import com.ljw.eduorder.client.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientFileDegradeFeignClient implements UcenterClient {

    /**
     * 通过会员id获取成员信息
     *
     * @param memberId
     * @return {@link UcenterMemberVo}
     */
    @Override
    public UcenterMemberVo getMemberInfoById(String memberId) {
        System.out.println("远程调用 getMemberInfoById失败");
        return null;
    }
}
