package com.ljw.staservice.service;

import com.ljw.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author luo
 * @since 2023-01-24
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 统计某一天的注册人数,添加到统计表中
     *
     * @param day 一天
     */
    void countRegisterByDay(String day);

    /**
     * 根据类型显示对应日期的数据
     *
     * @param type  类型
     * @param begin 开始
     * @param end   结束
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> showData(String type, String begin, String end);
}
