package com.ljw.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.commonutils.R;

import com.ljw.eduservice.entity.EduCourse;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.service.EduCourseService;
import com.ljw.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师前端控制器
 *
 * 首页导航栏的名师
 *
 * @author Luo
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacherfront")
@Api(description = "系统前台首页导航栏 名师管理api")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 系统前台首页导航栏 分页查询名师
     *
     * @param page  页面
     * @param limit 限制
     * @return {@link R}
     */
    @ApiOperation(value = "系统前台首页导航栏 分页查询名师")
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(
            @ApiParam(name = "page", value = "当前页", required = true) @PathVariable long page,
            @ApiParam(name = "limit", value = "每页大小", required = true) @PathVariable long limit){

        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        Map<String, Object> map = eduTeacherService.getTeacherFrontList(pageTeacher);

        return R.ok().data(map);
    }

    /**
     * 系统前台讲师详情-讲师、及其课程
     * 根据id查讲师、课程
     *
     * @param teacherId 老师id
     * @return {@link R}
     */
    @ApiOperation(value = "系统前台讲师详情-讲师、及其课程")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        // 1.查询讲师
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);

        // 2.查询讲师对应的课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.eq("teacher_id", teacherId);
        List<EduCourse> eduCourseList = eduCourseService.list(courseWrapper);
        return R.ok().data("teacher", eduTeacher).data("courseList", eduCourseList);
    }



}
