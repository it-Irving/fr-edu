package com.ljw.msmservice.controller;

import com.ljw.commonutils.R;
import com.ljw.msmservice.service.MsmService;
import com.ljw.msmservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 短信服务
 *
 * @author Luo
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
@Api(description="短信管理api")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 发送短信
     *
     * @param phone 电话
     * @return {@link R}
     */
    @ApiOperation(value = "发送短信")
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        // redis设置了5分钟有效
        String redisPhone = redisTemplate.opsForValue().get(phone);

        if(!StringUtils.isEmpty(redisPhone)){
            return R.ok();
        }

        //redis中没有数据，调用阿里云发短信

        // 生成4位随机数
        String code = RandomUtil.getFourBitRandom();
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("code", code);

        // 调用service中的阿里云发送短信方法
        boolean flag = msmService.sendMsm(codeMap, phone);

        // 保存验证码到redis
        if(flag){
            // 发送成功，把验证码存入redis, 设置5分钟有效时间
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return R.ok().message("验证码发送成功");
        }else {
            return R.error().message("短信发送失败");
        }

    }
}
