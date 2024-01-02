package com.ljw.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统前台 课程 根据一级分类、二级分类、销量、最新时间、价格这些条件来展示课程,所以需要创建vo类来封装这些条件数据
 *
 * @author Luo
 */
@Data
public class CourseFrontVo {
    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;
}


