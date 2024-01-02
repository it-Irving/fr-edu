package com.ljw.eduservice.entity.vo;

import lombok.Data;

/**
 * 课程发布
 *
 * @author Luo
 * 从edu_course表查询课程名称、
 * 课程价格;从edu_course_description表查询课程简介;
 * 从edu_subject表查询课程所属分类;
 * 从edu_teacher表查询课程所属讲师
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
