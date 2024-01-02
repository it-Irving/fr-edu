package com.ljw.eduservice.client.impl;

import com.ljw.commonutils.R;
import com.ljw.commonutils.vo.UcenterMemberVo;
import com.ljw.eduservice.client.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterFileDegradeFeignClient implements UcenterClient {
    /**
     * 通过会员id获取成员信息
     *
     * @param memberId
     * @return {@link UcenterMemberVo}
     */
    @Override
    public UcenterMemberVo getMemberInfoById(String memberId) {
        return null;
    }
}
