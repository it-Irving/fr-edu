package com.ljw.eduservice.service.impl;

import com.ljw.eduservice.entity.EduCourseDescription;
import com.ljw.eduservice.mapper.EduCourseDescriptionMapper;
import com.ljw.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author luo
 * @since 2022-12-13
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    /**
     * 删除课程描述通过id
     *
     * @param courseId 进程id
     */
    @Override
    public void deleteCourseDescriptionById(String courseId) {
        baseMapper.deleteById(courseId);
    }
}
