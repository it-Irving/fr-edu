package com.ljw.eduservice.mapper;

import com.ljw.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.eduservice.entity.frontvo.CourseWebVo;
import com.ljw.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author luo
 */
//@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * 获得发布课程信息
     *
     * @param id 课程id
     * @return {@link CoursePublishVo}
     */
    CoursePublishVo getPublishCourseInfo(String id);

    /**
     * 获取课程详情
     * 课程基本信息
     * 课程分类
     * 课程简介
     * 所属讲师
     *
     * @param courseId 进程id
     * @return {@link CourseWebVo}
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
