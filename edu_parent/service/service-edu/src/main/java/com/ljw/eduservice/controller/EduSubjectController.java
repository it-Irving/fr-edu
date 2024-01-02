package com.ljw.eduservice.controller;


import com.ljw.commonutils.R;
import com.ljw.eduservice.entity.subject.FirstSubject;
import com.ljw.eduservice.service.EduSubjectService;
import com.ljw.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程分类
 * </p>
 *
 * @author luo
 */
@Api(description = "课程分类api")
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin    // 跨域
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @ApiOperation("添加课程分类信息")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.addSubject(file, eduSubjectService);
        return R.ok();
    }

    @ApiOperation("查询课程分类（1级、2级）")
    @GetMapping("/getAllSubject")
    private R getAllSubject(){
        List<FirstSubject> firstSubjectList = eduSubjectService.getAllSubject();

        return R.ok().data("list", firstSubjectList);
    }
}

