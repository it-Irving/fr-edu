package com.ljw.eduorder.controller;


import com.ljw.commonutils.JwtUtils;
import com.ljw.commonutils.R;
import com.ljw.eduorder.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author luo
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
@Api(description = "支付管理api")

public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    /**
     * 生成支付二维码、和订单号、课程id、订单金额
     *
     * @param orderNo 订单没有
     * @return {@link R}
     */
    @ApiOperation("生成支付二维码")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        // 返回支付二维码地址（前端生成二维码），和订单号、课程id、订单金额
        Map<String, Object> map = payLogService.createNative(orderNo);

        //payObj
        return R.ok().data("map", map);
    }

    /**
     * 查询支付状态
     *
     * @param orderNo 订单号
     * @return {@link R}
     */
    @ApiOperation("查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        // 1.根据订单号查询支付状态，out_trade_no中有我们的订单号
        Map<String, String> queryMap = payLogService.queryPayStatus(orderNo);

        System.out.println("======查询支付状态，微信返回的信息========" + queryMap);

        if(queryMap == null){
            return R.error().message("支付出错，查不到该订单");
        }

        // 获取订单状态
        if (queryMap.get("trade_state").equals("SUCCESS")){
            // 支付成功：更新支付状态、插入支付日志
            payLogService.updateOrderStatusInsertPayLog(queryMap);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中。。。").code(25000);
    }



}

