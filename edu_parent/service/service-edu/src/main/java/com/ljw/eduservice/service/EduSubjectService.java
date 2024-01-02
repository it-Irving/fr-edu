package com.ljw.eduservice.service;

import com.ljw.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljw.eduservice.entity.subject.FirstSubject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author luo
 * @since 2022-12-08
 */
@Service
public interface EduSubjectService extends IService<EduSubject> {


    /**
     * 添加课程分类
     *
     * @param file 文件
     */
    void addSubject(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 得到所有课程分类（一级、二级分类）
     */
    List<FirstSubject> getAllSubject();
}
