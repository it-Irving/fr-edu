package com.ljw.educms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.commonutils.R;
import com.ljw.educms.entity.CrmBanner;
import com.ljw.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 系统后台台控制器
 * </p>
 *
 * @author luo
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
@Api(description = "系统后台台banner管理api")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    /**
     * 分页查询banner
     * @return {@link R}
     */
    @ApiOperation(value = "分页查询banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<CrmBanner> pageBanner = new Page<CrmBanner>(page, limit);

        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        crmBannerQueryWrapper.orderByDesc("sort");

        crmBannerService.page(pageBanner,crmBannerQueryWrapper);
        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    /**
     * 添加banner
     *
     * @param crmBanner
     * @return {@link R}
     */
//    @ApiOperation(value = "添加banner")
////    @Cacheable(value = "banner", key = "'selectIndexList'")
//    @PostMapping("addBanner")
//    public R addBanner(@RequestBody CrmBanner crmBanner){
//        crmBannerService.save(crmBanner);
//        return R.ok();
//    }
    @ApiOperation(value = "添加banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.addBanner(crmBanner);
        return R.ok();
    }


    /**
     * 通过id获取banner
     *
     * @param id id
     * @return {@link R}
     */
    @ApiOperation(value = "回显banner")
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("crmBanner", crmBanner);
    }

    /**
     * 更新通过id
     *
     * @param crmBanner crm横幅
     * @return {@link R}
     */
    @ApiOperation(value = "修改banner")
    @PostMapping("updateById")
    public R updateById(@RequestBody CrmBanner crmBanner){
        crmBannerService.myUpdateById(crmBanner);
        return R.ok();
    }

    /**
     * 删除通过id
     *
     * @param id id
     * @return {@link R}
     */
    @ApiOperation(value = "删除banner")
    @CacheEvict(key = "'getIndexBanner'", value = "banner")
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }
}

