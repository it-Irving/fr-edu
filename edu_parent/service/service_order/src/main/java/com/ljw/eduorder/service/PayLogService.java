package com.ljw.eduorder.service;

import com.ljw.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author luo
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成支付二维码、和订单号、课程id、订单金额
     *
     * @param orderNo 订单编号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> createNative(String orderNo);

    // 查询支付状态
    /**
     * 查询支付状态
     * 根据订单号查询支付状态，out_trade_no中有我们的订单号
     *
     * @param orderNo 订单没有
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, String> queryPayStatus(String orderNo);


    /**
     * 更新订单状态插入日志
     *
     * @param queryMap
     */
    void updateOrderStatusInsertPayLog(Map<String, String> queryMap);
}
