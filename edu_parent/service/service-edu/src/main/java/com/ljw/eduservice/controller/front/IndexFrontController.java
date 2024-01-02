package com.ljw.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.commonutils.R;
import com.ljw.eduservice.entity.EduCourse;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.entity.frontvo.CourseWebVo;
import com.ljw.eduservice.service.EduCourseService;
import com.ljw.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前端控制器
 *
 * 为什么不将上述代码写到service_cms模块?因为这些代码是需要查讲师表和课程表的,
 * 如果想在service_cms模块操作讲师表和课程表,
 * 就需要复制讲师和课程的entity、service、mapper、xml到service_cms模块,这样太麻烦了,
 * 所以我们在service_edu模块编写上述这些代码
 *
 * 主要编写前台页面的 课程、讲师 api接口
 *
 * @author Luo
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/indexfront")
@Api(description = "系统前台 首页管理api")
public class IndexFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 查询前8条热门课程,查询前4条名师
     *
     * @return {@link R}
     */
    @GetMapping("index")
    public R index(){
        //查询首页前4个讲师, 并存入redis中
        List<EduTeacher> eduTeacherList = eduTeacherService.getIndexTeacher();



        // 查询首页前8条热门课程，并存入redis中
//        List<EduCourse> eduCourseList = eduCourseService.getIndexCourse();
        List<CourseWebVo> eduCourseList = eduCourseService.getIndexCourse2();


        return R.ok().data("eduTeacherList",eduTeacherList).data("eduCourseList",eduCourseList);
    }
}
