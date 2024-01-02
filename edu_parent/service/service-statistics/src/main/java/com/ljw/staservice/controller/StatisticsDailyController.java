package com.ljw.staservice.controller;


import com.ljw.commonutils.R;
import com.ljw.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author luo
 * @since 2023-01-24
 */
@RestController
@RequestMapping("/staservice/statistics-daily")
@CrossOrigin
@Api(description = "网站统计日数据管理api")

public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation(value = "统计某一天的注册人数,添加到统计表中")
    @GetMapping("/countRegisterByDay/{day}")
    public R countRegisterByDay(@PathVariable String day){

        statisticsDailyService.countRegisterByDay(day);

        return R.ok();
    }

    @ApiOperation(value = "根据类型显示对应日期的数据")
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end
    ){

        Map<String, Object> map = statisticsDailyService.showData(type, begin, end);

        return R.ok().data(map);
    }
}

