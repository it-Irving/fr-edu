package com.ljw.eduservice.service;

import com.ljw.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author luo
 * @since 2022-12-13
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 删除视频课程简讯id
     *
     * @param courseId 进程id
     */
    void deleteVideoByCourseId(String courseId);
}
