package com.ljw.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.eduservice.entity.EduSubject;
import com.ljw.eduservice.entity.excel.SubjectPoJo;
import com.ljw.eduservice.service.EduSubjectService;
import com.ljw.servicebase.exceptionHandler.LjwException;

/**
 * 课程分类excel侦听器
 *
 * @author Luo
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectPoJo> {

    // 构造方法
    public SubjectExcelListener() {
    }
    // 构造方法,传入service对象
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    // service 对象
    private EduSubjectService eduSubjectService;

    /**
     * 判断一级标题
     *
     * @param eduSubjectService
     * @param name              名字
     * @return {@link EduSubject}
     */
    private EduSubject exitFirstSubject(EduSubjectService eduSubjectService, String name){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", "0");
        // 通过条件构造器，获取数据
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }

    /**
     * 判断二级标题
     *
     * @param eduSubjectService
     * @param name              名字
     * @param pid               pid
     * @return {@link EduSubject}
     */
    private EduSubject exitSecondSubject(EduSubjectService eduSubjectService, String name, String pid){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", pid);
        // 通过条件构造器，获取数据
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }

    /**
     * 一行一行读取(读完分类1，就读分类2)
     *
     * @param subjectPoJo     主题阿宝乔
     * @param analysisContext 分析上下文
     */
    @Override
    public void invoke(SubjectPoJo subjectPoJo, AnalysisContext analysisContext) {
        // 没有数据就抛异常
        if (subjectPoJo == null){
            throw new LjwException(20001, "文件内容为空");
        }

        // 保存数据
        // 保存一级分类
        String oneSubjectName = subjectPoJo.getOneSubjectName();
        // eduOneSubject是否存在
        EduSubject eduOneSubject = this.exitFirstSubject(eduSubjectService, oneSubjectName);
        // 不存在，进行添加
        if (eduOneSubject == null){
            eduOneSubject = new EduSubject();
            eduOneSubject.setParentId("0");
            eduOneSubject.setTitle(oneSubjectName);
            eduSubjectService.save(eduOneSubject);
        }

        // 读第2列
        // 获取第一列的信息，一级标题
        String oneSubjectId = eduOneSubject.getId();
        String twoSubjectName = subjectPoJo.getTwoSubjectName();
        EduSubject eduTwoSubject = this.exitSecondSubject(eduSubjectService, twoSubjectName, oneSubjectId);
        // 不存在，进行添加
        if (eduTwoSubject == null){
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setTitle(twoSubjectName);
            eduTwoSubject.setParentId(oneSubjectId);
            eduSubjectService.save(eduTwoSubject);
        }


    }

    // 读取完执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
