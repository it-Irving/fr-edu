package com.ljw.educenter.service;

import com.ljw.educenter.entity.RegisterVo;
import com.ljw.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author luo
 */
@Service
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     *  使用jwt生成token
     * @param ucenterMember ucenter成员
     * @return {@link String}
     */
    String login(UcenterMember ucenterMember);

    /**
     * 注册 需要校验redis验证码
     *
     * @param registerVo 注册签证官
     */
    void register(RegisterVo registerVo);

    void register2(RegisterVo registerVo);

    /**
     * 通过微信id查询会员信息
     *
     * @param openid openid
     * @return {@link UcenterMember}
     */
    UcenterMember getMemberByOpenId(String openid);

    /**
     * 统计注册人数（远程调用）
     *
     * @param day 一天
     * @return {@link Integer}
     */
    Integer countRegisterByDay(String day);

}
