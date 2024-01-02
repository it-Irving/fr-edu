package com.ljw.educms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.commonutils.R;
import com.ljw.educms.entity.CrmBanner;
import com.ljw.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统前台首页控制器
 *
 * @author Luo
 */
@RestController // 响应json数据
@RequestMapping("/educms/bannerfront")  // 请求映射
@CrossOrigin    // 跨域注解
@Api(description = "系统前台banner管理api") // Swagger注解对类描述
//public class BannerFrontController {
public class BannerFrontController {
    @Autowired  // 注入service服务
    private CrmBannerService crmBannerService;

    /**
     * 获取首页的banner数据，轮播图
     *
     * @return {@link R}
     */
    @ApiOperation(value = "查询首页轮播图")
    @GetMapping("getIndexBanner")   // get请求
    //getIndexBanner
    public R getBanner(){
        // 将查询的轮播图信息存到集合中
        List<CrmBanner> crmBannerList = crmBannerService.getIndexBanner();
        // 返回响应数据
        return R.ok().data("crmBannerList", crmBannerList);
    }
}

