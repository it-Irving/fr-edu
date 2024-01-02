package com.ljw.eduorder.client.impl;

import com.ljw.commonutils.orderVo.CourseWebVoOrder;
import com.ljw.eduorder.client.EduClient;
import org.springframework.stereotype.Component;

/**
 * edu降低装客户端文件
 *
 * @author Luo
 */
@Component

public class EduClientFileDegradeFeignClient implements EduClient {
    /**
     * 通过id获取课程，远程调用
     *
     * @param courseId 进程id
     * @return {@link CourseWebVoOrder}
     */
    @Override
    public CourseWebVoOrder getEduCourseInfoOrder(String courseId) {
        System.out.println("远程调用 getEduCourseInfoOrder失败");
        return null;
    }
}
