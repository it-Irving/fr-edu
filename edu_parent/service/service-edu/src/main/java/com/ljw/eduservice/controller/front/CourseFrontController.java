package com.ljw.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.commonutils.JwtUtils;
import com.ljw.commonutils.R;
import com.ljw.commonutils.orderVo.CourseWebVoOrder;
import com.ljw.eduservice.client.OrdersClient;
import com.ljw.eduservice.entity.EduCourse;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.entity.chapter.ChapterVo;
import com.ljw.eduservice.entity.frontvo.CourseFrontVo;
import com.ljw.eduservice.entity.frontvo.CourseWebVo;
import com.ljw.eduservice.service.EduChapterService;
import com.ljw.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 系统前台 课程部分
 *c
 * @author Luo
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/courseFront")
@Api(description = "系统前台 课程部分管理api")
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrdersClient ordersClient;

    /**
     * 分页查询课程带条件
     *  查询已发布课程
     * @param page          页面
     * @param limit         限制
     * @param courseFrontVo 课程前签证官
     * @return {@link R}
     */
    @ApiOperation(value = "分页查询课程带条件")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(
            @ApiParam(name = "page", value = "当前页", required = true) @PathVariable long page,
            @ApiParam(name = "limit", value = "每页大小", required = true) @PathVariable long limit,
            @ApiParam(name = "courseFrontVo", value = "条件查询", required = false) @RequestBody(required = false) CourseFrontVo courseFrontVo
    ){

        // 创建page对象
        Page<EduCourse> pageCourse = new Page<>(page,limit);

        Map<String, Object> map = eduCourseService.getFrontCourseList(pageCourse, courseFrontVo);

        return R.ok().data(map);
    }

    /**
     * 课程详情
     *
     * @param courseId 进程id
     * @return {@link R}
     */
    @ApiOperation(value = "课程详情")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){

        //1.根据课程id查询课程信息(手写sql语句来实现)
        //课程基本信息
        //课程分类
        //课程简介
        //所属讲师
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);

        //2.根据课程id查询课程章节和小节
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideo(courseId);

        Boolean isBuyCourse = false;
        // 3.查询是否购买该课程
        // 判断用户是否登录
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        // 处理用户已经登录
        if(!StringUtils.isEmpty(memberIdByJwtToken)){
            isBuyCourse = ordersClient.isBuyCourse(courseId, memberIdByJwtToken);
        }

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuyCourse",isBuyCourse);
    }

    /**
     * 通过id获取课程，远程调用
     *
     * @param courseId 进程id
     * @return {@link CourseWebVoOrder}
     */
    @ApiOperation(value = "通过id获取课程，远程调用")
    @GetMapping("getEduCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getEduCourseInfoOrder(@PathVariable String courseId){
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);

        // 封装到公共实体类，远程调用
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo, courseWebVoOrder);
        return courseWebVoOrder;
    }


}
