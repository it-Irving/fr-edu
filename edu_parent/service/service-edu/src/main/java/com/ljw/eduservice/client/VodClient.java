package com.ljw.eduservice.client;

import com.ljw.commonutils.R;
import com.ljw.eduservice.client.impl.VodFileDegradeFeignClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 视频点播客户
 * 服务调用
 *
 * @author Luo
 */
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class) // 用于指定从哪个服务中调用功能 ,名称与被调用的服务名保持一致
@Component
public interface VodClient {

    /**
     * 删除阿里云视频
     *
     * @param id id
     * @return {@link R}
     */
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id);

    /**
     * 删除批处理
     *
     * @param videoIdList 视频id列表
     * @return {@link R}
     */
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
