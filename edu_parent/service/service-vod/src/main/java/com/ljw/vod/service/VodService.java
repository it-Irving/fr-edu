package com.ljw.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 视频点播服务
 *
 * @author Luo
 */
public interface VodService {
    /**
     * 上传阿里视频
     *
     * @param file 文件
     * @return {@link String}
     */
    String uploadAlyVideo(MultipartFile file);

    /**
     * 删除阿里云视频
     *
     * @param id id
     */
    void removeAlyVideo(String id);

    /**
     * 删除批处理
     *
     * @param videoIdList 视频id列表
     */
    void deleteBatch(List<String> videoIdList);
}
