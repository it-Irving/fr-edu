package com.ljw.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljw.eduservice.entity.vo.TeacherQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author luo
 * @since 2022-11-27
 */
@Service
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 页面老师条件查询
     *
     * @param pageParam    页面参数
     * @param teacherQuery 老师查询
     */
    void pageTeacherConditionQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);


    /**
     * 获取首页老师数据
     *
     * @return {@link List}<{@link EduTeacher}>
     */
    List<EduTeacher> getIndexTeacher();

    /**
     * 系统前台首页导航栏 分页查询名师
     *
     * @param pageTeacher 页面老师
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
