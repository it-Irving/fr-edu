package com.ljw.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * excel侦听器
 *
 * @author Luo
 */
public class ExcelListener extends AnalysisEventListener<TestData> {

    //创建list集合封装最终的数据
    List<TestData> list = new ArrayList<>();

    //一行一行去读取excle内容
    @Override
    public void invoke(TestData testData, AnalysisContext analysisContext) {
        System.out.println("******"+ testData);
        list.add(testData);
    }

    // 读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息======"+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
