package com.ljw.eduservice.client.impl;

import com.ljw.commonutils.R;
import com.ljw.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断
 * service-vod 超时、宕机 时调用该方法
 *
 * @author Luo
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    /**
     * 删除阿里云视频
     *
     * @param id id
     * @return {@link R}
     */
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    /**
     * 删除批处理
     *
     * @param videoIdList 视频id列表
     * @return {@link R}
     */
    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("批量删除视频出错了");
    }
}
