package com.ljw.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.commonutils.R;
import com.ljw.staservice.client.UcenterClient;
import com.ljw.staservice.entity.StatisticsDaily;
import com.ljw.staservice.mapper.StatisticsDailyMapper;
import com.ljw.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author luo
 * @since 2023-01-24
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 统计某一天的注册人数,添加到统计表中
     *
     * @param day 一天
     */
    @Override
    public void countRegisterByDay(String day) {
        // 如果记录表有记录，删除再添加
        QueryWrapper<StatisticsDaily> statisticsDailyWrapper = new QueryWrapper<>();
        statisticsDailyWrapper.eq("date_calculated",day);
        baseMapper.delete(statisticsDailyWrapper);

        // 获取注册人数
        R r = ucenterClient.countRegister(day);
        Integer register_num = (Integer) r.getData().get("register_num");

        // 封装数据
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day); // 统计哪天的记录
        statisticsDaily.setRegisterNum(register_num);   // 注册人数

        // 生成数据数，后期自己完善
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(statisticsDaily);
    }

    /**
     * 根据类型显示对应日期的数据
     *
     * @param type  类型
     * @param begin 开始
     * @param end   结束
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        // 条件封装
        QueryWrapper<StatisticsDaily> statisticsDailyWrapper = new QueryWrapper<>();
        statisticsDailyWrapper.between("date_calculated", begin, end);
        // 指定2个列
        statisticsDailyWrapper.select("date_calculated", type);
        List<StatisticsDaily> statisticsDailyList = baseMapper.selectList(statisticsDailyWrapper);

        // 将日期、数量封装到集合中
        List<String> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (StatisticsDaily statisticsDaily : statisticsDailyList) {
            // 封装日期
            dayList.add(statisticsDaily.getDateCalculated());

            switch(type){
                case "login_num":
                    countList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num":
                    countList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    countList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    countList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("date_calculated", dayList);
        map.put("numDataList", countList);

        return map;
    }


}
