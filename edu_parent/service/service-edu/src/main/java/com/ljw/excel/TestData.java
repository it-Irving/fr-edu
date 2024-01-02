package com.ljw.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 测试对象( 写 )
 *
 * @author Luo
 */
@Data
public class TestData {
    // 表头
    @ExcelProperty("学号")
    private Integer stuNo;

    @ExcelProperty("姓名")
    private String stuName;
}
