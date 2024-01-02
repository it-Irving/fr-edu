package com.ljw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.commonutils.R;
import com.ljw.eduservice.entity.EduCourse;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.entity.vo.CourseInfoVo;
import com.ljw.eduservice.entity.vo.CoursePublishVo;
import com.ljw.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author luo
 */
@Api(description = "课程管理api") // Swagger注解对类描述
@RestController // 响应json数据
@RequestMapping("/eduservice/edu-course")   // 请求映射
@CrossOrigin // 跨域注解
public class EduCourseController {

    @Autowired  // 注入service
    private EduCourseService eduCourseService;

    @ApiOperation("添加课程信息") // Swagger注解对方法描述
    @PostMapping("/saveCourseInfo")
    //saveCourseInfo
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
//        String id = eduCourseService.saveCourseInfo(courseInfoVo);  // 返回课程id
        String courseId = eduCourseService.saveCourseInfo(courseInfoVo); // 返回课程id
        return R.ok().data("courseId", courseId); // 响应数据
    }

    /**
     * 获取课程信息(回显)
     *
     * @param courseId id
     * @return {@link R}
     */
    @ApiOperation("回显课程信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId); // 获取课程数据
        return R.ok().data("courseInfoVo", courseInfoVo); // 响应数据
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVo 课程信息
     * @return {@link R}
     */
    @ApiOperation("改-修改课程信息")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo); // 修改课程信息
        return R.ok(); // 响应数据
    }

    /**
     * 获得发布课程信息
     *
     * @param id id
     * @return {@link R}
     */
    @ApiOperation("查-获取课程信息（发布课程）")
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("coursePublishVo", coursePublishVo);
    }

    /**
     * 改-修改课程状态（发布课程）
     *
     * @param courseId 进程id
     * @return {@link R}
     */
    @ApiOperation("改-修改课程状态（发布课程）")
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        eduCourseService.publishCourse(courseId); // 发布状态
        return R.ok();  // 响应数据
    }

    /**
     * 获得课程列表
     *
     * @return {@link R}
     */
    @ApiOperation("查-显示所有课程")
    @GetMapping("/getCourseList")
    public R getCourseList(){
        List<EduCourse> eduCourseList = eduCourseService.getCourseList();
        return R.ok().data("courseList", eduCourseList);
    }

    /**
     * 页面列表
     *
     * @return {@link R}
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/coursePageList/{page}/{limit}")
    public R coursePageList(@ApiParam(name = "page", value = "当前页", required = true) @PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页大小", required = true) @PathVariable Long limit){

        //1.创建page对象
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);

        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("gmt_create");

        //2.使用分页查询方法，分页查询的方法会封装到 page对象中（pageParam）
        eduCourseService.page(pageParam, eduCourseQueryWrapper);

        //3.获取查询数据 (获取所有记录)
        List<EduCourse> records = pageParam.getRecords();
        //获取总记录数
        long total = pageParam.getTotal();

        //4.封装数据，返回结果集
        return R.ok().data("total",total).data("rows", records);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        eduCourseService.deleteCourse(courseId);    // 通过课程id删除课程
        return R.ok();  // 响应数据
    }

    @ApiOperation("修改课程状态")
    @PostMapping("/updateCourseStatus")
    public R updateCourseStatus(@RequestBody EduCourse eduCourse){
        eduCourseService.updateCourseStatus(eduCourse); // 更新课程状态
        return R.ok();  // 响应数据
    }
}

