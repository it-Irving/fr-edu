package com.ljw.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 讲师多条件查询
 *
 * @author Luo
 */
@ApiModel(value = "讲师edu_teacher查询", description = "讲师查询对象封装")
@Data
public class TeacherQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师名称")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private String level;

    @ApiModelProperty(value = "查询开始时间", example = "2022-01-01 11:11:11")
    //使用的是String类型，前端传过来的数据无需进行类型转换
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2022-12-01 11:11:11")
    private String end;
}
