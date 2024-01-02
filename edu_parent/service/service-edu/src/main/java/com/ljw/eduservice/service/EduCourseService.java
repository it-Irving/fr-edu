package com.ljw.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.entity.frontvo.CourseFrontVo;
import com.ljw.eduservice.entity.frontvo.CourseWebVo;
import com.ljw.eduservice.entity.vo.CourseInfoVo;
import com.ljw.eduservice.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author luo
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程信息
     *
     * @param courseInfoVo
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 获取课程信息(回显)
     *
     * @param courseId 进程id
     * @return {@link CourseInfoVo}
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     *
     * @param courseInfoVo 课程信息
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 获得发布课程信息
     *
     * @param id id
     * @return {@link CoursePublishVo}
     */
    CoursePublishVo getPublishCourseInfo(String id);

    /**
     * 改-修改课程状态（发布课程）
     */
    void publishCourse(String courseId);

    /**
     * 获得课程列表
     *
     * @return {@link List}<{@link EduCourse}>
     */
    List<EduCourse> getCourseList();

    /**
     * 删除课程
     *
     * @param courseId 进程id
     */
    void deleteCourse(String courseId);

    /**
     * 更新课程状态
     *
     * @param eduCourse edu课程
     */
    void updateCourseStatus(EduCourse eduCourse);

    //===========================前台===================================================

    /**
     * 获取首页课程
     *
     * @return {@link List}<{@link EduCourse}>
     */
    List<EduCourse> getIndexCourse();

    List<CourseWebVo> getIndexCourse2();


    /**
     * 分页查询课程带条件
     *
     * @param pageCourse    页面课程
     * @param courseFrontVo 课程前签证官
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

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
