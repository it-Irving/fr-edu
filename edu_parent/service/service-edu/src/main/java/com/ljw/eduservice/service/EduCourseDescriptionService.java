package com.ljw.eduservice.service;

import com.ljw.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author luo
 * @since 2022-12-13
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    /**
     * 删除课程描述通过id
     *
     * @param courseId 进程id
     */
    void deleteCourseDescriptionById(String courseId);
}
