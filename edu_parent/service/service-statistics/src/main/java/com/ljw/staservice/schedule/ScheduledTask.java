package com.ljw.staservice.schedule;

import com.ljw.staservice.service.StatisticsDailyService;
import com.ljw.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 *
 * @author Luo
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 定时任务
     * 在每天的凌晨一点查询前一天的统计数据并插入到统计表
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.countRegisterByDay(day);
    }


//    每5秒执行一次
//    @Scheduled(cron = "0/5 * * * * ?")
//    public static void task1(){
//        System.out.println("执行。。。");
//    }
}
