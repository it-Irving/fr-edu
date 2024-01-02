package com.ljw.educms.service;

import com.ljw.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author luo
 * @since 2023-01-14
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 获取首页index的banner数据
     *
     * @return {@link List}<{@link CrmBanner}>
     */
    List<CrmBanner> getIndexBanner();

    List<CrmBanner> myUpdateById(CrmBanner crmBanner);

    List<CrmBanner> addBanner(CrmBanner crmBanner);
}
