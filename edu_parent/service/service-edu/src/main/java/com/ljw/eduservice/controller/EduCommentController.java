package com.ljw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.commonutils.JwtUtils;
import com.ljw.commonutils.R;
import com.ljw.commonutils.vo.UcenterMemberVo;
import com.ljw.eduservice.client.UcenterClient;
import com.ljw.eduservice.entity.EduComment;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.entity.vo.CourseInfoVo;
import com.ljw.eduservice.service.EduCommentService;
import com.ljw.eduservice.service.EduCourseService;
import com.ljw.servicebase.exceptionHandler.LjwException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author luo
 */
@RequestMapping("/eduservice/edu-comment") // 请求映射
@RestController // 响应json数据
@CrossOrigin // 跨域注解
@Api(description = "评论管理api") // Swagger注解对类描述
public class EduCommentController {
    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;
    /**
     * 获取评论页面带分页
     *
     * @param page     页面
     * @param limit    限制
     * @param courseId 进程id
     * @return {@link R}
     */
    @ApiOperation(value = "通过课程id查询评论带分页")
    @GetMapping("/getCommentPage/{page}/{limit}/{courseId}")
    public R getCommentPage(@ApiParam(name = "page", value = "当前页", required = true) @PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页大小", required = true) @PathVariable Long limit,
                            @PathVariable String courseId
    ){
        // 1. 创建page对象
        Page<EduComment> commentPage = new Page<>(page, limit);

        // 2. 通过课程id查询评论
        QueryWrapper<EduComment> commentWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(courseId)){
            commentWrapper.eq("course_id", courseId);
        }
        //按最新时间排序
        commentWrapper.orderByDesc("gmt_create");
        // 查询数据库
        eduCommentService.page(commentPage, commentWrapper);

        //3.获取总记录
        List<EduComment> records = commentPage.getRecords();
        long total = commentPage.getTotal();

        //4.响应数据
        return R.ok().data("rows", records).data("total", total);
    }

    /**
     * 添加评论
     *
     * @param request    请求
     * @param eduComment edu评论
     * @return {@link R}
     */
    @ApiOperation(value = "添加评论")
    @PostMapping("/auth/addComment")
    public R addComment(HttpServletRequest request, @RequestBody EduComment eduComment){

        // 判断用户是否登录（用Jwt工具类获取请求头的token信息）
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        if (StringUtils.isEmpty(memberId)){
            throw new LjwException(20001, "请先登录后评论");
        }

        // 用户已登录，通过用户id查询用户信息(调用service-ucenter服务的方法)
        UcenterMemberVo ucenterMemberVo = ucenterClient.getMemberInfoById(memberId);

        // 封装用户信息
        eduComment.setMemberId(memberId);
        eduComment.setAvatar(ucenterMemberVo.getAvatar());
        eduComment.setNickname(ucenterMemberVo.getNickname());

        //添加到数据库
        eduCommentService.save(eduComment);

        return R.ok();
    }

    // ============================后台管理======================================
    /**
     * 查询所有评论带分页
     *
     * @return {@link List}<{@link EduTeacher}>
     */

    @ApiOperation(value = "查询所有评论带分页")
    @GetMapping("/getAllCommentPage/{page}/{limit}")
    public R getAllComment(@ApiParam(name = "page", value = "当前页", required = true) @PathVariable Long page,
                            @ApiParam(name = "limit", value = "每页大小", required = true) @PathVariable Long limit
    ){
        // 1. 创建page对象
        Page<EduComment> commentPage = new Page<>(page, limit);

        // 2. 通过课程id查询评论
        QueryWrapper<EduComment> commentWrapper = new QueryWrapper<>();

        //按最新时间排序
        commentWrapper.orderByDesc("gmt_create");
        // 查询数据库
        eduCommentService.page(commentPage, commentWrapper);

        //3.获取总记录
        List<EduComment> records = commentPage.getRecords();
        long total = commentPage.getTotal();

        //4.响应数据
        return R.ok().data("rows", records).data("total", total);
    }

    @ApiOperation(value = "删除评论")
    @DeleteMapping("/deleteComment/{commentId}")
    public R deleteComment(@PathVariable String commentId){

        if (StringUtils.isEmpty(commentId)){
            throw new LjwException(20001, "删除评论失败");
        }

        eduCommentService.removeById(commentId);

        return R.ok();
    }





}

