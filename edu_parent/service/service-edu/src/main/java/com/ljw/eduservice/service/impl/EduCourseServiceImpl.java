package com.ljw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.eduservice.entity.EduCourse;
import com.ljw.eduservice.entity.EduCourseDescription;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.entity.frontvo.CourseFrontVo;
import com.ljw.eduservice.entity.frontvo.CourseWebVo;
import com.ljw.eduservice.entity.vo.CourseInfoVo;
import com.ljw.eduservice.entity.vo.CoursePublishVo;
import com.ljw.eduservice.mapper.EduCourseMapper;
import com.ljw.eduservice.service.EduChapterService;
import com.ljw.eduservice.service.EduCourseDescriptionService;
import com.ljw.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.eduservice.service.EduVideoService;
import com.ljw.servicebase.exceptionHandler.LjwException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author luo
 * @since 2022-12-13
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;



    /**
     * 保存课程信息
     *
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1.保存课程信息
        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int row = baseMapper.insert(eduCourse);

        if (row <= 0){
            throw new LjwException(20001, "添加课程失败");
        }

        // 2.保存课程描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();

        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);

        // 保存到对应课程下( 课程id = 描述信息id )
        String eduCourseId = eduCourse.getId();
        eduCourseDescription.setId(eduCourseId);

        boolean row2 = eduCourseDescriptionService.save(eduCourseDescription);
        if (!row2){
            throw new LjwException(20001, "添加课程失败");
        }

        return eduCourseId;
    }

    /**
     * 获取课程信息(回显)
     *
     * 需要查2张表（课程表、描述表）
     *
     * @param courseId 进程id
     * @return {@link CourseInfoVo}
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 课程信息对象
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        // 课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // id使用的是courseId
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVo 课程信息
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0){
            throw new LjwException(20001, "修改课程信息失败");
        }

        // 描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 获得发布课程信息
     *
     * @param id id
     * @return {@link CoursePublishVo}
     */
    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getPublishCourseInfo(id);
        return coursePublishVo;
    }

    /**
     * 改-修改课程状态（发布课程）
     */
    @Override
    public void publishCourse(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        baseMapper.updateById(eduCourse);
    }

    /**
     * 获得课程列表
     *
     * @return {@link List}<{@link EduCourse}>
     */
    @Override
    public List<EduCourse> getCourseList() {
        List<EduCourse> eduCourseList = baseMapper.selectList(null);
        return eduCourseList;
    }

    /**
     * 删除课程
     *
     * @param courseId 进程id
     */
    @Override
    public void deleteCourse(String courseId) {
        // 小节
        eduVideoService.deleteVideoByCourseId(courseId);

        //章节
        eduChapterService.deleteChapterByCourseId(courseId);

        //描述
        eduCourseDescriptionService.deleteCourseDescriptionById(courseId);

        int flag = baseMapper.deleteById(courseId);
        if (flag == 0){
            throw new LjwException(20001, "删除课程失败");
        }
    }

    /**
     * 更新课程状态
     *
     * @param eduCourse edu课程
     */
    @Override
    public void updateCourseStatus(EduCourse eduCourse) {
        EduCourse eduCourse2 = new EduCourse();
        eduCourse2.setId(eduCourse.getId());

        if ("Draft".equals(eduCourse.getStatus())){
            eduCourse2.setStatus("Normal");
        }else {
            eduCourse2.setStatus("Draft");
        }

        baseMapper.updateById(eduCourse2);
    }

    //===========================系统前台==================================================================
    /**
     * 获取首页课程
     *
     * @return {@link List}<{@link EduCourse}>
     */
    @Cacheable(key = "'getIndexCourse'", value = "course")
    @Override
    public List<EduCourse> getIndexCourse() {

        QueryWrapper<EduCourse> eduCourseWrapper = new QueryWrapper<>();
        // 目前使用id字段进行排序、后续可以更改成浏览数量、销售数目
        eduCourseWrapper.orderByDesc("id");
        eduCourseWrapper.last("limit 9");
        List<EduCourse> eduCourseList = baseMapper.selectList(eduCourseWrapper);

        return eduCourseList;
    }

    /**
     * 获取首页课程,和描述信息
     *
     * @return {@link List}<{@link EduCourse}>
     */
//    @CachePut(key = "'getIndexCourse'", value = "course")
    @Override
    public List<CourseWebVo> getIndexCourse2() {

        QueryWrapper<EduCourse> eduCourseWrapper = new QueryWrapper<>();
        // 目前使用id字段进行排序、后续可以更改成浏览数量、销售数目
        eduCourseWrapper.orderByDesc("lesson_num");
        eduCourseWrapper.orderByDesc("id");
        eduCourseWrapper.eq("status", "Normal");
        eduCourseWrapper.last("limit 9");
        List<EduCourse> eduCourseList = baseMapper.selectList(eduCourseWrapper);

        List<CourseWebVo> courseWebVoList = new ArrayList<>();

        // 复制列表属性
        for (EduCourse course : eduCourseList) {
            CourseWebVo courseWebVo = new CourseWebVo();
            BeanUtils.copyProperties(course, courseWebVo);

            // 添加描述信息
            EduCourseDescription courseDescription = eduCourseDescriptionService.getById(course.getId());
            courseWebVo.setDescription(courseDescription.getDescription());

            courseWebVoList.add(courseWebVo);
        }

        return courseWebVoList;
    }

    /**
     * 分页查询课程带条件
     *
     * @param pageCourse    页面课程
     * @param courseFrontVo 课程前签证官
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper courseWrapper = new QueryWrapper();
        if (null != courseFrontVo){
            //判断条件值是否为空,不为空就拼接条件
            //一级分类
            if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
                courseWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
            }
            //二级分类
            if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
                courseWrapper.eq("subject_id", courseFrontVo.getSubjectId());
            }
            //关注度排序
            if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
                courseWrapper.orderByDesc("buy_count");
            }
            //最新时间排序
            if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
                courseWrapper.orderByDesc("gmt_create");
            }
            //价格排序
            if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
                courseWrapper.orderByDesc("price");
            }
        }

        courseWrapper.eq("status", "Normal");
        baseMapper.selectPage(pageCourse, courseWrapper);

        //获取分页所有数据
        List<EduCourse> records = pageCourse.getRecords(); //该页数据的list集合
        long current = pageCourse.getCurrent(); //当前页
        long pages = pageCourse.getPages(); //总页数
        long size = pageCourse.getSize(); //每页记录数
        long total = pageCourse.getTotal(); //总记录数
        boolean hasNext = pageCourse.hasNext(); //是否有下一页
        boolean hasPrevious = pageCourse.hasPrevious(); //是否有上一页

        //把分页数据放到map集合中
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

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
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        CourseWebVo courseInfo = baseMapper.getBaseCourseInfo(courseId);
        return courseInfo;
    }


}
