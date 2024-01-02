package com.ljw.eduservice.controller;


import com.ljw.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 登录
 *
 * @author Luo
 */
@Api(description="登录测试")
@RestController
@CrossOrigin    //解决跨域
@RequestMapping("/eduService/user")
public class EduLoginController {

    @ApiOperation(value = "登录校验")
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    @ApiOperation(value = "响应信息")
    @GetMapping("/info")
    public R info(){
        return R.ok()
                .data("roles","[admin]")
                .data("name", "admin")
                .data("avatar", "https://ljw-edu.oss-cn-guangzhou.aliyuncs.com/2023/03/21/c22483d02c634e7cab9aaa032cba9922th.jpg");
    }

}
