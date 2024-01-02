package com.ljw.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.commonutils.JwtUtils;
import com.ljw.commonutils.MD5;
import com.ljw.educenter.entity.RegisterVo;
import com.ljw.educenter.entity.UcenterMember;
import com.ljw.educenter.mapper.UcenterMemberMapper;
import com.ljw.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.servicebase.exceptionHandler.LjwException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author luo
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 登录
     *  使用jwt生成token
     * @param ucenterMember ucenter成员
     * @return {@link String}
     */
    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        // 是否禁用
        Boolean isDisabled = ucenterMember.getIsDisabled();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new LjwException(20001, "账号、密码不能为空！");
        }

        // MD5加密,不能解密
        String passwordMD5 = MD5.encrypt(password);

        // 获取手机号对应的密码
        QueryWrapper<UcenterMember> ucenterWrapper = new QueryWrapper<>();
        ucenterWrapper.eq("mobile", mobile);
        UcenterMember ucenterMemberDB = baseMapper.selectOne(ucenterWrapper);

        if(ucenterMemberDB == null){
            throw new LjwException(20001, "该账号未注册，请先注册");
        }


        // 判断密码
        if(!passwordMD5.equals(ucenterMemberDB.getPassword())){
            throw new LjwException(20001, "账号密码错误");
        }

        // 判断用户是否禁用
        if(ucenterMemberDB.getIsDisabled()){
            throw new LjwException(20001, "用户已禁用");
        }

        //成功登录，返回token, 工具类中传入id 呢称
        String jwtToken = JwtUtils.getJwtToken(ucenterMemberDB.getId(), ucenterMemberDB.getNickname());

        return jwtToken;
    }

    /**
     * 注册 需要校验redis验证码
     *
     * @param registerVo 注册签证官
     */
    @Override
    public void register(RegisterVo registerVo) {
        if (registerVo == null){
            throw new LjwException(20001, "注册失败，数据为null");
        }

        // 校验数据
        String mobile = registerVo.getMobile(); //手机号
        String password = registerVo.getPassword(); //密码
        String nickname = registerVo.getNickname(); //昵称
        String code = registerVo.getCode(); //验证码
        Integer sex = registerVo.getSex();
        Integer age = registerVo.getAge();

//        code="1234";

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)){
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)){

                throw new LjwException(20001, "注册失败，数据不能为空");
        }

        // 判断验证码是否正确,从redis获取缓存验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new LjwException(20001, "注册失败，验证码错误");
        }

        // 手机号是否重复
        // 查库
        QueryWrapper<UcenterMember> ucenterWrapper = new QueryWrapper();
        ucenterWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(ucenterWrapper);
        if(count > 0){
            throw new LjwException(20001, "注册失败，手机号已注册");
        }

        // 注册成功插入数据
        UcenterMember ucenterMember = new UcenterMember();

        ucenterMember.setMobile(mobile);
        //MD5加密密码
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setNickname(nickname);
        ucenterMember.setAvatar("https://ljw-edu.oss-cn-guangzhou.aliyuncs.com/2023/03/21/8a650f6544c34b1f9ba883d55a6e6b31th%20%281%29.jpg");
        ucenterMember.setSex(sex);
        ucenterMember.setAge(age);

        // 插入数据
        baseMapper.insert(ucenterMember);
    }

    /**
     * 注册 不需要校验redis验证码
     *
     * @param registerVo 注册签证官
     */
    @Override
    public void register2(RegisterVo registerVo) {
        if (registerVo == null){
            throw new LjwException(20001, "注册失败，数据为null");
        }

        // 校验数据
        String mobile = registerVo.getMobile(); //手机号
        String password = registerVo.getPassword(); //密码
        String nickname = registerVo.getNickname(); //昵称
//        String code = registerVo.getCode(); //验证码
//        Integer sex = registerVo.getSex();
//        Integer age = registerVo.getAge();

//        code="1234";
        System.out.println(registerVo);
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname) ){
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)){

            throw new LjwException(20001, "注册失败，数据不能为空");
        }

//        // 判断验证码是否正确,从redis获取缓存验证码
//        String redisCode = redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(redisCode)){
//            throw new LjwException(20001, "注册失败，验证码错误");
//        }

        // 手机号是否重复
        // 查库
        QueryWrapper<UcenterMember> ucenterWrapper = new QueryWrapper();
        ucenterWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(ucenterWrapper);
        if(count > 0){
            throw new LjwException(20001, "注册失败，手机号已注册");
        }

        // 注册成功插入数据
        UcenterMember ucenterMember = new UcenterMember();

        ucenterMember.setMobile(mobile);
        //MD5加密密码
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setNickname(nickname);
        ucenterMember.setAvatar("https://ljw-edu.oss-cn-guangzhou.aliyuncs.com/2023/03/21/8a650f6544c34b1f9ba883d55a6e6b31th%20%281%29.jpg");
//        ucenterMember.setSex(sex);
//        ucenterMember.setAge(age);

        // 插入数据
        baseMapper.insert(ucenterMember);
    }

    /**
     * 通过微信id查询会员信息
     *
     * @param openid openid
     * @return {@link UcenterMember}
     */
    @Override
    public UcenterMember getMemberByOpenId(String openid) {
        QueryWrapper<UcenterMember> memberWrapper = new QueryWrapper<>();
        memberWrapper.eq("openid", openid);
        UcenterMember ucenterMember = baseMapper.selectOne(memberWrapper);
        return ucenterMember;
    }

    /**
     * 统计注册人数（远程调用）
     *
     * @param day 一天
     * @return {@link Integer}
     */
    @Override
    public Integer countRegisterByDay(String day) {
        Integer count = baseMapper.countRegisterByDay(day);
        return count;
    }


}
