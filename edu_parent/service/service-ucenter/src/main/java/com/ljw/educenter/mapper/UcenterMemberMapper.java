package com.ljw.educenter.mapper;

import com.ljw.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author luo
 * @since 2023-01-16
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 统计注册人数（远程调用）
     *
     * @param day 一天
     * @return {@link Integer}
     */
    Integer countRegisterByDay(String day);
}
