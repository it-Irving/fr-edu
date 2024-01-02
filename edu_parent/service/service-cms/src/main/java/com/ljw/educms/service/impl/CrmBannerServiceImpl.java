package com.ljw.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.educms.entity.CrmBanner;
import com.ljw.educms.mapper.CrmBannerMapper;
import com.ljw.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author luo
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 将banner和selectIndexList之间加两个冒号(::)就组成了存到redis中的键banner::selectIndexList
    // value缓存名,必填,它指定了你的缓存存放在哪块命名空间,key可选属性,可以使用SpEL标签自定义缓存的key

    /**
     * 获取首页轮播图数据
     *
     * @return {@link List}<{@link CrmBanner}>
     */
    @Cacheable(key = "'getIndexBanner'", value = "banner")  // 组成"banner::getIndexBanner"存入redis中
    @Override
    public List<CrmBanner> getIndexBanner() {
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>(); // 创建条件构造器

        crmBannerQueryWrapper.orderByDesc("sort");    // 降序id，获取新加入的轮播图
        crmBannerQueryWrapper.last("limit 4");  // 拼接SQL语句

        List<CrmBanner> crmBannerList = baseMapper.selectList(crmBannerQueryWrapper); // 查询数据库
        return crmBannerList; // 返回响应数据
    }

    /**
     * 修改轮播图，并且更新缓存
     *
     * @param crmBanner crm横幅
     * @return {@link List}<{@link CrmBanner}>
     */
    @Override
    @CachePut(key = "'getIndexBanner'", value = "banner")   // 更新缓存数据
    public List<CrmBanner> myUpdateById(CrmBanner crmBanner) {
        baseMapper.updateById(crmBanner);
        // redisTemplate.opsForValue().set("banner:getIndexBanner", crmBanner);

        // 更新缓存的轮播图
        List<CrmBanner> list = new ArrayList<>();
        list = this.getIndexBanner();

        // 返回数据，更新到redis
        return list;
    }

    /**
     * 添加轮播图，并且更新缓存
     *
     * @param crmBanner crm横幅
     * @return {@link List}<{@link CrmBanner}>
     */
    @Override
    @CachePut(key = "'getIndexBanner'", value = "banner")   // 更新缓存数据
    public List<CrmBanner> addBanner(CrmBanner crmBanner) {
        baseMapper.insert(crmBanner);
        // redisTemplate.opsForValue().set("banner:getIndexBanner", crmBanner);

        // 更新缓存的轮播图
        List<CrmBanner> list = new ArrayList<>();
        list = this.getIndexBanner();

        // 返回数据，更新到redis
        return list;
    }


}
