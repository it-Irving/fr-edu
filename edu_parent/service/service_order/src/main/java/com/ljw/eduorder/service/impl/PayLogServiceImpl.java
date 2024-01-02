package com.ljw.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.ljw.eduorder.entity.Order;
import com.ljw.eduorder.entity.PayLog;
import com.ljw.eduorder.mapper.PayLogMapper;
import com.ljw.eduorder.service.OrderService;
import com.ljw.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.eduorder.utils.HttpClient;
import com.ljw.servicebase.exceptionHandler.LjwException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author luo
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    /**
     * 生成支付二维码、和订单号、课程id、订单金额
     *
     * @param orderNo 订单编号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            // 1.订单信息 by orderNo
            QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
            orderWrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(orderWrapper);

            // 2.生成二维码需要的参数（使用map集合）
            Map m = new HashMap();
            m.put("appid", "wx74862e0dfcf69954"); //支付id
            m.put("mch_id", "1558950191"); //商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr()); //生成随机唯一字符串,使得生成的每个二维码都不同
            m.put("body", order.getCourseTitle()); //生成的二维码显示什么名字
            m.put("out_trade_no", orderNo); //二维码的唯一标识,我们的订单号都是唯一的,所以一般赋值订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+""); //扫码的价格(元角分)
            m.put("spbill_create_ip", "127.0.0.1"); //支付服务的ip地址(域名也行),我们这里是本地,所以赋值127.0.0.1
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");//支付后回调的地址,老师说目前用不到
            m.put("trade_type", "NATIVE"); //支付类型,NATIVE就表示根据价格生成一个支付二维码

            // 3.发送httpclient请求，请求参数是xml格式
            //3.1设置请求地址(请求地址是微信给的固定的)
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //3.2设置请求参数(xml格式)
            // generateSignedXml方法作用:根据商户key对map集合做加密并将加密后的map集合转为xml格式
            // setXmlParam方法作用:将得到的xml格式字符串设置为请求参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb")); //根据商户key进行加密
            //3.3因为请求的地址是https的,默认无法请求,有了下面这行代码就可以请求了
            client.setHttps(true);
            //3.4执行发送请求(发送xml格式参数的请求)
            client.post();

             // 4.获取请求返回数据
            //4.1获取数据
            String xml = client.getContent();
            //4.2将xml数据转为map集合
            //发送请求后微信返回的内容是xml格式字符串,为了方便前端取值,我们把xml格式转换为map集合,把这个map集合返回给控制层
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //System.out.println(resultMap);
            //5.最终返回数据的封装
            //但此时我们需要的其它数据并没有在resultMap集合中(如:订单号、课程id...),需要我们手动封装
            Map map = new HashMap<String, Object>();
            map.put("out_trade_no", orderNo); //订单号
            map.put("course_id", order.getCourseId()); //课程id
            map.put("total_fee", order.getTotalFee()); //订单金额

            map.put("result_code", resultMap.get("result_code")); //二维码操作状态码
            map.put("code_url", resultMap.get("code_url")); //二维码地址

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LjwException(20001, "生成支付二维码失败");
        }

    }

    // 查询支付状态
    /**
     * 查询支付状态
     * 根据订单号查询支付状态，out_trade_no中有我们的订单号
     *
     * @param orderNo
     * @return {@link Map}<{@link String}, {@link String}>
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1.封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo); //二维码的唯一标识,我们的订单号都是唯一的,所以一般赋值订单号
            m.put("nonce_str", WXPayUtil.generateNonceStr()); //生成随机唯一字符串,使得生成的每个二维码都不同

            //2.发送httpclient请求
            HttpClient client = new HttpClient(
                    "https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(
                    m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3.获取微信返回的数据,并将xml格式数据转为map集合
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 已经支付成功
     * 更新订单状态插入日志
     *
     * @param queryMap
     */
    @Override
    public void updateOrderStatusInsertPayLog(Map<String, String> queryMap) {
        // 1.从微信查询状态返回值中，获取订单编号
        String orderNo = queryMap.get("out_trade_no");

        // 2.查询订单信息
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(orderWrapper);

        // 3.修改订单表的status支付状态（支付为1）
        // 订单已经支付过
        if (order.getStatus().intValue() == 1){
            return;
        }
        //未支付过,修改状态
        order.setStatus(1);
        orderService.updateById(order);

        // 添加一条支付日志
        PayLog payLog=new PayLog();
        payLog.setOrderNo(order.getOrderNo()); //订单号
        payLog.setPayTime(new Date()); //支付时间
        payLog.setPayType(1); //支付类型(1代表微信)
        payLog.setTotalFee(order.getTotalFee()); //支付金额(分)
        payLog.setTradeState(queryMap.get("trade_state")); //支付状态
        payLog.setTransactionId(queryMap.get("transaction_id")); //交易流水号
        payLog.setAttr(JSONObject.toJSONString(queryMap)); //其它属性
        baseMapper.insert(payLog);//插入到支付日志记录表

    }
}
