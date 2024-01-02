package com.ljw.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.commonutils.JwtUtils;
import com.ljw.commonutils.R;
import com.ljw.eduorder.entity.Order;
import com.ljw.eduorder.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author luo
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
@Api(description = "订单管理api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 1.保存订单（生成订单）
     *
     * @param request  请求
     * @param courseId 进程id
     * @return {@link R}
     */
    @ApiOperation("生成/保存订单")
    @PostMapping("/saveOrder/{courseId}")
    public R saveOrder(HttpServletRequest request, @PathVariable String courseId){

        // 在请求头token中获取会员id
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);

        // 判断用户是否登录
        if(StringUtils.isEmpty(memberIdByJwtToken)){
            return R.ok().message("请先登录。。。。。。。。。。。。").code(28004);
        }

        //创建订单，返回订单号
        String orderId = orderService.createOrder(courseId, memberIdByJwtToken);

        return R.ok().data("orderId", orderId);
    }


    /**
     * 2.通过订单号获取订单信息
     *
     * @param orderNo 订单号
     * @return {@link R}
     */
    @ApiOperation("获取订单信息byId")
    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<Order> orderWrapper = new QueryWrapper<Order>();
        orderWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(orderWrapper);
        return R.ok().data("order", order);
    }

    /**
     * 判断课程是否支付
     * 是否购买课程
     *
     * @param courseId 课程id
     * @param memberId 会员id
     * @return {@link Boolean}
     */
    @ApiOperation("判断课程是否支付")
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId){
        QueryWrapper<Order> orderWrapper = new QueryWrapper<Order>();
        orderWrapper.eq("course_id", courseId);
        orderWrapper.eq("member_id", memberId);
        orderWrapper.eq("status", 1);

        int count = orderService.count(orderWrapper);

        // 1表示已支付
        return count > 0;
    }
}

