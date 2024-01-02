package com.ljw.eduorder.service.impl;

import com.ljw.commonutils.orderVo.CourseWebVoOrder;
import com.ljw.commonutils.vo.UcenterMemberVo;
import com.ljw.eduorder.client.EduClient;
import com.ljw.eduorder.client.UcenterClient;
import com.ljw.eduorder.entity.Order;
import com.ljw.eduorder.mapper.OrderMapper;
import com.ljw.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author luo
 * @since 2023-01-20
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;


    /**
     * 创建订单
     *
     * @param courseId           课程id
     * @param memberIdByJwtToken 成员由jwt id令牌
     * @return {@link String}
     */
    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        // 查询课程信息(远程调用)
        CourseWebVoOrder courseWebVoOrder = eduClient.getEduCourseInfoOrder(courseId);

        // 查询用户信息（远程调用）
//        UcenterMemberVo ucenterMemberVo = ucenterClient.getMemberInfoById(memberIdByJwtToken);
        UcenterMemberVo ucenterMemberVo = ucenterClient.getMemberInfoById(memberIdByJwtToken);

        //创建订单对象
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); //订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseWebVoOrder.getTitle()); //课程名称
        order.setCourseCover(courseWebVoOrder.getCover()); //课程封面
        order.setTeacherName(courseWebVoOrder.getTeacherName()); //课程所属讲师
        order.setTotalFee(courseWebVoOrder.getPrice()); //订单金额(也就是课程价格)
        order.setMemberId(memberIdByJwtToken); //用户id
        order.setMobile(ucenterMemberVo.getMobile());//用户手机号
        order.setNickname(ucenterMemberVo.getNickname()); //用户昵称
        order.setStatus(0); //支付状态(0:未支付 1:已支付)
        order.setPayType(1); //支付类型(1:微信 2:支付宝)

        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
