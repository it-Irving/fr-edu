package com.ljw.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.eduservice.entity.EduSubject;
import com.ljw.eduservice.entity.excel.SubjectPoJo;
import com.ljw.eduservice.entity.subject.FirstSubject;
import com.ljw.eduservice.entity.subject.SecondSubject;
import com.ljw.eduservice.mapper.EduSubjectMapper;
import com.ljw.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.listener.SubjectExcelListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author luo
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     *
     * @param file 文件
     */
    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 读取文件
            EasyExcel.read(inputStream, SubjectPoJo.class, new SubjectExcelListener(eduSubjectService))
                    .sheet()
                    .doRead();

        } catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * 得到所有课程分类（一级、二级分类）
     *
     * @return {@link List}<{@link FirstSubject}>
     */
    @Override
    public List<FirstSubject> getAllSubject() {

        // 查询所有1级分类
        QueryWrapper<EduSubject> firstWrapper = new QueryWrapper<>();
        firstWrapper.eq("parent_id", "0");
        List<EduSubject> firstEduSubjectList = baseMapper.selectList(firstWrapper);

        //查询所有2级分类
        QueryWrapper<EduSubject> secondWrapper = new QueryWrapper<>();
        secondWrapper.ne("parent_id", "0");
        List<EduSubject> secondEduSubjectList = baseMapper.selectList(secondWrapper);
        
        // 创建容器存储 课程分类信息
        List<FirstSubject> finalSubjectList = new ArrayList<>();

        // 外层遍历1级分类
        for (EduSubject firstEduSubject : firstEduSubjectList) {
            FirstSubject firstSubject = new FirstSubject();

            // eduSubject 转换 需要的FirstSubject
            BeanUtils.copyProperties(firstEduSubject, firstSubject);

            // 创建集合存放每个一级分类下的二级分类
            List<SecondSubject> finalSecondSubjects = new ArrayList<>();
            // 遍历出2级分类，放入1级分类
            for (EduSubject secondEduSubject : secondEduSubjectList){

                // 2级分类pid = 1级分类id
                if (secondEduSubject.getParentId().equals(firstEduSubject.getId())){
                    // 把2级分类 装入 1级分类
                    SecondSubject secondSubject = new SecondSubject();

                    BeanUtils.copyProperties(secondEduSubject, secondSubject);

                    finalSecondSubjects.add(secondSubject);
                }
            }
            // 1级分类下存所有 2级分类
            firstSubject.setChildren(finalSecondSubjects);
            // 当前1级分类
            finalSubjectList.add(firstSubject);
        }

        return finalSubjectList;
    }


}
