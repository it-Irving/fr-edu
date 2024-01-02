package com.ljw.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 测试读
 *
 * @author Luo
 */
public class TestData02 {
    // 第一列
    @ExcelProperty(value = "学号", index = 0)
    private Integer strNo;

    @ExcelProperty(value = "姓名", index = 1)
    private String strName;
}
