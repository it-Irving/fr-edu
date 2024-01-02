package com.ljw.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 读取excel数据的类
 *
 * @author Luo
 */
@Data
public class SubjectPoJo {

    // 第一列
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
