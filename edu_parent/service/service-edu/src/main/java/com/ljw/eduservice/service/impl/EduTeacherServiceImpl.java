package com.ljw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.entity.vo.TeacherQuery;
import com.ljw.eduservice.mapper.EduTeacherMapper;
import com.ljw.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author luo
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 页面老师条件查询
     *
     * @param pageParam    页面参数
     * @param teacherQuery 老师查询
     */
    @Override
    public void pageTeacherConditionQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        //创建条件构造器
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //排序条件
        wrapper.orderByDesc("gmt_create");
        wrapper.orderByDesc("sort");


        //teacherQuery 空， 返回全部数据
        if (teacherQuery == null){
            baseMapper.selectPage(pageParam, wrapper);
            return;
        }

        //查看teacherQuery 有哪些条件
        String name = teacherQuery.getName();
        String level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //查看条件为空
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)){
            // >=
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)){
            // <=
            wrapper.le("gmt_create", end);
        }

        //条件查询
        baseMapper.selectPage(pageParam, wrapper);
    }

    //================================系统前台===================================================

    /**
     * 获取首页老师数据,加入redis缓存
     *
     * @return {@link List}<{@link EduTeacher}>
     */
    // 添加讲师信息到redis
    @Cacheable(value = "teacher", key = "'getIndexTeacher'")

    @Override
    public List<EduTeacher> getIndexTeacher() {

        QueryWrapper<EduTeacher> eduTeacherWrapper = new QueryWrapper<>();
        // 通过 sort 字段排序进行降序
        eduTeacherWrapper.orderByDesc("sort");
        eduTeacherWrapper.last("limit 4");

        List<EduTeacher> eduTeacherList = baseMapper.selectList(eduTeacherWrapper);
        return eduTeacherList;
    }



    /**
     * 系统前台首页导航栏 分页查询名师
     *
     * @param pageTeacher 页面老师
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> eduTeacherWrapper = new QueryWrapper<>();
        // 通过 sort, id 字段排序进行降序
        eduTeacherWrapper.orderByDesc("sort").orderByDesc("id");
        // 分页查询
        baseMapper.selectPage(pageTeacher, eduTeacherWrapper);

        // 获取分页数据
        List<EduTeacher> records = pageTeacher.getRecords(); //该页数据的list集合
        long current = pageTeacher.getCurrent(); //当前页
        long pages = pageTeacher.getPages(); //总页数
        long size = pageTeacher.getSize(); //每页记录数
        long total = pageTeacher.getTotal(); //总记录数
        boolean hasNext = pageTeacher.hasNext(); //是否有下一页
        boolean hasPrevious = pageTeacher.hasPrevious(); //是否有上一页

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



}
