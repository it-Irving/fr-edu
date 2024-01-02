package com.ljw.eduservice.controller;


import com.ljw.commonutils.R;
import com.ljw.eduservice.entity.EduCourseDescription;
import com.ljw.eduservice.entity.vo.CourseInfoVo;
import com.ljw.eduservice.service.EduCourseDescriptionService;
import com.ljw.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程简介 前端控制器 依赖于课程信息（不会使用该controller）
 * </p>
 *
 * @author luo
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/eduservice/edu-course-description")
public class EduCourseDescriptionController {
    @Autowired  // 注入service
    private EduCourseDescriptionService eduCourseDescriptionService;

    @ApiOperation("通过课程id获取描述信息") // Swagger注解对方法描述
    @PostMapping("/getDescBycourseId/{courseId}")
    public R getDescBycourseId(@PathVariable String courseId){
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        return R.ok().data("eduCourseDescription", eduCourseDescription); // 响应数据
    }

}

